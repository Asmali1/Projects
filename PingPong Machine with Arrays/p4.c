// MOHAMED ASMALI
#include <stdio.h>
#include <stdlib.h>
#include "output.h"
#include "physics.h"
#include "input.h"
#include "numericSymbols.h"
#include "n2.h"
//Tests xperfect.pbd
int main(){
    //initializes start and runtime to be doubles
    double start,runtime;
    //keeps track of current time
    start=now();
    //begins reading each line and value from a file 
    //the finalScanFVal is the value returned from scanf
    int finalScanFVal=readBallFile();
    //returns the runtime of the program
    runtime=now()-start;
    //prints the final value returned from scanF
    printf("Final scanf call returned %d\n",finalScanFVal);
    //prints runtime of the program
    printf("Total runtime is %.9lf seconds\n",runtime);
    return EXIT_SUCCESS;
}
//p4 < xperfect.pbd>my.xperfect.out
//diff my.xperfect.out xperfect.output.txt