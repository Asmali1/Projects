// Mohamed Asmali
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "structs.h"
#include "p1.h"
#include "sim.h"
#include "node.h"
#include "debug.h"
#include "n2.h"


//this prototype tests different command line inputs
bool commandLineCheck(int argc){
	if(argc<2){
		printf("ERROR: Insufficient arguments (argc is 1)\n");
		return 0;
	}
	if(argc==3){ 
		printf("ERROR: Bonus code is not present\n");
		return 0;
	}
	if(argc==3 && TEXT) {
	printf("ERROR: Lab is in Text mode and argc is 3\n");
		return 0;
	}
	// if(argc==2){
	// 	return 1;
	// }
	return 1;
}
int main(int argc, char *argv[])
{
double start, runtime;
	FILE *read;
	start = now(); // this is the very first executable statement
	if (commandLineCheck(argc))
	{
		read=fopen(argv[1],"r");
		if(read==NULL){
			printf("ERROR: Unable to open %s for reading.\n",argv[1]);
			fclose(read);
		} 
		else{
		printf("DIAGNOSTIC: Successfully opened %s for reading.\n",argv[1]);
		fclose(read);
		}
	}
	// fclose(read);
	printf("DIAGNOSTIC: Input file closed.\n");
	/* at this point we are done, graphics has been torn down*/
	runtime = now() - start;
	/* after graphics has been torn down, we can freely print */
	printf("Total runtime is %.9lf seconds\n", runtime);

	return 0;
}
