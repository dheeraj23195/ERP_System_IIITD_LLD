package erp;

import java.util.HashMap;

public class Login{
    HashMap<Integer, User> usersMap = new HashMap<>();

    public boolean signup(int id,String password,String name,String role) {
        if (usersMap.containsKey(id)){
            System.out.println("The user already exists, please try logging in.");
            return false;
        }
        else{
            User newuser=new User(id,password,name,role);
            usersMap.put(id,newuser);
            return true;
        }
    }
    public boolean loginin(int id,String password) {
        if (usersMap.containsKey(id)) {
            User user = usersMap.get(id);
            String ogpass=user.getPassword();
            if (password.equals(ogpass)) {
                return true;
            }
            else{
                System.out.println("The Password is incorrect. Please try again.");
                return false;
            }
        }
        else{
            System.out.println("The User doesn't exist. Please sign up.");
            return false;
        }
    }
}
