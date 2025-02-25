package JobApplicationManagement.Controller;

import JobApplicationManagement.Model.Application;
import JobApplicationManagement.Model.PreviousApplications;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationController {
    Application application;
    static PreviousApplications previousApplications;

    public ApplicationController(Application application) {
        this.application = application;
        PreviousApplications previousApplications = new PreviousApplications(new ArrayList<Application>());
    }


    public static void submitApplication(int applicationID, int postID, String jobPostingTitle, File resume, ArrayList<String> questions, ArrayList<String> questionResponses, Date dateCompleted, boolean accepted, boolean denied) {
        Application application = new Application(applicationID, postID, jobPostingTitle, resume, questions, questionResponses, dateCompleted, accepted, denied);
        previousApplications.addApplication(application);
    }


}
