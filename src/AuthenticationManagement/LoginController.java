    package AuthenticationManagement;

    import DatabaseManagement.UserRepository;

    public class LoginController {

        private LoginInterface view;
        private User currentUser;

        public LoginController(LoginInterface view) {
            this.view = view;
        }

        public boolean validateLogin(String username, String password) {
            System.out.println("Validating login for user: " + username);

            if (UserRepository.validateUser(username, password)) {
                System.out.println("Login Successful!");
                System.out.println("Username: " + username + "Password: " + password);
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

            // Successful registration
            if (!newUser.getUsername().isEmpty() && !newUser.getPassword().isEmpty()) {
                System.out.println("Registration successful for: " + newUser.getUsername());

                UserRepository.insertUser(newUser);
                return true;
            }

            System.out.println("Registration failed. Missing details.");
            return false;
        }
    }
