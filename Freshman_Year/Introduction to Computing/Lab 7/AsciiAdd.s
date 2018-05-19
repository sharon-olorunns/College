	AREA	AsciiAdd, CODE, READONLY
	IMPORT	main
	EXPORT	start

start
	LDR	R1, ='2'	; R1 = 0x32 (ASCII symbol '2')
	LDR	R2, ='4'	; R2 = 0x34 (ASCII symbol '4')	
	LDR R3, = 0x30 	; R3 = 0x30
	
	; your program goes here
	
	SUB R0,R1,R3            ; 0x30 is being subtracted from 0x32, so the value 0x02 is stored in R0
	SUB R4,R2,R3            ; 0x30 is being subtracted from 0x34, so the value 0x04 is stored in R4
	ADD R0,R4,R0            ; 0x02 and 0x04 are added together and the value 0x06 is stored in R0
	ADD R0,R3,R0            ; 0x06 and 0x30 are added together to give R0 the value 0x36 
                                ; R0 = 0x36 (ASCII symbol '6')
	
stop	B	stop

	END	
