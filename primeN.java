//cpm 426 //Tahara B Biva//Question# 2

	import java.util.Scanner;
	public class primeN {
	    public static void main(String[] args){
	 
	        System.out.println("Enter a number:");//to get a number from the user. 
	        Scanner kb = new Scanner(System.in);
	        int i = kb.nextInt();

	        RunPrime runprime = new RunPrime (i);//to call RunPrime class.
	        

	        runprime.SmallerPrimeNumbers();
	    }
	

	static class RunPrime extends Thread {//to implement RunPrime class

	    private int UserInput;

	    RunPrime (int n) {
	        UserInput = n;
	    }

	    public void SmallerPrimeNumbers() {
	        int count = 0;
	        for (int i = 1; i <= UserInput; i++) {//loops to print prime number
	           if (CheckPrime(i)) { // to  check the prime number
	                System.out.println(i);
	            }
	        }
	   }

	    public static boolean CheckPrime (int n) {//to check prime Number
	        for (int i=2 ; i<n ; i++) {
	            if (n%i == 0)
	                return false;
	        }
	        return true;
	    }

}}