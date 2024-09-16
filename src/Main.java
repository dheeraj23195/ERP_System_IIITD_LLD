import erp.User;
import erp.Login;
import java.util.Scanner;

public class Main {
    public static void login() {
        System.out.println("Welcome to IIITD ERP!!");
        System.out.println("Do you want to 1) Sign Up or 2) Log In: ");
        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();
        Login login = new Login();
        if (choice == 1) {
            System.out.println("Sign Up selected.");
            System.out.println("Please enter your userid: ");
            int username = Integer.parseInt(scanner.next());
            System.out.println("Please enter your password: ");
            String password = scanner.next();
            scanner.nextLine();
            System.out.println("Please enter your name: ");
            String name = scanner.nextLine();
            String rolein = "";
            while (true) {
                System.out.println("Please enter your role 1) Student 2) Professor: ");
                int role = scanner.nextInt();
                if (role == 1) {
                    rolein = "Student";
                    break;
                } else if (role == 2) {
                    rolein = "Professor";
                    break;
                } else {
                    System.out.println("Invalid role selected. Please select 1 for Student or 2 for Professor.");
                }
            }
            login.signup(username, password, name, rolein);
        }
        else if (choice == 2) {
            System.out.println("Log In selected.");
            System.out.println("Please enter your userid: ");
            int username = Integer.parseInt(scanner.next());
            System.out.println("Please enter your password: ");
            String password = scanner.next();
            login.loginin(username,password);
        }
        else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
        scanner.close();
    }
    public static void main(String[] args) {
        login();
    }
}
