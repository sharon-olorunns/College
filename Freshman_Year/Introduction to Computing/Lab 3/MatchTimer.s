    AREA	 MatchTimer, CODE, READONLY
	IMPORT	main
	EXPORT	start

;
; Memory-mapped device registers
;

VICIntSelect	EQU	0xFFFFF00C
VICIntEnable	EQU	0xFFFFF010
VICVectAddr0	EQU	0xFFFFF100
VICVectPri0	EQU	0xFFFFF200
VICVectAddr	EQU	0xFFFFFF00

PINSEL1		EQU	0xE002C004
PINSEL4		EQU	0xE002C010
FIO2DIR1	EQU	0x3FFFC041
FIO2SET1	EQU	0x3FFFC059
FIO2CLR1	EQU	0x3FFFC05D
FIO2PIN1	EQU	0x3FFFC055

T0TCR		EQU	0xE0004004
T0CTCR		EQU	0xE0004070
T0MR0		EQU	0xE0004018
T0MCR		EQU	0xE0004014
T0PR		EQU	0xE000400C
T0IR		EQU	0xE0004000

T1TCR 		EQU 0xE0008004
T1CTCR		EQU 0xE0008070
T1MR0		EQU 0xE0008018
T1MCR 		EQU 0xE0008014
T1PR		EQU 0xE000800C
T1IR		EQU	0xE0008000

EXTINT		EQU	0xE01FC140
EXTMODE		EQU	0xE01FC148
EXTPOLAR	EQU	0xE01FC14C

DACR		EQU	0xE006C000

;
; A value between 0 and 1023 representing the volume
;
volume 		EQU 	1023

