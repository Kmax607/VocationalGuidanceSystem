package AuthenticationManagement;

public class LoginController {

    private LoginInterface loginInterface;
    private User currentUser;

    public LoginController() {
        this.loginInterface = new LoginInterface();
    }

    public boolean validateLogin(String username, String password) {
        System.out.println("Validating login for user: " + username);
        
        // Simulating a successful login for "testUser"
        if (username.equals("testUser") && password.equals("password123")) {
            currentUser = new User(username, "Test User", password, "test@example.com", null, "regular");
            System.out.println("Login successful!");
            return true;
        }

        System.out.println("Login failed. Invalid credentials.");
        return false;
    }

    public void logoutUser() {
        if (currentUser != null) {
            System.out.println("Logging out user: " + currentUser.getUsername());
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public boolean registerUser(User newUser) {
        System.out.println("Registering new user: " + newUser.getUsername());

        // Simulating a successful registration
        if (!newUser.getUsername().isEmpty() && !newUser.getPassword().isEmpty()) {
            System.out.println("Registration successful for: " + newUser.getUsername());
            return true;
        }

        System.out.println("Registration failed. Missing details.");
        return false;
    }
}
