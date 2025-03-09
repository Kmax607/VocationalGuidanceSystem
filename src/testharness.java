package TestHarness;

import AuthenticationManagement.*;

public class TestHarness {

  
    public static void main(String[] args) {
        System.out.println("Initializing Test Harness...");

        LoginController loginController = new LoginController();

        // Registering a new user test
        User newUser = new User("kmax", "Max Kraus", "password123", "max@mail.com", null, "regular");
        boolean registrationSuccess = loginController.registerUser(newUser);
        System.out.println("Test: User registration " + (registrationSuccess ? "PASSED" : "FAILED"));

        // Valid login test
        boolean loginSuccess = loginController.validateLogin("testUser", "password123");
        System.out.println("Test: Valid login " + (loginSuccess ? "PASSED" : "FAILED"));

        // Invalid login test
        boolean invalidLogin = loginController.validateLogin("wrongUser", "wrongPass");
        System.out.println("Test: Invalid login " + (!invalidLogin ? "PASSED" : "FAILED"));

        // Logout Test
        loginController.logoutUser();
        System.out.println("Test: Logout PASSED");
    }
}
