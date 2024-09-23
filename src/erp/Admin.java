package erp;

public class Admin extends User {
    public Admin(int id, String password, String name, String role) {
        super(id, password, name, role);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getName() + " (ID: " + getId() + ")");
    }

    @Override
    public String getDetails() {
        return "Admin{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}