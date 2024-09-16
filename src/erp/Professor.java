package erp;

public class Professor extends User {
    private int age;
    private int holidaysLeft;

    public Professor(int id, String password, String name, int age) {
        super(id, password, name, "Professor");
        this.age = age;
        this.holidaysLeft = 30;
    }
}