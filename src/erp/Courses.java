package erp;

import java.time.LocalTime;
public class Courses {
    public String code;
    public String coursename;
    public String prof;
    public int credits;
    public String[] prereq;
    public LocalTime starttime;
    public LocalTime endtime;
    public String[] days;

    public Courses(String code, String coursename, String prof, int credits, String[] prereq, LocalTime starttime, LocalTime endtime, String[] days) {
        this.code = code;
        this.coursename = coursename;
        this.prof = prof;
        this.credits = credits;
        this.prereq = prereq;
        this.starttime = starttime;
        this.endtime = endtime;
        this.days = days;
    }
}
