#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

//Frank Filelli Q4
int main()

{



int f=1, g=1, pid, n=f+g,i;

int sum=1;


printf("Enter the number to fibonacci sequence\n");
scanf("%d ", &i);

pid = fork();
if (pid == 0)
{
    printf("Fibonacci sequence is\n");
    printf("0 %d ",n);
    while (i>0) {
        n=f+g;
        printf("%d ", n);
        f=g;
        g=n;
        i--;
	if (i == 0) {
        }
    }
}
    else 
    {
        printf("Parent is waiting for child to complete...\n");
        waitpid(pid, NULL, 0);
        printf("\nParent ends");
    }
    return 0;
}
