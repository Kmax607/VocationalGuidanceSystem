package JobApplicationManagement.Controller;

import AuthenticationManagement.LoginInterface;
import JobApplicationManagement.Model.Application;
import JobApplicationManagement.Model.PreviousApplications;
import JobApplicationManagement.View.ManageApplicationsUI;
import JobPostingManagement.Model.JobPost;


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
    private ManageApplicationsUI view;


    public ApplicationController(InterfaceRouter router) {
        this.view = view;
        this.router = router;
        this.previousApplications = previousApplications;
    }

    public void submitApplication(Application application, JobPost post) {
        post.addApplication(application);
        previousApplications.addApplication(application);
        ApplicationRepository.insertApplication(application);
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

    public List<Application> getApplicationsByJobTitle(String jobTitle) {
        List<Application> applications = ApplicationRepository.getApplicationsByJobTitle(jobTitle);
        return applications;
    }

    public List<Application> getApplicationsByUser() {
        String username = router.getCurrentUsername();
        System.out.println(username);
        return ApplicationRepository.getApplicationsByUsername(username);
    }

    public void routeToLogin() {
        new LoginInterface(router);
    }

    public InterfaceRouter getRouter() {
        return router;
    }
}
