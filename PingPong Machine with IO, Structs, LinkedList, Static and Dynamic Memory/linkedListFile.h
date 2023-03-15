//  Mohamed Asmali
/* header file for list */

/* these are the function pointer signatures needed */

#include <stdbool.h>

typedef void (* ActionFunction)( void *data);
typedef bool (* ComparisonFunction)(void *data1, void *data2);
typedef bool (* CriteriaFunction)(void *data);
typedef double (* NumericFunction)(void *data);


/* signatures that the list code provides to the outside world */

/* insert and delete need to be able to change the head pointer so you pass
 * in the address of the head pointer */

/* insert returns FALSE when it fails to do the insert */
bool insert(void *p2head, void *data, ComparisonFunction goesInFrontOf, 
		int text);
/* deleteSome returns the count of how many nodes were deleted. */
int deleteSome(void *p2head, CriteriaFunction mustGo, 
		ActionFunction disposal, int text);

/* The rest do not change the nodes, so you pass in the head
 * pointer and not the address of the head pointer */

/* call do this on everyitem in the list */
void iterate(void *head, ActionFunction doThis);

/* sort the list based on your function */
void sort(void *hptr, ComparisonFunction cf);



