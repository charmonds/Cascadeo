#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>

typedef struct {
	char class_list[20][100];
	int courses;
}classList_t;

classList_t get_List(char input[], FILE *fp);
void compare(classList_t classified, FILE *fp);
void print_schedule(int sequence[], int crayola[], int n, FILE *fp);

int main(void) 
{
	FILE *fp;
	FILE *fp_output;
	classList_t testing;
	char input [100];
	

/*****************************************/	
	fp_output = fopen("201005666.txt", "w");
	fclose(fp_output);
/*****************************************/
	// Checks if test.txt exixts
	if((fp = fopen("test.txt", "r")) == NULL) {
		printf("Can't open file\n");
		exit(1);
	}
	do{
   	testing = get_List(input, fp);
   	if (testing.class_list[testing.courses][0] == '\n' || testing.courses == 20) { compare(testing, fp_output);
   	}
   	else if (testing.class_list[testing.courses][0] == 'E') {
	   	compare(testing, fp_output);
	   	break;
	   }

 
   }while (1);

	// Closes the opened file, test.txt
	fclose(fp);
	
	return 0;

}

classList_t get_List(char input[], FILE *fp) {
	classList_t classified;
	classified.courses = 0;
	int i;
	
	for(i = 0; i < 20; i++) {
   		fgets(input, 100, fp);
   		strcpy(&classified.class_list[i][0], input);
   		
   		if (classified.class_list[i][0] == '\n'|| classified.class_list[i][0] == 'E') {
	   		classified.class_list[i][1] = '\0';
	   		break;
	   	}
   		classified.class_list[i][strlen(input)] = '\0';
   		
   		classified.courses++;
   		if (feof(fp)) break;
   }
   //printf("%d\n", classified.courses);	
   
   /*for(i = 0; i < classified.courses; i++)
   			printf("%s", &classified.class_list[i][0]);*/
	return classified;
}

