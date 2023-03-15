// Mohamed Asmali
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "structs.h"
#include "allocate.h"
#include "sim.h"
#include "physics.h"
#include "debug.h"
#include "output.h"
#include "lab4.h"
#include "bits.h"
#include "sim.h"
#include "input.h"
#include "linkedListFile.h"

//keeps count of ball in offplay
void countOffBall(void *data){
    Ball *bp=data;
    static int count;
    count++;
    countReturn(count);
}
//returns total balls removed in offplay list.
int countReturn(int x){
    static int count=0;
    if(x==0) return count;
    count=x;
    return x;
}

// comparison function for VX
bool compareY(void *data1, void *data2)
{
    Ball *ball1 = data1;
    Ball *ball2 = data2;
    if (ball1->Y > ball2->Y)
    {
        return 1;
    }

    return 0;
}
// comparison function for VY
bool compareVY(void *data1, void *data2)
{
    Ball *ball1 = data1;
    Ball *ball2 = data2;
    if (ball1->VY > ball2->VY)
    {
        return 1;
    }
    return 0;
}
//updates values in the ball
void updateEverything(void *data)
{
    Ball *ball = data;
    update_ball(ball->bits, ball);

}
//checks if ball is off table
bool offTable(void *data)
{
    Ball *ball = data;
    if (ball->Y < 0)
    {
        return 1;
    }
    
    return 0;
}

// compare VY in offPlay list and disposes 
void dispose(void *data)
{
    Ball *ball1 = data;
    printOffTable(ball1);
    int returned = insert(&(ball1->sim->off_Play), ball1, compareVY, TEXT);
    if(returned==0){
		 if(TEXT)printf("ERROR: Unable to insert into off table list, freeing %02X\n",set_in_play(ball1->bits));
         freeBalls(ball1);
    } else{
    // checkInsert(returned);
    printCurrentBallScore(ball1);
    ball1->sim->score+=abs((int)ball1->VY);
    }
}
//loads the ball in with the loading message
void loadBall(void *data)
{
    Ball *ball = data;
    polar2cart(ball);
    ball->bits = set_in_play(ball->bits);
    launch_message(ball->bits, *ball);
}
//prints the scores
void printScores(void *data){
    
    Ball *ball=data;
    if(TEXT)printf("%7s %02X  %3d points\n",color_string(ball->bits), ball->bits,abs((int)ball->VY));
}

