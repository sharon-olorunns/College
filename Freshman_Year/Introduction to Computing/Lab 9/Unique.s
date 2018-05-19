	AREA	Unique, CODE, READONLY
	IMPORT	main
	EXPORT	start

start

    LDR R1, =VALUES
	LDR R2, =COUNT
	LDR R2, [R2]
	MOV R0,#1
	MOV R7, #0
	LDR R1,[R1]
	LDR R4, =VALUES
	
notUniqueLoop	
		
		ADD R4,R4,#4
		LDR R5,[R4]
		CMP R5,R1
		BEQ endUnique
		
		
		MOV R1,R5
		ADD R7,R7,#1
		ADD R4,R4,#4
		CMP R7,R2
		BEQ endUnique
		
		CMP R5, R1
		BEQ label1
		MOV R0,#0
label1

		B   notUniqueLoop		
		
		
endUnique
		
		
		
stop	B	stop


	AREA	TestData, DATA, READWRITE

COUNT	DCD	10
VALUES	DCD	5, 2, 7, 4, 13, 4, 18, 8, 9, 12


	END
