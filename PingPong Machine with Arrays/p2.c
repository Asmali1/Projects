// MOHAMED ASMALI
#include <stdio.h>
#include "input.h"
#include "printArrays.h" 
//Tests ballReader and printArrays
int main(void){
    unsigned char hexVal;
    double array[4];
    int y=ballReader(array,&hexVal);
    // printf("%hhX ",hexVal);
    printf("%d ",y);
    printArrays(array);
    return 0;
}