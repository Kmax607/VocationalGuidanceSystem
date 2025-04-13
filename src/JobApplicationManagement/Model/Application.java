package JobApplicationManagement.Model;

import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Application {

    int applicationID;
    int postID;
    String jobPostingTitle;
    String resume;
    ArrayList<String> questions;
    ArrayList<String> questionResponses;
    Date dateCompleted;
    boolean accepted;
    boolean denied;

    public Application (int applicationID, int postID, String jobPostingTitle, String resume, ArrayList<String> questions, ArrayList<String> questionResponses, Date dateCompleted, boolean accepted, boolean denied) {
        this.applicationID = applicationID;
        this.postID = postID;
        this.jobPostingTitle = jobPostingTitle;
        this.resume = resume;
        this.questions = new ArrayList<>(questions);
        this.questionResponses = new ArrayList<>(questionResponses);
        this.dateCompleted = dateCompleted;
        this.accepted = accepted;
        this.denied = denied;
    }

    public Document toDocument() {
        return new Document("applicationID", applicationID)
                .append("postID", postID)
                .append("jobPostingTitle", jobPostingTitle)
                .append("resume", resume)
                .append("questions", questions)
                .append("questionResponses", questionResponses)
                .append("dateCompleted", dateCompleted)
                .append("accepted", accepted)
                .append("denied", denied);
    }

    public void addQuestion(String question) {
        questions.add(question);
    }
    public void answerQuestion() {

        String response = ""; // placeholder
        questionResponses.add(response);
    }


    public void setAccepted() {
        accepted = true;
    }

    public void setDenied() {
        denied = true;
    }

    public void printApplicationDetails() {
        System.out.println("Application ID: " + applicationID);
        System.out.println("Post ID: " + postID);
        System.out.println("Job Posting Title: " + jobPostingTitle);
        System.out.println("Resume: " + resume);
        System.out.println("Date Completed: " + dateCompleted);
        System.out.println("Accepted: " + accepted);
        System.out.println("Denied: " + denied);

        System.out.println("\nQuestions:");
        for (String question : questions) {
            System.out.println("- " + question);
        }

        System.out.println("\nQuestion Responses:");
        for (String response : questionResponses) {
            System.out.println("- " + response);
        }
    }

}