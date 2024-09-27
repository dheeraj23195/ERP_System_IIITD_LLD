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
    private static List<List<Object>> courseAsTA = new ArrayList<>();
    private static List<TAApplication> taApplications = new ArrayList<>();

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
    public boolean applyForTA(String courseCode) {
        if (this.semester <= 5) {
            System.out.println("You must be above 5th semester to apply for a TA position.");
            return false;
        }

        CourseManager.CompletedCourse completedCourse = courseManager.getCompletedCourses().get(courseCode);
        if (completedCourse == null || completedCourse.getGradePoint() < 8.0) {
            System.out.println("You must have completed the course with a grade of 8.0 or higher to apply for a TA position.");
            return false;
        }

        TAApplication application = new TAApplication(this, courseCode);
        taApplications.add(application);
        System.out.println("Application submitted for TA position in " + courseCode);
        return true;
    }

    public static List<TAApplication> getTAApplications() {
        return taApplications;
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

    public CourseManager getCourseManager() {
        return courseManager;
    }


    class TAApplication {
        private Student student;
        private String courseCode;
        private boolean approved;

        public TAApplication(Student student, String courseCode) {
            this.student = student;
            this.courseCode = courseCode;
            this.approved = false;
        }

        // Getters and setters
        public Student getStudent() { return student; }
        public String getCourseCode() { return courseCode; }
        public boolean isApproved() { return approved; }
        public void setApproved(boolean approved) { this.approved = approved; }
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