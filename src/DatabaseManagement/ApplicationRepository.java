package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ApplicationRepository {
    private static final String uri = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static final MongoClient mongoClient = MongoClients.create(uri);

    public static void insertApplication(Application application) {
        try {
            MongoDatabase db = mongoClient.getDatabase("applications");
            MongoCollection<Document> applicationsCollection = db.getCollection("applications");

            Document applicationDoc = application.toDocument();
            applicationsCollection.insertOne(applicationDoc);
            System.out.println("Application inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void submitApplication(Application application) {
        insertApplication(application);  // Insert the application into the database
    }

    public static List<Application> getAllApplications() {
        List<Application> applicationsList = new ArrayList<>();

        try {
            MongoDatabase db = mongoClient.getDatabase("applications");
            MongoCollection<Document> applicationsCollection = db.getCollection("applications");

            FindIterable<Document> applications = applicationsCollection.find();

            for (Document doc : applications) {
                Application app = new Application(
                        doc.getInteger("applicationID"),
                        doc.getInteger("postID"),
                        doc.getString("jobPostingTitle"),
                        doc.getString("resume"),
                        (ArrayList<String>) doc.get("questions"),
                        (ArrayList<String>) doc.get("questionResponses"),
                        doc.getDate("dateCompleted"),
                        Application.Status.valueOf(doc.getString("status"))
                );
                ObjectId generatedId = doc.getObjectId("_id");
                app.setId(generatedId);
                applicationsList.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationsList;
    }

    public static void acceptApplication(String id) {
        try {
            MongoDatabase db = mongoClient.getDatabase("applications");
            MongoCollection<Document> applicationsCollection = db.getCollection("applications");

            Document query = new Document("_id", new ObjectId(id));
            Document update = new Document("$set", new Document("status", "ACCEPTED"));

            applicationsCollection.updateOne(query, update);

            System.out.println("Application with ID " + id + " has been accepted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void denyApplication(String id) {
        try {
            MongoDatabase db = mongoClient.getDatabase("applications");
            MongoCollection<Document> applicationsCollection = db.getCollection("applications");

            Document query = new Document("_id", new ObjectId(id));
            Document update = new Document("$set", new Document("status", "DENIED"));

            applicationsCollection.updateOne(query, update);

            System.out.println("Application with ID " + id + " has been denied.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Application> getApplicationsByUsername(String username) {
        List<Application> userApps = new ArrayList<>();

        MongoDatabase db = mongoClient.getDatabase("applications");
        MongoCollection<Document> collection = db.getCollection("applications");
        FindIterable<Document> applications = collection.find(Filters.eq("username", username));

        for (Document doc : applications) {
            Application app = new Application(
                    doc.getInteger("applicationID"),
                    doc.getInteger("postID"),
                    doc.getString("jobPostingTitle"),
                    doc.getString("resume"),
                    (ArrayList<String>) doc.get("questions"),
                    (ArrayList<String>) doc.get("questionResponses"),
                    doc.getDate("dateCompleted"),
                    Application.Status.valueOf(doc.getString("status"))
            );
            userApps.add(app);
        }
        System.out.println(userApps);
        return userApps;
    }

    public static List<Application> getApplicationsByJobTitle(String jobTitle) {
        List<Application> matchingApps = new ArrayList<>();
        System.out.println("job title:" + jobTitle);

        MongoDatabase db = mongoClient.getDatabase("applications");
        MongoCollection<Document> collection = db.getCollection("applications");

        FindIterable<Document> applications = collection.find(
                Filters.regex("jobPostingTitle", Pattern.compile("^" + Pattern.quote(jobTitle) + "$", Pattern.CASE_INSENSITIVE))
        );

        for (Document doc : applications) {
            Application app = new Application(
                    doc.getInteger("applicationID"),
                    doc.getInteger("postID"),
                    doc.getString("jobPostingTitle"),
                    doc.getString("resume"),
                    (ArrayList<String>) doc.get("questions"),
                    (ArrayList<String>) doc.get("questionResponses"),
                    doc.getDate("dateCompleted"),
                    Application.Status.valueOf(doc.getString("status"))
            );
            System.out.println("did this run");
            matchingApps.add(app);
        }
        System.out.println(matchingApps);
        return matchingApps;
    }
}
