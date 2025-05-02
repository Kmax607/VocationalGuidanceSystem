package JobApplicationManagement.Controller;

import JobApplicationManagement.Model.Application;
import JobApplicationManagement.Model.PreviousApplications;
import JobPostingManagement.Model.JobPost;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import DatabaseManagement.ApplicationRepository;
import Main.InterfaceRouter;
import org.bson.types.ObjectId;

public class ApplicationController {
    Application application;
    PreviousApplications previousApplications;
    Scanner scan = new Scanner(System.in);
    private InterfaceRouter router;

    public ApplicationController(InterfaceRouter router) {
        this.router = router;
    }

    public void submitApplication(Application application, JobPost post) {
        post.addApplication(application);
        previousApplications.addApplication(application);
    }

    public void acceptApplication(Application application) {
        ObjectId id = application.getObjectId();
        ApplicationRepository.acceptApplication(id.toString());

    }

    public void denyApplication(Application application) {
        ObjectId id = application.getObjectId();
        ApplicationRepository.denyApplication(id.toString());
    }

    public List<Application> getAllApplications() {
        List<Application> applications = ApplicationRepository.getAllApplications();
        return applications;
    }

    public File uploadResume(int choice) {
        File resume = new File("src/resume.txt");
        return resume;
    }

    public List<Application> getApplicationsByUser(String username) {
        return ApplicationRepository.getApplicationsByUsername(username); 
    }

    public void routeToLogin() { router.showLoginInterface(); }

}
