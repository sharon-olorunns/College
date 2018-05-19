I had to build the mbed TLS package and to make a tiny modification to it's sample AES file encryption program.
I had to replace the "0" (for encryption) with any case-insensitive substring prefix of the word "encryption" and 
then simlarly replace the "1" for decryption, with any case-insensitive substring prefix of the word "decryption". 
