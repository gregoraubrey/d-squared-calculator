import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4};
        ArrayList<ArrayList<Integer>> allPermutations = getPermutations(numbers);
        printAllPermutations(allPermutations);
//        String fileName = "permutations-from-1-to-" + String.valueOf(numbers.length);
//        writeAllPermutationsToFile(allPermutations, fileName);
    }

    private static ArrayList<ArrayList<Integer>> getPermutations(int[] arr) {
        /*
          Generates and returns all permutations of the given array.

          @param arr the input array
          @return the list of all permutations
         */
        ArrayList<ArrayList<Integer>> allPermutations = new ArrayList<>();
        getPermutationsUtil(arr, 0, allPermutations);
        return allPermutations;
    }

    private static void getPermutationsUtil(int[] arr, int index, ArrayList<ArrayList<Integer>> allPermutations) {
        /*
          Recursively generates all permutations of the given array.

          @param arr   the input array
          @param index the current index being processed
         */
        if (index == arr.length) {
            // Base case: add the current permutation to the list
            ArrayList<Integer> permutation = new ArrayList<>();
            for (int num : arr) {
                permutation.add(num);
            }
            allPermutations.add(permutation);
            return;
        }

        for (int i = index; i < arr.length; i++) {
            // Swap the current element with the element at index
            swap(arr, index, i);
            // Recursively generate permutations
            getPermutationsUtil(arr, index + 1, allPermutations);
            // Swap back to restore the original order
            swap(arr, index, i);
        }
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

    private static int getDSquared(ArrayList<Integer> numbersInOrder, ArrayList<Integer> permutation) {
        /*
          Generates and returns d-squared value by squaring the difference between each value in two given permutations.

          @param numbersInOrder the original permutation (integers 1 to `n` in ascending order)
          @param permutation the current permutation to be compared with the original
          @return the sum of the square of all the differences
         */
        int dSquared = 0;
        for (int i = 0; i < numbersInOrder.size(); i++) {
            int difference = permutation.get(i) - numbersInOrder.get(i);
            dSquared += difference * difference;
        }
        return dSquared;
    }

    private static void printAllPermutations(ArrayList<ArrayList<Integer>> allPermutations) {
        /*
          Prints all permutations of the given array list in order.

          @param allPermutations an array list of all the permutations for the numbers 1 to `n`
         */
        for (int i = 0; i < allPermutations.size(); i++) {
            // Print which permutation this is
            System.out.println(i + 1);
            // Print the current permutation
            System.out.println(allPermutations.get(i));
            // Print the d-squared value for the current permutation against the original one
            int dSquared = getDSquared(allPermutations.getFirst(), allPermutations.get(i));
            System.out.println("d-squared = " + dSquared);
            // Print a line of whitespace
            System.out.println();
        }
    }
}
