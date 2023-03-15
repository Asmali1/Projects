// Mohamed Asmali
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "altmem.h"

#include "structs.h"
#include "p1.h"
#include "sim.h"
#include "node.h"
#include "debug.h"
#include "n2.h"

//tests alternative calloc and alternative malloc with unreliable.
int main(int argc, char *argv[])
{
	int count=10;
	int failed=0;
	int success=0;
	while(count>0){
	Ball *ball=alternative_malloc(sizeof(Ball));
	if(ball==NULL){
		failed++;
		printf("FAILED\n");
		// alternative_free("Ball Freed\n");
	} else{
		success++;
		printf("SUCCESS\n");
		alternative_free(ball);
		printf("ball freed\n");
	}	
	count--;
	}

	printf("\nball failed allocation: %d times\n",failed);
	printf("ball successfully allocated: %d times\n",success);
	

	return 0;
}
