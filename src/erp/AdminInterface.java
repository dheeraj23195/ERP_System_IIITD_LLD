package erp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdminInterface {
    static Scanner sc = new Scanner(System.in);
    private static Login loginSystem;

    public AdminInterface(Login loginSystem) {
        AdminInterface.loginSystem = loginSystem;
    }

    public static void courses() {
        while (true) {
            System.out.println("Do you want to: ");
            System.out.println("1) View all Courses");
            System.out.println("2) Update Courses");
            System.out.println("3) Add Courses");
            System.out.println("4) Delete Course");
            System.out.println("5) Go back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    List<Courses> courseList = Courses.getAllCourses();
                    if (courseList.isEmpty()) {
                        System.out.println("No courses available.");
                    } else {
                        for (Courses c : courseList) {
                            System.out.println(c.toString());
                        }
                    }
                    break;
                case 2:
                    updateCourse();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCourse() {
        System.out.println("Enter course details:");
        System.out.print("Course Code: ");
        String code = sc.nextLine();
        System.out.print("Course Name: ");
        String name = sc.nextLine();
        System.out.print("Credits: ");
        int credits = sc.nextInt();
        sc.nextLine();
        System.out.print("Prerequisites (comma-separated, or 'none'): ");
        String prereqInput = sc.nextLine();
        String[] prereq = prereqInput.equalsIgnoreCase("none") ? new String[]{} : prereqInput.split(",");
        System.out.print("Start Time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(sc.nextLine());
        System.out.print("End Time (HH:mm): ");
        LocalTime endTime = LocalTime.parse(sc.nextLine());
        System.out.print("Days (comma-separated): ");
        String[] days = sc.nextLine().split(",");
        System.out.print("Semester: ");
        int semester = sc.nextInt();
        sc.nextLine();

        Courses newCourse = new Courses(code, name, credits, prereq, startTime, endTime, days, semester);
        Courses.addCourse(newCourse);
        System.out.println("Course added successfully!");

        System.out.print("Do you want to assign a professor to this course? (Y/N): ");
        String assignProf = sc.nextLine();
        if (assignProf.equalsIgnoreCase("Y")) {
            assignProfessorToCourse(newCourse);
        }
    }

    private static void deleteCourse() {
        System.out.print("Enter the course code to delete: ");
        String courseCode = sc.nextLine();
        if (Courses.removeCourse(courseCode)) {
            System.out.println("Course deleted successfully.");
        } else {
            System.out.println("Course not found. Deletion failed.");
        }
    }

    private static boolean updateCourse() {
        System.out.println("Enter the Course ID you would like to update: ");
        String courseCode = sc.nextLine();
        Courses course = Courses.getCourse(courseCode);
        if (course == null) {
            System.out.println("This is not a valid course code");
            return false;
        }
        System.out.println("Here are the current course details: ");
        System.out.println(course);

        while (true) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Course Name");
            System.out.println("2. Professor");
            System.out.println("3. Credits");
            System.out.println("4. Prerequisites");
            System.out.println("5. Start Time");
            System.out.println("6. End Time");
            System.out.println("7. Days");
            System.out.println("8. Semester");
            System.out.println("9. Finish Updating");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter new course name: ");
                    String newName = sc.nextLine();
                    course.coursename = newName;
                    break;
                case 2:
                    assignProfessorToCourse(course);
                    break;
                case 3:
                    System.out.println("Enter new credit value: ");
                    int newCredits = sc.nextInt();
                    course.credits = newCredits;
                    break;
                case 4:
                    System.out.println("Enter new prerequisites (comma-separated, or 'none'): ");
                    String prereqInput = sc.nextLine();
                    if (prereqInput.equalsIgnoreCase("none")) {
                        course.prereq = new String[]{};
                    } else {
                        course.prereq = prereqInput.split(",");
                    }
                    break;
                case 5:
                    System.out.println("Enter new start time (HH:mm): ");
                    String startTimeStr = sc.nextLine();
                    course.starttime = LocalTime.parse(startTimeStr);
                    break;
                case 6:
                    System.out.println("Enter new end time (HH:mm): ");
                    String endTimeStr = sc.nextLine();
                    course.endtime = LocalTime.parse(endTimeStr);
                    break;
                case 7:
                    System.out.println("Enter new days (comma-separated): ");
                    String daysInput = sc.nextLine();
                    course.days = daysInput.split(",");
                    break;
                case 8:
                    System.out.println("Enter new semester: ");
                    int newSemester = sc.nextInt();
                    course.semester = newSemester;
                    break;
                case 9:
                    System.out.println("Course updated successfully!");
                    return true;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println("Updated course details: ");
            System.out.println(course);
        }
    }

    public static void students() {
        while (true) {
            System.out.println("\nStudent Management:");
            System.out.println("1) View All Students");
            System.out.println("2) View Specific Student");
            System.out.println("3) Update Student Information");
            System.out.println("4) Update Student Grades");
            System.out.println("5) Go back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllStudents();
                    break;
                case 2:
                    viewSpecificStudent();
                    break;
                case 3:
                    updateStudentInfo();
                    break;
                case 4:
                    updateStudentGrades();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllStudents() {
        List<Student> students = new ArrayList<>(loginSystem.getStudentsMap().values());
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                displayStudentInfo(student);
            }
        }
    }

    private static void viewSpecificStudent() {
        System.out.print("Enter student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        Student student = loginSystem.getStudentsMap().get(studentId);
        if (student != null) {
            displayStudentInfo(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayStudentInfo(Student student) {
        System.out.println("\nStudent Information:");
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Phone: " + student.getPhoneno());
        System.out.println("Roll No: " + student.getRollno());
        System.out.println("Semester: " + student.getSemester());
        System.out.println("Registered Courses:");
        student.courseManager.displayRegisteredCourses();
        System.out.println("Grades:");
        student.courseManager.displayAllGrades();
        System.out.printf("CGPA: %.2f%n", student.courseManager.calculateCGPA());
    }

    private static void updateStudentInfo() {
        System.out.print("Enter student ID to update: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        Student student = loginSystem.getStudentsMap().get(studentId);
        if (student != null) {
            System.out.println("Current information:");
            displayStudentInfo(student);
            System.out.println("\nWhat would you like to update?");
            System.out.println("1) Name");
            System.out.println("2) Phone Number");
            System.out.println("3) Roll Number");
            System.out.println("4) Semester");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    student.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new phone number: ");
                    long newPhone = sc.nextLong();
                    student.setPhoneno(newPhone);
                    break;
                case 3:
                    System.out.print("Enter new roll number: ");
                    int newRollNo = sc.nextInt();
                    student.setRollno(newRollNo);
                    break;
                case 4:
                    System.out.print("Enter new semester: ");
                    int newSemester = sc.nextInt();
                    student.setSemester(newSemester);
                    System.out.println("Student's semester updated. Registered courses have been cleared.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            System.out.println("Student information updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudentGrades() {
        System.out.print("Enter student ID to update grades: ");
        int studentId = sc.nextInt();
        sc.nextLine();
        Student student = loginSystem.getStudentsMap().get(studentId);
        if (student != null) {
            System.out.println("Current grades:");
            student.courseManager.displayAllGrades();
            System.out.print("Enter course code to update grade: ");
            String courseCode = sc.nextLine();
            System.out.print("Enter new grade: ");
            String newGrade = sc.nextLine();
            student.courseManager.addGrade(courseCode, newGrade);
            System.out.println("Grade updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void complaints() {
        while (true) {
            System.out.println("\nComplaint Management:");
            System.out.println("1) View All Complaints");
            System.out.println("2) Update Complaint Status");
            System.out.println("3) Filter Complaints");
            System.out.println("4) Go back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllComplaints();
                    break;
                case 2:
                    updateComplaintStatus();
                    break;
                case 3:
                    filterComplaints();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllComplaints() {
        List<Complaints> allComplaints = new ArrayList<>(ComplaintManager.getAllComplaints());
        if (allComplaints.isEmpty()) {
            System.out.println("No complaints found.");
        } else {
            displayComplaints(allComplaints);
        }
    }

    private static void displayComplaints(List<Complaints> complaints) {
        for (Complaints complaint : complaints) {
            System.out.println("Complaint ID: " + complaint.getCompId());
            System.out.println("Title: " + complaint.getCompName());
            System.out.println("Description: " + complaint.getCompDesc());
            System.out.println("Date: " + complaint.getCompDate());
            System.out.println("Status: " + complaint.getCompStatus());
            System.out.println("User ID: " + complaint.getUserid());
            System.out.println("-----------------------------");
        }
    }

    private static void updateComplaintStatus() {
        System.out.print("Enter complaint ID to update: ");
        String complaintId = sc.nextLine();
        Complaints complaint = ComplaintManager.getComplaint(complaintId);
        if (complaint != null) {
            System.out.println("Current status: " + complaint.getCompStatus());
            System.out.println("Enter new status (PENDING/RESOLVED): ");
            String newStatus = sc.nextLine().toUpperCase();
            try {
                Complaints.ComplaintStatus status = Complaints.ComplaintStatus.valueOf(newStatus);
                complaint.setCompStatus(status);
                System.out.println("Complaint status updated successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter PENDING or RESOLVED.");
            }
        } else {
            System.out.println("Complaint not found.");
        }
    }
    private static void filterComplaints() {
        System.out.println("Filter by:");
        System.out.println("1) Status");
        System.out.println("2) Date");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        List<Complaints> filteredComplaints;
        switch (choice) {
            case 1:
                System.out.print("Enter status (PENDING/RESOLVED): ");
                String status = sc.nextLine().toUpperCase();
                try {
                    Complaints.ComplaintStatus complaintStatus = Complaints.ComplaintStatus.valueOf(status);
                    filteredComplaints = ComplaintManager.getAllComplaints().stream()
                            .filter(c -> c.getCompStatus() == complaintStatus)
                            .collect(Collectors.toList());
                    displayComplaints(filteredComplaints);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status.");
                }
                break;
            case 2:
                System.out.print("Enter date (YYYY-MM-DD): ");
                String dateStr = sc.nextLine();
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    filteredComplaints = ComplaintManager.getAllComplaints().stream()
                            .filter(c -> LocalDate.parse(c.getCompDate()).equals(date))
                            .collect(Collectors.toList());
                    displayComplaints(filteredComplaints);
                } catch (Exception e) {
                    System.out.println("Invalid date format.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void assignProfessorsToCourses() {
        while (true) {
            System.out.println("\nAssign Professors to Courses:");
            System.out.println("1) View All Courses and Assigned Professors");
            System.out.println("2) Assign Professor to a Course");
            System.out.println("3) Remove Professor from a Course");
            System.out.println("4) Go back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllCoursesAndProfessors();
                    break;
                case 2:
                    assignProfessorToCourse();
                    break;
                case 3:
                    removeProfessorFromCourse();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllCoursesAndProfessors() {
        List<Courses> allCourses = Courses.getAllCourses();
        System.out.println("Courses and Assigned Professors:");
        for (Courses course : allCourses) {
            System.out.println(course.getCode() + " - " + course.coursename + " : " + course.getProfessorName());
        }
    }

    private static void assignProfessorToCourse() {
        System.out.print("Enter course code: ");
        String courseCode = sc.nextLine();
        Courses course = Courses.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        assignProfessorToCourse(course);
    }

    private static void assignProfessorToCourse(Courses course) {
        System.out.print("Enter professor ID: ");
        int professorId = sc.nextInt();
        sc.nextLine();
        Professor professor = loginSystem.getProfessorsMap().get(professorId);
        if (professor == null) {
            System.out.println("Professor not found.");
            return;
        }

        course.assignProfessor(professor);
        System.out.println("Professor " + professor.getName() + " assigned to course " + course.getCode());
    }

    private static void removeProfessorFromCourse() {
        System.out.print("Enter course code: ");
        String courseCode = sc.nextLine();
        Courses course = Courses.getCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        Professor assignedProfessor = course.getAssignedProfessor();
        if (assignedProfessor == null) {
            System.out.println("No professor assigned to this course.");
            return;
        }

        String professorName = assignedProfessor.getName();
        course.removeProfessor();

        System.out.println("Professor " + professorName + " removed from course " + courseCode);
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1) Manage Courses");
            System.out.println("2) Manage Students");
            System.out.println("3) Manage Complaints");
            System.out.println("4) Assign Professors to Courses");
            System.out.println("5) Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    courses();
                    break;
                case 2:
                    students();
                    break;
                case 3:
                    complaints();
                    break;
                case 4:
                    assignProfessorsToCourses();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}