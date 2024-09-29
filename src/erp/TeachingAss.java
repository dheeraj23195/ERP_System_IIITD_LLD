package erp;

import java.util.ArrayList;
import java.util.List;

public class TeachingAss extends Student {
    private final List<String> assignedCourses;
    private static final List<TeachingAss> approvedTAs = new ArrayList<>();

    public TeachingAss(Student student) {
        super(student.getId(), student.getPassword(), student.getName(), "TeachingAssistant", student.getPhoneno(), student.getRollno(), student.getSemester());
        this.assignedCourses = new ArrayList<>();
        approvedTAs.add(this);
    }

    public void assignCourse(String courseCode) {
        if (!assignedCourses.contains(courseCode)) {
            assignedCourses.add(courseCode);
        }
    }

    public List<String> getAssignedCourses() {
        return new ArrayList<>(assignedCourses);
    }

    public static List<TeachingAss> getApprovedTAs() {
        return new ArrayList<>(approvedTAs);
    }

    public void manageGrades(String courseCode, int studentId, String grade) {
        if (!assignedCourses.contains(courseCode)) {
            System.out.println("You are not assigned to this course.");
            return;
        }
        Student student = Login.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.addGrade(courseCode, grade);
        System.out.println("Grade added for student " + studentId + " in course " + courseCode);
    }
    public void removeAssignedCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }

    public static void removeApprovedTA(TeachingAss ta) {
        approvedTAs.remove(ta);
    }

    @Override
    public void setTAStatus(boolean status) {
        super.setTAStatus(status);
        if (!status) {
            assignedCourses.clear();
        }
    }


    @Override
    public void displayInfo() {
        System.out.println("TA: " + getName() + " (ID: " + getId() + ")");
        System.out.println("Courses assigned: " + String.join(", ", assignedCourses));
    }

    @Override
    public String getDetails() {
        return "TA{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", assignedCourses=" + assignedCourses +
                '}';
    }

    @Override
    public boolean getTAstatus() {
        return true;
    }

    public static boolean isApprovedTA(int studentId) {
        return approvedTAs.stream().anyMatch(ta -> ta.getId() == studentId);
    }

    public static TeachingAss getApprovedTAById(int studentId) {
        return approvedTAs.stream()
                .filter(ta -> ta.getId() == studentId)
                .findFirst()
                .orElse(null);
    }
}