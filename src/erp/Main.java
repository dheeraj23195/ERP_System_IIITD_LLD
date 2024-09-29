package erp;
import java.time.LocalDateTime;

public class Main {
    private static Login loginSystem;
    private static ProfessorInterface professorInterface;
    private static StudentInterface studentInterface;
    private static AdminInterface adminInterface;
    private static TAInterface taInterface;
    private static LocalDateTime now;

    public static void main(String[] args) {
        initializeSystem();
        now=LocalDateTime.now();
        runMainLoop();
    }

    private static void initializeSystem() {
        Courses.initializeCourses();
        System.out.println("Welcome to IIITD ERP!!");
        loginSystem = new Login();
        professorInterface = new ProfessorInterface(loginSystem);
        studentInterface = new StudentInterface();
        adminInterface = new AdminInterface(loginSystem);
        taInterface = new TAInterface(loginSystem);
        StudentInterface.setTAInterface(taInterface);
    }

    private static void runMainLoop() {
        while (true) {
            User user = loginSystem.start();
            if (user == null) {
                System.out.println("Login failed. Please try again.");
                continue;
            }

            user.displayInfo();

            if (user instanceof Student) {
                StudentInterface.run(user);
            } else if (user instanceof Professor) {
                professorInterface.run((Professor) user);
            } else if (user instanceof Admin) {
                AdminInterface.adminMenu();
            } else {
                System.out.println("Unknown user type. Exiting.");
                break;
            }

            System.out.println("Logged out. " + user.getDetails());
        }
    }

    public static LocalDateTime getNow() {
        return now;
    }

}