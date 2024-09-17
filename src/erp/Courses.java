package erp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Courses {
    private static List<Courses> allCourses = new ArrayList<>();

    public String code;
    public String coursename;
    public String prof;
    public int credits;
    public String[] prereq;
    public LocalTime starttime;
    public LocalTime endtime;
    public String[] days;
    public int semester;

    public Courses(String code, String coursename, String prof, int credits, String[] prereq, LocalTime starttime, LocalTime endtime, String[] days, int semester) {
        this.code = code;
        this.coursename = coursename;
        this.prof = prof;
        this.credits = credits;
        this.prereq = prereq;
        this.starttime = starttime;
        this.endtime = endtime;
        this.days = days;
        this.semester = semester;
    }

    public static void initializeCourses() {
        String[] professors = {
                "Dr. Alan Turing",
                "Dr. Ada Lovelace",
                "Dr. John von Neumann",
                "Dr. Grace Hopper",
                "Dr. Claude Shannon"
        };

        addCourse(new Courses("DES101", "HCI", professors[0], 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 1));
        addCourse(new Courses("CSE101", "IP", professors[1], 4, new String[]{}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 1));
        addCourse(new Courses("CSE111", "DC", professors[2], 4, new String[]{}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 1));
        addCourse(new Courses("COM101", "COM", professors[3], 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Tue", "Thu"}, 1));
        addCourse(new Courses("MTH100", "LA", professors[4], 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Tue", "Thu"}, 1));

        addCourse(new Courses("CSE112", "CO", professors[0], 4, new String[]{"CSE111"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Mon", "Wed"}, 2));
        addCourse(new Courses("CSE111", "DSA", professors[1], 4, new String[]{"CSE101"}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Tue", "Thu"}, 2));
        addCourse(new Courses("SSH101", "CTRSS", professors[2], 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Mon", "Wed"}, 2));
        addCourse(new Courses("SSH112", "ISA", professors[3], 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 2));
        addCourse(new Courses("MTH201", "PNS", professors[4], 4, new String[]{"MTH100"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 2));
        addCourse(new Courses("ECO101", "MB", professors[0], 4, new String[]{}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 2));

        addCourse(new Courses("CP1", "CP1", professors[1], 2, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 0), new String[]{"Fri"}, 0));
        addCourse(new Courses("SG", "SG", professors[2], 2, new String[]{}, LocalTime.of(17, 0), LocalTime.of(18, 0), new String[]{"Fri"}, 0));
        addCourse(new Courses("CW", "CW", professors[3], 2, new String[]{}, LocalTime.of(18, 0), LocalTime.of(19, 0), new String[]{"Fri"}, 0));

        addCourse(new Courses("CSE201", "AP", professors[4], 4, new String[]{"CSE111"}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Mon", "Wed"}, 3));
        addCourse(new Courses("MTH203", "M-III", professors[0], 4, new String[]{"MTH201"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Tue", "Thu"}, 3));
        addCourse(new Courses("SSH201", "RMSSD", professors[1], 4, new String[]{}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Mon", "Wed"}, 3));
        addCourse(new Courses("SSH202", "PASSP", professors[2], 4, new String[]{}, LocalTime.of(16, 0), LocalTime.of(17, 30), new String[]{"Tue", "Thu"}, 3));
        addCourse(new Courses("SSH211", "SSP", professors[3], 4, new String[]{}, LocalTime.of(9, 0), LocalTime.of(10, 30), new String[]{"Tue", "Thu"}, 3));
        addCourse(new Courses("MTH211", "DM", professors[4], 4, new String[]{"MTH100"}, LocalTime.of(11, 0), LocalTime.of(12, 30), new String[]{"Mon", "Wed"}, 3));
        addCourse(new Courses("CSE211", "OS", professors[0], 4, new String[]{"CSE112"}, LocalTime.of(14, 0), LocalTime.of(15, 30), new String[]{"Tue", "Thu"}, 3));
    }

    public static void addCourse(Courses course) {
        allCourses.add(course);
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

    @Override
    public String toString() {
        return String.format("Course: %s - %s, Professor: %s, Credits: %d, Semester: %d", code, coursename, prof, credits, semester);
    }
}