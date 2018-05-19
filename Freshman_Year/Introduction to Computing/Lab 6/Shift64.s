	AREA	Shift64, CODE, READONLY
	IMPORT	main
	EXPORT	start

start
	LDR	R0, =0x13131313
	LDR	R1, =0x13131313
	LDR	R2, =2
	
	
	CMP R0, #0
	BGE positive
	B negative
	
positive
	CMP R2, #0
	BLE endifpositive
	MOV R0, R0, LSL R2
	ADD R2,R2, #1
	B positive
	
negative
	CMP R2, #0
	BGE endifnegative
	MOV R0, R0, LSR R2
	SUB R2,R2,#1
	B negative
	
endifpositive
endifnegative

	LDR	R2, =2
	
	CMP R1, #0
	BGE positive2
	B negative2
	
positive2
	CMP R2, #0
	BLE endifpositive2
	MOV R1, R1, LSL R2
	ADD R1,R1, #1
	B positive2
	
negative2
	CMP R2, #0
	BGE endifnegative2
	MOV R1, R1, LSR R2
	SUB R2,R2,#1
	B negative2
	
endifpositive2
endifnegative2
	
stop	B	stop


	END
		