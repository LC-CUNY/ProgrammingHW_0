#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

//Question 1 Jonathan Fabian

int
main ()
{

//initializes the number num = 0

  int num = 0;

  //declare the parent id

  pid_t p;

  do

    {

      //get the number

      printf ("Please enter a number greater than 0 for given program: ");

      scanf ("%d", &num);

    }
  while (num <= 0);

  //initialize the parent id to fork function

  p = fork ();

  //if parent id is equal to 0

  if (p == 0)

    {

      printf ("Child is working...\n");

      printf ("%d\n", num);

      /*this is for check and print all positive

         numbers will finally reach 1 */

      while (num != 1)

	{

	  if (num % 2 == 0)

	    {

	      num = num / 2;

	    }

	  else if (num % 2 == 1)

	    {

	      num = 3 * (num) + 1;

	    }

	  printf ("%d\n", num);

	}

      printf ("Child process is completed.\n");

    }

  else

    {

      printf ("Parents process is waiting on child process...\n");

      //call the waiting function

      wait ();

      printf ("Parent process is completed.\n");

    }

  return 0;

}
