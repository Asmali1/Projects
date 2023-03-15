// MOHAMED ASMALI
#include <stdio.h>
#include <stdlib.h>
#include "output.h"
#include "physics.h"
#include "input.h"
#include "numericSymbols.h"
#include "constants.h"
#include "n2.h"
//TESTS X3WALLS.OUT
int main(){
    //initializes start and runtime to be doubles
    double start,runtime;
    //keeps track of current time
    start=now();
    double ball[4]={-12.32,12,3,3};
    fixBallLeftWall(ball);
    printf("%f ",ball[X]);
    return EXIT_SUCCESS;
}
