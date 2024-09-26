package erp;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class CourseManager {
    private static final int MAX_CREDITS = 20;
    private List<Courses> registeredCourses;
    private Map<String, CompletedCourse> completedCourses;
    private int currentSemester;
    private int currentCredits;
    private static Scanner sc = new Scanner(System.in);
    private static Login loginSystem;

    public CourseManager(int currentSemester) {
        this.registeredCourses = new ArrayList<>();
        this.completedCourses = new HashMap<>();
        this.currentSemester = currentSemester;
        this.currentCredits = 0;
    }

    public static void setLoginSystem(Login loginSystem) {
        CourseManager.loginSystem = loginSystem;
    }

    public static void addCourse() {
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
            System.out.print("Enter professor ID: ");
            int profId = sc.nextInt();
            sc.nextLine();
            Professor prof = loginSystem.getProfessorsMap().get(profId);
            if (prof != null) {
                newCourse.assignProfessor(prof);
                System.out.println("Professor assigned successfully!");
            } else {
                System.out.println("Professor not found. You can assign a professor later.");
            }
        }
    }

    public boolean addCourse(String courseCode) {
        Courses course = Courses.getCourse(courseCode);
        if (course == null) {
            System.out.println("This is not a valid course code");
            return false;
        } else if (course.getSemester() != currentSemester) {
            System.out.println("This is not a valid course in this semester");
            return false;
        }
        String[] prereqs = course.getPrereq();
        for (String prereq : prereqs) {
            if (!Student.checkCourseCompletion(prereq)) {
                System.out.println("You don't satisfy the Prerequisites for this course: " + prereq);
                return false;
            }
        }
        if (currentCredits + course.getCredits() <= MAX_CREDITS) {
            registeredCourses.add(course);
            currentCredits += course.getCredits();
            System.out.println("Course " + courseCode + " added successfully.");
            return true;
        }
        System.out.println("You cannot register for this course as this will exceed the maximum credits i.e. 20");
        return false;
    }

    public boolean dropCourse(String courseCode) {
        Courses course = registeredCourses.stream()
                .filter(c -> c.getCode().equals(courseCode))
                .findFirst()
                .orElse(null);
        if (course == null) {
            System.out.println("You are not registered for this course: " + courseCode);
            return false;
        }
        registeredCourses.remove(course);
        currentCredits -= course.getCredits();
        System.out.println("Course " + courseCode + " dropped successfully.");
        return true;
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses:");
        if (registeredCourses.isEmpty()) {
            System.out.println("There are no registered courses");
            return;
        }
        for (Courses course : registeredCourses) {
            System.out.println(course.getCode() + " - " + course.coursename + " (" + course.getCredits() + " credits)");
        }
        System.out.println("Total credits: " + currentCredits);
    }

    public List<Courses> getAvailableCourses() {
        return Courses.getAllCourses().stream()
                .filter(course -> course.getSemester() == currentSemester || course.getSemester() == 0)
                .filter(this::arePrerequisitesMet)
                .collect(Collectors.toList());
    }

    private boolean arePrerequisitesMet(Courses course) {
        if (course.getPrereq() == null || course.getPrereq().length == 0) {
            return true;
        }
        for (String prereq : course.getPrereq()) {
            if (!Student.checkCourseCompletion(prereq)) {
                return false;
            }
        }
        return true;
    }

    public void displayCoursesTable(List<Courses> coursesList) {
        System.out.printf("%-10s %-20s %-15s %-7s %-25s %-10s%n", "Code", "Course Name", "Professor", "Credits", "Timings", "Semester");
        System.out.println("---------------------------------------------------------------------------------------------");

        for (Courses course : coursesList) {
            StringBuilder timings = new StringBuilder();
            for (int i = 0; i < course.days.length; i++) {
                timings.append(course.days[i])
                        .append(": ")
                        .append(course.starttime)
                        .append(" - ")
                        .append(course.endtime);
                if (i < course.days.length - 1) {
                    timings.append("\n           ");
                }
            }
            System.out.printf("%-10s %-20s %-15s %-7d %-25s %-10d%n",
                    course.getCode(),
                    course.coursename,
                    course.getProfessorName(),
                    course.getCredits(),
                    timings.toString(),
                    course.getSemester());
        }
    }

    public void addGrade(String courseCode, String grade) {
        Courses course = registeredCourses.stream()
                .filter(c -> c.getCode().equals(courseCode))
                .findFirst()
                .orElse(null);
        if (course != null) {
            CompletedCourse completedCourse = new CompletedCourse(course, grade);
            completedCourses.put(courseCode, completedCourse);
            course.setGraded(true);
            System.out.println("Grade added successfully for course: " + courseCode);

            checkAndMoveCompletedCourses();
        } else {
            System.out.println("Course not found in registered courses: " + courseCode);
        }
    }

    private void checkAndMoveCompletedCourses() {
        boolean allGraded = registeredCourses.stream().allMatch(Courses::isGraded);
        if (allGraded) {
            for (Courses course : registeredCourses) {
                CompletedCourse completedCourse = completedCourses.get(course.getCode());
                if (completedCourse == null) {
                    completedCourse = new CompletedCourse(course, "F");
                    completedCourses.put(course.getCode(), completedCourse);
                }
            }
            registeredCourses.clear();
            currentCredits = 0;
            currentSemester++;
            System.out.println("All courses for the semester have been graded. Moving to semester " + currentSemester);
        }
    }

    public void updateSemester(int newSemester) {
        this.currentSemester = newSemester;
        this.registeredCourses.clear();
        this.currentCredits = 0;
    }

    public void displayGrades(int semester) {
        if (semester == 0) {
            displayAllGrades();
            System.out.printf("CGPA: %.2f%n", calculateCGPA());
        } else {
            displayGradesForSemester(semester);
            System.out.printf("SGPA for semester %d: %.2f%n", semester, calculateSGPA(semester));
            System.out.printf("CGPA: %.2f%n", calculateCGPA());
        }
    }

    private void displayGradesForSemester(int semester) {
        System.out.printf("Completed Courses for Semester %d:%n", semester);
        System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", "Code", "Course Name", "Credits", "Grade", "Grade Points");
        System.out.println("-------------------------------------------------------------------------------------");

        for (CompletedCourse course : completedCourses.values()) {
            if (course.getCourse().getSemester() == semester) {
                System.out.printf("%-10s %-30s %-10d %-10s %-10.2f%n",
                        course.getCourse().getCode(),
                        course.getCourse().coursename,
                        course.getCourse().getCredits(),
                        course.getGrade(),
                        course.getGradePoint());
            }
        }

        System.out.println("-------------------------------------------------------------------------------------");
    }

    public void displayAllGrades() {
        System.out.println("All Completed Courses:");
        System.out.printf("%-10s %-30s %-10s %-10s %-10s %-10s%n", "Code", "Course Name", "Credits", "Grade", "Grade Points", "Semester");
        System.out.println("------------------------------------------------------------------------------------------------");

        for (CompletedCourse course : completedCourses.values()) {
            System.out.printf("%-10s %-30s %-10d %-10s %-10.2f %-10d%n",
                    course.getCourse().getCode(),
                    course.getCourse().coursename,
                    course.getCourse().getCredits(),
                    course.getGrade(),
                    course.getGradePoint(),
                    course.getCourse().getSemester());
        }

        System.out.println("------------------------------------------------------------------------------------------------");
    }

    public double calculateSGPA(int semester) {
        return calculateGPA(semester);
    }

    public double calculateCGPA() {
        return calculateGPA(0);
    }

    private double calculateGPA(int semester) {
        List<CompletedCourse> courses = completedCourses.values().stream()
                .filter(c -> semester == 0 || c.getCourse().getSemester() == semester)
                .collect(Collectors.toList());

        if (courses.isEmpty()) {
            return 0.0;
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (CompletedCourse course : courses) {
            totalGradePoints += course.getGradePoint() * course.getCourse().getCredits();
            totalCredits += course.getCourse().getCredits();
        }

        return totalGradePoints / totalCredits;
    }

    public Map<String, CompletedCourse> getCompletedCourses() {return this.completedCourses;}

    public List<Courses> getRegisteredCourses() {
        return new ArrayList<>(registeredCourses);
    }

    class CompletedCourse {
        private Courses course;
        private String grade;

        public CompletedCourse(Courses course, String grade) {
            this.course = course;
            this.grade = grade;
        }

        public Courses getCourse() {
            return course;
        }

        public String getGrade() {
            return grade;
        }

        public double getGradePoint() {
            switch (grade) {
                case "A+": return 10.0;
                case "A": return 9.0;
                case "A-": return 8.0;
                case "B": return 7.0;
                case "B-": return 6.0;
                case "C": return 5.0;
                case "C-": return 4.0;
                case "D": return 3.0;
                case "F": return 0.0;
                default: return 0.0;
            }
        }
    }
}