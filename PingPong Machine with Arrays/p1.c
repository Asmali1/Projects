// MOHAMED ASMALI
#include <stdio.h>
#include <stdbool.h>
#include "printArrays.h"
#include "physics.h"
#include "output.h"
//tests functions from physics and printArray
int main(){
    double ball[4]={1,0,3,4};
    printArrays(ball);
    printf("\n");
    bool x=drainedBall(ball);
    getVelocityX(ball,32);
    printArrays(ball);
    // printf("%d ",x);
    return 0;
}