void compare (classList_t classified, FILE *fp){
	
	int i,j, k, l;
	char s1[4], s2[4];
	int matrix[classified.courses][classified.courses];
	memset(matrix, 0, sizeof(int)*classified.courses*classified.courses);
	
	int colors = 1;
	int crayola[classified.courses];
	memset(crayola, 0, classified.courses*sizeof(int));
	crayola[0] = colors;
	
	int sequence[classified.courses];
	char course_num[3];
	int number;

	
	for (i = 0; i < classified.courses; i++) {
		strncpy(course_num, classified.class_list[i], 3);
		if(isspace(course_num[2])){
			course_num[2] = '\0';
			number = atoi(course_num);
			sequence[i] = number;
		}else{
			course_num[1] = '\0';
			number = atoi(course_num);
			sequence[i] = number;
		}
		if (number < 10){
   		for (j = 2; j < strlen(classified.class_list[i]); j+=4) {
   			for (k = 0; k < classified.courses; k++) {
	   			strncpy(course_num, classified.class_list[k], 3);
         		if(isspace(course_num[2])){
         			course_num[2] = '\0';
         			number = atoi(course_num);
         		}else{
         			course_num[1] = '\0';
         			number = atoi(course_num);
         		}
	   			if (number < 10){
      				for (l = 2; classified.class_list[k][l-1] !='\0'; l+=4) {
      					if (i == k) {
      						matrix[i][k] = 0;
      						break;
      					
      					}else {
	      					strncpy(s1, &classified.class_list[i][j], 3);
         	   			s1[3] = '\0';
         	   			strncpy(s2, &classified.class_list[k][l], 3);
         	   			s2[3] = '\0';
         	   			if (isalpha(s1[0]) && isalpha(s2[0])){
   	      					for( int m = 0; m < 3; m++){
            	   				s1[m] = toupper(s1[m]);
            	   				s2[m] = toupper(s2[m]);
            	   			}
            	   		}
      	   				if (s1[0] == '\0' || s2[0] == '\0') break;
      	   				else{
            					if (strcmp(s1, s2) == 0) {
            						matrix[i][k] = 1;
            						break;
            					}else
            						if (matrix[i][k] == 1) break;
            						matrix[i][k] = 0;
            				}
         				}
         			}
					}else {
	         		for (l = 3; classified.class_list[k][l-1] !='\0'; l+=4) {
         				if (i == k) {
         					matrix[i][k] = 0;
         					break;
/*********************************************CS32 MP1 by: Charmond Santiago 201005666*********************************************/      					
         				}else {
         	   			strncpy(s1, &classified.class_list[i][j], 3);
         	   			s1[3] = '\0';
         	   			strncpy(s2, &classified.class_list[k][l], 3);
         	   			s2[3] = '\0';
         	   			if (isalpha(s1[0]) && isalpha(s2[0])){
   	      					for( int m = 0; m < 3; m++){
            	   				s1[m] = toupper(s1[m]);
            	   				s2[m] = toupper(s2[m]);
            	   			}
            	   		}
      	   				if (s1[0] == '\0' || s2[0] == '\0') break;
      	   				else{
            					if (strcmp(s1, s2) == 0) {
            						matrix[i][k] = 1;
            						break;
            					}else
            						if (matrix[i][k] == 1) break;
            						matrix[i][k] = 0;
            				}
           				}
           			}
        			}
				}
  			}
   	}else {
	   	for (j = 3; j < strlen(classified.class_list[i]); j+=4) {
   			for (k = 0; k < classified.courses; k++) {
	   			strncpy(course_num, classified.class_list[k], 3);
         		if(isspace(course_num[2])){
         			course_num[2] = '\0';
         			number = atoi(course_num);
         		}else{
         			course_num[1] = '\0';
         			number = atoi(course_num);
         		}
   				if (number < 10){
      				for (l = 2; classified.class_list[k][l-1] !='\0'; l+=4) {
      					if (i == k) {
      						matrix[i][k] = 0;
      						break;
      					
      					}else {
      	   				strncpy(s1, &classified.class_list[i][j], 3);
         	   			s1[3] = '\0';
         	   			strncpy(s2, &classified.class_list[k][l], 3);
         	   			s2[3] = '\0';
         	   			if (isalpha(s1[0]) && isalpha(s2[0])){
   	      					for( int m = 0; m < 3; m++){
            	   				s1[m] = toupper(s1[m]);
            	   				s2[m] = toupper(s2[m]);
            	   			}
            	   		}
      	   				if (s1[0] == '\0' || s2[0] == '\0') break;
      	   				else{
            					if (strcmp(s1, s2) == 0) {
            						matrix[i][k] = 1;
            						break;
            					}else
            						if (matrix[i][k] == 1) break;
            						matrix[i][k] = 0;
            				}
         				}
         			}
         		}else {
	         			for (l = 3; classified.class_list[k][l-1] !='\0'; l+=4) {
      					if (i == k) {
      						matrix[i][k] = 0;
      						break;
      					
      					}else {
      	   				strncpy(s1, &classified.class_list[i][j], 3);
         	   			s1[3] = '\0';
         	   			strncpy(s2, &classified.class_list[k][l], 3);
         	   			s2[3] = '\0';
         	   			if (isalpha(s1[0]) && isalpha(s2[0])){
   	      					for( int m = 0; m < 3; m++){
            	   				s1[m] = toupper(s1[m]);
            	   				s2[m] = toupper(s2[m]);
            	   			}
            	   		}
      	   				if (s1[0] == '\0' || s2[0] == '\0') break;
      	   				else{
            					if (strcmp(s1, s2) == 0) {
            						matrix[i][k] = 1;
            						break;
            					}else
            						if (matrix[i][k] == 1) break;
            						matrix[i][k] = 0;
            				}
         				}
         			}
   				}
   			}
   		}
   	}
	}
		
	/*for (i = 0; i < classified.courses; i++) {
		for (j = 0; j < classified.courses; j++)
			printf("%d ", matrix[i][j]);
		printf("\n");
	}*/
	
	for (j = 1; j < classified.courses; j++) {
		for (i = 0, colors = 1; i < j; i++) {
			if(matrix[i][j]) {
				if (j == 1){
					colors++;
					crayola[j] = colors;
					break;
				}else if ( crayola [j] != 0 ){
					for (k = j-1; k > 0; k--){
						if (crayola[k] == crayola[j]){
							if (matrix[k][j] != 0){
         					colors++;
         					crayola[j] = colors;
 							}
						}	
					}
				}else {
					colors++;
					crayola[j] = colors;
				}
			}else crayola [j] = colors;
		}
	}
	
	/*for (i = 0; i < classified.courses; i++)
		printf("C[%d] = %d\n", i+1, crayola[i]);  */
	print_schedule(sequence, crayola, classified.courses, fp);
			
}

