package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import com.mongodb.client.*;
import org.bson.Document;

// Note: use this to retrieve new data added for testing
// Note: even though it shows warning in console, it just means we can't use advanced logging without the import for it

public class Mongo_DB {
    public static void main(String[] args) {

        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance")){

            // Testing User Connectivity
            MongoDatabase db = mongoClient.getDatabase("users");
            MongoCollection<Document> usersCollection = db.getCollection("users");

            FindIterable<Document> users = usersCollection.find();

            for (Document user: users) {
                System.out.println(user.toJson());
            }

            // Testing JobPost Connectivity
            MongoDatabase db2 = mongoClient.getDatabase("jobposts");
            MongoCollection<Document> jobPostsCollection = db2.getCollection("jobposts");

            FindIterable<Document> jobPosts = jobPostsCollection.find();

            for (Document post : jobPosts) {
                System.out.println(post.toJson());
            }

            // Testing Application Connectivity
            //ApplicationRepository.getApplicationsByUsernam

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static MongoDatabase getDatabase() {

        return getDatabase();
    }
}