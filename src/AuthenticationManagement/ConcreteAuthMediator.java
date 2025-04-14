package AuthenticationManagement;

public class ConcreteAuthMediator implements AuthMediator {
    private LoginController loginController;
    private LoginInterface loginInterface;
    private User currentUser;

    public ConcreteAuthMediator(LoginController loginController, LoginInterface loginInterface) {
        this.loginController = loginController;
        this.loginInterface = loginInterface;
    }

    @Override
    public void login(String username, String password) {
        boolean success = loginController.validateLogin(username, password);
        if (success) {
            System.out.println("Login successful for: " + username);
        } else {
            loginInterface.showLoginError("Invalid credentials.");
        }
    }

    @Override
    public void register(User user) {
        boolean success = loginController.registerUser(user);
        if (success) {
            System.out.println("User registered: " + user.getUsername());
        } else {
            loginInterface.showLoginError("Registration failed.");
        }
    }

    @Override
    public void logout() {
        loginController.logoutUser();
        System.out.println("User logged out.");
    }
}
