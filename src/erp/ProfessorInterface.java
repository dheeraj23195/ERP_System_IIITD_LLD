package erp;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProfessorInterface {
    private Scanner scanner;
    private Login loginSystem;

    public ProfessorInterface(Login loginSystem) {
        this.loginSystem = loginSystem;
        this.scanner = new Scanner(System.in);
    }

    public void run(Professor prof) {
        while (true) {
            System.out.println("\nProfessor Menu");
            System.out.println("1) List all my Courses");
            System.out.println("2) Update my Course");
            System.out.println("3) View Enrolled Students");
            System.out.println("4) View TA Applications");
            System.out.println("5) Approve TA Applications");
            System.out.println("6) View Approved TAs");
            System.out.println("0) Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayCourses(prof);
                    break;
                case 2:
                    updateCourse(prof);
                    break;
                case 3:
                    viewEnrolledStudents(prof);
                    break;
                case 4:
                    viewTAApplications(prof);
                    break;
                case 5:
                    approveTAApplications(prof);
                    break;
                case 6:
                    viewApprovedTAs(prof);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayCourses(Professor prof) {
        List<String> listCourses = prof.getTaughtCourses();
        if (listCourses.isEmpty()) {
            System.out.println("You are not teaching any courses at the moment.");
        } else {
            System.out.println("Your courses:");
            for (String courseCode : listCourses) {
                Courses course = Courses.getCourse(courseCode);
                if (course != null) {
                    System.out.println(course);
                }
            }
        }
    }

    private void updateCourse(Professor prof) {
        System.out.print("Enter the course code you want to update: ");
        String courseCode = scanner.nextLine();

        if (!prof.getTaughtCourses().contains(courseCode)) {
            System.out.println("You are not assigned to this course.");
            return;
        }

        Courses course = Courses.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        while (true) {
            System.out.println("\nUpdate Course: " + course.coursename);
            System.out.println("1) Update syllabus");
            System.out.println("2) Update class timings");
            System.out.println("3) Update credits");
            System.out.println("4) Update prerequisites");
            System.out.println("5) Update enrollment limit");
            System.out.println("6) Update office hours");
            System.out.println("0) Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    updateSyllabus(course);
                    break;
                case 2:
                    updateClassTimings(course);
                    break;
                case 3:
                    updateCredits(course);
                    break;
                case 4:
                    updatePrerequisites(course);
                    break;
                case 5:
                    updateEnrollmentLimit(course);
                    break;
                case 6:
                    updateOfficeHours(course);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateSyllabus(Courses course) {
        System.out.println("Current syllabus: " + course.getSyllabus());
        System.out.print("Enter new syllabus: ");
        String newSyllabus = scanner.nextLine();
        course.setSyllabus(newSyllabus);
        System.out.println("Syllabus updated successfully.");
    }

    private void updateClassTimings(Courses course) {
        System.out.println("Current class timings: " + course.starttime + " - " + course.endtime);
        System.out.print("Enter new start time (HH:mm): ");
        LocalTime newStartTime = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter new end time (HH:mm): ");
        LocalTime newEndTime = LocalTime.parse(scanner.nextLine());
        course.starttime = newStartTime;
        course.endtime = newEndTime;
        System.out.println("Class timings updated successfully.");
    }

    private void updateCredits(Courses course) {
        System.out.println("Current credits: " + course.getCredits());
        System.out.print("Enter new credit value: ");
        int newCredits = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        course.credits = newCredits;
        System.out.println("Credits updated successfully.");
    }

    private void updatePrerequisites(Courses course) {
        System.out.println("Current prerequisites: " + String.join(", ", course.getPrereq()));
        System.out.print("Enter new prerequisites (comma-separated, or 'none'): ");
        String prereqInput = scanner.nextLine();
        String[] newPrereq = prereqInput.equalsIgnoreCase("none") ? new String[]{} : prereqInput.split(",");
        course.prereq = newPrereq;
        System.out.println("Prerequisites updated successfully.");
    }

    private void updateEnrollmentLimit(Courses course) {
        System.out.println("Current enrollment limit: " + course.getEnrollmentLimit());
        System.out.print("Enter new enrollment limit: ");
        int newLimit = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        course.setEnrollmentLimit(newLimit);
        System.out.println("Enrollment limit updated successfully.");
    }

    private void updateOfficeHours(Courses course) {
        System.out.println("Current office hours: " + course.getOfficeHours());
        System.out.print("Enter new office hours: ");
        String newOfficeHours = scanner.nextLine();
        course.setOfficeHours(newOfficeHours);
        System.out.println("Office hours updated successfully.");
    }

    private void viewEnrolledStudents(Professor prof) {
        System.out.println("Your courses:");
        List<String> taughtCourses = prof.getTaughtCourses();
        for (int i = 0; i < taughtCourses.size(); i++) {
            System.out.println((i + 1) + ") " + taughtCourses.get(i));
        }
        System.out.print("Enter the number of the course to view enrolled students: ");
        int courseChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (courseChoice < 1 || courseChoice > taughtCourses.size()) {
            System.out.println("Invalid course number.");
            return;
        }

        String selectedCourseCode = taughtCourses.get(courseChoice - 1);
        List<Student> enrolledStudents = getEnrolledStudents(selectedCourseCode);

        if (enrolledStudents.isEmpty()) {
            System.out.println("No students enrolled in this course.");
        } else {
            System.out.println("Enrolled students for course " + selectedCourseCode + ":");
            for (Student student : enrolledStudents) {
                displayStudentInfo(student);
            }

            System.out.println("Do you want to grade students? (Y/N)");
            String gradeChoice = scanner.nextLine();
            if (gradeChoice.equalsIgnoreCase("Y")) {
                gradeStudents(enrolledStudents, selectedCourseCode);
            }
        }
    }

    private void gradeStudents(List<Student> students, String courseCode) {
        for (Student student : students) {
            System.out.println("Grading " + student.getName() + " (ID: " + student.getId() + ")");
            System.out.print("Enter grade (A+, A, A-, B, B-, C, C-, D, F): ");
            String grade = scanner.nextLine();
            student.courseManager.addGrade(courseCode, grade);
        }
        System.out.println("All students graded for course " + courseCode);
    }
    private void viewTAApplications(Professor prof) {
        System.out.println("Select a course to view TA applications:");
        List<String> taughtCourses = prof.getTaughtCourses();
        for (int i = 0; i < taughtCourses.size(); i++) {
            System.out.println((i + 1) + ") " + taughtCourses.get(i));
        }
        System.out.print("Enter the number of the course: ");
        int courseChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (courseChoice < 1 || courseChoice > taughtCourses.size()) {
            System.out.println("Invalid course number.");
            return;
        }

        String selectedCourseCode = taughtCourses.get(courseChoice - 1);
        List<Student.TAApplication> applications = Student.getTAApplications().stream()
                .filter(app -> app.getCourseCode().equals(selectedCourseCode) && !app.isApproved())
                .collect(Collectors.toList());

        if (applications.isEmpty()) {
            System.out.println("No pending TA applications for " + selectedCourseCode);
            return;
        }

        System.out.println("Pending TA Applications for " + selectedCourseCode + ":");
        for (Student.TAApplication app : applications) {
            Student student = app.getStudent();
            System.out.println("Student: " + student.getName() + " (ID: " + student.getId() + ")");
        }
    }

    private void approveTAApplications(Professor prof) {
        System.out.println("Select a course to approve TA applications:");
        List<String> taughtCourses = prof.getTaughtCourses();
        for (int i = 0; i < taughtCourses.size(); i++) {
            System.out.println((i + 1) + ") " + taughtCourses.get(i));
        }
        System.out.print("Enter the number of the course: ");
        int courseChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (courseChoice < 1 || courseChoice > taughtCourses.size()) {
            System.out.println("Invalid course number.");
            return;
        }

        String selectedCourseCode = taughtCourses.get(courseChoice - 1);
        List<Student.TAApplication> applications = Student.getTAApplications().stream()
                .filter(app -> app.getCourseCode().equals(selectedCourseCode) && !app.isApproved())
                .collect(Collectors.toList());

        if (applications.isEmpty()) {
            System.out.println("No pending TA applications for " + selectedCourseCode);
            return;
        }

        System.out.println("Pending TA Applications for " + selectedCourseCode + ":");
        for (int i = 0; i < applications.size(); i++) {
            Student.TAApplication app = applications.get(i);
            Student student = app.getStudent();
            System.out.println((i + 1) + ") " + student.getName() + " (ID: " + student.getId() + ")");
        }

        System.out.print("Enter the number of the application to approve (0 to cancel): ");
        int approveChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (approveChoice == 0) {
            return;
        }

        if (approveChoice < 1 || approveChoice > applications.size()) {
            System.out.println("Invalid application number.");
            return;
        }

        Student.TAApplication selectedApp = applications.get(approveChoice - 1);
        selectedApp.setApproved(true);
        TeachingAss ta = new TeachingAss(selectedApp.getStudent());
        ta.assignCourse(selectedCourseCode);
        System.out.println("TA application approved for student " + ta.getName() + " in course " + selectedCourseCode);
    }

    private void viewApprovedTAs(Professor prof) {
        System.out.println("Select a course to view approved TAs:");
        List<String> taughtCourses = prof.getTaughtCourses();
        for (int i = 0; i < taughtCourses.size(); i++) {
            System.out.println((i + 1) + ") " + taughtCourses.get(i));
        }
        System.out.print("Enter the number of the course: ");
        int courseChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (courseChoice < 1 || courseChoice > taughtCourses.size()) {
            System.out.println("Invalid course number.");
            return;
        }

        String selectedCourseCode = taughtCourses.get(courseChoice - 1);
        List<TeachingAss> approvedTAs = TeachingAss.getApprovedTAs().stream()
                .filter(ta -> ta.getAssignedCourses().contains(selectedCourseCode))
                .collect(Collectors.toList());

        if (approvedTAs.isEmpty()) {
            System.out.println("No approved TAs for " + selectedCourseCode);
            return;
        }

        System.out.println("Approved TAs for " + selectedCourseCode + ":");
        for (TeachingAss ta : approvedTAs) {
            System.out.println("TA: " + ta.getName() + " (ID: " + ta.getId() + ")");
        }
    }

    private List<Student> getEnrolledStudents(String courseCode) {
        return loginSystem.getStudentsMap().values().stream()
                .filter(student -> student.getRegisteredCourses().stream()
                        .anyMatch(course -> course.getCode().equals(courseCode)))
                .collect(Collectors.toList());
    }

    private void displayStudentInfo(Student student) {
        System.out.println("Name: " + student.getName());
        System.out.println("ID: " + student.getId());
        System.out.println("Roll No: " + student.getRollno());
        System.out.println("Phone: " + student.getPhoneno());
        System.out.println("Semester: " + student.getSemester());
        System.out.println("Academic Standing: " + getAcademicStanding(student));
        System.out.println("-----------------------------");
    }

    private String getAcademicStanding(Student student) {
        double cgpa = student.getCGPA();
        if (cgpa >= 9.0) {
            return "Excellent";
        } else if (cgpa >= 8.0) {
            return "Very Good";
        } else if (cgpa >= 7.0) {
            return "Good";
        } else if (cgpa >= 6.0) {
            return "Average";
        } else if (cgpa >= 5.0) {
            return "Below Average";
        } else {
            return "Poor";
        }
    }
}