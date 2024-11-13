import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.TreeMap;

public class DSquaredCalculator {
    private final int[] numbers;
    private final String fileName;

    public DSquaredCalculator(int size, String fileName) {
        this.numbers = new int[size];
        for (int i = 0; i < size; i++) {
            this.numbers[i] = i + 1;
        }
        this.fileName = fileName;
    }

    public void calculateAndWriteToFile() {
        int maxDSquaredValue = getMaxDSquaredValue(numbers.length);

        // Create a hash table where each key is an even number
        // from 0 up to the maximum d-squared value since those are
        // all the possible d-squared values (a d-squared value will
        // always be an even number)
        Map<Integer, Integer> dSquaredMap = new HashMap<>();
        for (int i = 0; i <= maxDSquaredValue; i += 2) {
            dSquaredMap.put(i, 0);
        }

        // Run through every permutation of `numbers`, and in each case
        // calculate the d-squared value, then increment the relevant value
        // in the hash table by 1
        fillOutDSquaredMap(dSquaredMap, numbers);

        writeMapToFile(dSquaredMap, fileName);
    }

    private int getMaxDSquaredValue(int num) {
        // This formula always gives the highest possible d-squared
        // value from 1 to `num`
        return (num + 1) * num * (num - 1) / 3;
    }

    private void fillOutDSquaredMap(Map<Integer, Integer> hashMap, int[] nums) {
        // Get each permutation one by one, find the d-squared value,
        // and increment the relevant value in the hash table
        int[] originalOrderNums = nums.clone();

        Consumer<int[]> consumer = permutation -> {
            int dSquaredValue = getDSquared(originalOrderNums, permutation);
            hashMap.put(dSquaredValue, hashMap.get(dSquaredValue) + 1);
        };

        findPermutations(nums, 0, consumer);
    }

    private void findPermutations(int[] arr, int currentIndex, Consumer<int[]> consumer) {
        // Recursively generate all permutations of the given array
        // and in each case pass the permutation to the consumer to
        // be processed
        if (currentIndex == arr.length) {
            // When a complete permutation is formed, pass that
            // order to the consumer for processing
            consumer.accept(arr.clone());
            return;
        }

        for (int i = currentIndex; i < arr.length; i++) {
            swap(arr, currentIndex, i);
            findPermutations(arr, currentIndex + 1, consumer);
            swap(arr, currentIndex, i);
        }
    }

    private void swap(int[] arr, int i, int j) {
        // Swap the elements at the given indices in the array
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int getDSquared(int[] numbersInOrder, int[] permutation) {
        // Generate and return the d-squared value by squaring the
        // difference between each value in two given permutations
        int dSquared = 0;
        for (int i = 0; i < numbersInOrder.length; i++) {
            int difference = permutation[i] - numbersInOrder[i];
            dSquared += difference * difference;
        }
        return dSquared;
    }

    private void writeMapToFile(Map<Integer, Integer> hashMap, String fileName) {
        // Write the key-value pairs of the given hash table to a file in the
        // form `key,value` with one key-value pair per line
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(hashMap);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
