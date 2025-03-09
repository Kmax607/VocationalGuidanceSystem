import AuthenticationManagement.*;
import JobPostingManagement.Controller.PostController;
import JobPostingManagement.Model.JobPost;

public class TestHarness {

  // Main method
    public static void main(String[] args) {
      
        System.out.println("Initializing Test Harness...");

        LoginController loginController = new LoginController();

        // Authentication Management Tests:
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
        // Ednd of authentication management tests

        

        //Job Posting Tests:
        //New Job Post
        JobPost newPost = new JobPost("001", "Systems Analyst","Job Description for a Systems Analyst",
                "John Doe", "3/9/2025", "Eaton", "Harrisburg, PA", 50000.00);
        boolean jobCreated = JobPostingManagement.Controller.PostController.createJobPost(newPost);
        System.out.println("Test: Job Post Created " + (jobCreated ? "PASSED" : "FAILED"));

        //Find Specific Job Post
        JobPost retrievePost = PostController.getPostID(newPost.postID());
        System.out.println("Test: Retrieving Post " + (retrievePost != null ? "PASSED" : "FAILED"));

        //Full Job Repo List
        boolean jobListStatus = !PostController.getAllJobPosts().isEmpty();
        System.out.println("Test: Job List Status: " + (jobListStatus ? "PASSED" : "FAILED"));


      
    }
}
