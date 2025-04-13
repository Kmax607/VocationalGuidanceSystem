package DatabaseManagement;

import com.mongodb.client.*;
import org.bson.Document;

// Note: to be used as an example for when adding database connection and for testing
// Note: even though it shows warning in console, it just means we can't use advanced logging without the import for it

public class Mongo_DB {
    public static void main(String[] args) {

        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance")){

            MongoDatabase db = mongoClient.getDatabase("users");
            MongoCollection<Document> usersCollection = db.getCollection("recruiters");

            FindIterable<Document> users = usersCollection.find();

            int count = 0;
            for (Document user: users) {
                if (count > 10) {
                    break;
                }
                System.out.println("Recruiter name: " + user.get("first_name"));
                count++;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}