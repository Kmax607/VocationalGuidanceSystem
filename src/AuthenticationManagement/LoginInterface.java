package AuthenticationManagement;

import java.util.Scanner;

public class LoginInterface {
    private Scanner scanner = new Scanner(System.in);

    public void displayLoginScreen() {
        System.out.println("Displaying login screen...");
    }

    public String[] getUserCredentials() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        return new String[]{username, password};
    }

    public void showLoginError(String errorMessage) {
        System.out.println("Login Error: " + errorMessage);
    }
}
