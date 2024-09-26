package erp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student extends User implements Gradable,Schedulable{
    private long phoneno;
    private int rollno;
    private int semester;
    CourseManager courseManager;
    private static List<Courses> completedCourses = new ArrayList<>();
    private static List<CourseManager.CompletedCourse> courseasTA=new ArrayList<CourseManager.CompletedCourse>();

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

    public List<Courses> getRegisteredCourses() {
        return courseManager.getRegisteredCourses();
    }

    public Map<String, CourseManager.CompletedCourse> getCompletedCourses() {
        return courseManager.getCompletedCourses();
    }

    public double getCGPA() {
        return courseManager.calculateCGPA();
    }

    public void addCompletedCourse(Courses course) {
        completedCourses.add(course);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneno(long phoneno) {
        this.phoneno = phoneno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }
    @Override
    public void displaySchedule() {
        Schedule.displaySchedule(this);
    }
    public void addCourseforTA(CourseManager.CompletedCourse course) {
        courseasTA.add(course);
    }
    public void setSemester(int semester) {
        this.semester = semester;
        this.courseManager.updateSemester(semester);
    }
    public static boolean checkCourseCompletion(String course) {
        for (Courses c : completedCourses) {
            if (course.equals(c.getCode())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void displayInfo() {
        System.out.println("Student: " + getName() + " (ID: " + getId() + ")");
        System.out.println("Roll No: " + rollno + ", Semester: " + semester);
    }

    @Override
    public String getDetails() {
        return "Student{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", rollno=" + rollno +
                ", semester=" + semester +
                '}';
    }
    @Override
    public void addGrade(String courseCode, String grade) {
        courseManager.addGrade(courseCode, grade);
    }

    @Override
    public void displayGrades(int semester) {
        courseManager.displayGrades(semester);
    }

    @Override
    public double calculateGPA() {
        return courseManager.calculateCGPA();
    }
}