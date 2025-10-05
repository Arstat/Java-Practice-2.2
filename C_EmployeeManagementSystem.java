import java.io.*;
import java.util.Scanner;

/**
 * Employee class implementing Serializable
 */
class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int id;
    private String designation;
    private double salary;
    
    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public double getSalary() {
        return salary;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s | Designation: %-15s | Salary: $%.2f",
                           id, name, designation, salary);
    }
}

/**
 * Menu-based Employee Management System
 */
public class C_EmployeeManagementSystem {
    private static final String FILENAME = "employees.dat";
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean running = true;
        System.out.println(" Employee Management System ");
        System.out.println("-----------------------------");
        
        
        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    running = false;
                    System.out.println("\n Thank you for using Employee Management System!");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("\n Invalid choice! Please select 1-3.");
            }
            
            if (running) {
                System.out.println("\n Press Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display the main menu
     */
    private static void displayMenu() {
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Exit");
        System.out.print("Enter your choice (1-3): ");
    }
    
    /**
     * Get and validate menu choice
     */
    private static int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // clear invalid input
            return -1;
        }
    }
    
    /**
     * Add a new employee to the file
     */
    private static void addEmployee() {
        System.out.println("\n" + "-".repeat(45));
        System.out.println("           ADD NEW EMPLOYEE");
        System.out.println("-".repeat(45));
        
        try {
            System.out.print("Enter Employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine();
            
            System.out.print("Enter Salary: $");
            double salary = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            
            // Create employee object
            Employee employee = new Employee(name, id, designation, salary);
            
            // Save to file
            saveEmployee(employee);
            
            System.out.println("\n Employee added successfully!");
            System.out.println("\nEmployee Details:");
            System.out.println(employee);
            
        } catch (Exception e) {
            System.out.println("\n Error: Invalid input! Please try again.");
            scanner.nextLine(); // clear buffer
        }
    }
    
    /**
     * Save employee to file using serialization
     */
    private static void saveEmployee(Employee employee) {
        try {
            // Read existing employees
            File file = new File(FILENAME);
            ObjectOutputStream out;
            
            if (file.exists() && file.length() > 0) {
                // Append to existing file
                out = new AppendableObjectOutputStream(new FileOutputStream(file, true));
            } else {
                // Create new file
                out = new ObjectOutputStream(new FileOutputStream(file));
            }
            
            out.writeObject(employee);
            out.close();
            
        } catch (IOException e) {
            System.err.println("Error saving employee: " + e.getMessage());
        }
    }
    
    /**
     * Display all employees from the file
     */
    private static void displayAllEmployees() {
        System.out.println("\n" + "-".repeat(45));
        System.out.println("         ALL EMPLOYEE RECORDS");
        System.out.println("-".repeat(45));
        
        File file = new File(FILENAME);
        
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n No employee records found!");
            System.out.println("Please add employees first.");
            return;
        }
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            int count = 0;
            System.out.println();
            
            while (true) {
                try {
                    Employee employee = (Employee) in.readObject();
                    count++;
                    System.out.println(count + ". " + employee);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
            
            System.out.println("\n" + "-".repeat(45));
            System.out.println("Total Employees: " + count);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading employees: " + e.getMessage());
        }
    }
}

/**
 * Custom ObjectOutputStream to append objects to existing file
 */
class AppendableObjectOutputStream extends ObjectOutputStream {
    
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    
    @Override
    protected void writeStreamHeader() throws IOException {
        // Don't write header when appending
        reset();
    }
}