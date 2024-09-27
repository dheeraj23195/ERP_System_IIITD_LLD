package erp;

import java.util.HashMap;
import java.util.Scanner;

public class Login {
    private HashMap<Integer, User> usersMap = new HashMap<>();
    private static HashMap<Integer, Student> studentsMap = new HashMap<>();
    private static HashMap<Integer, Professor> professorsMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private static final int ADMIN_ID = 2005;
    private static final String ADMIN_PASSWORD = "admin123";

    public Login() {
        prepopulateStudents();
        prepopulateProfessors();
    }

    private void prepopulateStudents() {
        Student student1 = new Student(1001, "pass1001", "Alice Smith", "Student", 1234567890L, 1001, 1);
        Student student2 = new Student(1002, "pass1002", "Bob Johnson", "Student", 2345678901L, 1002, 2);
        Student student3 = new Student(1003, "pass1003", "Charlie Brown", "Student", 3456789012L, 1003, 7);
        Student student4 = new Student(1004, "pass1004", "Diana Prince", "Student", 4567890123L, 1004, 1);
        Student student5 = new Student(1005, "pass1005", "Ethan Hunt", "Student", 5678901234L, 1005, 2);

        addUser(student1);
        addUser(student2);
        addUser(student3);
        addUser(student4);
        addUser(student5);
    }

    private void prepopulateProfessors() {
        Professor prof1 = new Professor(2001, "profpass1", "Dr. Alan Turing");
        Professor prof2 = new Professor(2002, "profpass2", "Dr. Ada Lovelace");
        Professor prof3 = new Professor(2003, "profpass3", "Dr. John von Neumann");
        Professor prof4 = new Professor(2004, "profpass4", "Dr. Grace Hopper");
        Professor prof5 = new Professor(2005, "profpass5", "Dr. Claude Shannon");

        assignCourseToProfessor("DES101", prof1);
        assignCourseToProfessor("CSE101", prof2);
        assignCourseToProfessor("CSE111", prof3);
        assignCourseToProfessor("COM101", prof4);
        assignCourseToProfessor("MTH100", prof5);

        assignCourseToProfessor("CSE112", prof1);
        assignCourseToProfessor("SSH101", prof3);
        assignCourseToProfessor("SSH112", prof4);
        assignCourseToProfessor("MTH201", prof5);
        assignCourseToProfessor("ECO101", prof1);

        assignCourseToProfessor("CP1", prof2);
        assignCourseToProfessor("SG", prof3);
        assignCourseToProfessor("CW", prof4);

        assignCourseToProfessor("CSE201", prof5);
        assignCourseToProfessor("MTH203", prof1);
        assignCourseToProfessor("SSH201", prof2);
        assignCourseToProfessor("SSH202", prof3);
        assignCourseToProfessor("SSH211", prof4);
        assignCourseToProfessor("MTH211", prof5);
        assignCourseToProfessor("CSE211", prof1);

        addUser(prof1);
        addUser(prof2);
        addUser(prof3);
        addUser(prof4);
        addUser(prof5);
    }

    private void assignCourseToProfessor(String courseCode, Professor professor) {
        Courses course = Courses.getCourse(courseCode);
        if (course != null) {
            course.assignProfessor(professor);
        } else {
            System.out.println("Course " + courseCode + " not found.");
        }
    }

    private void addUser(User user) {
        usersMap.put(user.getId(), user);
        if (user instanceof Student) {
            studentsMap.put(user.getId(), (Student) user);
        } else if (user instanceof Professor) {
            professorsMap.put(user.getId(), (Professor) user);
        }
    }

    public User start() {
        while (true) {
            System.out.println("Do you want to 1) Sign Up 2) Log In or 3) Exit: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                signupProcess();
            } else if (choice == 2) {
                User user = loginProcess();
                if (user != null) {
                    return user;
                }
            } else if (choice == 3) {
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            }
        }
    }

    private void signupProcess() {
        System.out.println("Sign Up selected.");
        System.out.println("Please enter your userid: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        String role = "";
        while (true) {
            System.out.println("Please enter your role 1) Student 2) Professor: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();
            if (roleChoice == 1) {
                role = "Student";
                System.out.println("Enter your phone number: ");
                long phoneNo = scanner.nextLong();
                System.out.println("Enter your roll number: ");
                int rollNo = scanner.nextInt();
                int semester = 1;
                scanner.nextLine();
                signup(userId, password, name, role, phoneNo, rollNo, semester);
                break;
            } else if (roleChoice == 2) {
                role = "Professor";
                signup(userId, password, name, role, 0, 0, 0);
                break;
            } else {
                System.out.println("Invalid role selected. Please select 1 for Student or 2 for Professor.");
            }
        }
    }

    private User loginProcess() {
        System.out.println("Log In selected.");
        System.out.println("Please enter your userid: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        return login(userId, password);
    }

    private boolean signup(int id, String password, String name, String role, long phoneno, int rollno, int semester) {
        if (usersMap.containsKey(id)) {
            System.out.println("The user already exists, please try logging in.");
            return false;
        }
        User newUser;
        if (role.equalsIgnoreCase("Student")) {
            newUser = new Student(id, password, name, role, phoneno, rollno, semester);
            studentsMap.put(id, (Student) newUser);
        } else if (role.equalsIgnoreCase("Professor")) {
            newUser = new Professor(id, password, name);
            professorsMap.put(id, (Professor) newUser);
        } else {
            System.out.println("Invalid role. Cannot create user.");
            return false;
        }
        usersMap.put(id, newUser);
        System.out.println("Sign up successful. Please login.");
        return true;
    }

    private User login(int id, String password) {
        if (id == ADMIN_ID && password.equals(ADMIN_PASSWORD)) {
            return new Admin(ADMIN_ID, ADMIN_PASSWORD, "Administrator", "Administrator");
        }
        if (usersMap.containsKey(id)) {
            User user = usersMap.get(id);
            if (password.equals(user.getPassword())) {
                return user;
            } else {
                System.out.println("The Password is incorrect. Please try again.");
                return null;
            }
        } else {
            System.out.println("The User doesn't exist. Please sign up.");
            return null;
        }
    }

    public HashMap<Integer, Student> getStudentsMap() {
        return studentsMap;
    }

    public HashMap<Integer, Professor> getProfessorsMap() {
        return professorsMap;
    }

    public static Student getStudentById(int studentId) {
        return studentsMap.get(studentId);
    }
}