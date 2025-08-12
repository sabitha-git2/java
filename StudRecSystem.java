import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudRecSystem {

    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Student Record Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Delete Student by Roll No");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> deleteStudent();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    static void addStudent() {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            System.out.print("Enter Roll No: ");
            String roll = sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            fw.write(roll + "," + name + "," + dept + "\n");
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewStudents() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\nRoll No\tName\tDepartment");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[0] + "\t" + data[1] + "\t" + data[2]);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void searchStudent() {
        System.out.print("Enter Roll No to search: ");
        String rollNo = sc.nextLine();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(rollNo)) {
                    System.out.println("Found: " + data[1] + " - " + data[2]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student not found.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteStudent() {
        System.out.print("Enter Roll No to delete: ");
        String rollNo = sc.nextLine();
        List<String> newLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equals(rollNo)) {
                    newLines.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (String newLine : newLines) {
                fw.write(newLine + "\n");
            }
            if (found) {
                System.out.println("Student deleted.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
