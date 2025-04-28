package JobApplicationManagement.Model;

import JobApplicationManagement.Observer;
import JobApplicationManagement.Subject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class Application implements Subject {

    public enum Status {
        ACCEPTED,
        DENIED,
        UNDER_CONSIDERATION
    }
    private ArrayList<Observer> observers = new ArrayList<>();
    ObjectId id;
    int applicationID;
    int postID;
    String jobPostingTitle;
    String resume;
    ArrayList<String> questions;
    ArrayList<String> questionResponses;
    Date dateCompleted;
    Status status;

    public Application (int applicationID, int postID, String jobPostingTitle, String resume, ArrayList<String> questions, ArrayList<String> questionResponses, Date dateCompleted, Status status) {
        this.applicationID = applicationID;
        this.postID = postID;
        this.jobPostingTitle = jobPostingTitle;
        this.resume = resume;
        this.questions = new ArrayList<>(questions);
        this.questionResponses = new ArrayList<>(questionResponses);
        this.dateCompleted = dateCompleted;
        this.status = status;
    }

    public Document toDocument() {
        return new Document("applicationID", applicationID)
                .append("postID", postID)
                .append("jobPostingTitle", jobPostingTitle)
                .append("resume", resume)
                .append("questions", questions)
                .append("questionResponses", questionResponses)
                .append("dateCompleted", dateCompleted)
                .append("status", status);
    }

    public void addQuestion(String question) {
        questions.add(question);
    }
    public void answerQuestion() {

        String response = ""; // placeholder
        questionResponses.add(response);
    }

    public void printApplicationDetails() {
        System.out.println("Application ID: " + applicationID);
        System.out.println("Post ID: " + postID);
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
        for (Observer observer: observers) {
            observer.update(this);
        }
    }

    // when application is denied, notifies observers
    public void setDenied() {
        this.status = Status.DENIED;
        notifyObservers();
    }

    // // when application is accepted, notifies observers
    public void setAccepted() {
        this.status = Status.ACCEPTED;
        notifyObservers();
    }

    // GETTERS:
    public ObjectId getObjectId() { return id;}
    public int getApplicationID() {
        return applicationID;
    }

    public int getPostID() {
        return postID;
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

    //SETTERS
    public void setId(ObjectId id) {
        this.id = id;
    }
}