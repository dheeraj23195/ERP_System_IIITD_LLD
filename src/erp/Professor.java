package erp;

import java.util.ArrayList;
import java.util.List;


public class Professor extends User implements Schedulable {
    private final List<String> taughtCourses;

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