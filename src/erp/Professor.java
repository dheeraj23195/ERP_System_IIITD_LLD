package erp;

public class Professor extends User {
    private String[] taughtCourses;

    public Professor(int id, String password, String name) {
        super(id, password, name, "Professor");
        this.taughtCourses = new String[5];
    }
}