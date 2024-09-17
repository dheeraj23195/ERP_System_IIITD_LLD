package erp;

public class Main {
    private static Login loginSystem;
    private static ProfessorInterface professorInterface;
    private static StudentInterface studentInterface;
    private static AdminInterface adminInterface;

    public static void main(String[] args) {
        Courses.initializeCourses();
        System.out.println("Welcome to IIITD ERP!!");
        loginSystem = new Login();
        professorInterface = new ProfessorInterface(loginSystem);
        studentInterface = new StudentInterface();
        adminInterface = new AdminInterface(loginSystem);

        while (true) {
            User user = loginSystem.start();
            if (user instanceof Student) {
                StudentInterface.run((Student) user);
            } else if (user instanceof Professor) {
                professorInterface.run((Professor) user);
            } else if (user instanceof Admin) {
                AdminInterface.adminMenu();
            } else {
                System.out.println("Unknown user type. Exiting.");
                break;
            }
        }
    }
}