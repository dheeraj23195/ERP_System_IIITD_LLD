package erp;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private long phoneno;
    private int rollno;
    private int semester;
    CourseManager courseManager;
    private static List<Courses> completedCourses = new ArrayList<>();

    public Student(int id, String password, String name, String role, long phoneno, int rollno, int semester) {
        super(id, password, name, role);
        this.phoneno = phoneno;
        this.rollno = rollno;
        this.semester = semester;
        this.courseManager = new CourseManager(semester);
    }

    public long getPhoneno() {
        return phoneno;
    }

    public int getRollno() {
        return rollno;
    }

    public int getSemester() {
        return semester;
    }

    public void addCompletedCourse(Courses course) {
        completedCourses.add(course);
    }

    public static boolean checkCourseCompletion(String course) {
        for (Courses c : completedCourses) {
            if (course.equals(c.getCode())) {
                return true;
            }
        }
        return false;
    }
}