package erp;

public class Main {
    private static Login loginSystem = new Login();

    public static void professorInterface(Professor professor) {
        System.out.println("Welcome, Professor " + professor.getName() + "!");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to IIITD ERP!!");
        while (true) {
            User user = loginSystem.start();
            if (user instanceof Student) {
                StudentInterface.run((Student) user);
            } else if (user instanceof Professor) {
                professorInterface((Professor) user);
            } else {
                System.out.println("Unknown user type. Exiting.");
                break;
            }
        }
    }
}