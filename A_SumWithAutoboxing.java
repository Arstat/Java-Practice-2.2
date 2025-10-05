import java.util.ArrayList;
import java.util.Scanner;

/**
 * Program demonstrating autoboxing, unboxing, and string parsing
 * to calculate sum of integers
 */
public class A_SumWithAutoboxing {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        
        System.out.println("=== Sum Calculator with Autoboxing ===");
        System.out.print("How many numbers do you want to enter? ");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        // Input phase - demonstrating parsing and autoboxing
        System.out.println("\nEnter " + count + " integers (one per line):");
        for (int i = 0; i < count; i++) {
            System.out.print("Number " + (i + 1) + ": ");
            String input = scanner.nextLine();
            
            try {
                // Parse string to int (primitive)
                int primitiveValue = Integer.parseInt(input);
                
                // Autoboxing: primitive int automatically converted to Integer object
                numbers.add(primitiveValue);
                
                System.out.println("  → Added: " + primitiveValue + 
                                 " (autoboxed to Integer object)");
            } catch (NumberFormatException e) {
                System.out.println("  → Invalid input! Please enter a valid integer.");
                i--; // retry this iteration
            }
        }
        
        // Calculation phase - demonstrating unboxing
        System.out.println("\n=== Calculating Sum ===");
        int sum = 0;
        
        for (Integer num : numbers) {
            // Unboxing: Integer object automatically converted to primitive int
            sum += num;
            System.out.println("Adding " + num + " (unboxed), running total: " + sum);
        }
        
        // Display results
        System.out.println("\n=== Results ===");
        System.out.println("Numbers entered: " + numbers);
        System.out.println("Total count: " + numbers.size());
        System.out.println("Sum of all numbers: " + sum);
        
        // Calculate and display average
        if (!numbers.isEmpty()) {
            double average = (double) sum / numbers.size();
            System.out.printf("Average: %.2f%n", average);
        }
        
        // Demonstrate explicit boxing and unboxing
        demonstrateBoxingOperations(numbers);
        
        scanner.close();
    }
    
    /**
     * Additional method to demonstrate explicit boxing/unboxing operations
     */
    private static void demonstrateBoxingOperations(ArrayList<Integer> numbers) {
        System.out.println("\n=== Boxing/Unboxing Demonstration ===");
        
        if (!numbers.isEmpty()) {
            // Get first number
            Integer firstNumber = numbers.get(0);
            
            // Explicit unboxing using intValue()
            int primitiveValue = firstNumber.intValue();
            System.out.println("Explicit unboxing: " + firstNumber + 
                             " → " + primitiveValue);
            
            // Explicit boxing using valueOf()
            Integer boxedValue = Integer.valueOf(primitiveValue * 2);
            System.out.println("Explicit boxing: " + primitiveValue * 2 + 
                             " → " + boxedValue);
            
            // Comparison operations (triggers unboxing)
            System.out.println("\nComparison (triggers unboxing):");
            System.out.println(firstNumber + " > 10? " + (firstNumber > 10));
            
            // Arithmetic operations (triggers unboxing)
            System.out.println("\nArithmetic (triggers unboxing):");
            Integer result = firstNumber + 5; // unbox, add, autobox
            System.out.println(firstNumber + " + 5 = " + result);
        }
    }
}