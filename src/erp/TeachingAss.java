package erp;

import java.util.List;

public class TeachingAss extends Student {
    private boolean completed;
    private List<String> assignedCourses;

    public TeachingAss(int id, String password, String name, long phoneno, int rollno, int semester) {
        super(id, password, name, "TeachingAssistant", phoneno, rollno, semester);
        this.completed = false;
    }

    public void assignCourse(String courseCode) {
        if (!assignedCourses.contains(courseCode)) {
            assignedCourses.add(courseCode);
        }
    }

    public void removeAssignedCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }

    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
}