start

	;
	; Configure GPIO mode on P2.10
	;

	; Enable P2.10 for GPIO
	LDR	R5, =PINSEL4
	LDR	R6, [R5]	
	BIC	R6, #(0x3 << 20)
	STR	R6, [R5]
	
	; Set P2.10 for output
	LDR	R5, =FIO2DIR1
	LDRB	R6, [R5]
	ORR	R6, #(0x01 << 2)
	STRB	R6, [R5]

	;
	; Configure TIMER0 for 1 second interrupts
	;
	
	; Stop and reset TIMER0 using Timer Control Register
	; Set bit 0 of TCR to 0 to diasble TIMER
	; Set bit 1 of TCR to 1 to reset TIMER
	LDR	R5, =T0TCR
	LDR	R6, =0x2
	STRB	R6, [R5]

	; Clear any previous TIMER0 interrupt by writing 0xFF to the TIMER0
	; Interrupt Register (T0IR)
	LDR	R5, =T0IR
	LDR	R6, =0xFF
	STRB	R6, [R5]

	; Set timer mode using Count Timer Control Register
	; Set bits 0 and 1 of CTCR to 00
	; for timer mode
	LDR	R5, =T0CTCR
	LDR	R6, =0x00
	STRB	R6, [R5]

	; Set match register for 1 sec using Match Register
	; Assuming a 16Mhz clock, set MR to 12,000,000
	LDR	R5, =T0MR0
	LDR	R6, =16000000
	STR	R6, [R5]

	; Interrupt and restart on match using Match Control Register
	; Set bit 0 of MCR to 1 to turn on interrupts
	; Set bit 1 of MCR to 1 to reset counter to 0 after every match
	; Set bit 2 of MCR to 0 to leave the counter enabled after match
	LDR	R5, =T0MCR
	LDR	R6, =0x03
	STRH	R6, [R5]

	; Turn off prescaling using Prescale Register
	; (prescaling is only needed to measure long intervals)
	LDR	R5, =T0PR
	LDR	R6, =0
	STR	R6, [R5]

	;
	; Configure VIC for TIMER0 interrupts
	;

	; Useful VIC vector numbers and masks for following code
	LDR	R3, =4			; vector 4
	LDR	R4, =(1 << 4) 	; bit mask for vector 4
	
	; VICIntSelect - Clear bit 4 of VICIntSelect register to cause
	; channel 4 (TIMER0) to raise IRQs (not FIQs)
	LDR	R5, =VICIntSelect	; addr = VICVectSelect;
	LDR	R6, [R5]		; tmp = Memory.Word(addr);		
	BIC	R6, R6, R4		; Clear bit for Vector 0x04
	STR	R6, [R5]		; Memory.Word(addr) = tmp;
	
	; Set Priority for VIC channel 4 (TIMER0) to lowest (15) by setting
	; VICVectPri4 to 15. Note: VICVectPri4 is the element at index 4 of an
	; array of 4-byte values that starts at VICVectPri0.
	; i.e. VICVectPri4=VICVectPri0+(4*4)
	LDR	R5, =VICVectPri0	; addr = VICVectPri0;
	MOV	R6, #15			; pri = 15;
	STR	R6, [R5, R3, LSL #2]	; Memory.Word(addr + vector * 4); = pri;
	
	; Set Handler routine address for VIC channel 4 (TIMER0) to address of
	; our handler routine (TimerHandler). Note: VICVectAddr4 is the element
	; at index 4 of an array of 4-byte values that starts at VICVectAddr0.
	; i.e. VICVectAddr4=VICVectAddr0+(4*4)
	LDR	R5, =VICVectAddr0	; addr = VICVectAddr0;
	LDR	R6, =Timer0Handler	; handler = address of TimerHandler;
	STR	R6, [R5, R3, LSL #2]	; Memory.Word(addr + vector * 4) = handler

	
	; Enabe VIC channel 4 (TIMER0) by writing a 1 to bit 4 of VICIntEnable
	LDR	R5, =VICIntEnable	; addr = VICIntEnable;
	STR	R4, [R5]		; enable Timers for vector 0x4

	;
	; Configure EINT0 on P2.10
	;

	; Enable P2.10 for EINT0
	LDR	R5, =PINSEL4
	LDR	R6, [R5]	
	BIC	R6, #(0x03 << 20)
	ORR	R6, #(0x01 << 20)
	STR	R6, [R5]
	
	; Set edge-sensitive mode for EINT0
	LDR	R5, =EXTMODE
	LDR	R6, [R5]
	ORR	R6, #1
	STRB	R6, [R5]
	
	; Set rising-edge mode for EINT0
	LDR	R5, =EXTPOLAR
	LDR	R6, [R5]
	BIC	R6, #1
	STRB	R6, [R5]
	
	; Reset EINT0
	LDR	R5, =EXTINT
	MOV	R6, #1
	STRB	R6, [R5]
	

	
	;
	; Configure push button (Vector 0x14) interrupt handler
	;

	MOV	R3, #14			; vector = 14;
	MOV	R4, #1			; vmask = 1;
	MOV	R4, R4, LSL R3		; vmask = vmask << vector;

	
	; VICIntSelect - Set Vector 0x14 for IRQ (clear bit 14)
	LDR	R5, =VICIntSelect	; addr = VICIntSelect;
	LDR	R6, [R5]		; tmp = Memory.Word(addr);		
	BIC	R6, R6, R4		; Clear bit for Vector 0x14
	STR	R6, [R5]		; Memory.Word(addr) = tmp;
	
	; Set Priority to lowest (15)
	LDR	R5, =VICVectPri0	; addr = VICVectPri0;
	MOV	R6, #0xF		; pri = 15;
	STR	R6, [R5, R3, LSL #2]	; Memory.Word(addr + vector * 4); = pri;
	
	; Set handler address
	LDR	R5, =VICVectAddr0	; addr = VICVectAddr0;
	LDR	R6, =ButtonHandler	; handler = address of ButtonHandler;
	STR	R6, [R5, R3, LSL #2]	; Memory.Word(addr + vector * 4) = handler;

	
	; VICIntEnable
	LDR	R5, =VICIntEnable	; addr = VICVectEnable;
	STR	R4, [R5]		; enable interrupts for vector 0x14

	;
	; Infinite loop
	;
	


	;
	; Configure TIMER1 to generate frequency for middle C
	;
	
	; Stop and reset TIMER1
	LDR	R5, =T1TCR
	LDR	R6, =0x2
	STRB	R6, [R5]

	; Set timer mode
	LDR	R5, =T1CTCR
	LDR	R6, =0x00
	STRB	R6, [R5]

	; Set match register for 1 sec
	LDR	R5, =T1MR0
	LDR	R6, =15000 ;  12MHz / (261.626Hz * 2)
	STR	R6, [R5]

	; Configure to interrupt and restart on match
	LDR	R5, =T1MCR
	LDR	R6, =0x03
	STRH	R6, [R5]

	; Set prescale = 1 (no prescaling)
	LDR	R5, =T1PR
	LDR	R6, =0		; Set to (wanted prescale - 1)
	STR	R6, [R5]	
	
	; NOTE: We won't start TIMER0 until the button is pressed down!!
	;       (See ButtonHandler)
	
	;
	; Configure VIC for TIMER1
	;
	
	; Just some useful values
	LDR	R3, =5			; vector 5
	LDR	R4, =1			;
	MOV	R4, R4, LSL R3 		; vector mask
	
	; VICIntSelect - Set Vector 0x04 for IRQ (clear bit 5)
	LDR	R5, =VICIntSelect	; addr = VICVectSelect;
	LDR	R6, [R5]		; tmp = Memory.Word(addr);		
	BIC	R6, R6, R4		; Clear bit for Vector 0x04
	STR	R6, [R5]		; Memory.Word(addr) = tmp;
	
	; Set Priority to lowest (15)
	LDR	R5, =VICVectPri0	; addr = VICVectPri0;
	MOV	R6, #0xF		; pri = 15;
	STR	R6, [R5, R3, LSL #2]	; Memory.Word(addr + vector * 4); = pri;
	
	; Set handler address
	LDR	R5, =VICVectAddr0	; addr = VICVectAddr0;
	LDR	R6, =TimerHandler1	; handler = address of TimerHandler;
	STR	R6, [R5, R3, LSL #2]	; Memory.Word(addr + vector * 4) = handler

	; VICIntEnable
	LDR	R5, =VICIntEnable	; addr = VICIntEnable;
	STR	R4, [R5]		; enable Timers for vector 0x4

		;
	; Configure DAC	(Digital to Audio Converter)
	;

	; Configure pin P0.26 for AOUT (DAC analog out)
	LDR	R5, =PINSEL1
	LDR	R6, [R5]
	BIC	R6, R6, #(0x03 << 20)
	ORR	R6, R6, #(0x02 << 20)
	STR	R6, [R5]

	; DAC is always on so no further configuration required


	; Start TIMER0
	;

	; Start TIMER0 using the Timer Control Register
	; Set bit 0 of TCR to enable the timer
	LDR	R5, =T0TCR
	LDR	R6, =0x01
	STRB	R6, [R5]

	;
	; Initialisation is finished. The remainder of the functionality is
	; provided by the interrupt handler.
	;
	
	
stop	B	stop


;
; EINT0 Button Handler
;
ButtonHandler
	SUB	LR, LR, #4		; Adjust return address
	STMFD	sp!, {r0-r12, LR}	; save registers

	;
	; Reset EINT0
	;
	LDR	R5, =EXTINT
	MOV	R6, #1
	STRB	R6, [R5]
	
	;
	; Change Button On/Off
	;
	LDR	R3, =buttonOnOff		; address = buttonOnOff
	
	LDR	R4, [R3]		; buttonOnOff = Memory.Word(address);
	CMP R4, #0			; if(buttonOnOff !=0){
	BEQ elseIf
	MOV R4, #0		    ; buttonOnOff = 0;
	STR	R4, [R3]		; Store buttonOnOff back in memory
	B endIf
elseIf					; else{ 
	MOV R4, #1		   	; buttonOnOff = 1
	STR	R4, [R3]  	  	; Store buttonOnOff back in memory
endIf 
	

	;
	; Clear source of interrupt
	;
	LDR	R3, =VICVectAddr	; addr = VICVectAddr
	MOV	R4, #0			; tmp = 0;
	STR	R4, [R3]		; Memory.Word(addr) = tmp;

	;
	; Return
	;
	LDMFD	sp!, {r0-r12, PC}^	; restore register and CPSR

   
;							  
; Timer interrupt handler
;
Timer0Handler
	SUB	LR, LR, #4		; Adjust return address (because the processor
					; sets it 4 bytes after the real return address!!)

	STMFD	sp!, {r0-r12, LR}	; save registers to avoid unintended side effects
	
	; Reset TIMER0 interrupt by writing 0xFF to T0IR
	LDR	R5, =T0IR
	MOV	R6, #0xFF
	STRB	R6, [R5]
	
	LDR	R4, =0x04		;   setup bit mask for bit 2 of FIO2 (P2.10)
	LDR R10, =buttonOnOff	; load the address of buttonOnOff 
	LDR R10, [R10]		   ; load contents of buttonOnOff
	CMP R10, #1			   ; if(buttonOnOff is on (ie. buttonCount == 1)){
	BNE dontCount
	LDR R10, =timerCount 	; load address of timerCount
	LDR R11, [R10]	    	; load contents of timerCount
	SUB R11, R11, #1	   	; timerCount--
	STR R11, [R10]		   	; Store edited timerCount back in memory
							; }
dontCount

	LDR R10, =timerCount
	LDR R10, [R10]
	CMP R10, #0
	BGT dontBuzz
	LDR R10, =buzzerLength
	LDR R10, [R10]
	CMP R10, #0
	BEQ elsbuzz

	;
	; Start TIMER1 (starts sound)
	;

	LDR	R5, =T1TCR
	LDR	R6, =0x01
	STRB	R6, [R5]
	LDR R10, =buzzerLength
	LDR R11, [R10]
	SUB R11, R11, #1
	STR R11, [R10]
	B	eifbuzz
elsbuzz

	;
	; Stop TIMER1 (stops sound)
	;

	LDR	R5, =T1TCR
	LDR	R6, =0x2
	STRB	R6, [R5]
			
eifbuzz

dontBuzz 
	LDR	R5, =FIO2PIN1		;
	LDRB	R6, [R5]		;   read FIO2PIN1
	AND	R7, R6, R4		;   only want to test bit 2 – mask other bits
	CMP	R7, #0			;   if (LED off)
	BNE	elseoff			;   {
	ORR	R6, R6, R4		;     set bit 2 (turn LED on)
	B	endif			;   }
elseoff					;   else {
	BIC	R6, R6, R4		;     clear bit 2 (turn LED on)
endif					;   }
	STRB	R6, [R5]		;   write new FIO2PIN1 value

	; Clear source of interrupt by writing 0 to VICVectAddr
	LDR	R5, =VICVectAddr
	MOV	R6, #0		
	STR	R6, [R5]	
	
	; Return
	LDMFD	sp!, {r0-r12, PC}^	; restore register and CPSR	


;
; Timer interrupt handler
;
TimerHandler1

	SUB	LR, LR, #4		; Adjust return address
	STMFD	sp!, {r0-r12, LR}	; save registers
	
	;
	; Reset TIMER1 interrupt by writing 0xFF to T1IR
	;
	LDR	R5, =T1IR
	MOV	R6, #0xFF
	STRB	R6, [R5]
	
	
	;
	; Change analog output to cause square wave signal
	; If signal is currently high, send it low. If its low, send it high
	;
	
	; Load the current DAC output value
	LDR	R5, =DACR
	LDR	R6, [R5]
	
	; Mask out all but bits 15...6
	LDR	R7, =0x0000FFC0
	AND	R6, R6, R7
	
	CMP	R6, #0			; if (DACR == 0)
	BNE	high			; {
	LDR	R6, =(volume << 6)	;  DACR = volume << 6
	B	endIf3			; }
high					; else {
	LDR	R6, =0			;  DACR = 0
endIf3					; }
	STR	R6, [R5]		; store new DACR


	;
	; Clear source of interrupt by writing 0 to VICVectAddr
	;
	
	LDR	R5, =VICVectAddr
	MOV	R6, #0		
	STR	R6, [R5]	
	
	
	;
	; Return
	;
	LDMFD	sp!, {r0-r12, PC}^	; restore register and CPSR
	

	  	AREA	TestData, DATA, READWRITE
	
timerCount	DCD 	10
buzzerLength DCD 	5
buttonOnOff	SPACE	2	
	END