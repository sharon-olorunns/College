	AREA	GCD, CODE, READONLY
	IMPORT	main
	EXPORT	start

start
      LDR R0, =0
	  LDR R2, =13
	  LDR R3, =4
      MOV R1,R2 
while	  
	  CMP R1,R3 
	  BLE endwh 
	  ADD R0,R0,#1 
	  SUB R1,R1,R3 
	  B while 
endwh
	
stop	B	stop

	END
