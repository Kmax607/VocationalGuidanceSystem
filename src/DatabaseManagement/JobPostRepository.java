package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;

public class JobPostRepository {

    private static final String uri = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static final MongoClient mongoClient = MongoClients.create(uri);

    public static void insertJobPost(JobPost post) {
        try (mongoClient) {
            MongoDatabase db = mongoClient.getDatabase("jobposts");
            MongoCollection<Document> jobPostsCollection = db.getCollection("jobposts");

            Document jobPostDoc = new Document("postID", post.getPostID())
                    .append("jobTitle", post.getJobTitle())
                    .append("postDescription", post.getPostDescription())
                    .append("recruiter", post.getRecruiter())
                    .append("date", post.getDate())
                    .append("company", post.getCompany())
                    .append("location", post.getLocation())
                    .append("salary", post.getSalary());

            ArrayList<Document> applicationDoc = new ArrayList<>();
            for (Application app : post.getApplications()) {
                applicationDoc.add(app.toDocument());
            }

            jobPostDoc.append("applications", applicationDoc);

            jobPostsCollection.insertOne(jobPostDoc);
            System.out.println("Job Post inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllJobPosts() {
        try (mongoClient) {
            MongoDatabase db = mongoClient.getDatabase("jobposts");
            MongoCollection<Document> jobPostsCollection = db.getCollection("jobposts");

            FindIterable<Document> jobPosts = jobPostsCollection.find();

            for (Document post: jobPosts) {
                System.out.println("Job Post title: " + post.get("jobTitle"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}