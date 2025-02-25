package JobApplicationManagement.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Application {

    int applicationID;
    int postID;
    String jobPostingTitle;
    File resume;
    ArrayList<String> questions;
    static ArrayList<String> questionResponses;
    Date dateCompleted;
    boolean accepted;
    boolean denied;

    public Application (int applicationID, int postID, String jobPostingTitle, File resume, ArrayList<String> questions, ArrayList<String> questionResponses, Date dateCompleted, boolean accepted, boolean denied) {
        this.applicationID = applicationID;
        this.postID = postID;
        this.jobPostingTitle = jobPostingTitle;
        this.resume = resume;
        this.questions = new ArrayList<>();
        this.questionResponses = new ArrayList<>();
        this.dateCompleted = new Date();
        this.accepted = accepted;
        this.denied = denied;
    }

    public static String answerQuestion() {

        String response = ""; // placeholder
        questionResponses.add(response);
        return response;
    }

    public void uploadResume(File fileName) {
        File resume = fileName;

        this.resume = resume;
        // retrieves resume on file or uploads new one
    }

}
