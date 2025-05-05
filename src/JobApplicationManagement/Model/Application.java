package JobApplicationManagement.Model;

import JobApplicationManagement.Observer;
import JobApplicationManagement.Subject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application implements Subject {
    public enum Status {
        ACCEPTED,
        DENIED,
        UNDER_CONSIDERATION
    }

    private ArrayList<Observer> observers = new ArrayList<>();
    private ObjectId id;
    private int applicationID;
    private String postID;
    private String username; // New field to track the user
    private String jobPostingTitle;
    private String resume;
    private ArrayList<String> questions;
    private ArrayList<String> questionResponses;
    private Date dateCompleted;
    private Status status;

    public Application(Document doc) {
        this.id = doc.getObjectId("_id");
        this.applicationID = doc.getInteger("applicationID");
        // Handle postID as either String or Integer
        Object postIdObj = doc.get("postID");
        if (postIdObj instanceof String) {
            this.postID = (String) postIdObj;
        } else if (postIdObj instanceof Integer) {
            this.postID = String.valueOf((Integer) postIdObj);
        } else {
            this.postID = "0";
        }
        this.username = doc.getString("username") != null ? doc.getString("username") : "";
        this.jobPostingTitle = doc.getString("jobPostingTitle") != null ? doc.getString("jobPostingTitle") : "";
        this.resume = doc.getString("resume") != null ? doc.getString("resume") : "";
        // Handle questions as a List and convert to ArrayList<String>
        this.questions = new ArrayList<>();
        List<?> rawQuestions = doc.get("questions", List.class);
        if (rawQuestions != null) {
            for (Object question : rawQuestions) {
                if (question instanceof String) {
                    this.questions.add((String) question);
                } else {
                    System.err.println("Skipping invalid question in Application document: " + question);
                }
            }
        }
        // Handle questionResponses as a List and convert to ArrayList<String>
        this.questionResponses = new ArrayList<>();
        List<?> rawResponses = doc.get("questionResponses", List.class);
        if (rawResponses != null) {
            for (Object response : rawResponses) {
                if (response instanceof String) {
                    this.questionResponses.add((String) response);
                } else {
                    System.err.println("Skipping invalid question response in Application document: " + response);
                }
            }
        }
        this.dateCompleted = doc.getDate("dateCompleted");
        String statusStr = doc.getString("status");
        this.status = (statusStr != null && !statusStr.isEmpty()) ? Status.valueOf(statusStr) : Status.UNDER_CONSIDERATION;
    }

    public Application(int applicationID, String postID, String username, String jobPostingTitle, String resume, ArrayList<String> questions, ArrayList<String> questionResponses, Date dateCompleted, Status status) {
        this.applicationID = applicationID;
        this.postID = postID;
        this.username = username;
        this.jobPostingTitle = jobPostingTitle;
        this.resume = this.resume;
        this.questions = new ArrayList<>(questions);
        this.questionResponses = new ArrayList<>(questionResponses);
        this.dateCompleted = dateCompleted;
        this.status = status;
    }

    public Document toDocument() {
        Document doc = new Document("applicationID", applicationID)
                .append("postID", postID)
                .append("username", username)
                .append("jobPostingTitle", jobPostingTitle)
                .append("resume", resume)
                .append("questions", questions)
                .append("questionResponses", questionResponses)
                .append("dateCompleted", dateCompleted)
                .append("status", status.toString());
        if (id != null) {
            doc.append("_id", id);
        }
        return doc;
    }

    public void addQuestion(String question) {
        questions.add(question);
    }

    public void printApplicationDetails() {
        System.out.println("Application ID: " + applicationID);
        System.out.println("MongoDB ID: " + (id != null ? id.toString() : "Not set"));
        System.out.println("Post ID: " + postID);
        System.out.println("Username: " + username);
        System.out.println("Job Posting Title: " + jobPostingTitle);
        System.out.println("Resume: " + resume);
        System.out.println("Date Completed: " + dateCompleted);
        System.out.println("Status: " + status);

        System.out.println("\nQuestions:");
        for (String question : questions) {
            System.out.println("- " + question);
        }

        System.out.println("\nQuestion Responses:");
        for (String response : questionResponses) {
            System.out.println("- " + response);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setDenied() {
        this.status = Status.DENIED;
        notifyObservers();
    }

    public void setAccepted() {
        this.status = Status.ACCEPTED;
        notifyObservers();
    }

    // GETTERS
    public String getApplicationId() {
        return id != null ? id.toString() : null;
    }

    public ObjectId getObjectId() {
        return id;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public String getPostID() {
        return postID;
    }

    public String getUsername() {
        return username;
    }

    public String getJobPostingTitle() {
        return jobPostingTitle;
    }

    public String getResume() {
        return resume;
    }

    public ArrayList<String> getQuestions() {
        return new ArrayList<>(questions);
    }

    public ArrayList<String> getQuestionResponses() {
        return new ArrayList<>(questionResponses);
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public Status getStatus() {
        return status;
    }

    // SETTERS
    public void setId(ObjectId id) {
        this.id = id;
    }
}