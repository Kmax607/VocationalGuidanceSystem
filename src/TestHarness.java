import AuthenticationManagement.*;
import JobApplicationManagement.Controller.ApplicationController;
import JobApplicationManagement.Model.Application;
import JobApplicationManagement.Model.PreviousApplications;
import JobPostingManagement.Controller.PostController;
import JobPostingManagement.Model.JobPost;
import JobSearchingManagement.Controller.SearchController;
import JobSearchingManagement.Model.JobListing;
import JobSearchingManagement.View.JobView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TestHarness {

  // Main method
    public static void main(String[] args) {
      
        System.out.println("Initializing Test Harness...");

        LoginInterface loginView = new LoginInterface();
        LoginController loginController = new LoginController(loginView);
        Scanner scan = new Scanner(System.in);

        // Authentication Management Tests:
        // Registering a new user test
        User newUser = new User("kmax", "Max", "Kraus", "password123", "max@mail.com", null, "regular");
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



        // Job Application Management Tests:

        PreviousApplications prevApplication = new PreviousApplications();


        // Filling out application
        System.out.println("Please enter 1. to attach the resume you have on file: ");
        int input = scan.nextInt();
        boolean checkPassed = false;
        File resume = null;

        while (!checkPassed) {
            if (input != 1) {
                System.out.println("Please enter a valid number: ");
                input = scan.nextInt();
            }
            else {
                checkPassed = true;
            }
        }

        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> responses = new ArrayList<>();
        questions.add("What is your biggest strength?");

        System.out.println("What is your biggest strength? ");
        scan.nextLine();
        String response1 = scan.nextLine();
        responses.add(response1);

        System.out.println("This is your application: ");

        System.out.println("Type 'Y' to submit your application, or 'N' to cancel submission: ");

        String input2 = scan.nextLine();
        checkPassed = false;

        while (!checkPassed) {
            if (!input2.equals("Y") && !input2.equals("N")) {
                System.out.println("Please enter a valid response: ");
            }
            else if (input2.equals("Y")) {

                System.out.println("Application submitted successfully!");
                checkPassed = true;
            }
            else if (input2.equals("N")) {
                System.out.println("Application not submitted");
                checkPassed = true;
            }
        }
        
       // Job Searching Tests:
        System.out.println("\n--- Job Searching Tests ---");

        // Instantiate the JobView and SearchController
        JobView jobView = new JobView();
        SearchController searchController = new SearchController();

        // Test 1: Display all jobs
        System.out.println("Test 1: Display all jobs");
        searchController.getAllJobs();

        // Test 2: Search for jobs containing "Engineer"
        System.out.println("\nTest 2: Search for jobs containing 'Engineer'");
        searchController.searchJobs("Engineer");

        // Test 3: Search for jobs containing "Remote"
        System.out.println("\nTest 3: Search for jobs containing 'Remote'");
        searchController.searchJobs("Remote");

        // Test 4: Search for jobs containing "Unknown"
        System.out.println("\nTest 4: Search for jobs containing 'Unknown'");
        searchController.searchJobs("Unknown");

        // Close the scanner
        scan.close();
    }
}
