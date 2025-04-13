package DatabaseManagement;
import AuthenticationManagement.User;
import com.mongodb.client.*;
import org.bson.Document;

public class UserRepository {

    private static final String uri = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static final MongoClient mongoClient = MongoClients.create(uri);

    public static void insertUser(User user) {
        try (mongoClient) {
            MongoDatabase db = mongoClient.getDatabase("users");
            MongoCollection<Document> usersCollection = db.getCollection("users");

            Document doc = new Document("username", user.getUsername())
                    .append("first_name", user.getFirstName())
                    .append("last_name", user.getLastName())
                    .append("password", user.getPassword())
                    .append("email", user.getEmail())
                    .append("dob", user.getDob())
                    .append("userType", user.getUserType());

            usersCollection.insertOne(doc);
            System.out.println("User inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validateUser(String username, String password) {
        try (mongoClient) {
            MongoDatabase db = mongoClient.getDatabase("users");
            MongoCollection<Document> usersCollection = db.getCollection("users");

            Document query = new Document("username",username).append("password", password);
            Document userValid = usersCollection.find(query).first();

            return userValid != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
