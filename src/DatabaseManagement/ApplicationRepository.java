package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository {

    private static final String DATABASE_NAME = "jobposts";
    private static final String COLLECTION_NAME = "applications";

    static {
        // Create an index on the username field for faster queries
        MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
        collection.createIndex(Indexes.ascending("username"));
        // Additional index on jobPostingTitle for getApplicationsByJobTitle
        collection.createIndex(Indexes.ascending("jobPostingTitle"));
    }

    public static void insertApplication(Application application) {
        if (application == null) {
            throw new IllegalArgumentException("Application cannot be null");
        }
        if (application.getUsername() == null || application.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Application username cannot be null or empty");
        }

        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            Document doc = application.toDocument();
            collection.insertOne(doc);
            application.setId(doc.getObjectId("_id"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert application: " + e.getMessage(), e);
        }
    }

    public static List<Application> getApplicationsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        List<Application> applications = new ArrayList<>();
        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            FindIterable<Document> docs = collection.find(Filters.eq("username", username));
            for (Document doc : docs) {
                applications.add(new Application(doc));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch applications for user " + username + ": " + e.getMessage(), e);
        }
        return applications;
    }

    public static List<Application> getApplicationsByJobTitle(String jobTitle) {
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be null or empty");
        }

        List<Application> applications = new ArrayList<>();
        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            FindIterable<Document> docs = collection.find(Filters.eq("jobPostingTitle", jobTitle));
            for (Document doc : docs) {
                applications.add(new Application(doc));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch applications for job title " + jobTitle + ": " + e.getMessage(), e);
        }
        return applications;
    }

    public static boolean deleteApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }

        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            Document result = collection.findOneAndDelete(Filters.eq("_id", new ObjectId(applicationId)));
            return result != null; // Return true if an application was deleted, false if not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete application with ID " + applicationId + ": " + e.getMessage(), e);
        }
    }

    public static void acceptApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }

        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            Document result = collection.findOneAndUpdate(
                    Filters.eq("_id", new ObjectId(applicationId)),
                    Updates.set("status", Application.Status.ACCEPTED.toString())
            );
            if (result == null) {
                throw new IllegalStateException("Application with ID " + applicationId + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to accept application with ID " + applicationId + ": " + e.getMessage(), e);
        }
    }

    public static void denyApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }

        try {
            MongoDatabase db = MongoClientManager.getMongoClient().getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            Document result = collection.findOneAndUpdate(
                    Filters.eq("_id", new ObjectId(applicationId)),
                    Updates.set("status", Application.Status.DENIED.toString())
            );
            if (result == null) {
                throw new IllegalStateException("Application with ID " + applicationId + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to deny application with ID " + applicationId + ": " + e.getMessage(), e);
        }
    }

    public static void closeClient() {
        MongoClientManager.closeClient();
    }
}