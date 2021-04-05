import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class BeautifulSubList {
    public static void main(String[] args) {
        Sorter sorter = new Sorter();
        boolean flag = true;
        for (int i = 0; i < 20; i++) {
            int[] list = arrayGenerator(10);
            int[] test = Arrays.copyOf(list, list.length);
            Arrays.sort(test);
            list = sorter.mergeSort(list);
            if (!Arrays.equals(list, test)) {
                flag = false;
            }
        }
        System.out.println(flag);

        // // see
        // System.out.println("orignal:");
        // System.out.println(Arrays.toString(list));
        // list = sorter.mergeSort(list);
        // System.out.println("now:");
        // System.out.println(Arrays.toString(list));
    }

    static int[] arrayGenerator(int len) {
        int[] arr = new int[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            arr[i] = r.nextInt(100) + 1;
        }
        return arr;
    }
}

class Sorter {
    void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // switch
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE;
            int index = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < min) {
                    index = j;
                    min = arr[j];
                }
            }
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
    }

    void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] < arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    void shellSort(int[] arr) {
        for (int step = (int) Math.floor(arr.length / 2); step > 0; step = (int) Math.floor(step / 2)) {
            for (int i = step; i < arr.length; i++) {
                int currentVal = arr[i];
                int j = i;
                while (j - step >= 0 && currentVal < arr[j - step]) {
                    arr[j] = arr[j - step];
                    j = j - step;
                }
                arr[j] = currentVal;
            }
        }
    }

    void myShellSort(int[] arr) {
        for (int step = arr.length / 2; step > 0; step /= 2) {
            for (int i = step; i < arr.length; i++) {
                // 接下来就是一个简单的插入排序
                int forward = i - step;
                int later = i;
                while (forward >= 0 && arr[forward] > arr[later]) {
                    int temp = arr[forward];
                    arr[forward] = arr[later];
                    arr[later] = temp;
                    forward -= step;
                    later -= step;
                }
            }
        }
    }

    int[] mergeSort(int[] arr) {
        if (arr.length >= 2) {
            var left = Arrays.copyOfRange(arr, 0, arr.length / 2);
            var right = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
            return merge(mergeSort(left), mergeSort(right));
        } else {
            return arr;
        }
    }

    int[] merge(int[] left, int[] right) {
        int[] res = new int[left.length + right.length];
        int res_index = 0;
        int left_index = 0;
        int right_index = 0;
        while (left_index < left.length && right_index < right.length) {
            if (left[left_index] <= right[right_index]) {
                res[res_index++] = left[left_index++];
            } else {
                res[res_index++] = right[right_index++];
            }
        }

        while (left_index < left.length) {
            res[res_index++] = left[left_index++];
        }
        while (right_index < right.length) {
            res[res_index++] = right[right_index++];
        }
        return res;
    }

    void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
    }

    int partition(int[] arr, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                int temp = arr[i];
                arr[i] = arr[pivot];
                arr[pivot] = temp;
            }
        }
        return index - 1;
    }
}