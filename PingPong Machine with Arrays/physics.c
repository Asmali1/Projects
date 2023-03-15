/*MOHAMED ASMALI*/
//checks if x value is greater than table constraits.
#include <stdbool.h>
#include <math.h>
#include "constants.h"
#include "physics.h"
#include "numericSymbols.h"
#include "output.h"
#define G_FORCE_VALUE -47.052534795
#define RIGHT_WALL 12.0
#define LEFT_WALL -12.0
#define TABLE_DEPTH 48.0
#define MOMENTUM_CHANGE 0.95
#define HALF .5
#define ONE_EIGHTY 180.0
#define NEGATIVE_ONE -1
//checks constraints 
//if constraint is caught, it is printed and fixed 
void checkConstraints(double ball[],unsigned char hex){
    if(passedLeftWall(ball)){
        leftWall(ball,hex), fixBallLeftWall(ball);
    }else if(passedRightWall(ball)){
        rightWall(ball,hex), fixBallRightWall(ball);
   }
    if(passedTopWall(ball)){
        upperWall(ball,hex),fixPassedTopWall(ball);
    }
}
//moves position of ball, updates X,Y,YVelocity, and checks constraints
void move(double ball[],unsigned char hex){
    updateX(ball);
    updateY(ball);
    updateYVelocity(ball);
    checkConstraints(ball,hex);
    
}
//updatesvalue of x
void updateX(double ball []){
    ball[X]+=(ball[VX]*DELTA_T);
}
//updates value of Y
void updateY(double ball[]){
    ball[Y]+=(ball[VY]*DELTA_T)+(HALF*G_FORCE_VALUE*DELTA_T*DELTA_T);
}
//updates Y Velocity
void updateYVelocity(double ball[]){
    ball[VY]+=(G_FORCE_VALUE)*(DELTA_T);
}
//checks if the ball is passed the right wall.
bool passedRightWall(double ball[]){
    return ball[X]>RIGHT_WALL;
}
//fixes position of the ball and updates the velocity.
void fixBallRightWall(double ball[]){
    double distanceExceeded=ball[X]-RIGHT_WALL;
    ball[X]=RIGHT_WALL-distanceExceeded;
    ball[VX]*=NEGATIVE_ONE*MOMENTUM_CHANGE;
    ball[VY]*=MOMENTUM_CHANGE;
}
//checks if ball is passed the left wall
bool passedLeftWall(double ball[]){
    return(ball[X]<LEFT_WALL);
}
//fixes ball if it passes leftWall and updates Y-velocity
void fixBallLeftWall(double ball[]){
    double distanceExceeded=ball[X]+RIGHT_WALL;//12
    ball[X]=LEFT_WALL+(NEGATIVE_ONE*distanceExceeded); //-12
    ball[VX]*=NEGATIVE_ONE*MOMENTUM_CHANGE;
    ball[VY]*=MOMENTUM_CHANGE;

}
//checks if ball passed the top wall
bool passedTopWall(double ball[]){
    return ball[Y]>TABLE_DEPTH;
}
//corrects the ball if it passed the top wall and updates velocity
//run gdb here
void fixPassedTopWall(double ball[]){
    double distanceExceeded=ball[Y]-TABLE_DEPTH;
    ball[Y]=TABLE_DEPTH-distanceExceeded;
    ball[VX]*=MOMENTUM_CHANGE;
    ball[VY]*=NEGATIVE_ONE*MOMENTUM_CHANGE;
}
//checks if the ball stopped moving or is off the table
bool drainedBall(double ball[]){
    return ball[Y]<=0;
}

//converts from polar to rectangular
double convertToRadians(double degree){
    return degree*(M_PI/ONE_EIGHTY);

}
//gets intitial velocity x of the ball
void getVelocityX(double ball[],double force){
    ball[VX]=force * cos(convertToRadians(ball[THETA]));

}
//gets initial velocity y of the ball
void getVelocityY(double ball[],double force){
    ball[VY]=force * sin(convertToRadians(ball[THETA]));
}
