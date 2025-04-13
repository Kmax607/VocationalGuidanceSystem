package DatabaseManagement;

import com.mongodb.client.*;
import org.bson.Document;

// Note: use this to retrieve new data added for testing
// Note: even though it shows warning in console, it just means we can't use advanced logging without the import for it

public class Mongo_DB {
    public static void main(String[] args) {

        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance")){

            MongoDatabase db = mongoClient.getDatabase("users");
            MongoCollection<Document> usersCollection = db.getCollection("users");

            FindIterable<Document> users = usersCollection.find();

            int count = 0;
            for (Document user: users) {
                if (count > 10) {
                    break;
                }
                System.out.println("User's name: " + user.get("first_name") + " " + user.get("last_name"));
                count++;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}