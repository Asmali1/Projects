// Added comments by MOHAMED ASMALi
/* Copyright 2023 Neil Kirby, not for disclosure */
#include "structs.h"
#include "bits.h"
#define COLOR_SHIFT 3
#define COLOR_MASK 7

#define STATUS_SHIFT 6
#define STATUS_MASK 7

#define STATUS_IN_PLAY 2
//sets ball in play
unsigned char set_in_play(unsigned char bits)
{
	/* don't take this as mgic, work out how it works */
	bits = bits &  ( ~( (unsigned char)STATUS_MASK << STATUS_SHIFT));
	bits = bits | ( (unsigned char)STATUS_IN_PLAY <<STATUS_SHIFT);
	return bits;
}
//gets color of ball
int get_color(unsigned char bits)
{
	int color = (bits >> COLOR_SHIFT) & COLOR_MASK;

	return color;
}
//gets specefic string color of bit that was sent
const char *color_string(unsigned char bits){
	if(get_color(bits)==0) return "Black";
	if(get_color(bits)==1) return "Red";
	if(get_color(bits)==2) return "Green";
	if(get_color(bits)==3) return "Yellow";
	if(get_color(bits)==4) return "Blue";
	if(get_color(bits)==5) return "Magenta";
	if(get_color(bits)==6) return "Cyan";
	// if(get_color(bits)==7) return "White";
	return "White";
}

