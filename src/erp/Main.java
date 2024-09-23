package erp;

public class Main {
    private static Login loginSystem;
    private static ProfessorInterface professorInterface;
    private static StudentInterface studentInterface;
    private static AdminInterface adminInterface;

    public static void main(String[] args) {
        initializeSystem();
        runMainLoop();
    }

    private static void initializeSystem() {
        Courses.initializeCourses();
        System.out.println("Welcome to IIITD ERP!!");
        loginSystem = new Login();
        professorInterface = new ProfessorInterface(loginSystem);
        studentInterface = new StudentInterface();
        adminInterface = new AdminInterface(loginSystem);
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
                handleStudentUser((Student) user);
            } else if (user instanceof Professor) {
                handleProfessorUser((Professor) user);
            } else if (user instanceof Admin) {
                handleAdminUser((Admin) user);
            } else {
                System.out.println("Unknown user type. Exiting.");
                break;
            }

            System.out.println("Logged out. " + user.getDetails()); // Using the abstract method
        }
    }

    private static void handleStudentUser(Student student) {
        System.out.println("Welcome, Student " + student.getName());
        if (student instanceof Schedulable) {
            ((Schedulable) student).displaySchedule();
        }
        if (student instanceof Gradable) {
            System.out.println("Current CGPA: " + ((Gradable) student).calculateGPA());
        }
        StudentInterface.run(student);
    }

    private static void handleProfessorUser(Professor professor) {
        System.out.println("Welcome, Professor " + professor.getName());
        professorInterface.run(professor);
    }

    private static void handleAdminUser(Admin admin) {
        System.out.println("Welcome, Administrator " + admin.getName());
        AdminInterface.adminMenu();
    }
}