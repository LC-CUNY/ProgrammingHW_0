// Findlay Edwards Q3
package threads;

public class Main {

	public static void main(String[] args) {
		int [] nums = {90,81,78,95,79,72,85};
		Thread a = new av(nums);
		Thread a1 = new Min(nums);
		Thread a2 = new max(nums);
		a.start();
		a1.start();
		a2.start();
	}

}