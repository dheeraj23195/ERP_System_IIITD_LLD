package erp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Courses {
    private static final List<Courses> allCourses = new ArrayList<>();
    public String code;
    public String coursename;
    private Professor assignedProfessor;
    public int credits;
    public String[] prereq;
    public LocalTime starttime;
    public LocalTime endtime;
    public String[] days;
    public int semester;
    private String syllabus;
    private int enrollmentLimit;
    private String officeHours;
    private boolean graded;

    public Courses(String code, String coursename, int credits, String[] prereq, LocalTime starttime, LocalTime endtime, String[] days, int semester) {
        this.code = code;
        this.coursename = coursename;
        this.credits = credits;
        this.prereq = prereq;
        this.starttime = starttime;
        this.endtime = endtime;
        this.days = days;
        this.semester = semester;
        this.syllabus = "";
        this.enrollmentLimit = 0;
        this.officeHours = "";
        this.graded = false;
    }

    public static void initializeCourses() {
        addCourse(new Courses("DES101", "HCI", 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 1));
        addCourse(new Courses("CSE101", "IP", 4, new String[]{}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 1));
        addCourse(new Courses("CSE111", "DC", 4, new String[]{}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 1));
        addCourse(new Courses("COM101", "COM", 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Tue", "Thu"}, 1));
        addCourse(new Courses("MTH100", "LA", 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Tue", "Thu"}, 1));

        addCourse(new Courses("CSE112", "CO", 4, new String[]{"CSE111"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Mon", "Wed"}, 2));
        addCourse(new Courses("CSE121", "DSA", 4, new String[]{"CSE101"}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Tue", "Thu"}, 2));
        addCourse(new Courses("SSH101", "CTRSS", 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Mon", "Wed"}, 2));
        addCourse(new Courses("SSH112", "ISA", 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 2));
        addCourse(new Courses("MTH201", "PNS", 4, new String[]{"MTH100"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 2));
        addCourse(new Courses("ECO101", "MB", 4, new String[]{}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 2));

        addCourse(new Courses("CSE201", "AP", 4, new String[]{"CSE121"}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 3));
        addCourse(new Courses("MTH203", "M-III", 4, new String[]{"MTH201"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 3));
        addCourse(new Courses("SSH201", "RMSSD", 4, new String[]{}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 3));
        addCourse(new Courses("SSH202", "PASSP", 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Tue", "Thu"}, 3));
        addCourse(new Courses("SSH211", "SSP", 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Tue", "Thu"}, 3));
        addCourse(new Courses("MTH211", "DM", 4, new String[]{"MTH100"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Mon", "Wed"}, 3));
        addCourse(new Courses("CSE211", "OS", 4, new String[]{"CSE112"}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Tue", "Thu"}, 3));

        addCourse(new Courses("CSE202", "FCS", 4, new String[]{"CSE121"}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 4));
        addCourse(new Courses("CSE231", "OOP", 4, new String[]{"CSE101"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 4));

        addCourse(new Courses("CSE301", "DBMS", 4, new String[]{"CSE121"}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 5));
        addCourse(new Courses("CSE321", "CN", 4, new String[]{"CSE211"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 5));
        addCourse(new Courses("CSE341", "TOC", 4, new String[]{"MTH211"}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 5));
        addCourse(new Courses("HSS2xx", "HSS Elective", 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Tue", "Thu"}, 5));
        addCourse(new Courses("SCI2xx", "Science Elective", 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Fri"}, 5));

        addCourse(new Courses("CSE302", "CD", 4, new String[]{"CSE201"}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 6));
        addCourse(new Courses("CSE322", "AI", 4, new String[]{"CSE121", "MTH201"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 6));
        addCourse(new Courses("CSE342", "ML", 4, new String[]{"CSE121", "MTH201"}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 6));
        addCourse(new Courses("CSE3xx", "CSE Elective", 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Tue", "Thu"}, 6));
        addCourse(new Courses("OPNxxx", "Open Elective", 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Fri"}, 6));

        addCourse(new Courses("CP1", "CP1", 2, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 0), new String[]{"Fri"}, 0));
        addCourse(new Courses("SG", "SG", 2, new String[]{}, LocalTime.of(17, 0), LocalTime.of(18, 0), new String[]{"Fri"}, 0));
        addCourse(new Courses("CW", "CW", 2, new String[]{}, LocalTime.of(18, 0), LocalTime.of(19, 0), new String[]{"Fri"}, 0));
    }

    public static void addCourse(Courses course) {
        allCourses.add(course);
    }

    public void assignProfessor(Professor professor) {
        if (this.assignedProfessor == null) {
            this.assignedProfessor = professor;
            professor.addCourse(this.code);
        } else {
            System.out.println("Course " + this.code + " already has an assigned professor.");
        }
    }

    public Professor getAssignedProfessor() {
        return assignedProfessor;
    }

    public String getProfessorName() {
        return (assignedProfessor != null) ? assignedProfessor.getName() : "Not Assigned";
    }

    public static boolean removeCourse(String courseCode) {
        return allCourses.removeIf(course -> course.getCode().equals(courseCode));
    }

    public static Courses getCourse(String courseCode) {
        return allCourses.stream()
                .filter(course -> course.getCode().equals(courseCode))
                .findFirst()
                .orElse(null);
    }

    public static List<Courses> getAllCourses() {
        return new ArrayList<>(allCourses);
    }

    public int getSemester() {
        return semester;
    }

    public int getCredits() {
        return credits;
    }

    public String[] getPrereq() {
        return prereq;
    }

    public String getCode() {
        return code;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }

    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public boolean isGraded() {
        return graded;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setGraded(boolean graded) {
        this.graded = graded;
    }

    @Override
    public String toString() {
        return "Course: " + code + " - " + coursename +
                "\nProfessor: " + getProfessorName() +
                "\nCredits: " + credits +
                "\nSemester: " + semester +
                "\nTimings: " + starttime + " - " + endtime +
                "\nDays: " + String.join(", ", days) +
                "\nPrerequisites: " + String.join(", ", prereq) +
                "\nSyllabus: " + syllabus +
                "\nEnrollment Limit: " + enrollmentLimit +
                "\nOffice Hours: " + officeHours +
                "\nGraded: " + graded;
    }

    public void removeProfessor() {
        this.assignedProfessor = null;
    }

}