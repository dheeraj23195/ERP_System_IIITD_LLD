package erp;

import java.util.*;

public class StudentInterface {
    private static Scanner scanner = new Scanner(System.in);
    private static TAInterface taInterface;

    public static void setTAInterface(TAInterface taInterface) {
        StudentInterface.taInterface = taInterface;
    }

    public static void run(User user) {
        if (!(user instanceof Student)) {
            System.out.println("Invalid user type for Student Interface.");
            return;
        }

        Student student = (Student) user;
        System.out.println("Welcome, " + student.getName() + "!");

        while (true) {
            int a = displayMenu(student);
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
                    student.getCourseManager().displayRegisteredCourses();
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
                    if (student.getTAstatus()) {
                        openTAMenu(student);
                    } else {
                        applyForTAship(student);
                    }
                    break;
                case 9:
                    enterFeedback(student);
                    break;
                case 10:
                    System.out.println("Logging out...");
                    return;
                case 11:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
            }
        }
    }

    private static void openTAMenu(Student student) {
        if (taInterface == null) {
            System.out.println("TA Interface is not initialized.");
            return;
        }

        TeachingAss ta = TeachingAss.getApprovedTAs().stream()
                .filter(t -> t.getId() == student.getId())
                .findFirst()
                .orElse(null);

        if (ta != null) {
            System.out.println("Switching to TA Interface...");
            taInterface.run(ta);
            System.out.println("Returning to Student Interface...");
        } else {
            System.out.println("Error: TA status not found. Please contact an administrator.");
        }
    }
    private static void enterFeedback(Student student) {
        Map<String, CourseManager.CompletedCourse> comcourses = student.getCompletedCourses();
        System.out.println("You can submit feedback for: ");
        for (Map.Entry<String, CourseManager.CompletedCourse> entry : comcourses.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getCourse().getCoursename());
        }
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        CourseManager.CompletedCourse completedCourse = comcourses.get(courseCode);
        if (completedCourse != null) {
            Courses course = completedCourse.getCourse();
            System.out.print("Enter your feedback (text, integer, or decimal): ");
            String input = scanner.nextLine();
            Feedback<?> feedback;
            if (isInteger(input)) {
                feedback = new Feedback<>(Integer.parseInt(input));
            }
            else if (isDouble(input)) {
                feedback = new Feedback<>(Double.parseDouble(input));
            }
            else {
                feedback = new Feedback<>(input);
            }
            Feedback.addFeedback(courseCode, course.getSemester(), feedback);
            System.out.println("Feedback added successfully.");
            Feedback.displayAllFeedbackForCourse(courseCode, course.getSemester());
        }
        else {
            System.out.println("Course not found in completed courses.");
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static int displayMenu(Student student) {
        int a=0;
        System.out.println("Please Enter the task Number that you would like to do:");
        System.out.println("1) View Schedule");
        System.out.println("2) View Grades");
        System.out.println("3) Display Registered Courses");
        System.out.println("4) Display Available Courses");
        System.out.println("5) Add/Drop a Course");
        System.out.println("6) Register a Complaint");
        System.out.println("7) Check Complaint Status");
        if(student.getTAstatus()){
            a=1;
            System.out.println("8) Open TA Menu");
        }
        else{
            System.out.println("8) Apply for TAship");
        }
        System.out.println("9) Enter Course Feedback");
        System.out.println("10) Log Out");
        System.out.println("11) Exit");
        return a;
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
                try {
                    boolean added = student.courseManager.addCourse(addCourseCode);
                    if (added) {
                        System.out.println("Course " + addCourseCode + " added successfully.");
                    }
                } catch (CourseFullException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println("Please choose a different course or contact the administrator.");
                }
                break;
            case 2:
                System.out.println("Enter the course code you would like to drop: ");
                String dropCourseCode = scanner.nextLine();
                try {
                    boolean dropped = student.courseManager.dropCourse(dropCourseCode);
                    if (dropped) {
                        System.out.println("Course " + dropCourseCode + " dropped successfully.");
                    }
                    break;
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    break;
                }
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