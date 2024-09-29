package erp;

import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCourseDetails(ta);
                    break;
                case 2:
                    viewEnrolledStudents(ta);
                    break;
                case 3:
                    assignGrades(ta);
                    break;
                case 4:
                    modifyGrades(ta);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewCourseDetails(TeachingAss ta) {
        List<String> assignedCourses = ta.getAssignedCourses();
        if (assignedCourses.isEmpty()) {
            System.out.println("You are not assigned to any courses.");
            return;
        }

        System.out.println("Your assigned courses:");
        for (String courseCode : assignedCourses) {
            Courses course = Courses.getCourse(courseCode);
            if (course != null) {
                System.out.println(course);
            }
        }
    }

    private void viewEnrolledStudents(TeachingAss ta) {
        String courseCode = selectCourse(ta);
        if (courseCode == null) return;

        Courses course = Courses.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        List<Student> enrolledStudents = loginSystem.getStudentsMap().values().stream()
                .filter(student -> student.getRegisteredCourses().stream()
                        .anyMatch(c -> c.getCode().equals(courseCode)))
                .collect(Collectors.toList());

        if (enrolledStudents.isEmpty()) {
            System.out.println("No students enrolled in this course.");
            return;
        }

        System.out.println("Enrolled students in " + courseCode + ":");
        for (Student student : enrolledStudents) {
            System.out.println(student.getName() + " (ID: " + student.getId() + ")");
        }
    }

    private void assignGrades(TeachingAss ta) {
        String courseCode = selectCourse(ta);
        if (courseCode == null) return;

        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = Login.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (!student.getRegisteredCourses().stream().anyMatch(c -> c.getCode().equals(courseCode))) {
            System.out.println("This student is not enrolled in the course.");
            return;
        }

        System.out.print("Enter grade (A+, A, A-, B, B-, C, C-, D, F): ");
        String grade = scanner.nextLine().toUpperCase();

        ta.manageGrades(courseCode, studentId, grade);
    }

    private void modifyGrades(TeachingAss ta) {
        String courseCode = selectCourse(ta);
        if (courseCode == null) return;

        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = Login.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        Map<String, CourseManager.CompletedCourse> completedCourses = student.getCompletedCourses();
        CourseManager.CompletedCourse completedCourse = completedCourses.get(courseCode);

        if (completedCourse == null) {
            System.out.println("No grade assigned yet for this course. Use the 'Assign Grades' option to add a new grade.");
            return;
        }

        System.out.println("Current grade: " + completedCourse.getGrade());
        System.out.print("Enter new grade (A+, A, A-, B, B-, C, C-, D, F): ");
        String newGrade = scanner.nextLine().toUpperCase();

        ta.manageGrades(courseCode, studentId, newGrade);
    }

    private String selectCourse(TeachingAss ta) {
        List<String> assignedCourses = ta.getAssignedCourses();
        if (assignedCourses.isEmpty()) {
            System.out.println("You are not assigned to any courses.");
            return null;
        }

        System.out.println("Select a course:");
        for (int i = 0; i < assignedCourses.size(); i++) {
            System.out.println((i + 1) + ") " + assignedCourses.get(i));
        }

        int selection = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (selection < 1 || selection > assignedCourses.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return assignedCourses.get(selection - 1);
    }
}