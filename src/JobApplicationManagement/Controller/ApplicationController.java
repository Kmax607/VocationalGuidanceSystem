package JobApplicationManagement.Controller;

import JobApplicationManagement.Model.Application;
import JobApplicationManagement.Model.PreviousApplications;
import JobPostingManagement.Model.JobPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationController {
    Application application;
    PreviousApplications previousApplications;
    Scanner scan = new Scanner(System.in);

    public ApplicationController(PreviousApplications previousApplications) {
        this.previousApplications = previousApplications;
    }


    public void submitApplication(Application application, JobPost post) {
        post.addApplication(application);
        previousApplications.addApplication(application);
    }

    public void acceptApplication(Application application) {
        application.setAccepted();
    }

    public void denyApplication(Application application) {
        application.setDenied();
    }

    public File uploadResume(int choice) {
        File resume = new File("src/resume.txt");
        return resume;
    }

}
