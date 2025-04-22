package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import com.mongodb.client.*;
import org.bson.Document;

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

    public static void getAllApplications() {
        try {
            MongoDatabase db = mongoClient.getDatabase("applications");
            MongoCollection<Document> applicationsCollection = db.getCollection("applications");

            FindIterable<Document> applications = applicationsCollection.find();

            for (Document app: applications) {
                System.out.println("Application details: " + app.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
