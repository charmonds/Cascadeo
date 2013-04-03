#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<math.h>

typedef struct{
	char coordinates[11];
}input_t;


int connectivity(input_t vertex[], int n, double mc);
double distance_formula(char v1[], char v2[]);
void build_DFStree(char *matrix, int n, int i,char array[]);
void push(char x, char stack[]);
char pop(char stack[]);

int top = 0;
int discovery[101];
int finish[101];

int main(void) 
{
	FILE *fp;
	FILE *fp_output;
	int j, i = 1,  start_vertex = 0;
	int answer1;
	double max_coverage = 0;
	char *end;
	input_t vertex[103];
	memset(vertex, '\0', sizeof(char)*102*11);
	
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
   	// No vertex[0] //
   	for(i = 1; i < 103; i++){	
   		fgets(vertex[i].coordinates, 11, fp);
   		if (vertex[i].coordinates[0] != '('){
   			if (vertex[i].coordinates[0] != '\n' && strcmp(vertex[i].coordinates, "***END***") != 0){
   				max_coverage = strtod(vertex[i].coordinates, &end);
   				i--;
   			}else break;
   		}else vertex[i].coordinates[5] = '\0';
   		if(feof(fp)) break;
   	}
   	
   	for (j = 1; j < i; j++){
   		if(strcmp(vertex[j].coordinates, vertex[i-1].coordinates) == 0){
   			start_vertex = j;
   			break;
   		}
   	}

   	answer1 = connectivity(vertex, i-2, max_coverage);
   	
   	printf("start vertex = %d\n", start_vertex);
   	printf("max coverage = %f\n", max_coverage);
		if (answer1 == 1) printf("YES\n");
		else printf("NO\n");
   	
	}while(strcmp(vertex[i].coordinates, "***END***") != 0 );
		
	
/*****************************************	
   fp_output = fopen("201005666.txt", "a");
   for(; i < 26; i++){
		if(i == 25) fprintf(fp, "%c=%s", i+65,translate[i]);
		else fprintf(fp, "%c=%s\n", i+65,translate[i]);
	}
	fclose(fp_output);
*****************************************/
	fclose(fp);
	
	return 0;

}

int connectivity(input_t vertex[], int n, double mc){
	
	char matrix[n+1][n+1], i, j;
	char array[n];
	memset(matrix, '0', sizeof(char)*(n+1)*(n+1));
	for( i = 1; i <= n; i++){
		for (j = 1; j <= n ; j++){
			if(j == i )
				matrix[i][j] = '0';
			else{
   			if(distance_formula(vertex[i].coordinates, vertex[j].coordinates) <= mc)
   				matrix[i][j] = '1';
			}
		}
	}
	push('1', array);
	discovery[1]++;
	build_DFStree(&matrix[0][0], n+1, 1, array);
   return 1;
}

double distance_formula(char v1[], char v2[]){
	
	int x1, x2, y1, y2;
	
	x1 = atoi(&v1[1]); y1 = atoi(&v1[3]);
	x2 = atoi(&v2[1]); y2 = atoi(&v2[3]);
	
	
	
	return sqrt((pow((x2-x1), 2) + pow((y2-y1), 2)));
}
void build_DFStree(char *matrix, int n, int i, char array[]){
	int j;
	for ( ; i < n; i++) {
		for ( j = 1; j < n; j++)
			printf("%c ", *(matrix + (n * i) + j));
		printf("\n");
	}
	for ( ; i < n; i++) {
		for ( j = 1; j < n; j++){
			if (*(matrix + (n * i) + j) == 1)
				push(j, array);
				discovery[j]++;
				build_DFStree((matrix + (n * 0) + 0), n+1, j, array);
			else if ( j == n )
		}
	}

}

void push(char x, char stack[]){
	
	stack[top] = x;
	top++;
}

char pop(char stack[]){
	char x;
	if (top == 0) return -1;
	x = stack[top];
	top--;
	return x;
}