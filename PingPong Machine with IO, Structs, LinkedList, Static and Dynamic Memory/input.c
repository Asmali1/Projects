
/* Copyright 2023, Neil Kirby.  Not for disclosure without permission */
// edited by MOHAMED ASMALi
/* system includes go first */
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "structs.h"
#include "allocate.h"
// #include "linkedlist.h"
#include "sim.h"
#include "input.h"
#include "physics.h"
#include "bits.h"
// #include "linkedListFile.h"
/* includes for constants and tyupes are next */
#include "debug.h"

/* includes for functions go last */
#include "output.h"
#include "lab4.h"

#include "linkedListFile.h"


//checks insertion
void checkInsert(int returnedValue){
	if(returnedValue==-1){
	if(TEXT)printf("INSERTION FAILED\n");
	}

}
//insert balls into inPlay list
bool insertBalls(Ball *dynamicBall)
{
	// ball is inserted into inPlay head
	int returned = insert(&(dynamicBall->sim->inPlay), dynamicBall, compareY, TEXT);
	if(returned==0){
		 if(TEXT)printf("ERROR: Unable to insert into in-play list, freeing %02X\n",set_in_play(dynamicBall->bits));
		freeBalls(dynamicBall);
		return 0;
	}
	return 1;
	// checkInsert(returned);
}
//puts ball in simulation
void placeBallInSim(simData *sim, Ball *staticBall){
		Ball *dynamicBall = dynamic();
		if(dynamicBall!=NULL){
		*dynamicBall = *staticBall;
		dynamicBall->sim = sim;
		// inserts ball into inplay List
		int insertWorked=insertBalls(dynamicBall);
		if(insertWorked){
		// converts ball into cart
		polar2cart(dynamicBall);
		// ball is released to play
		dynamicBall->bits = set_in_play(dynamicBall->bits);
		launch_message(dynamicBall->bits, *dynamicBall);
		}
		}
		// ball is set to staticBallptr and its sim pointer is set to simulation pointer

}
// added this to read ball and add to linked list
void readBalls(FILE *file)
{
    // ball is statically allocated to a pointer and simData struct varaible
	// is initalized and also set to a pointer
	Ball staticBall;
	Ball *staticBallptr = &staticBall;
	int tokens;
	simData real = {0, 0, NULL, NULL};
	simData *simulation = &real;
	// keeps reading lines
	while (5 == (tokens =fscanf(file,"%hhx %lf %lf %lf %lf", &staticBallptr->bits, &staticBallptr->X, &staticBallptr->Y, &staticBallptr->THETA, &staticBallptr->FORCE)))
	{

		// load message is printed
		load_message(staticBallptr->bits, *staticBallptr);
		//places ball in the simulation
		placeBallInSim(simulation,staticBallptr);
	}
	if(TEXT)printf("Final scanf call returned %d\n\n", tokens);
	runBalls(simulation);
}
