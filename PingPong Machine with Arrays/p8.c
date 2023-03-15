// MOHAMED ASMALI
#include <stdio.h>
#include <stdlib.h>
#include "output.h"
#include "physics.h"
#include "input.h"
#include "numericSymbols.h"
#include "n2.h"
//tests x4balls.pbd
int main(){
    double start,runtime;
    start=now();;
    int finalScanFVal=readBallFile();
    runtime=now()-start;
    printf("Final scanf call returned %d\n",finalScanFVal );
    printf("Total runtime is %.9lf seconds\n",runtime);
    return EXIT_SUCCESS;
}
//p8 < x4balls.pbd>my.x4balls.out
//diff my.x4balls.out x4balls.output.txt