# Mohamed Asmali
#YOU MUST HAVE YOUR NAME IN YOUR MAKEFILE!

#Makefile for lab 1-4.  You need to add you name to this file in the comments
# You will need to add a entry to create your zip file

#change the last dependency for each new lab
all:  tags  lab4 lab4u p1 p2 p3 p4

#this entry has to change for lab 2 - 4
# NEVER compile .c files with this rule - it lacks needed compile time flags
# There is an entry below that turns .c files into .o files
lab4: lab4.o linkedListFile.o bits.o input.o output.o physics.o n2.o allocate.o sim.o reliable.o
	gcc -g -o $@ $^  -lm -L. -lpb -lncurses -llinkedlist

lab4u: lab4u.o linkedListFile.o bits.o input.o output.o physics.o n2.o allocate.o sim.o unreliable.o
	gcc -g -o $@ $^  -lm -L. -lpb -lncurses -llinkedlist

#this is where you create an entry to build yuour zip file
#it will also update with new labs



# n2:n2.o
# 	gcc -g -o $@ $^
#this entry stays for C code labs
tags: *.c
	ctags -R .

# this entry stays for C code labs
# headers: *.c tags
# 	headers.sh
# tests command line prompts
p1: p1.o n2.o
	gcc -g -o $@ $^
# tests unreliable 
p2: p2.o n2.o unreliable.o
	gcc -g -o $@ $^
#tests reliable
p3: p3.o n2.o reliable.o
	gcc -g -o $@ $^
# tests p4 with x7.pbd with reliable memory allocation
p4: p4.o linkedListFile.o bits.o input.o output.o physics.o n2.o allocate.o sim.o reliable.o
	gcc -g -o $@ $^  -lm -L. -lpb -lncurses -llinkedlist
# lab4.zip: makefile bits.c input.c lab4.c lab4.c output.c physics.c allocate.c linkedListFile.c sim.c *.c *.h README_LAB4 n2.o libpb.a *linkedlist.a *libpb.a n2.o
lab4.zip: makefile *.c *.h README_LAB4 n2.o reliable.o unreliable.o libpb.a *linkedlist.a *libpb.a
	zip $@ $^
#old install folder is removed
	rm -rf install
#install folder is created
	mkdir install
	unzip $@ -d install
#self-test what I want graded
	make -C install -r lab4
	make -C install -r lab4u
	make -C install -r p1
	make -C install -r p2
	make -C install -r p3
	make -C install -r p4
	rm -rf install

# this entry takes care of C files depending on header files
# It has the flags for compiling the c code
# It you don't like makefiles, consider typing the following line a few
# thousand times this semester and reconsider.
# Be sure that this one doesn't get an inserted line break anywhere
%.o:%.c *.h
	gcc -std=c99 -pedantic -Wformat -Wreturn-type -g -c $< -o $@


