// MOHAMED ASMALI
#include <stdio.h>
#include "input.h"
#include "numericSymbols.h"
#include "debug.h"
#define SHIFT6 6
#define SHIFT3 3
#define HEX3 0x03
#define HEX7 0x07
#define STATUS2 0x02
//how to use debug statements
int getStatus(unsigned char bit){
    //gets copy of the original bits
    unsigned char copy=bit;
    if(DEBUG) printf("Here is copy of bits for getStatus: %hhx,",copy);
    //shifts the copy to the right 6 times
    copy>>=SHIFT6;
    if(DEBUG) printf("Copy of bits shifted to the right 6 times: %hhx,",copy);
    //gets the stats by bit wise ANDing the copy and 0x03
    int status=(copy& HEX3);
    if(DEBUG) printf("Status of the ball: %hhx,",status);
    return status;
}

int getColor(unsigned char bit){
    //creates copy of the bits
    unsigned char copy=bit;
    if(DEBUG) printf("Here is copy of bits for getColor: %hhx,",copy);
    //shifts the copy 3 times to the right
    copy>>=SHIFT3;
    if(DEBUG) printf("Copy of bits shifted to the right 3 times: %hhx,",copy);
    //gets the color by bitwise ANDING the copy and 0x07
    int color=(copy& HEX7);
    if(DEBUG) printf("Status of the ball: %hhx,",color);
    return color;

}
int updateStatus(unsigned char bits){
    //maskvalue is retrieved by shifting 0x03 to the left 6 times
    unsigned char maskVal=HEX3<<SHIFT6;
    if(DEBUG) printf("Mask Value from shifting 0x03 to the left 6 times: %hhx,",maskVal);
    //uniary negates maskVal
    maskVal=~maskVal;
    if(DEBUG) printf("Negation of the Mask Value: %hhx,",maskVal);
    //gets the updated status by bit wise ANDING maskVal and the original bits
    // and bitwise OR with 0x02(the ball being forced into play) being shifted to the left 6 time
    int status=(maskVal & bits) | (STATUS2<<SHIFT6);
    if(DEBUG) printf("Updated Status of the ball: %hhx,",status);
    return status;


}

