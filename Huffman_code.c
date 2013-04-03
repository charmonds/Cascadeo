#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>

typedef struct {
	char alphabet;
	int num;
}LowtoLar_t;

typedef struct binTree {
	LowtoLar_t beta;
	struct binTree *next;
	struct binTree *left;
	struct binTree *right;	
}binTree_t;

void freqSort(int array[], int m);
void insertNode(binTree_t **head, binTree_t *new_node);
void forest(binTree_t *head);
void preorder(binTree_t *root);
void inorder(binTree_t *root);
void encoding(char arr[], int low, int high);
int middle();
void cleaning(binTree **head);

char prefix[50];
char infix[50];
int j = 0;
char translate[26][15];

int main(void) 
{
	FILE *fp;
	FILE *fp_output;
	char letter;
	char ascii;
	int frequency[26];
	int i = 0;
	memset(frequency, 0, 26*sizeof(int));
	memset(prefix, '\0', 26*sizeof(char));
	memset(infix, '\0', 26*sizeof(char));
	memset(translate, '\0', sizeof(char)*26*15);

/*****************************************/	
	fp_output = fopen("201005666.txt", "w");
	fclose(fp_output);
/*****************************************/
	// Checks if test.txt exists
	if((fp = fopen("test.txt", "r")) == NULL) {
		printf("Can't open file\n");
		exit(1);
	}
	do{
		letter = fgetc(fp);
		
		if( letter == '*'){
			letter = fgetc(fp);
			if( letter == '*'){
				letter = fgetc(fp);
				if( letter == '*'){
					letter = fgetc(fp);
					if( letter == 'E'){
						letter = tolower(letter);
						ascii = letter % 97;
						frequency[ascii]++;
						letter = fgetc(fp);
						if( letter == 'N'){
							letter = tolower(letter);
							ascii = letter % 97;
							frequency[ascii]++;
							letter = fgetc(fp);
							if( letter == 'D'){
								letter = tolower(letter);
   							ascii = letter % 97;
   							frequency[ascii]++;
								letter = fgetc(fp);
								if( letter == '*'){
									letter = fgetc(fp);
									if( letter == '*'){
										letter = fgetc(fp);
      								if( letter == '*'){
      									frequency['e' % 97]--;
      									frequency['n' % 97]--;
      									frequency['d' % 97]--;
      									break;
      								}
      							}
      						}
							}
						}
					} 	
				}
			}	
		}
		if(isalpha(letter)){
			letter = tolower(letter);
			ascii = letter % 97;
			frequency[ascii]++;
			
		}
		
		if(feof(fp)) break;

   }while (1);
   
   freqSort(frequency, 26);
   
   fp = fopen("201005666.txt", "a");
   for(; i < 26; i++){
		if(i == 25) fprintf(fp, "%c=%s", i+65,translate[i]);
		else fprintf(fp, "%c=%s\n", i+65,translate[i]);
	}
	fclose(fp);

	fclose(fp);
	
	return 0;

}
void freqSort(int array[], int m){
	binTree_t *head = NULL;
	binTree_t *new_node;
	int i;
	
	for(i = 0; i < m; i++){
   	new_node = (binTree_t *)malloc(sizeof(binTree_t));
   	new_node->beta.alphabet = i + 65;
   	new_node->beta.num = array[i];	
   	new_node->next = NULL;
   	new_node->left = NULL;
   	new_node->right = NULL;
   	
   	insertNode(&head,new_node);
   }
	
	forest(head);

	
}

void forest(binTree_t *head){
	binTree_t *rover = head;
	binTree_t *new_node = NULL;
	binTree_t *root = NULL;
	int i, k = 0;	
		
	while(1){
		if(rover->next == NULL){
			root = rover;
			break;
		}
		
		new_node = (binTree_t *)malloc(sizeof(binTree_t));
   	new_node->beta.alphabet = k + 97;
   	new_node->beta.num = rover->beta.num + rover->next->beta.num;	
   	new_node->next = NULL;
   	new_node->left = rover;
   	new_node->right = rover->next;
   	k++;
   	insertNode(&rover, new_node);
   	
   	for(i = 0; i < 2; i++)
   		rover = rover->next;
	}
	
	preorder(root);
	j = 0;
	inorder(root);
	j = 0;
	
	encoding(infix, 0, strlen(infix) - 1);
/*********************************************CS32 MP2 by: Charmond Santiago 201005666*********************************************/
	cleaning(&head);
	
	binTree_t *current_node;
	current_node = head;
	while(current_node != NULL) {
		printf("%c = %d\n", current_node->beta.alphabet, current_node->beta.num);
		current_node = current_node->next;	
	}
}
void cleaning(binTree_t **head){
	binTree_t *terminator = NULL;
	
	
	while(*head != NULL) {
		terminator = *head;
		*head = (*head)->next;
		free(terminator);	
	}
}
void encoding(char arr[], int low, int high){
	int mid, k;
	if(low < high) {
		mid = middle();
		for(k = low; k < mid; k++){
      	if (infix[k] >= 65 && infix [k]<= 90)
      		strcat(translate[infix[k] % 65], "0");
      }
		encoding(arr,low,mid);
		for(k = mid+1; k <= high; k++){
      	if (infix[k] >= 65 && infix [k]<= 90)
      		strcat(translate[infix[k] % 65], "1");
      }
		encoding(arr,mid+1,high);
	}
}
int middle(){
	int i = 0;
	while(prefix[j] != infix[i])
		i++;
	j++;
	return i;
}
void preorder(binTree_t *root){
	
	prefix[j] = root->beta.alphabet;
	j++;

	if (root->left != NULL) preorder(root->left);
	
	if (root->right != NULL) preorder(root->right);
	
}

void inorder(binTree_t *root){
	if (root->left != NULL) {

		inorder(root->left);
	}
	
	infix[j] = root->beta.alphabet;
	j++;
	
	if (root->right != NULL) {
		inorder(root->right);
	}
	
}

void insertNode(binTree_t **head, binTree_t *new_node){
	
	if(*head == NULL){
	  	*head = new_node;
	  	new_node->next = NULL;	
	}else{
   	if (new_node->beta.num < (*head)->beta.num){
	   	new_node->next = *head;
   	   *head = new_node;
   	}else{
			binTree_t *rover = *head;
			
			while(rover->beta.num <= new_node->beta.num && rover->next != NULL)
				rover=rover->next;
   				
			if (rover->beta.num >= new_node->beta.num){
				if ( rover->beta.alphabet > new_node->beta.alphabet || rover->beta.num > new_node->beta.num){
					binTree_t *insert = *head;
      				
					while( insert->next != rover)
						insert = insert->next;
      					
      			new_node->next = rover;
      			insert->next = NULL;
      			insert->next = new_node;
      		}else if ( rover->next == NULL){
     				rover->next = new_node;
     				new_node->next = NULL;
     			}else{
	     			new_node->next = rover->next;
	     			rover->next = NULL;
	     			rover->next = new_node;
	     		}
   		}else{
   			rover->next = new_node;
   			new_node->next = NULL;
   		}
   	}	
	}
}