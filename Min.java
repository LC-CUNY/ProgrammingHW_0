package threads;

public class Min extends Thread{
	int[] nums;
	public Min(int[] nums) {
		this.nums = nums;
	}
	public void run() {
		int min = nums[0];
		for (int i = 0; i < nums.length; i++) {
			min = Math.min(min, nums[i]);
		}
		System.out.println("The minimum value is " + min);
	}
}