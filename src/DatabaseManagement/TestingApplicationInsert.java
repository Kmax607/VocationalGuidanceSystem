package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestingApplicationInsert {

    private static final String URI = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static final String DATABASE_NAME = "jobposts";
    private static final String COLLECTION_NAME = "applications";

    public static void main(String[] args) {
        // Clean up existing test data
        try {
            MongoClient mongoClient = MongoClients.create(URI);
            MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> applicationsCollection = db.getCollection(COLLECTION_NAME);
            applicationsCollection.deleteMany(Filters.in("postID", "1001", "200"));
            System.out.println("Cleaned up existing Applications with postIDs: 1001, 200");
            mongoClient.close(); // Close the local client used for cleanup
        } catch (Exception e) {
            System.err.println("Failed to clean up test data: " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 1: Insert Applications
        Application dummyApp = null;
        Application dummyApp2 = null;
        try {
            dummyApp = new Application(
                    1,
                    "1001",
                    "applicant1",
                    "testing jobs",
                    "/mock/path/resume1.pdf", // Updated resume to a mock absolute path
                    new ArrayList<>(Arrays.asList("Why do you want this job?")),
                    new ArrayList<>(Arrays.asList("Need money")),
                    new Date(),
                    Application.Status.UNDER_CONSIDERATION
            );

            dummyApp2 = new Application(
                    2,
                    "200",
                    "applicant2",
                    "WebDev",
                    "/mock/path/resume2.pdf", // Updated resume to a mock absolute path
                    new ArrayList<>(Arrays.asList("Do you require visa sponsorship?")),
                    new ArrayList<>(Arrays.asList("No")),
                    new Date(),
                    Application.Status.UNDER_CONSIDERATION
            );

            ApplicationRepository.insertApplication(dummyApp);
            System.out.println("Test Case 1: Inserted dummyApp with ID: " + dummyApp.getApplicationId());

            ApplicationRepository.insertApplication(dummyApp2);
            System.out.println("Test Case 1: Inserted dummyApp2 with ID: " + dummyApp2.getApplicationId());
        } catch (Exception e) {
            System.err.println("Test Case 1 Failed (Insert Applications): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 2: Retrieve Applications by Username
        try {
            List<Application> userApplications = ApplicationRepository.getApplicationsByUsername("applicant1");
            System.out.println("Test Case 2: Applications for applicant1: " + userApplications);
            System.out.println("Test Case 2: Total Applications for applicant1: " + userApplications.size());
            for (Application app : userApplications) {
                System.out.println(" - Application ID: " + app.getApplicationId() + ", Job Title: " + app.getJobPostingTitle());
            }

            userApplications = ApplicationRepository.getApplicationsByUsername("applicant2");
            System.out.println("Test Case 2: Applications for applicant2: " + userApplications);
            System.out.println("Test Case 2: Total Applications for applicant2: " + userApplications.size());
            for (Application app : userApplications) {
                System.out.println(" - Application ID: " + app.getApplicationId() + ", Job Title: " + app.getJobPostingTitle());
            }
        } catch (Exception e) {
            System.err.println("Test Case 2 Failed (Retrieve Applications by Username): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 3: Withdraw an Application
        try {
            if (dummyApp == null || dummyApp.getApplicationId() == null) {
                throw new IllegalStateException("dummyApp or its ID is null, cannot withdraw");
            }
            boolean withdrawn = ApplicationRepository.deleteApplication(dummyApp.getApplicationId());
            if (withdrawn) {
                System.out.println("Test Case 3: Successfully withdrew application with ID: " + dummyApp.getApplicationId());
            } else {
                System.err.println("Test Case 3: Failed to withdraw application with ID: " + dummyApp.getApplicationId());
            }
        } catch (Exception e) {
            System.err.println("Test Case 3 Failed (Withdraw Application): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 4: Accept an Application
        try {
            if (dummyApp2 == null || dummyApp2.getApplicationId() == null) {
                throw new IllegalStateException("dummyApp2 or its ID is null, cannot accept");
            }
            ApplicationRepository.acceptApplication(dummyApp2.getApplicationId());
            System.out.println("Test Case 4: Successfully accepted application with ID: " + dummyApp2.getApplicationId());

            // Verify the status update
            List<Application> updatedApps = ApplicationRepository.getApplicationsByUsername("applicant2");
            for (Application app : updatedApps) {
                if (app.getApplicationId().equals(dummyApp2.getApplicationId())) {
                    System.out.println("Test Case 4: Verified status for application ID " + app.getApplicationId() + ": " + app.getStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("Test Case 4 Failed (Accept Application): " + e.getMessage());
            e.printStackTrace();
        }

        // Test Case 5: Deny an Application
        try {
            if (dummyApp2 == null || dummyApp2.getApplicationId() == null) {
                throw new IllegalStateException("dummyApp2 or its ID is null, cannot deny");
            }
            ApplicationRepository.denyApplication(dummyApp2.getApplicationId());
            System.out.println("Test Case 5: Successfully denied application with ID: " + dummyApp2.getApplicationId());

            // Verify the status update
            List<Application> updatedApps = ApplicationRepository.getApplicationsByUsername("applicant2");
            for (Application app : updatedApps) {
                if (app.getApplicationId().equals(dummyApp2.getApplicationId())) {
                    System.out.println("Test Case 5: Verified status for application ID " + app.getApplicationId() + ": " + app.getStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("Test Case 5 Failed (Deny Application): " + e.getMessage());
            e.printStackTrace();
        }
    }
}