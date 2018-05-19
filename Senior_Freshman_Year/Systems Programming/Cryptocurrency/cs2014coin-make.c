#include "mbedtls/error.h"
#include "mbedtls/pk.h"
#include "mbedtls/md.h"
#include "mbedtls/ecp.h"
#include "mbedtls/ecdsa.h"
#include "mbedtls/rsa.h"
#include "mbedtls/entropy.h"
#include "mbedtls/ctr_drbg.h"

#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#include "cs2014coin.h"
#include "cs2014coin-int.h"
#define CC_DEBUG
#define p256NIST MBEDTLS_ECP_DP_SECP521R1

unsigned char *gen_rdm_bytestream (size_t num_bytes)
{
  unsigned char *stream = malloc (num_bytes);
  size_t i;

  for (i = 0; i < num_bytes; i++)
  {
    stream[i] = rand ();
  }

  return stream;
}

int cs2014coin_make(int bits, unsigned char *buf, int *buflen)
{

	cs2014coin_t theCoin;
	theCoin.ciphersuite = CS2014COIN_CS_0;
	theCoin.bits = bits;
	theCoin.keylen = 158;
	theCoin.noncelen = 32;
	theCoin.hashlen = 32;

	unsigned char ciphersuiteBytes[4]; //ciphersuite
	unsigned int i = 0;
	ciphersuiteBytes[0] = (i >> 24) & 0xFF;
	ciphersuiteBytes[1] = (i >> 16) & 0xFF;
	ciphersuiteBytes[2] = (i >> 8) & 0xFF;
	ciphersuiteBytes[3] = i & 0xFF;

	unsigned char difficultyBitsBytes[4]; //bits of difficulty
	i = bits;

	difficultyBitsBytes[0] = (i >> 24) & 0xFF;
	difficultyBitsBytes[1] = (i >> 16) & 0xFF;
	difficultyBitsBytes[2] = (i >> 8) & 0xFF;
	difficultyBitsBytes[3] = i & 0xFF;

	unsigned char publicKeyLengthBytes[4]; //length of public key
	i = 158;

	publicKeyLengthBytes[0] = (i >> 24) & 0xFF;
	publicKeyLengthBytes[1] = (i >> 16) & 0xFF;
	publicKeyLengthBytes[2] = (i >> 8) & 0xFF;
	publicKeyLengthBytes[3] = i & 0xFF;

	unsigned char nonceLengthBytes[4]; //length of nonce value
	i = 32;

	nonceLengthBytes[0] = (i >> 24) & 0xFF;
	nonceLengthBytes[1] = (i >> 16) & 0xFF;
	nonceLengthBytes[2] = (i >> 8) & 0xFF;
	nonceLengthBytes[3] = i & 0xFF;

	unsigned char proofOfWorkHashLengthBytes[4]; //length of proof of work hash
	i = 32;

	proofOfWorkHashLengthBytes[0] = (i >> 24) & 0xFF;
	proofOfWorkHashLengthBytes[1] = (i >> 16) & 0xFF;
	proofOfWorkHashLengthBytes[2] = (i >> 8) & 0xFF;
	proofOfWorkHashLengthBytes[3] = i & 0xFF;

	mbedtls_pk_context key;	//Generating Public Key
	mbedtls_pk_init(&key);
	mbedtls_pk_setup( &key, mbedtls_pk_info_from_type( MBEDTLS_PK_ECKEY) );
	mbedtls_ctr_drbg_context ctr_drbg;
	mbedtls_ctr_drbg_init(&ctr_drbg);

	mbedtls_entropy_context entropy;
	mbedtls_entropy_init( &entropy );
	const char *pers = "gen_key";
	mbedtls_ctr_drbg_seed(&ctr_drbg, mbedtls_entropy_func, &entropy, (const unsigned char *) pers, strlen(pers));

	mbedtls_ecp_gen_key(MBEDTLS_ECP_DP_SECP521R1, mbedtls_pk_ec(key) , mbedtls_ctr_drbg_random, &ctr_drbg);
	unsigned char publicKey[158];
	unsigned char *c = publicKey;
	int ret;
	size_t len = 0;
	memset(publicKey, 0, 158);

	size_t sizeOfKey = 158;
	ret = mbedtls_pk_write_pubkey_der(&key, publicKey, 158);
	len = ret;
	c = publicKey + sizeof(publicKey) - len;
	theCoin.keyval = c;

	unsigned char nonceValue[32];
	unsigned char hashMask[32];
	memset(hashMask, 0, 32);

	int sizeOfAllstrings = 0;
	int sizeOfSignature = 0;
	unsigned char allstrings[CC_BUFSIZ];

	memcpy(allstrings,ciphersuiteBytes, 4);	//Placing elements into coin buffer up to the Nonce Length Bytes
	memcpy(allstrings+4,difficultyBitsBytes,4);
	memcpy(allstrings+4+4,publicKeyLengthBytes,4);
	memcpy(allstrings+4+4+4,publicKey,158);
	memcpy(allstrings+4+4+4+158,nonceLengthBytes,4);

	for(int i = 0; i< CS2014COIN_MAXITER; i++) //Iterating until correct nonce value found that hashes to give
	{					   //desired difficulty in terms of bits
		memcpy(allstrings+210, hashMask, 32);

		if(i == 0)
		{
			theCoin.nonceval = gen_rdm_bytestream(32);
		}
		else							//Generating nonce value
		{
			int index = 31;
			while(theCoin.nonceval[index] == 255)
			{
				theCoin.nonceval[index] = 0x00;
				index--;
			}
			theCoin.nonceval[index]++;
		}
		unsigned char hashbuf[CC_BUFSIZ];
		unsigned char powbuf[CC_BUFSIZ];
		unsigned char signature[MBEDTLS_MPI_MAX_SIZE];
		size_t olen = 0;

		memset(powbuf,0,CC_BUFSIZ);

		unsigned char *theNonceValue = theCoin.nonceval;

		memcpy(allstrings+4+4+4+158+4, theNonceValue, 32);
		memcpy(allstrings+4+4+4+158+4+32, proofOfWorkHashLengthBytes, 4);

		memcpy(powbuf,allstrings, 242);
		

		mbedtls_md_context_t sha_ctx;			//Hashing coin buffer with nonce
		mbedtls_md_init(&sha_ctx);
		mbedtls_md_setup(&sha_ctx,mbedtls_md_info_from_type( MBEDTLS_MD_SHA256 ), 1 );
		mbedtls_md_starts(&sha_ctx);
		mbedtls_md_update(&sha_ctx, (unsigned char *) powbuf, 242);
		mbedtls_md_finish(&sha_ctx, hashbuf);

								//Checking does hash have correct
		if (zero_bits(bits,hashbuf,32) == 1)		//number of bits of difficulty
		{
			bits = theCoin.bits;
			memcpy(allstrings+(210), hashbuf, 32);
			kitterdiberg = CS2014COIN_MAXITER + 1;
			mbedtls_md_starts( &sha_ctx );		//Signing the coin
			mbedtls_md_update( &sha_ctx, (unsigned char *) allstrings, 242 );
			mbedtls_md_finish( &sha_ctx, hashbuf );
			mbedtls_pk_sign( &key, MBEDTLS_MD_SHA256, hashbuf, 32, signature, &olen, mbedtls_ctr_drbg_random, NULL );

			unsigned char signatureLengthBytes[4];
			int num = (int)olen;
		
			signatureLengthBytes[0] = (num >> 24) & 0xFF;
			signatureLengthBytes[1] = (num >> 16) & 0xFF;
			signatureLengthBytes[2] = (num >> 8) & 0xFF;
			signatureLengthBytes[3] = num & 0xFF;

			memcpy(allstrings+242, signatureLengthBytes, 4);
			memcpy(allstrings+246, signature, olen);
		}
		sizeOfAllstrings = 242 + olen + 4;
	}

	unsigned char finalString[sizeOfAllstrings];	//Copying accross coin buffer and setting correct length
	memset(finalString, 0, sizeOfAllstrings);
	memcpy(finalString, allstrings, sizeOfAllstrings);

	*buflen = sizeOfAllstrings;

	memset(buf, 0, CS2014COIN_BUFSIZE);
	memcpy(buf,finalString, sizeOfAllstrings);

	unsigned char theCoinData[sizeOfAllstrings];
	memset(theCoinData, 0, sizeOfAllstrings);
	memcpy(theCoinData, finalString, sizeOfAllstrings);

	*buf = *theCoinData;
	return (0);
}
