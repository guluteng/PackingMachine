package parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Permutation {
    private List<double[]> allSorts;
        
    private void permutation(double[] nums, int start, int end) {
        if (start == end) { // 当只要求对数组中一个数字进行全排列时，只要就按该数组输出即可
            double[] newNums = new double[nums.length]; // 为新的排列创建一个数组容器
            for (int i=0; i<=end; i++) {
                newNums[i] = nums[i];
            }
            allSorts.add(newNums); // 将新的排列组合存放起来
        } else {
            for (int i=start; i<=end; i++) {
                double temp = nums[start]; // 交换数组第一个元素与后续的元素
                nums[start] = nums[i];
                nums[i] = temp;
                permutation(nums, start + 1, end); // 后续元素递归全排列
                nums[i] = nums[start]; // 将交换后的数组还原
                nums[start] = temp;
            }
        }
    }
        
    public static void main(String[] args) {
        double[] numArray = {10, 2, 3};
        Permutation p=new Permutation();
        List<double[]> all=p.getPermutation(numArray);
        for(double[] e:all){
        	System.out.println(Arrays.toString(e));
        } 
    }
    
    public List<double[]> getPermutation(double[] numArray){
    	allSorts= new ArrayList<double[]>();
    	permutation(numArray, 0, numArray.length - 1);
    	return allSorts;
    }
}