void print_schedule(int sequence[], int crayola[], int n, FILE *fp){
	int i;
	fp = fopen("201005666.txt", "a");
	
	for (i = 0; i < n; i++) {
		switch (crayola[i]){
			
			case 1: fprintf(fp, "%d MON 8:00-10:00 AM\n", sequence[i]); break;
			case 2: fprintf(fp, "%d MON 10:00-12:00 NN\n", sequence[i]); break;
			case 3: fprintf(fp, "%d MON 12:00-2:00 PM\n", sequence[i]); break;
			case 4: fprintf(fp, "%d MON 2:00-4:00 PM\n", sequence[i]); break;
			case 5: fprintf(fp, "%d MON 4:00-6:00 PM\n", sequence[i]);	break;
			case 6: fprintf(fp, "%d TUE 8:00-10:00 AM\n", sequence[i]); break;
			case 7: fprintf(fp, "%d TUE 10:00-12:00 NN\n", sequence[i]); break;
			case 8: fprintf(fp, "%d TUE 12:00-2:00 PM\n", sequence[i]); break;
			case 9: fprintf(fp, "%d TUE 2:00-4:00 PM\n", sequence[i]); break;
			case 10: fprintf(fp, "%d TUE 4:00-6:00 PM\n", sequence[i]);	break;
			case 11: fprintf(fp, "%d WED 8:00-10:00 AM\n", sequence[i]); break;
			case 12: fprintf(fp, "%d WED 10:00-12:00 NN\n", sequence[i]); break;
			case 13: fprintf(fp, "%d WED 12:00-2:00 PM\n", sequence[i]); break;
			case 14: fprintf(fp, "%d WED 2:00-4:00 PM\n", sequence[i]); break;
			case 15: fprintf(fp, "%d WED 4:00-6:00 PM\n", sequence[i]);	break;
			case 16: fprintf(fp, "%d THURS 8:00-10:00 AM\n", sequence[i]); break;
			case 17: fprintf(fp, "%d THURS 10:00-12:00 NN\n", sequence[i]); break;
			case 18: fprintf(fp, "%d THURS 12:00-2:00 PM\n", sequence[i]); break;
			case 19: fprintf(fp, "%d THURS 2:00-4:00 PM\n", sequence[i]); break;
			case 20: fprintf(fp, "%d THURS 4:00-6:00 PM\n", sequence[i]);	break;
			case 21: fprintf(fp, "%d FRI 8:00-10:00 AM\n", sequence[i]); break;
			case 22: fprintf(fp, "%d FRI 10:00-12:00 NN\n", sequence[i]); break;
			case 23: fprintf(fp, "%d FRI 12:00-2:00 PM\n", sequence[i]); break;
			case 24: fprintf(fp, "%d FRI 2:00-4:00 PM\n", sequence[i]); break;
			case 25: fprintf(fp, "%d FRI 4:00-6:00 PM\n", sequence[i]);	break;
		}
	}
	if ( i != 0 ) fprintf(fp, "\n");
	
	fclose(fp);
}