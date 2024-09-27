package erp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Professor extends User implements Schedulable {
    private List<String> taughtCourses;

    public Professor(int id, String password, String name) {
        super(id, password, name, "Professor");
        this.taughtCourses = new ArrayList<>();
    }

    public void addCourse(String courseCode) {
        if (!taughtCourses.contains(courseCode)) {
            taughtCourses.add(courseCode);
        }
    }

    public void removeCourse(String courseCode) {
        taughtCourses.remove(courseCode);
    }

    public List<String> getTaughtCourses() {
        return new ArrayList<>(taughtCourses);
    }

    // New methods for TA management
    public void viewTAApplications(String courseCode) {
        List<Student.TAApplication> applications = Student.getTAApplications().stream()
                .filter(app -> app.getCourseCode().equals(courseCode) && !app.isApproved())
                .collect(Collectors.toList());

        if (applications.isEmpty()) {
            System.out.println("No pending TA applications for " + courseCode);
            return;
        }

        System.out.println("TA Applications for " + courseCode + ":");
        for (Student.TAApplication app : applications) {
            Student student = app.getStudent();
            System.out.println("Student: " + student.getName() + " (ID: " + student.getId() + ")");
        }
    }

    public void approveTA(String courseCode, int studentId) {
        Student.TAApplication application = Student.getTAApplications().stream()
                .filter(app -> app.getCourseCode().equals(courseCode) && app.getStudent().getId() == studentId)
                .findFirst()
                .orElse(null);

        if (application == null) {
            System.out.println("No pending application found for the given student and course.");
            return;
        }

        application.setApproved(true);
        TeachingAss ta = new TeachingAss(application.getStudent());
        ta.assignCourse(courseCode);
        System.out.println("TA application approved for student " + studentId + " in course " + courseCode);
    }

    public void viewApprovedTAs(String courseCode) {
        List<TeachingAss> approvedTAs = TeachingAss.getApprovedTAs().stream()
                .filter(ta -> ta.getAssignedCourses().contains(courseCode))
                .collect(Collectors.toList());

        if (approvedTAs.isEmpty()) {
            System.out.println("No approved TAs for " + courseCode);
            return;
        }

        System.out.println("Approved TAs for " + courseCode + ":");
        for (TeachingAss ta : approvedTAs) {
            System.out.println("TA: " + ta.getName() + " (ID: " + ta.getId() + ")");
        }
    }

    @Override
    public void displaySchedule() {
        Schedule.displaySchedule(this);
    }

    @Override
    public void displayInfo() {
        System.out.println("Professor: " + getName() + " (ID: " + getId() + ")");
        System.out.println("Courses taught: " + String.join(", ", taughtCourses));
    }

    @Override
    public String getDetails() {
        return "Professor{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", taughtCourses=" + taughtCourses +
                '}';
    }
}