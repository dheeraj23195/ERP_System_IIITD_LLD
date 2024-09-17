package erp;

public class Main {
    private static Login loginSystem = new Login();

    public static void main(String[] args) {
        System.out.println("Welcome to IIITD ERP!!");
        while (true) {
            User user = loginSystem.start();
            if (user instanceof Student) {
                StudentInterface.run((Student) user);
            } else if (user instanceof Professor) {
                ProfessorInterface.run((Professor) user);
            } else if (user instanceof Admin) {
                AdminInterface.run((Admin) user);
            } else {
                System.out.println("Unknown user type. Exiting.");
                break;
            }
        }
    }
}