package JobPostingManagement.Model;

import JobApplicationManagement.Model.Application;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class JobPost {
    private ObjectId id;
    private String postID;
    private String jobTitle;
    private String postDescription;
    private String recruiter;
    private String date;
    private String company;
    private String location;
    private double salary;
    private ArrayList<Application> applications = new ArrayList<>();
    private String status;

    public JobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary, ArrayList<Application> applications) {
        this.postID = postID;
        this.jobTitle = jobTitle;
        this.postDescription = postDescription;
        this.recruiter = recruiter;
        this.date = date;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.applications = new ArrayList<>(applications != null ? applications : new ArrayList<>());
        this.status = "Open";
    }

    public JobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary) {
        this(postID, jobTitle, postDescription, recruiter, date, company, location, salary, new ArrayList<>());
    }

    public JobPost(Document doc) {
        this.id = doc.getObjectId("_id");
        this.postID = doc.getString("postID") != null ? doc.getString("postID") : "0";
        this.jobTitle = doc.getString("jobTitle");
        this.postDescription = doc.getString("postDescription");
        this.recruiter = doc.getString("recruiter");
        this.date = doc.getString("date");
        this.company = doc.getString("company");
        this.location = doc.getString("location");
        this.salary = doc.getDouble("salary") != null ? doc.getDouble("salary") : 0.0;
        this.applications = new ArrayList<>();
        List<?> rawApplications = doc.get("applications", List.class);
        if (rawApplications != null) {
            for (Object appDoc : rawApplications) {
                if (appDoc instanceof Document) {
                    this.applications.add(new Application((Document) appDoc));
                } else {
                    System.err.println("Skipping invalid Application in JobPost document: " + appDoc);
                }
            }
        }
        this.status = doc.getString("status") != null ? doc.getString("status") : "Open"; // Fixed line 61
    }

    public Document toDocument() {
        Document doc = new Document("postID", postID)
                .append("jobTitle", jobTitle)
                .append("postDescription", postDescription)
                .append("recruiter", recruiter)
                .append("date", date)
                .append("company", company)
                .append("location", location)
                .append("salary", salary)
                .append("status", status);
        if (id != null) {
            doc.append("_id", id);
        }
        if (!applications.isEmpty()) {
            ArrayList<Document> applicationDocs = new ArrayList<>();
            for (Application app : applications) {
                applicationDocs.add(app.toDocument());
            }
            doc.append("applications", applicationDocs);
        }
        return doc;
    }

    // Getters
    public String getApplicationId() {
        return id != null ? id.toString() : null;
    }

    public ObjectId getId() {
        return id;
    }

    public String getPostID() {
        return postID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getRecruiter() {
        return recruiter;
    }

    public String getDate() {
        return date;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public ArrayList<Application> getApplications() {
        return new ArrayList<>(applications);
    }

    public String getStatus() {
        return status != null ? status : "Open";
    }

    // Setters
    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addApplication(Application application) {
        if (application != null) {
            applications.add(application);
        }
    }
}