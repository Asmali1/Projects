//MOHAMED ASMALi
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include "debug.h"
#include "node.h"
#include "linkedListFile.h"
#include "altmem.h"
//frees node and prints it out
static void freeNode(void *data)
{
    static int counter;
    counter++;
    Node *node = data;
    alternative_free(node);
    node = NULL;
    if (TEXT)
        printf("DIAGNOSTIC: %d nodes freed\n", counter);
}
//checks if node was allocated
static bool checkAllocatedNode(Node *node){
      return node!=NULL;
}
//allocates a node dynamically
static Node *allocateNode(){
	static int counter;                           
    // print counter
    Node *node;
    node = alternative_malloc(sizeof(Node));
	if(checkAllocatedNode(node)){
		counter++;
		if (TEXT)printf("DIAGNOSTIC: %d nodes allocated.\n", counter);
	}	
    // if doesnt allocate
    return node;
}
//inserts ball into list
 bool insert(void *p2head, void *data, ComparisonFunction goesInFrontOf, int text)
{
static int count;
    Node **head=p2head;
    Node* newNode;
	newNode = allocateNode(); 
	if(newNode == NULL) {
        if(TEXT)printf("ERROR: linkedlist.c: Failed to malloc a Node\n");
        // freeNode(newNode);
        return 0;
    }	
    newNode->data=data;
    newNode->next=NULL;
    if(*head==NULL || goesInFrontOf(data,(*head)->data)){
        newNode->next=*head;
        *head=newNode;
    } else{
        Node *traverse=*head;
        while(traverse->next!=NULL && !goesInFrontOf(data,traverse->next->data)){
            traverse=traverse->next;
        }
        newNode->next=traverse->next;
        traverse->next=newNode;
    }
	return 1;
}
// added this to check ball insertion
static void checkInsert(int returnedValue){
	if(returnedValue==-1){
	if(TEXT)printf("INSERTION FAILED\n");
	}

}
//deletes ball from a linkedlist if it meets the crteria and disposes it.
 int deleteSome(void *p2head, CriteriaFunction mustGo,ActionFunction disposal, int text){
    Node *freedBall;
    static int count;
    Node **head=p2head;
    struct Node *temp = *head, *prev;
    while(temp!=NULL){
        if(mustGo(temp->data)){
            if(temp==*head){
            disposal(temp->data);
            *head=temp->next;
            temp=*head;
            count++;
		if (TEXT)printf("DIAGNOSTIC: %d nodes freed.\n", count);
            } else{
                disposal(temp->data);
            count++;
		if (TEXT)printf("DIAGNOSTIC: %d nodes freed.\n", count);
            prev->next=temp->next;
            alternative_free(temp);
            temp=prev->next;
            }
        } else{
            prev=temp;
            temp=temp->next;
        } 
    }

	return count;
}
//sorts function using bubble sort
 void sort(void *hptr, ComparisonFunction cf){
    Node *done=NULL;
    Node *traversedNode=hptr;
    while(hptr!=done){
        traversedNode=hptr;
        while(traversedNode->next!=done){
            if(cf(traversedNode->next->data,traversedNode->data)){
                void *tempVal=traversedNode->data;
                traversedNode->data=traversedNode->next->data;
                traversedNode->next->data=tempVal;
            }
            traversedNode=traversedNode->next;
        }
        done=traversedNode;
    }
}
//iterates over function
 void iterate(void *head, ActionFunction doThis){
    Node *traversal=head;
    while(traversal!=NULL){
        doThis(traversal->data);
        traversal=traversal->next;
    }
}