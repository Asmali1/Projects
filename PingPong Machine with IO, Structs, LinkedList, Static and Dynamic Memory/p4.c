// Mohamed Asmali
/* system includes go first */
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "structs.h"
#include "libpb.h"

/* includes for constants and tyupes are next */
#include "debug.h"
#include "constants.h"
#include "sim.h"
#include "lab4.h"
/* includes for functions go last */
#include "n2.h"
#include "output.h"
#include "physics.h"
#include "bits.h"
#include "input.h"
// #include "linkedlist.h"
#include "linkedListFile.h"



//added this to run balls
void runBalls(simData *sim)
{
	//reads each ball and plays frame by frame
	int x=0;
	while (sim->inPlay != NULL)
	{ 
		master_output(sim);
		sim->elapsedTime += DELTA_T;
		iterate(sim->inPlay, updateEverything);
		int x=deleteSome(&(sim->inPlay), offTable, dispose, TEXT);
		if(TEXT)printf("\n");
		sort(sim->inPlay,compareY);
	}
	//prints final output
	finalOutput(sim,x);

}


bool init()
{
	return (TEXT || (GRAPHICS && pb_initialize()));
}
//tears down the program
void teardown()
{
	if (GRAPHICS)
		pb_teardown();
}

// runs x7 ball to see if it outputs correctly.
int main(int argc, char *argv[])
{
double start, runtime;
	FILE *read;
	start = now(); // this is the very first executable statement
	if (init())
	{
		read=fopen("x7.pbd","r");
		if(read==NULL){
			printf("ERROR: Unable to open %s for reading.\n",argv[1]);
			fclose(read);
		} 
		else{
		printf("DIAGNOSTIC: Successfully opened x7.pbd for reading.\n");
		readBalls(read);
		fclose(read);
		teardown();
		}
	}
	// fclose(read);
	printf("DIAGNOSTIC: Input file closed.\n");
	/* at this point we are done, graphics has been torn down*/
	runtime = now() - start;
	/* after graphics has been torn down, we can freely print */
	printf("Total runtime is %.9lf seconds\n", runtime);

	return 0;
}
