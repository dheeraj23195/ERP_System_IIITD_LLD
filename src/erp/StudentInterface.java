package erp;

import java.util.*;

public class StudentInterface {
    private static Scanner scanner = new Scanner(System.in);

    public static void run(Student student) {
        System.out.println("Welcome, " + student.getName() + "!");
        while(true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Schedule.displaySchedule(student);
                    break;
                case 2:
                    displayGrades(student);
                    break;
                case 3:
                    student.courseManager.displayRegisteredCourses();
                    break;
                case 4:
                    displayAvailableCourses(student);
                    break;
                case 5:
                    handleAddDropCourse(student);
                    break;
                case 6:
                    registerComplaint(student);
                    break;
                case 7:
                    checkComplaintStatus();
                    break;
                case 8:
                    applyForTAship(student);
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                case 10:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 10.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nPlease Enter the task Number that you would like to do:");
        System.out.println("1) View Schedule");
        System.out.println("2) View Grades");
        System.out.println("3) Display Registered Courses");
        System.out.println("4) Display Available Courses");
        System.out.println("5) Add/Drop a Course");
        System.out.println("6) Register a Complaint");
        System.out.println("7) Check Complaint Status");
        System.out.println("8) Apply for TAship");
        System.out.println("9) Log Out");
        System.out.println("10) Exit");
    }

    public static boolean applyForTAship(Student student) {
        System.out.println("Courses available for TAship: ");
        Map<String, CourseManager.CompletedCourse> availableCourses = new HashMap<>();
        Map<String, CourseManager.CompletedCourse> completedCourses = student.getCourseManager().getCompletedCourses();

        for (Map.Entry<String, CourseManager.CompletedCourse> entry : completedCourses.entrySet()) {
            CourseManager.CompletedCourse course = entry.getValue();
            if (course.getGradePoint() >= 8.0) {
                System.out.println(entry.getKey());
                availableCourses.put(entry.getKey(), course);
            }
        }

        if (availableCourses.isEmpty()) {
            System.out.println("No courses available for TAship.");
            return false;
        }

        System.out.print("Enter the course code you want to apply for: ");
        String courseCode = scanner.next();

        if (!availableCourses.containsKey(courseCode)) {
            System.out.println("Sorry, the course is unavailable for TAship.");
            return false;
        }

        return student.applyForTA(courseCode);
    }

    private static void displayGrades(Student student) {
        System.out.println("Enter the semester for which you want to view grades (0 for all semesters): ");
        int semester = scanner.nextInt();
        scanner.nextLine();

        student.courseManager.displayGrades(semester);
    }

    private static void displayAvailableCourses(Student student) {
        List<Courses> coursesList = student.courseManager.getAvailableCourses();
        student.courseManager.displayCoursesTable(coursesList);
    }

    private static void handleAddDropCourse(Student student) {
        System.out.println("Do you want to 1) Add 2) Drop a course 3) Go back to Main Menu? : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter the course code you would like to add: ");
                String addCourseCode = scanner.nextLine();
                student.courseManager.addCourse(addCourseCode);
                break;
            case 2:
                System.out.println("Enter the course code you would like to drop: ");
                String dropCourseCode = scanner.nextLine();
                student.courseManager.dropCourse(dropCourseCode);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void registerComplaint(Student student) {
        System.out.println("Enter Complaint Title:");
        String complaintTitle = scanner.nextLine();
        System.out.println("Enter Complaint Description:");
        String complaintDescription = scanner.nextLine();
        Complaints complaint = new Complaints(student.getId(), complaintTitle, complaintDescription);
        ComplaintManager.addComplaint(complaint);
        System.out.println("Your Complaint is registered. Complaint ID: " + complaint.getCompId());
    }

    private static void checkComplaintStatus() {
        System.out.println("Enter Complaint ID: ");
        String complaintId = scanner.nextLine();
        Complaints.ComplaintStatus status = ComplaintManager.getComplaintStatus(complaintId);
        System.out.println("Complaint Status: " + status);
    }
}