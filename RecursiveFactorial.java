import java.util.Scanner;

public class RecursiveFactorial {

    // Recursive method to calculate factorial
    public static long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1; // Base case: 0! = 1! = 1
        }
        return n * factorial(n - 1); // Recursive case
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = -1; // Initialize with an invalid number

        // Prompt user for input with bounds checking
        while (true) {
            System.out.print("Enter a non-negative integer to calculate its factorial: ");
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number >= 0) {
                    break; // Valid input
                } else {
                    System.out.println("Error: Please enter a non-negative integer.");
                }
            } else {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }
}