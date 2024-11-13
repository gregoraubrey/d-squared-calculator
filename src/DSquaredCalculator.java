import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.TreeMap;

public class DSquaredCalculator {
    private final int[] numbers;

    public DSquaredCalculator(int size) {
        this.numbers = new int[size];
        for (int i = 0; i < size; i++) {
            this.numbers[i] = i + 1;
        }
    }

    public void calculateAndWriteToFile() {
        int maxDSquaredValue = getMaxDSquaredValue(numbers.length);
        Map<Integer, Integer> dSquaredMap = new HashMap<>();
        for (int i = 0; i <= maxDSquaredValue; i += 2) {
            dSquaredMap.put(i, 0);
        }
        // Run through every permutation of `numbers`, find its d-squared value, and
        // increment the relevant value in the HashMap by 1
        fillOutDSquaredMap(dSquaredMap, numbers);
        String fileName = "occurrences-of-d-squared-values-from-1-to-" + numbers.length + ".txt";
        writeHashMapToFile(dSquaredMap, fileName);
    }

    private int getMaxDSquaredValue(int num) {
        return (num + 1) * num * (num - 1) / 3;
    }

    private void fillOutDSquaredMap(Map<Integer, Integer> hashMap, int[] nums) {
        // Get each permutation one by one, find its d-squared value, and increment the
        // relevant value in the HashMap
        int[] originalOrderNums = nums.clone();
        findPermutations(nums, permutation -> {
            int[] currentPermutation = new int[nums.length];
            int index = 0;
            // Process each permutation here
            for (int num : permutation) {
                currentPermutation[index++] = num;
            }
            int dSquaredValue = getDSquared(originalOrderNums, currentPermutation);
            hashMap.put(dSquaredValue, hashMap.getOrDefault(dSquaredValue, 0) + 1);
        });
    }

    private void findPermutations(int[] arr, Consumer<int[]> consumer) {
        /*
          Generates and processes all permutations of the given array using the provided consumer.

          @param arr the input array
          @param consumer the consumer to process each permutation
         */
        findPermutationsUtil(arr, 0, consumer);
    }

    private void findPermutationsUtil(int[] arr, int index, Consumer<int[]> consumer) {
        /*
          Recursively generates all permutations of the given array and processes each one using the provided consumer.

          @param arr   the input array
          @param index the current index being processed
          @param consumer the consumer to process each permutation
         */
        if (index == arr.length) {
            consumer.accept(arr.clone());
            return;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            findPermutationsUtil(arr, index + 1, consumer);
            swap(arr, index, i);
        }
    }

    private void writeHashMapToFile(Map<Integer, Integer> hashMap, String fileName) {
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(hashMap);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void swap(int[] arr, int i, int j) {
        /*
          Swaps the elements at the given indices in the array.

          @param arr array in which the elements are to be swapped
          @param i   index of the first element to be swapped
          @param j   index of the second element to be swapped
         */
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int getDSquared(int[] numbersInOrder, int[] permutation) {
        /*
          Generates and returns d-squared value by squaring the difference between each value in two given permutations.

          @param numbersInOrder the original permutation (integers 1 to `n` in ascending order)
          @param permutation the current permutation to be compared with the original
          @return the sum of the square of all the differences
         */
        int dSquared = 0;
        for (int i = 0; i < numbersInOrder.length; i++) {
            int difference = permutation[i] - numbersInOrder[i];
            dSquared += difference * difference;
        }
        return dSquared;
    }

    public static void main(String[] args) {
        DSquaredCalculator calculator = new DSquaredCalculator(6);
        calculator.calculateAndWriteToFile();
    }
}
