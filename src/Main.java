import java.util.Arrays;
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7};
        int[][] allPermutations = getPermutations(numbers);


//        printAllPermutations(allPermutations);
//        String fileName = "permutations-from-1-to-" + numbers.length + ".txt";
//        writeAllPermutationsToFile(allPermutations, fileName);


        // Sort the array by d-squared value in ascending order
        Arrays.sort(allPermutations, Comparator.comparingInt(p -> getDSquared(allPermutations[0], p)));
        String fileName = "d-squared-values-from-1-to-" + numbers.length + ".txt";
        writeAllDSquaredValuesToFile(allPermutations, fileName);
    }

    private static int[][] getPermutations(int[] arr) {
        /*
          Generates and returns all permutations of the given array.

          @param arr the input array
          @return the list of all permutations
         */
        int totalPermutations = 1;
        // Use n! to calculate how many possible permutations there are
        for (int num : arr) {
            totalPermutations *= num;
        }
        int[][] allPermutations = new int[totalPermutations][arr.length];
        getPermutationsUtil(arr, 0, allPermutations, 0);
        return allPermutations;
    }

    private static int getPermutationsUtil(int[] arr, int index, int[][] allPermutations, int count) {
        /*
          Recursively generates all permutations of the given array.

          @param arr   the input array
          @param index the current index being processed
          @param allPermutations the array to store all permutations
          @param count the current count of permutations
         */
        if (index == arr.length) {
            allPermutations[count] = arr.clone();
            return count + 1;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            count = getPermutationsUtil(arr, index + 1, allPermutations, count);
            swap(arr, index, i);
        }
        return count;
    }

    private static void swap(int[] arr, int i, int j) {
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

    private static int getDSquared(int[] numbersInOrder, int[] permutation) {
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

    private static void printAllPermutations(int[][] allPermutations) {
        /*
          Prints all permutations of the given array list in order of d-squared value (ascending).

          @param allPermutations an array list of all the permutations for the numbers 1 to `n`
         */
        int[] originalPermutation = allPermutations[0];
        Arrays.sort(allPermutations, Comparator.comparingInt(p -> getDSquared(originalPermutation, p)));

        for (int i = 0; i < allPermutations.length; i++) {
            // Print which permutation this is
            System.out.println("Permutation " + (i + 1) + ": " + Arrays.toString(allPermutations[i]));
            // Print the d-squared value for the current permutation against the original one
            int dSquared = getDSquared(originalPermutation, allPermutations[i]);
            System.out.println("d-squared = " + dSquared);
            // Print a line of whitespace
            System.out.println();
        }
    }

    private static void writeAllPermutationsToFile(int[][] allPermutations, String fileName) {
        /*
          Writes all permutations to a file in order of d-squared value (ascending).

          @param allPermutations an array list of all the permutations for the numbers 1 to `n`
          @param fileName the name of the file to write the permutations to
         */
        int[] originalPermutation = allPermutations[0];
        Arrays.sort(allPermutations, Comparator.comparingInt(p -> getDSquared(originalPermutation, p)));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < allPermutations.length; i++) {
                writer.write("Permutation " + (i + 1) + ":\n");
                writer.write(Arrays.toString(allPermutations[i]) + "\n");
                writer.write("d-squared = " + getDSquared(originalPermutation, allPermutations[i]) + "\n\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void writeAllDSquaredValuesToFile(int[][] sortedPermutations, String fileName) {
        /*
          Writes lines to a file in the format `x,y` where x is the last permutation to have a certain d-squared value
          and y is said d-squared value.

          @param sortedPermutations an array of all the permutations for the numbers 1 to `n` sorted by d-squared value in ascending order
          @param fileName the name of the file to write to
         */
        int highestDSquaredSoFar = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write lines with the last permutation to have a certain d-squared value
            // separated by a comma from said d-squared value
            for (int i = 1; i < sortedPermutations.length; i++) {
                int dSquared = getDSquared(sortedPermutations[0], sortedPermutations[i]);
                if (dSquared > highestDSquaredSoFar) {
                    writer.write(i + "," + highestDSquaredSoFar + "\n");
                    highestDSquaredSoFar = dSquared;
                }
            }
            // Write the final permutation and its d-squared value
            writer.write(sortedPermutations.length + "," + getDSquared(sortedPermutations[0], sortedPermutations[sortedPermutations.length - 1]));
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
