package erp;

import java.util.Scanner;

public class TAInterface {
    private Scanner scanner;
    private Login loginSystem;

    public TAInterface(Login loginSystem) {
        this.loginSystem = loginSystem;
        this.scanner = new Scanner(System.in);
    }

    public void run(TeachingAss ta) {
        while (true) {
            System.out.println("\nTA Menu");
            System.out.println("1) View Assigned Course Details");
            System.out.println("2) View Enrolled Students");
            System.out.println("3) Assign Grades to Students");
            System.out.println("4) Modify Student Grades");
            System.out.println("0) Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:

                    break;
                case 2:
                    // Call function to display enrolled students
                    break;
                case 3:
                    // Call function to assign grades
                    break;
                case 4:
                    // Call function to modify grades
                    break;
                case 0:
                    return; // Log out and exit
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
