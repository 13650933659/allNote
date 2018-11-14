
public class SortUtil {
	
	/**
	 * 冒泡排序
	 * @param arr	需要排序的数组
	 */
	public static void bubbleSort(int[] arr) {
		int len = arr.length;
		for (int i = 0; i < len - 1; i ++) {
			for (int j = 0; j < len - i - 1; j ++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
	}
	
	/**
	 * 选择排序
	 * @param arr	需要排序的数组
	 */
	public static void selectSort(int[] arr) {
		int len = arr.length;
		for (int i = 0; i < len - 1; i ++) {
			int minIndex = i;
			int minValue = arr[i];
			boolean replace = false;
			
			for (int j = i; j < len - 1; j ++) {
				if (minValue > arr[j + 1]) {
					minIndex = j + 1;
					minValue = arr[j + 1];
					replace = true;
				}
			}
			
			if (replace) {
				int temp = arr[i];
				arr[i] = minValue;
				arr[minIndex]= temp;
			}
		}
	}

	
	/**
	 * 插入排序
	 * @param arr	需要排序的数组
	 */
	public static void insertSort(int[] arr) {
		int len = arr.length;
		
		for (int i = 0; i < len - 1; i ++) {
			int j = i + 1;
			int insertValue = arr[j];
			boolean insert = false;
			for (; j > 0; j --) {
				if (insertValue > arr[j - 1]) {	//找到了合适的位置
					break;
				} else {
					arr[j] = arr[j - 1];
					insert = true;
				}
			}
			
			if (insert) {
				arr[j] = insertValue;
			}
		}
	}
	
	/**
	 * 快速排序
	 * @param arr	需要排序的数组
	 * @param low	数组的起始下标
	 * @param high	数组的终止下标
	 */
	public static void quickSort_foreign(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0 || low >= high) {
			throw new RuntimeException("参数有误！");
		}
 
		// 找出中心点
		int pivot = arr[low + (high - low) / 2];
 
		int start = low;
		int end = high;
		while (start <= end) {
			while (arr[start] < pivot) {	// 从左到右找出第一个比中心点大的元素
				start++;
			}
 
			while (arr[end] > pivot) {		// 从右到左找出比第一个中心点小的元素
				end--;
			}
 
			if (start <= end) {
				int temp = arr[start];
				arr[start] = arr[end];
				arr[end] = temp;
				start++;
				end--;
			}
		}
 
		// 递归调用
		if (low < end) {
			quickSort_foreign(arr, low, end);
		}
 
		if (high > start) {
			quickSort_foreign(arr, start, high);
		}
	}
	
	public static void binaryChop(int l, int r, int val, int arr[]){
		// 首先找到中间的数
		int midIndex = ((r + l)/2);
		int midVal = arr[midIndex];
		if(r >= l){
			//如果要找的数比midVal大
			if(midVal > val){
				// 在arr数组左边数列中找
				binaryChop(l, midIndex - 1, val, arr);
			}else if(midVal < val){
				// 在arr数组右边数列中找
				binaryChop(midIndex + 1, r, val, arr);
			}else if(midVal == val){
				System.out.println("数组arr["+midIndex+"]中的数字是"+arr[midIndex]);
			}
		}else{
			System.out.println("没有找到你要找的数!");
		}
	}
}
