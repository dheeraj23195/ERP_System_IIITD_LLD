package erp;

public abstract class User {
    protected int id;
    protected String password;
    protected String name;
    protected String role;

    public User(int id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
    public abstract void displayInfo();
    public abstract String getDetails();
}