// MOHAMED ASMALI
#include <stdio.h>
#include "numericSymbols.h"
#include "output.h"
#define REQUIRED_VALUES 5
#define ARRAY_SIZE 4
//reads the ball into the array and hex pointer
int ballReader(double array[], unsigned char *hexVal){
    int x=scanf("%hhX%lf%lf%lf%lf", hexVal, &array[X], &array[Y], &array[THETA], &array[FORCE]);
    //returns number of values read. if no values is read, it returns -1
    return x;
}
//reads a ball from every line in a given file.
int readBallFile(){
    //creates an array of 4 which stores the 4 doubles from
    double ball[ARRAY_SIZE];
    unsigned char hex;
    int scanFVal=ballReader(ball,&hex);
    //continues as long as a ball is read
    while(scanFVal==REQUIRED_VALUES){
    //begins reading function and prints out status, launch, and loaded.
    printLaunchLoaded(ball,hex),beginFunction(ball,hex);
    //reads next value from file
    scanFVal=ballReader(ball,&hex);
}
    return scanFVal;
}

