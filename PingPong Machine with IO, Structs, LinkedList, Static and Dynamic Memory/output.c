
/* Copyright 2023, Neil Kirby.  Not for disclosure without permission */
// edited by Mohamed Asmali
/* system includes go first */
#include <stdio.h>
#include <stdlib.h>
#include<stdbool.h>
#include "structs.h"
#include "libpb.h"
#include "n2.h"
#include "sim.h"
#include "allocate.h"
#include "output.h"

/* includes for constants and tyupes are next */
#include "debug.h"
// #include "subscripts.h"
#include "constants.h"

/* includes for functions go last */
#include "bits.h"
#include "output.h"
// #include "linkedlist.h"
#include "lab4.h"

#include "linkedListFile.h"


/* functions internal to this file go here at the top */
// changed this to read score and elasped time from simulation
static void print_header(double et, int score)
{
	/* VY can hit -100, so it needs more room */
	printf("%7s %2s  %9s %9s  %9s %10s  ET=%9.6lf  Score= %3d\n",
		   "Color","ST", "___X_____", "___Y_____", "___VX____", "____VY____", et, score);
}
// changed this to be used in iterate function to keep printing out ball information
static void print_ball(void *data)
{
	/* VY can hit -100, so it needs more room */
	Ball *ball = data;
	printf("%7s %02X  %9.5lf %9.5lf  %9.5lf %10.5lf\n",
		   color_string(ball->bits),ball->bits, ball->X, ball->Y, ball->VX, ball->VY);
}
// changed this to print when ball leaves table
void printOffTable(Ball *ball)
{
	if (TEXT)
		printf("Off table: %7s %02X  %9.5lf %9.5lf  %9.5lf %10.5lf\n",
			   color_string(ball->bits),ball->bits, ball->X, ball->Y, ball->VX, ball->VY);
	if(GRAPHICS)pb_status("Off Table");
}
// added this to print currentBall score
void printCurrentBallScore(Ball *ball)
{
	if (TEXT)
		printf("%d points\n", abs((int)ball->VY));
}
void pr(){
	printf("works");
}
// added this to print final output
void finalOutput(simData *sim, int x)
{
	if (TEXT)
		printf("The final scores was %d points:\n", sim->score);
	sort(sim->off_Play,compareVY);
	iterate(sim->off_Play, printScores);
	//  iterateer(sim->off_Play,freeBalls);
	if (TEXT)
		printf("\nDeleted %d balls from in play list.\n", x);
	iterate(sim->off_Play,countOffBall);
	int totalRemoved = deleteSome(&(sim->off_Play), offTable, freeBalls, TEXT);
	if (TEXT)
		printf("Deleted %d balls from off table list.\n", countReturn(0));
	if (GRAPHICS)
		final_graphics(sim->elapsedTime, sim->score);
}
// added this to draw ball
static void draw_ball(void *data)
{
	Ball *ball = data;
	pb_ball(get_color(ball->bits), ball->X, ball->Y);
}
// changed this to iterate to keep printing ball
static void master_text(simData *sim)
{
	print_header(sim->elapsedTime, sim->score);
	iterate(sim->inPlay, print_ball);
	printf("\n");
}
// changed this to keep iterating over graphics
static void master_graphics(simData *sim)
{
	pb_clear();
	iterate(sim->inPlay, draw_ball);
	pb_time((int)(sim->elapsedTime * 1000));
	if (GRAPHICS)
		pb_score(sim->score);
	pb_refresh();
	microsleep((unsigned int)(DELTA_T * 1000000.0));
}
// changed this to have score at the end of simulation
void final_graphics(double et, int score)
{
	double wait = 0.0;

	while (wait < 4.0)
	{
		pb_clear();
		pb_time((int)et * 1000);
		pb_score(score);
		pb_refresh();
		microsleep((unsigned int)(DELTA_T * 1000000.0));
		wait += DELTA_T;
	}
}

/* public functions below here **********************************/
// changed this to take in simData struct
void master_output(simData *sim)
{
	if (TEXT)
		master_text(sim);
	if (GRAPHICS)
		master_graphics(sim);
}
// changed this to take in Ball
void load_message(unsigned char bits, Ball ball)
{
	/* get the color */
	int color = get_color(bits);
	if (GRAPHICS)
		pb_status("Loaded");
	if (TEXT)
		printf("Loaded %7s %02X %d ball at %9.5lf, %9.5lf %9.5lf deg %9.5lf ips\n",
			   color_string(bits),bits, color, ball.X, ball.Y, ball.THETA, ball.FORCE);
}
//prints launch message
void launch_message(unsigned char bits, Ball ball)
{
	/* get the color */
	int color = get_color(bits);
	if (GRAPHICS)
		pb_status("Launch");
	if (TEXT)
		printf("Launched %7s %02X %d ball at %9.5lf, %9.5lf at %9.5lf, %9.5lf\n",
			   color_string(ball.bits),bits, color, ball.X, ball.Y, ball.VX, ball.VY);
}

/* not technically public but it goes right next to the funcitons that call
 * it */
static void x_message(char *string, unsigned char bits, Ball ball)
{
	/* get the color */
	int color = get_color(bits);
	if (TEXT)
		printf("%s: %7s %02X  %9.5lf %9.5lf  %9.5lf %10.5lf\n",
			   string, color_string(bits),bits, ball.X, ball.Y, ball.VX, ball.VY);

	if (GRAPHICS)
		pb_status(string);
}
// prints messages for affected wall/flipper
void left_message(unsigned char bits, Ball ball)
{
	x_message("Left_ wall", bits, ball);
}

void right_message(unsigned char bits, Ball ball)
{
	x_message("Right wall", bits, ball);
}

void top_message(unsigned char bits, Ball ball)
{
	x_message("Upper wall", bits, ball);
}

void off_message(unsigned char bits, Ball ball)
{
	x_message("Off table", bits, ball);
}
void left_flipper(unsigned char bits, Ball ball)
{
	x_message("Left_ flipper", bits, ball);
	pb_left();
}
void right_flipper(unsigned char bits, Ball ball)
{
	x_message("Right flipper", bits, ball);
	pb_right();
}
