#Mohamed Asmali
#YOU MUST HAVE YOUR NAME IN YOUR MAKEFILE!

#Makefile for lab 1-4.  You need to add you name to this file in the comments
# You will need to add a entry to create your zip file

#change the last dependency for each new lab
all:  tags headers lab2
#lab2 

#this entry has to change for lab 2 - 4
# NEVER compile .c files with this rule - it lacks needed compile time flags
# There is an entry below that turns .c files into .o files

lab2: lab2.o output.o physics.o bits.o n2.o input.o
	gcc -g -o $@ $^ -lm

#this is where you create an entry to build yuour zip file
#it will also update with new labs

#this entry stays for C code labs
tags: *.c
	ctags -R .

# this entry stays for C code labs
headers: *.c tags
	headers.sh
#tests functions from physics and printArray
n2:n2.o
	gcc -g -o $@ $^

p1: p1.o printArrays.o output.o physics.o bits.o input.o
	gcc -g -o $@ $^ -lm
#tests ballReader and printArrays
p2: p2.o output.o physics.o bits.o input.o printArrays.o
	gcc -g -o $@ $^ -lm
#tests getStatus
p3: p3.o output.o physics.o bits.o input.o
	gcc -g -o $@ $^ -lm
# Tests xperfect.pbd
p4: p4.o n2.o output.o physics.o bits.o input.o 
	gcc -g -o $@ $^ -lm
# tests getColor
p5: p5.o printArrays.o bits.o
	gcc -g -o $@ $^ -lm
# tests xhalf.pbd
p6: p9.o output.o physics.o bits.o n2.o input.o
	gcc -g -o $@ $^ -lm
# tests xmaxy.pbd
p7: p7.o output.o physics.o bits.o n2.o input.o
	gcc -g -o $@ $^ -lm
# tests x4balls.pbd
p8: p8.o output.o physics.o bits.o n2.o input.o
	gcc -g -o $@ $^ -lm
# tests x3walls.out
p9: p9.o output.o physics.o bits.o n2.o input.o
	gcc -g -o $@ $^ -lm
# tests fixBall from physics
p10: p10.o output.o physics.o bits.o n2.o input.o
	gcc -g -o $@ $^ -lm
#Self-test code for lab2
lab2.zip: makefile *.c *.h README_LAB2 n2.o
	zip $@ $^
#old install folder is removed
	rm -rf install
#install folder is created
	mkdir install
#lab2.zip is unzipped to the install folder
	unzip lab2.zip -d install
#installs executables
	make -C install -r lab2
# make -C install -r intput
# make -C install -r output;
# make -C install -r bits;
# make -C install -r physics
	make -C install -r lab2
	make -C install p1
	make -C install p2
	make -C install p3
	make -C install p4
	make -C install p5
	make -C install p6
	make -C install p7
	make -C install p8
	make -C install p9
	make -C install p10
	rm -rf install 
# this entry takes care of C files depending on header files
# It has the flags for compiling the c code
# It you don't like makefiles, consider typing the following line a few
# thousand times this semester and reconsider.
# Be sure that this one doesn't get an inserted line break anywhere
%.o:%.c *.h
	gcc -std=c99 -pedantic -Wformat -Wreturn-type -g -c $< -o $@
