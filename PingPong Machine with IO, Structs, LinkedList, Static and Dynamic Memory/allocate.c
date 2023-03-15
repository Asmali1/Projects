// MOHAMED ASMALi
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "altmem.h"
#include "structs.h"
#include "debug.h"
#include "sim.h"
#include "allocate.h"

//dynamically allocates ball
Ball *dynamic()
{
    static int counter;                           
    Ball *baller;
    baller = alternative_malloc(sizeof(Ball));
    if(baller==NULL){
        if(TEXT) printf("ERROR: failed to allocate ball\n");
    } else{
        counter++;
        if (TEXT)printf("DIAGNOSTIC: %d balls allocated\n", counter);
    }

    return baller;
}
//keeps track of when ball was freed
int freeBallCounter(int x){
    static int count=0;
    if(x==0) return count;
    count=x;
    return x;
}
// used in delete function to delete ball and free node
void freeBalls(void *data)
{
    static int counter=0;
    counter++;
    freeBallCounter(counter);
    Ball *ball = data;
    alternative_free(ball);
    ball = NULL;
    if (TEXT)
        printf("DIAGNOSTIC: %d balls freed\n", counter);
}


// /* Copyright 2023, Neil Kirby.  Not for disclosure without permission */
// // edited by Mohamed Asmali
// /* system includes go first */
// #include <stdio.h>
// #include <stdbool.h>
// #include <stdlib.h>
// #include "structs.h"
// #include "libpb.h"

// /* includes for constants and tyupes are next */
// #include "debug.h"
// #include "constants.h"
// #include "sim.h"
// #include "lab4.h"
// /* includes for functions go last */
// #include "n2.h"
// #include "output.h"
// #include "physics.h"
// #include "bits.h"
// #include "input.h"
// #include "linkedListFile.h"



// //added this to run balls
// void runBalls(simData *sim)
// {
// 	//reads each ball and plays frame by frame
// 	int x=0;
// 	while (sim->inPlay != NULL)
// 	{ 
// 		master_output(sim);
// 		sim->elapsedTime += DELTA_T;
// 		iterate(sim->inPlay, updateEverything);
// 		int x=deleteSome(&(sim->inPlay), offTable, dispose, TEXT);
// 		if(TEXT)printf("\n");
// 		sort(sim->inPlay,compareY);
// 	}
// 	//prints final output
// 	finalOutput(sim,x);

// }


// bool init()
// {
// 	return (TEXT || (GRAPHICS && pb_initialize()));
// }
// //tears down the program
// void teardown()
// {
// 	if (GRAPHICS)
// 		pb_teardown();
// }
// //check command line values
// bool commandLineCheck(int argc){
// 	if(argc<2){
// 		printf("ERROR: Insufficient arguments (argc is 1)\n");
// 		return 0;
// 	}
// 	if(argc==3){ 
// 		printf("ERROR: Bonus code is not present\n");
// 		return 0;
// 	}
// 	if(argc==3 && TEXT) {
// 	printf("ERROR: Lab is in Text mode and argc is 3\n");
// 		return 0;
// 	}

// 	return 1;
// }
// void runLab(char *argv[]){
// 	FILE *read;
// 	read=fopen(argv[1],"r");
// 		if(read==NULL){
// 			printf("ERROR: Unable to open %s for reading.\n",argv[1]);
// 		} 
// 		else{
// 		printf("DIAGNOSTIC: Successfully opened %s for reading.\n",argv[1]);
// 		readBalls(read);
// 		fclose(read);
// 		teardown();
// 		}
// }
// int main(int argc, char *argv[])
// {
// double start, runtime;
// 	// FILE *read;
// 	start = now(); // this is the very first executable statement
// 	if (commandLineCheck(argc) && init())
// 	{
// 		runLab(argv);
		
// 	}
// 	// fclose(read);
// 	printf("DIAGNOSTIC: Input file closed.\n");
// 	/* at this point we are done, graphics has been torn down*/
// 	runtime = now() - start;
// 	/* after graphics has been torn down, we can freely print */
// 	printf("Total runtime is %.9lf seconds\n", runtime);

// 	return 0;
// }


