/* Copyright 2023 Neil Kirby, not for disclosure */
/* All physics goes here */
// Edited by Mohamed Asmali
#define GRAVITY (-47.052534795) /* inches per second per second */
#define LEFT_LIMIT (-12.0)
#define RIGHT_LIMIT (12.0)
#define UPPER_LIMIT (48.0)
#define LOWER_LIMIT (0.0)
#define INELASTIC (0.95)
#define TWO (2.0)
#define HALF (.5)
#define ONE_EIGHTY (180.0)

//flippers
#define RIGHT_FLIPPER_LEFT_X (2.0)
#define RIGHT_FLIPPER_RIGHT_X (5.0)
#define LEFT_FLIPPER_LEFT_X (-5.0)
#define LEFT_FLIPPER_RIGHT_X (-2.0)
#define HITBOX_Y_LEFT (0)
#define HITBOX_Y_RIGHT (1)
//change in VX and VY for flippers
#define VY_CHANGE (75.0)
#define VX_CHANGE (6.4)
#define Y_CHANGE (1)

             
#include <stdbool.h>
#include <math.h>
#include "structs.h"
#include "constants.h"
#include "n2.h"
#include "libpb.h"
#include "physics.h"

// #include "subscripts.h"

#include "output.h"
#include "debug.h"

/* validate my own header file */
#include "physics.h"

bool on_table(Ball *ball)
{
	return ((ball->Y) >= LOWER_LIMIT);
}
//checks to see if ball hits left flipper
static void left_hitbox(unsigned char bits, Ball **ball)
{

	bool xBound=LEFT_FLIPPER_LEFT_X<=(*ball)->X && (*ball)->X<=LEFT_FLIPPER_RIGHT_X;
	bool yBound=HITBOX_Y_LEFT<=(*ball)->Y && (*ball)->Y<=HITBOX_Y_RIGHT;
	if (xBound && yBound)
	{
		left_flipper(bits, **ball);
		(*ball)->VY += VY_CHANGE;
		(*ball)->Y += Y_CHANGE;
		(*ball)->VX += VX_CHANGE;
	}
}
//checks to see if ball hit right flipper
static void right_hitbox(unsigned char bits, Ball **ball)
{
	bool xBound=RIGHT_FLIPPER_LEFT_X<=(*ball)->X && (*ball)->X<=RIGHT_FLIPPER_RIGHT_X;
	bool yBound=HITBOX_Y_LEFT<=(*ball)->Y && (*ball)->Y<=HITBOX_Y_RIGHT;
	if (xBound && yBound)
	{
		right_flipper(bits, **ball);
		(*ball)->VY += VY_CHANGE;
		(*ball)->Y += Y_CHANGE;
		(*ball)->VX -= VX_CHANGE;
	}
}
//checks left wall and fixes it if it hits it
static void left_wall(unsigned char bits, Ball **ball)
{
	if ((*ball)->X < LEFT_LIMIT)
	{
		left_message(bits, **ball);
		(*ball)->X = TWO * LEFT_LIMIT - (*ball)->X;
		(*ball)->VX = -(*ball)->VX;
		(*ball)->VX *= INELASTIC;
		(*ball)->VY *= INELASTIC;
	}
}
//checks right wall and fixes it if it hits it
static void right_wall(unsigned char bits, Ball **ball)
{
	if ((*ball)->X > RIGHT_LIMIT)
	{
		right_message(bits, **ball);
		(*ball)->X = TWO * RIGHT_LIMIT - (*ball)->X;
		(*ball)->VX = -(*ball)->VX;
		(*ball)->VX *= INELASTIC;
		(*ball)->VY *= INELASTIC;
	}
}
//checks top wall and fixes it if it hits it
static void top_wall(unsigned char bits, Ball **ball)
{
	if ((*ball)->Y > UPPER_LIMIT)
	{
		top_message(bits, **ball);
		(*ball)->Y = TWO * UPPER_LIMIT - (*ball)->Y;
		(*ball)->VY = -(*ball)->VY;
		(*ball)->VX *= INELASTIC;
		(*ball)->VY *= INELASTIC;
	}
}
//added left hitbox and right hitbox constraint
void constraints(unsigned char bits, Ball *ball)
{
	left_wall(bits, &ball);
	right_wall(bits, &ball);
	top_wall(bits, &ball);
	left_hitbox(bits, &ball);
	right_hitbox(bits, &ball);
}
//updates balls accordingly
void update_ball(unsigned char bits, Ball *ball)
{
	move(DELTA_T, &*ball);
	constraints(bits, &*ball);
}
//changes positioning of X,Y and VY
void move(double dt, Ball *ball)
{
	// stats
	/* position first, then velocity */
	ball->X += ball->VX * dt;
	ball->Y += ball->VY * dt + HALF * GRAVITY * dt * dt;
	/* no change in VX */
	ball->VY += GRAVITY * dt;
}
//computes VX and VY by converting from polar to card
void polar2cart(Ball *ball)
{
	double computedVX = ball->FORCE * cos(ball->THETA * M_PI / ONE_EIGHTY);
	double computedVY = ball->FORCE * sin(ball->THETA * M_PI / ONE_EIGHTY);
	ball->VX = computedVX;
	ball->VY = computedVY;
}
