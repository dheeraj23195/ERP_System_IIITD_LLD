package erp;

public class User {
    private int id;
    private String password;
    private String name;
    private String role;

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
}
