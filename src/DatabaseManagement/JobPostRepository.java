package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class JobPostRepository {

    private static final String DATABASE_NAME = "jobposts";
    private static final String COLLECTION_NAME = "jobposts";

    public static void insertJobPost(JobPost post) {
        if (post == null) {
            throw new IllegalArgumentException("JobPost cannot be null");
        }

        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> jobPostsCollection = db.getCollection(COLLECTION_NAME);

            Document jobPostDoc = new Document("postID", post.getPostID())
                    .append("jobTitle", post.getJobTitle())
                    .append("postDescription", post.getPostDescription())
                    .append("recruiter", post.getRecruiter())
                    .append("date", post.getDate())
                    .append("company", post.getCompany())
                    .append("location", post.getLocation())
                    .append("salary", post.getSalary())
                    .append("status", post.getStatus());

            ArrayList<Document> applicationDocs = new ArrayList<>();
            for (Application app : post.getApplications()) {
                if (app.getStatus() == null) {
                    throw new IllegalArgumentException("Application status cannot be null for JobPost with ID: " + post.getPostID());
                }
                applicationDocs.add(app.toDocument());
            }
            jobPostDoc.append("applications", applicationDocs);

            jobPostsCollection.insertOne(jobPostDoc);
            post.setId(jobPostDoc.getObjectId("_id"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert job post: " + e.getMessage(), e);
        }
    }

    public static boolean validatePost(String postID) {
        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> jobPostsCollection = db.getCollection(COLLECTION_NAME);

            Document foundPost = jobPostsCollection.find(Filters.eq("postID", postID)).first();
            return foundPost != null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate job post: " + e.getMessage(), e);
        }
    }

    public static List<JobPost> getAllJobPosts() {
        List<JobPost> jobListings = new ArrayList<>();
        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> jobPostsCollection = db.getCollection(COLLECTION_NAME);

            FindIterable<Document> jobPosts = jobPostsCollection.find();

            for (Document post : jobPosts) {
                JobPost job = createJobPostFromDocument(post);
                jobListings.add(job);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all job posts: " + e.getMessage(), e);
        }
        return jobListings;
    }

    public static List<JobPost> getJobPostsByRecruiter(String recruiterUsername) {
        if (recruiterUsername == null || recruiterUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Recruiter username cannot be null or empty");
        }

        List<JobPost> jobListings = new ArrayList<>();
        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> jobPostsCollection = db.getCollection(COLLECTION_NAME);

            FindIterable<Document> jobPosts = jobPostsCollection.find(Filters.eq("recruiter", recruiterUsername));

            for (Document post : jobPosts) {
                JobPost job = createJobPostFromDocument(post);
                jobListings.add(job);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch job posts by recruiter: " + e.getMessage(), e);
        }
        return jobListings;
    }

    public static void addApplicationToPost(JobPost job, Application application) {
        if (job == null || application == null) {
            throw new IllegalArgumentException("JobPost and Application cannot be null");
        }
        if (job.getId() == null) {
            throw new IllegalStateException("JobPost must have a valid ID to add an application");
        }
        if (application.getStatus() == null) {
            throw new IllegalArgumentException("Application status cannot be null for JobPost with ID: " + job.getPostID());
        }

        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> jobPostsCollection = db.getCollection(COLLECTION_NAME);

            jobPostsCollection.updateOne(
                    Filters.eq("_id", job.getId()),
                    Updates.push("applications", application.toDocument())
            );

            job.addApplication(application);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add application to job post: " + e.getMessage(), e);
        }
    }

    private static JobPost createJobPostFromDocument(Document post) {
        List<Document> applicationDocs = post.get("applications", List.class) != null ? post.get("applications", List.class) : new ArrayList<>();
        ArrayList<Application> applications = new ArrayList<>();
        for (Document appDoc : applicationDocs) {
            try {
                // Validate required fields for Application
                if (appDoc.getString("status") == null || appDoc.getString("status").isEmpty()) {
                    System.err.println("Skipping invalid Application in JobPost document: Missing or null status field in " + appDoc);
                    continue;
                }
                if (appDoc.getString("jobPostingTitle") == null || appDoc.getString("jobPostingTitle").isEmpty()) {
                    System.err.println("Skipping invalid Application in JobPost document: Missing or null jobPostingTitle field in " + appDoc);
                    continue;
                }
                if (appDoc.getString("resume") == null || appDoc.getString("resume").isEmpty()) {
                    System.err.println("Skipping invalid Application in JobPost document: Missing or null resume field in " + appDoc);
                    continue;
                }
                applications.add(new Application(appDoc));
            } catch (Exception e) {
                System.err.println("Failed to deserialize Application in JobPost document: " + appDoc);
                e.printStackTrace();
            }
        }

        // Validate required fields for JobPost
        String jobTitle = post.getString("jobTitle");
        String recruiter = post.getString("recruiter");
        if (jobTitle == null || jobTitle.isEmpty()) {
            throw new IllegalArgumentException("JobPost document missing required field: jobTitle in " + post);
        }
        if (recruiter == null || recruiter.isEmpty()) {
            throw new IllegalArgumentException("JobPost document missing required field: recruiter in " + post);
        }

        // Handle salary type mismatch (MongoDB may store as int, but we need double)
        Double salary = post.getDouble("salary");
        if (salary == null) {
            // Check if stored as int
            Integer salaryInt = post.getInteger("salary");
            salary = salaryInt != null ? salaryInt.doubleValue() : 0.0;
        }

        JobPost job = new JobPost(
                post.getString("postID") != null ? post.getString("postID") : "0",
                jobTitle,
                post.getString("postDescription"),
                recruiter,
                post.getString("date"),
                post.getString("company"),
                post.getString("location"),
                salary,
                applications
        );
        job.setId(post.getObjectId("_id"));
        String status = post.getString("status");
        job.setStatus(status != null ? status : "Open");
        return job;
    }

    public static void closeClient() {
        MongoClientManager.closeClient();
    }
}