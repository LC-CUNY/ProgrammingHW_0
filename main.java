import java.util.concurrent.Semaphore;
import java.util.Random;

public class Main 
{
    public static void main(String[] args) 
    {
        //Declare a variable with the number of students 
        int NumOfStudents = 6;
        
        // Create semaphores
        SignalSemaphore wakeup = new SignalSemaphore();
        Semaphore seats = new Semaphore(3);
        Semaphore available = new Semaphore(1);
        
        
        // randomly generates program time
        Random studentWait = new Random();
        
        // joins all student threads 
        for (int i = 0; i < NumOfStudents; i++)
        {
            Thread student = new Thread(new Student(studentWait.nextInt(20), wakeup, seats, available, i+1));
            student.start();
        }
        
        // Create and start TA Thread
        Thread ta = new Thread(new TA(wakeup, seats, available));
        ta.start();
    }
}


 // This semaphore is used to wakeup the TA
class SignalSemaphore {
    private boolean signal = false;

    // Use to send the signal
    public synchronized void take() {
        this.signal = true;
        this.notify();
    }

    // Will wait until it receives a signal before continuing
    public synchronized void release() throws InterruptedException{
        while(!this.signal) wait();
        this.signal = false;
    }
}

 // The student thread will alternate between programming and seeking help from the TA
class Student implements Runnable
{       
    // Time to program before asking for help 
    private int programTime;
    
    // Student number
    private int studentNum;

    // Semaphore used to wakeup TA
    private SignalSemaphore wakeup;

    // Semaphore used to wait in seats outside the office
    private Semaphore seats;

    // Mutex lock used to determine if TA is available
    private Semaphore available;

    // Relates to the current thread
    private Thread t;

    // Non-default constructor
    public Student(int program, SignalSemaphore w, Semaphore s, Semaphore a, int num)
    {
        programTime = program;    
        seats = s;
        available = a;
        wakeup = w;
        studentNum = num;
        t = Thread.currentThread();
    }

    /**
      The run method will infinitely loop between programming and
      asking for help until the thread is interrupted
     */
    public void run()
    {
        // Infinite loop
        while(true)
        {
            try
            {
               // Program first
               System.out.println("Student " + studentNum + " has started programming for " + programTime + " seconds.");
               t.sleep(programTime * 1000);
                
               // Checks if TA is available first
               System.out.println("Student " + studentNum + " is checking if TA is available.");
               if (available.tryAcquire())
               {
                   try
                   {
                       // Wakeup TA
                       wakeup.take();
                       System.out.println("Student " + studentNum + " has woke up the TA.");
                       System.out.println("Student " + studentNum + " has started working with the TA.");
                       t.sleep(5500); // holds the process for few seconds 
                       System.out.println("Student " + studentNum + " has stopped working with the TA.");
                   }
                   catch (InterruptedException e)
                   {
                       // error
                       continue;
                   }
                   finally
                   {
                       available.release();
                   }
               }
               else
               {
                   // Check to see if any seats are available
                   System.out.println("Student " + studentNum + " could not see the TA.  Checking for available seats.");
                   if (seats.tryAcquire())
                   {
                       try
                       {
                           // Wait for TA to finish with other student
                           System.out.println("Student " + studentNum + " is sitting outside the office.  "
                                   + "He is #" + ((3 - seats.availablePermits())) + " in line.");
                           available.acquire();
                           System.out.println("Student " + studentNum + " has started working with the TA.");
                           t.sleep(5500); //holds the process for few seconds 
                           System.out.println("Student " + studentNum + " has stopped working with the TA.");
                           available.release();
                       }
                       catch (InterruptedException e)
                       {
                           //unexpected error
                           continue;
                       }
                   }
                   else
                   {
                       System.out.println("Student " + studentNum + " could not see the TA and all seats were taken. Student " + studentNum + " Is back to programming!");
                   }
               }
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}

class TA implements Runnable
{
    // Semaphore use to wait in the seats outside the office
    private Semaphore seats;

    // Mutex lock use to determine if TA is available
    private Semaphore available;

    // Semaphore use to wakeup TA
    private SignalSemaphore wakeup;

    // relate to the current thread
    private Thread t;
    
    public TA(SignalSemaphore w, Semaphore s, Semaphore a)
    {
        t = Thread.currentThread();
        seats = s;
        available = a;
        wakeup = w;
    }
    
    public void run()
    {
        while (true)
        {
            try
            {
                System.out.println("No students left to teach. The TA is will nap.");
                wakeup.release();
                System.out.println("The TA was awoke by a student.");
                
                t.sleep(5500);
                
                // checks If there are other students waiting
                if (seats.availablePermits() != 3)
                {
                    do
                    {
                        t.sleep(5500);
                        seats.release();
                    }
                    while (seats.availablePermits() != 3);                   
                }
            }
            catch (InterruptedException e)
            {
                // error
                continue;
            }
        }
    }
}