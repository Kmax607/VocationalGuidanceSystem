package DatabaseManagement;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientManager {

    private static final String URI = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static volatile MongoClient mongoClient;

    // Singleton getter for MongoClient
    public static synchronized MongoClient getMongoClient() {
        if (mongoClient == null) {
            synchronized (MongoClientManager.class) {
                if (mongoClient == null) {
                    mongoClient = MongoClients.create(URI);
                }
            }
        }
        return mongoClient;
    }

    public static synchronized void closeClient() {
        if (mongoClient != null) {
            synchronized (MongoClientManager.class) {
                if (mongoClient != null) {
                    mongoClient.close();
                    mongoClient = null; // Allow reinitialization if needed
                }
            }
        }
    }
}