// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

//// Replace this comment with your code.

// initialize parameters
@8192
D=A
@words
M=D // words = 8192;

(LOOP)
// probe the KBD
	@KBD
	D=M

	// initialize i = 0
	@i
	M=0 

	// if KBD != 0; draw black;
	@DRAWBLACK
	D;JNE

	// else if KBD == 0; draw white
	(DRAWWHITE)
		@i
		D=M
		@SCREEN
		A=A+D
		M=0 // RAM[SCREEN + i] = 0
				 // this effectively set the word white

		@i
		M=M+1 // i++

		// if i < words, continue
		@i
		D=M
		@words
		D=D-M
		@DRAWWHITE
		D;JLT

	// continue this loop
	@LOOP
	D;JEQ


	(DRAWBLACK)
		@i
		D=M
		@SCREEN
		A=A+D
		M=-1 // RAM[SCREEN + i] = -1
				 // this effectively set the word black

		@i
		M=M+1 // i++

		// if i < words, continue
		@i
		D=M
		@words
		D=D-M
		@DRAWBLACK
		D;JLT

	@LOOP
	0;JMP // keep looping

