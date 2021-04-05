import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Sort {
    public static void main(String[] args) {
        Sorter sorter = new Sorter();
        boolean flag = true;
        for (int i = 0; i < 20; i++) {
            int[] list = arrayGenerator(10);
            int[] test = Arrays.copyOf(list, list.length);
            Arrays.sort(test);
            sorter.bucketSort(list);
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
                swap(arr, i, index);

                index++;
            }
        }
        swap(arr, index - 1, pivot);
        return index - 1;
    }

    void buildHeap(int[] arr) {
        for (int i = arr.length / 2; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
    }

    void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 堆调整
     * 
     * @param arr
     * @param index
     */
    void heapify(int[] arr, int index, int end) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;
        if (left < end && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < end && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != index) {
            swap(arr, index, largest);
            heapify(arr, largest, end);
        }
    }

    void heapSort(int[] arr) {
        buildHeap(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, i, 0);
            heapify(arr, 0, i);
        }
    }

    void countingSort(int[] arr, int maxValue) {
        int[] res = new int[maxValue + 1];
        for (int i = 0; i < arr.length; i++) {
            res[arr[i]]++;
        }
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            if (res[i] > 0) {
                arr[index++] = i;
            }
        }
    }

    void bucketSort(int[] arr) {
        int bucketSize = 10;
        ArrayList[] bucket = new ArrayList[bucketSize];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<>();
        }
        for (int i = 0; i < arr.length; i++) {
            bucket[(arr[i] - 1) / 10].add(arr[i]);
        }
        for (int i = 0; i < bucket.length; i++) {
            Collections.sort(bucket[i]);
        }
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i].size(); j++) {
                arr[index++] = (int) bucket[i].get(j);
            }
        }
    }
}