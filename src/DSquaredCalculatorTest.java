import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class DSquaredCalculatorTest {
    private DSquaredCalculator calculator;
    private final String fileName = "test_output.txt";

    public static void main(String[] args) {
        DSquaredCalculatorTest test = new DSquaredCalculatorTest();

        Map<Integer, Integer> expectedValuesForN1 = new HashMap<>();
        expectedValuesForN1.put(0, 1);
        test.testCalculateAndWriteToFile(1, expectedValuesForN1);
        test.cleanUp();

        Map<Integer, Integer> expectedValuesForN3 = new HashMap<>();
        expectedValuesForN3.put(0, 1);
        expectedValuesForN3.put(2, 2);
        expectedValuesForN3.put(4, 0);
        expectedValuesForN3.put(6, 2);
        expectedValuesForN3.put(8, 1);
        test.testCalculateAndWriteToFile(3, expectedValuesForN3);
        test.cleanUp();

        Map<Integer, Integer> expectedValuesForN4 = new HashMap<>();
        expectedValuesForN4.put(0, 1);
        expectedValuesForN4.put(2, 3);
        expectedValuesForN4.put(4, 1);
        expectedValuesForN4.put(6, 4);
        expectedValuesForN4.put(8, 2);
        expectedValuesForN4.put(10, 2);
        expectedValuesForN4.put(12, 2);
        expectedValuesForN4.put(14, 4);
        expectedValuesForN4.put(16, 1);
        expectedValuesForN4.put(18, 3);
        expectedValuesForN4.put(20, 1);
        test.testCalculateAndWriteToFile(4, expectedValuesForN4);
        test.cleanUp();

        System.out.println("All tests passed.");
    }

    private void cleanUp() {
        File file = new File(fileName);
        if (file.exists() && !file.delete()) {
            throw new AssertionError("Failed to delete the output file after the test.");
        }
    }

    public void testCalculateAndWriteToFile(int size, Map<Integer, Integer> expectedValues) {
        calculator = new DSquaredCalculator(size, fileName);
        calculator.calculateAndWriteToFile();

        File file = new File(fileName);
        assertCondition(file.exists(), "Output file should be created after calculation.");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int key = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);
                assertCondition(expectedValues.containsKey(key), "Unexpected d-squared value in output file.");
                assertCondition(expectedValues.get(key) == value, "Unexpected frequency for d-squared value " + key);
            }
        } catch (IOException e) {
            throw new AssertionError("IOException occurred while reading the output file.");
        }
    }

    private void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
