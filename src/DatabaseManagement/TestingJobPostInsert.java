package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import JobPostingManagement.Controller.PostController;
import Main.InterfaceRouter;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestingJobPostInsert {

    private static final String URI = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static final String DATABASE_NAME = "jobposts";
    private static final String COLLECTION_NAME = "jobposts";

    public static void main(String[] args) {
        InterfaceRouter router = new InterfaceRouter();
        PostController postController = new PostController(router);
        JobPost dummyJob = null;

        // Clean up existing test data and fix status fields
        try {
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> jobPostsCollection = db.getCollection(COLLECTION_NAME);
            MongoCollection<Document> applicationsCollection = db.getCollection("applications");
            jobPostsCollection.deleteMany(Filters.eq("postID", "1001"));
            applicationsCollection.deleteMany(Filters.eq("postID", "1001"));
            System.out.println("Cleaned up existing JobPost and Applications with postID: 1001");

            // Fix existing Application documents by setting a default status
            UpdateOptions options = new UpdateOptions();
            List<Document> arrayFilters = new ArrayList<>();
            arrayFilters.add(new Document("elem.status", new Document("$exists", false)));
            options.arrayFilters(arrayFilters);
            jobPostsCollection.updateMany(
                    Filters.exists("applications.status", false),
                    new Document("$set", new Document("applications.$[elem].status", "UNDER_CONSIDERATION")),
                    options
            );
            System.out.println("Set default status for Applications missing status field");
            mongoClient.close(); // Close the local client used for cleanup
        } catch (Exception e) {
            System.err.println("Failed to clean up test data or fix status: " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 1: Create and Validate a Job Post
        try {
            dummyJob = new JobPost(
                    "1001",
                    "Junior Developer",
                    "Exciting opportunity to join our dev team.",
                    "recruiter123",
                    "2025-04-13",
                    "TechCorp",
                    "Remote",
                    60000.00
            );

            postController.createJobPost(dummyJob);
            System.out.println("Test Case 1: Job Post Created through Post Controller with ID: " + dummyJob.getPostID());
            System.out.println("Test Case 1: Generated MongoDB ID: " + dummyJob.getId());

            // Small delay to ensure MongoDB write consistency
            Thread.sleep(1000);

            boolean validated = postController.validateJobPost("1001");
            if (validated) {
                System.out.println("Test Case 1: Job Post Validated through Post Controller with ID: 1001");
            } else {
                System.err.println("Test Case 1: Job Post Validation Failed for ID: 1001");
            }
        } catch (Exception e) {
            System.err.println("Test Case 1 Failed (Create and Validate Job Post): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 2: Get Job Posts by Recruiter
        try {
            List<JobPost> recruiterJobs = postController.getJobPostsByRecruiter("recruiter123");
            System.out.println("Test Case 2: Job Posts for recruiter123: " + recruiterJobs);
            if (!recruiterJobs.isEmpty()) {
                System.out.println("Test Case 2: Found " + recruiterJobs.size() + " job posts for recruiter123");
                for (JobPost job : recruiterJobs) {
                    System.out.println(" - Job Post: " + job.getJobTitle() + ", Applications: " + job.getApplications());
                }
            } else {
                System.out.println("Test Case 2: No job posts found for recruiter123");
            }
        } catch (Exception e) {
            System.err.println("Test Case 2 Failed (Get Job Posts by Recruiter): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 3: Get All Job Posts
        try {
            List<JobPost> allJobs = postController.getAllJobPosts();
            System.out.println("Test Case 3: All Job Posts: " + allJobs);
            System.out.println("Test Case 3: Total Job Posts: " + allJobs.size());
        } catch (Exception e) {
            System.err.println("Test Case 3 Failed (Get All Job Posts): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 4: Add Applicant to Job Post
        Application newApp = null;
        try {
            newApp = new Application(
                    2,
                    "1001",
                    "applicant2",
                    "Junior Developer",
                    "/mock/path/resume2.pdf", // Updated resume to a mock absolute path
                    new ArrayList<>(Arrays.asList("What’s your experience?")),
                    new ArrayList<>(Arrays.asList("5 years in coding!")),
                    new Date(),
                    Application.Status.UNDER_CONSIDERATION
            );

            if (dummyJob == null) {
                dummyJob = postController.getJobById("1001");
            }
            if (dummyJob == null) {
                throw new IllegalStateException("JobPost with ID 1001 not found for adding applicant");
            }
            if (dummyJob.getId() == null) {
                throw new IllegalStateException("JobPost with ID 1001 does not have a valid MongoDB ID");
            }

            postController.addApplicantToJobPost(dummyJob, newApp);
            System.out.println("Test Case 4: Applicant added to Job Post with ID: " + dummyJob.getPostID());
            System.out.println("Test Case 4: Updated Applications in JobPost: " + dummyJob.getApplications());
        } catch (Exception e) {
            System.err.println("Test Case 4 Failed (Add Applicant to Job Post): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 5: Get Applications by Username
        try {
            List<Application> userApplications = postController.getApplicationsByUsername("applicant1");
            System.out.println("Test Case 5: Applications for applicant1: " + userApplications);
            System.out.println("Test Case 5: Total Applications for applicant1: " + userApplications.size());
            for (Application app : userApplications) {
                System.out.println(" - Application ID: " + app.getApplicationId() + ", Job Title: " + app.getJobPostingTitle());
            }

            userApplications = postController.getApplicationsByUsername("applicant2");
            System.out.println("Test Case 5: Applications for applicant2: " + userApplications);
            System.out.println("Test Case 5: Total Applications for applicant2: " + userApplications.size());
            for (Application app : userApplications) {
                System.out.println(" - Application ID: " + app.getApplicationId() + ", Job Title: " + app.getJobPostingTitle());
            }
        } catch (Exception e) {
            System.err.println("Test Case 5 Failed (Get Applications by Username): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 6: Verify Application in applications Collection
        try {
            List<Application> userApplications = postController.getApplicationsByUsername("applicant2");
            if (userApplications.isEmpty()) {
                System.err.println("Test Case 6 Failed: No applications found for applicant2 in applications collection");
            } else {
                System.out.println("Test Case 6: Verified applications for applicant2 in applications collection: " + userApplications.size());
                for (Application app : userApplications) {
                    System.out.println(" - Application ID: " + app.getApplicationId() + ", Job Title: " + app.getJobPostingTitle());
                }
            }
        } catch (Exception e) {
            System.err.println("Test Case 6 Failed (Verify Application in applications Collection): " + e.getMessage());
            e.printStackTrace();
        }
    }
}