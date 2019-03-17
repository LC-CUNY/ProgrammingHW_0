package threads;
import java.lang.Thread;


public class av extends Thread{
	int[] nums;
	public av(int[] nums) {
		this.nums = nums;
	}
	public void run() {
		int tot = 0;
		for (int i = 0; i < nums.length; i++){
			tot += nums[i];
		}
		System.out.println("The average value is " + tot / nums.length);
	}

