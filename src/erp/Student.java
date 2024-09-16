package erp;

import java.util.Date;

public class Student extends User {
    public int rollno;
    private long phoneno;
    private Date dob;
    public int semesterno;
    public int comcredits;
    public String[] comcourses;
    public float grades;

    public Student(int id, String password, String name, int rollno, long phoneno, Date dob, int semesterno, int comcredits, String[] comcourses, float grades) {
        super(id, password, name, "Student");
        this.rollno = rollno;
        this.phoneno = phoneno;
        this.dob = dob;
        this.semesterno = semesterno;
        this.comcredits = comcredits;
        this.comcourses = comcourses;
        this.grades = grades;
    }
}