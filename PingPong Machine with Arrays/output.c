// MOHAMED ASMALI
#include <stdio.h>
#include "debug.h"
#include "numericSymbols.h"
#include "bits.h"
#include "physics.h"
#include "constants.h"
//prints loaded ball with its hex value, color, X, Y, THETA, and FORCE value
void printLoaded(double ball[],unsigned char hex){
    if(TEXT)printf("Loaded %02hhX %d ball at %9.5f, %9.5f %9.5f deg %9.5f ips\n",hex,getColor(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}
//prints launched ball with its status and color, X, Y, VX, and VY value
void printLaunched(double ball[],unsigned char hex){
    double startingForce=ball[FORCE];
    getVelocityY(ball,startingForce);
    getVelocityX(ball,startingForce);
    if(TEXT)printf("Launched %hhX %d ball at %9.5f, %9.5f at %9.5f, %9.5f\n",updateStatus(hex),getColor(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}
//prints when ball is launched and loaded.
void printLaunchLoaded(double ball[],unsigned char hex){
    printLoaded(ball,hex);
    printLaunched(ball,hex);
}
//prints current status with the elapsed time, array, and unsigined char value
void printStatus(double ball[],unsigned char hex,double elapsedTime){
if(TEXT)printf("ST    ___X_____ ___Y_____    ___VX____ ____VY____    ET= %8.6f\n",elapsedTime);
if(TEXT)printf("%hhX    %9.5f %9.5f    %9.5f%11.5f\n\n",updateStatus(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}
//prints when the ball reflects off the right wall.
void rightWall(double ball[],unsigned char hex){
    if(TEXT)printf("Right wall: %hhX    %9.5f %9.5f    %9.5f %10.5f\n",updateStatus(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}
//prints when the ball reflects off the left wall.
void leftWall(double ball[],unsigned char hex){
    if(TEXT)printf("Left_ wall: %hhX    %9.5f %9.5f    %9.5f %10.5f\n",updateStatus(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}
//prints when the ball reflects off the top wall
void upperWall(double ball[],unsigned char hex){
    if(TEXT)printf("Upper wall: %hhX    %9.5f %9.5f    %9.5f %10.5f\n",updateStatus(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}
//prints when the ball is drained
void offTable(double ball[],unsigned char hex){
    if(TEXT)printf("Off table: %hhX    %9.5f %9.5f    %9.5f %10.5f\n",updateStatus(hex),ball[X],ball[Y],ball[VX],ball[VY]);
}

void beginFunction(double ball[],unsigned char hex){
    //keeps track of elapsed time by 1.0/64 time frame.
    double elapsed_time;
    while(1){
        printStatus(ball,hex,elapsed_time);
        move(ball,hex);
        if(drainedBall(ball)) break;
        elapsed_time+=DELTA_T;
    }
    offTable(ball,hex);
}