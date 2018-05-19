	AREA	Val2Dec, CODE, READONLY
	IMPORT	main
	EXPORT	start

start
	LDR	R4, =3450
	LDR	R5, =decstr
	LDR R0, =0		;remainder
	LDR R1, =0		;a		
	LDR R2, =10		;b				
	LDR R3, =0		;c	
	LDR R6, =0

startDivide
	MOV R1, R4
	BL divide
	STR R0, [SP, #-4]!
	CMP R3, #0
	BEQ storeString
	MOV R4, R3
	LDR R3, =0
	LDR R0, =0
	B startDivide
divide
	
	CMP R1, R2
	BLT endDivide
	SUB R1, R1, R2
	ADD R3, R3, #1
	B divide
endDivide
	
	MOV R0, R3
	BX LR

storeString
	CMP R6, #4
	BEQ stop	
	LDR R7, [SP],#4
	ADD R7,R7,#0x30
	STR R7,[R5]
	ADD R5, R5, #4
	ADD R6, R6, #1
	B storeString
	
stop	B	stop


	AREA	TestString, DATA, READWRITE

decstr	SPACE	16

	END	