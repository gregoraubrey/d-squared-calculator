import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("This program calculates the distribution of d-squared values "
                + "for all permutations of numbers from 1 to n "
                + "and writes the results to a text file.");

        Scanner scanner = new Scanner(System.in);
        int n = -1;

        while (n <= 0) {
            System.out.print("Please enter a positive integer (n): ");
            boolean isIntegerInput = scanner.hasNextInt();
            if (isIntegerInput) {
                int input = scanner.nextInt();
                if (input > 0) {
                    n = input;
                }
            }
            else {
                System.out.println("Invalid input: the input must be an integer greater than 0.");
                scanner.next();
            }
        }
        scanner.close();

        String fileName = "d-squared-from-1-to-" + n + ".txt";
        DSquaredCalculator calculator = new DSquaredCalculator(n, fileName);
        calculator.calculateAndWriteToFile();

        String filePath = System.getProperty("user.dir") + "/d-squared-from-1-to-" + n + ".txt";
        System.out.println("The d-squared values have been calculated and written to disk: " + filePath);
    }
}
