CC=gcc -g -o0 -Wall
program_NAME := Practical2

all: $(program_NAME)

$(program_NAME): Practical2.c
	$(CC) -o $(program_NAME) $(program_NAME).c -pthread 
clean:
	rm $(program_NAME)
check:
	valgrind --leak-check=full --trace-children=yes --track-origins=yes --dsymutil=yes ./$(program_NAME)
