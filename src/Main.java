package erp;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Login loginSystem = new Login();
    private static Scanner scanner = new Scanner(System.in);

    public static void studentInterface(Student student) {
        System.out.println("Welcome, " + student.getName() + "!");
        while(true) {
            System.out.println("Please Enter the task Number that you would like to do: \n1) View Schedule \n2) View Grades \n3) Display Registered Courses \n4) Display Available Course \n5)Add/Drop a Course \n6) Register a Complaint \n7) Log Out \n8) Exit");
            int choice = scanner.nextInt();
            if (choice == 1) {
                Schedule.displaySchedule(student);
            } else if (choice == 2) {
                student.courseManager.displayGrades();
            } else if (choice == 3) {
                student.courseManager.displayRegisteredCourses();
            } else if(choice==4){
                List<Courses> coursesList=student.courseManager.getAvailableCourses();
                student.courseManager.displayCoursesTable(coursesList);
            } else if(choice==5){
                System.out.println("Do you want to 1) Add 2) Drop a course 3)Go back to Main Menu? : ");
                int choice1 = scanner.nextInt();
                if(choice1 == 1) {
                    System.out.println("Enter the course code you would like to add: ");
                    String courseCode = scanner.nextLine();
                    student.courseManager.addCourse(courseCode);
                }
                else if(choice1 == 2) {
                    System.out.println("Enter the course code you would like to drop: ");
                    String courseCode = scanner.nextLine();
                    student.courseManager.dropCourse(courseCode);
                }
                else if(choice1 == 3) {
                    continue;
                }
            } else if(choice==6){
                //Complaint.Register()
            } else if (choice == 7) {
                System.out.println("Logging out...");
                return;
            } else if (choice == 8) {
                System.out.println("Exiting the program...");
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }

    public static void professorInterface(Professor professor) {
        System.out.println("Welcome, Professor " + professor.getName() + "!");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to IIITD ERP!!");
        while (true) {
            User user = loginSystem.start();
            if (user instanceof Student) {
                studentInterface((Student) user);
            } else if (user instanceof Professor) {
                professorInterface((Professor) user);
            } else {
                System.out.println("Unknown user type. Exiting.");
                break;
            }
        }
    }
}