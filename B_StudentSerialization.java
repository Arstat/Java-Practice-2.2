import java.io.*;

/**
 * Student class implementing Serializable interface
 */
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int studentID;
    private String name;
    private double grade;
    
    // Constructor
    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
    
    // Getters
    public int getStudentID() {
        return studentID;
    }
    
    public String getName() {
        return name;
    }
    
    public double getGrade() {
        return grade;
    }
    
    // toString for easy display
    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name=%s, Grade=%.2f]", 
                           studentID, name, grade);
    }
}

/**
 * Main program demonstrating serialization and deserialization
 */
public class B_StudentSerialization {
    private static final String FILENAME = "student.ser";
    
    public static void main(String[] args) {
        System.out.println("=== Student Serialization Demo ===\n");
        
        // Create a Student object
        Student student = new Student(101, "Alice Johnson", 85.5);
        System.out.println("Original Student Object:");
        System.out.println(student);
        
        // Serialize the student object
        serializeStudent(student);
        
        // Deserialize the student object
        Student deserializedStudent = deserializeStudent();
        
        // Verify deserialization
        if (deserializedStudent != null) {
            System.out.println("\n=== Verification ===");
            System.out.println("Deserialized Student Object:");
            System.out.println(deserializedStudent);
            
            System.out.println("\nDetailed Information:");
            System.out.println("Student ID: " + deserializedStudent.getStudentID());
            System.out.println("Name: " + deserializedStudent.getName());
            System.out.println("Grade: " + deserializedStudent.getGrade());
            
            // Verify objects are different instances but have same data
            System.out.println("\nAre they the same object? " + 
                             (student == deserializedStudent));
            System.out.println("Do they have the same data? " + 
                             student.toString().equals(deserializedStudent.toString()));
        }
    }
    
    /**
     * Serialize a Student object to a file
     */
    private static void serializeStudent(Student student) {
        System.out.println("\n--- Serialization Process ---");
        
        try (FileOutputStream fileOut = new FileOutputStream(FILENAME);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            
            objectOut.writeObject(student);
            System.out.println("Student object serialized successfully!");
            System.out.println("Saved to file: " + FILENAME);
            
            // Display file size
            File file = new File(FILENAME);
            System.out.println("File size: " + file.length() + " bytes");
            
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Deserialize a Student object from a file
     */
    private static Student deserializeStudent() {
        System.out.println("\n--- Deserialization Process ---");
        Student student = null;
        
        try (FileInputStream fileIn = new FileInputStream(FILENAME);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            student = (Student) objectIn.readObject();
            System.out.println("Student object deserialized successfully!");
            System.out.println("Loaded from file: " + FILENAME);
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FILENAME);
            System.err.println("Please run serialization first.");
        } catch (IOException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Student class not found: " + e.getMessage());
            e.printStackTrace();
        }
        
        return student;
    }
}