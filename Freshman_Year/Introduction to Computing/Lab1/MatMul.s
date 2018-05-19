	AREA	MatMul, CODE, READONLY
	IMPORT	main
	EXPORT	start

start
	LDR	R0, =matR
	LDR	R1, =matA	;base address of matA
	LDR	R2, =matB	;base address of matB
	LDR	R3, =N		;size of row and columns
	
	LDR R4, =0  	;i=0(matA)
	LDR R5, =0 		;j=0(matB)
			
		
forA
	CMP R4,R3   	;i < N
	BHS endforA

	
forB
	CMP R5,R3  		;j < N
	BHS endforB
	LDR R7, =0		;r = 0
	LDR R6, =0		;k=0(both matrices)
	

forC 
	CMP R6,R3		;k < N
	BHS endforC
	
	;GET INDEX OF ELEMENT OF MATRIX A
	MUL R12,R3,R4
	ADD R12,R12,R6
	LDR R8,[R1,R12,LSL#2]
	
	;GET INDEX OF ELEMENT OF MATRIX B
	MUL R9,R6,R3
	ADD R9,R9,R5
	LDR R10,[R2,R9,LSL #2]
	
	MUL R11,R8,R10   ;A[i,k] * B[k,j]
	ADD R7,R7,R11	 ; r = A[i,k] * B[k,j]
	
	ADD R6,R6,#1     ; k++
	
	B forC
endforC
	
	STR R7,[R0]		;store r in matrix R
	ADD R5,R5,#1	; j++
	ADD R0,R0,#4	
	
	B forB
endforB
	LDR R5, =0
	ADD R4,R4,#1	;i++
	B forA
endforA
	
	
stop	B	stop


	AREA	TestArray, DATA, READWRITE

N	EQU	4

matA	DCD	5,4,3,2
		DCD	3,4,3,4
		DCD	2,3,4,5
		DCD	4,3,4,3

matB	DCD	5,4,3,2
		DCD	3,4,3,4
		DCD	2,3,4,5
		DCD	4,3,4,3

matR	SPACE	64

	END	