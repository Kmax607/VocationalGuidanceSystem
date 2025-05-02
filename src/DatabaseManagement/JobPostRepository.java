package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import JobSearchingManagement.Model.JobListing;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class JobPostRepository {

    private static final String uri = "mongodb+srv://jeffta1080:y7BctXVKdenQAMOu@vocationalguidance.5ybuj6p.mongodb.net/?retryWrites=true&w=majority&appName=VocationalGuidance";
    private static final MongoClient mongoClient = MongoClients.create(uri);

    public static void insertJobPost(JobPost post) {
        try {
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

    public static boolean validatePost(String postID) {
        try {
            MongoDatabase db = mongoClient.getDatabase("jobposts");
            MongoCollection<Document> jobPostsCollection = db.getCollection("jobposts");

            Document query = new Document("postID", postID);
            Document foundPost = jobPostsCollection.find(query).first();

            return foundPost != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<JobPost> getAllJobPosts() {
        List<JobPost> jobListings = new ArrayList<>();
        try {
            MongoDatabase db = mongoClient.getDatabase("jobposts");
            MongoCollection<Document> jobPostsCollection = db.getCollection("jobposts");

            FindIterable<Document> jobPosts = jobPostsCollection.find();

            for (Document post: jobPosts) {
                JobPost job = new JobPost(
                        post.getString("postID"),
                        post.getString("jobTitle"),
                        post.getString("postDescription"),
                        post.getString("recruiter"),
                        post.getString("date"),
                        post.getString("company"),
                        post.getString("location"),
                        post.getDouble("salary")
                );
                ObjectId generatedId = post.getObjectId("_id");
                job.setId(generatedId);
                jobListings.add(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobListings;
    }

}