    package AuthenticationManagement;

    import DatabaseManagement.UserRepository;
    import Main.InterfaceRouter;

    public class LoginController {

        private LoginInterface view;
        private User currentUser;
        private InterfaceRouter router;

        public LoginController(LoginInterface view, InterfaceRouter router) {
            this.view = view;
            this.router = router;
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

        public String getUserType(String username, String password) {
            String type = UserRepository.getUserType(username, password);
            return type;
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

        public void routeToManageJobPosts() {
            router.showManageJobPostsInterface();
        }
        public void routeToJobListings() { router.showJobSearchInterface(); }
    }
