package erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseManager {
    private static final int MAX_CREDITS = 20;
    private List<Courses> registeredCourses;
    private Map<String, CompletedCourse> completedCourses;
    private int currentSemester;
    private int currentCredits;

    public CourseManager(int currentSemester) {
        this.registeredCourses = new ArrayList<>();
        this.completedCourses = new HashMap<>();
        this.currentSemester = currentSemester;
        this.currentCredits = 0;
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
                    course.prof,
                    course.getCredits(),
                    timings.toString(),
                    course.getSemester());
        }
    }

    public void addGrade(String courseCode, String grade) {
        Courses course = Courses.getCourse(courseCode);
        if (course != null) {
            CompletedCourse completedCourse = new CompletedCourse(course, grade);
            completedCourses.put(courseCode, completedCourse);
            registeredCourses.removeIf(c -> c.getCode().equals(courseCode));
            System.out.println("Grade added successfully for course: " + courseCode);
        } else {
            System.out.println("Course not found: " + courseCode);
        }
    }

    public double calculateSGPA() {
        return calculateGPA(currentSemester);
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

    public void displayGrades() {
        System.out.println("Completed Courses:");
        System.out.printf("%-10s %-30s %-10s %-10s %-10s%n", "Code", "Course Name", "Credits", "Grade", "Grade Points");
        System.out.println("-------------------------------------------------------------------------------------");

        for (CompletedCourse course : completedCourses.values()) {
            System.out.printf("%-10s %-30s %-10d %-10s %-10.2f%n",
                    course.getCourse().getCode(),
                    course.getCourse().coursename,
                    course.getCourse().getCredits(),
                    course.getGrade(),
                    course.getGradePoint());
        }

        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf("SGPA: %.2f%n", calculateSGPA());
        System.out.printf("CGPA: %.2f%n", calculateCGPA());
    }

    public List<Courses> getRegisteredCourses() {
        return new ArrayList<>(registeredCourses);
    }

    private class CompletedCourse {
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