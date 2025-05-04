package JobApplicationManagement.Controller;

import AuthenticationManagement.LoginInterface;
import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import DatabaseManagement.ApplicationRepository;
import Main.InterfaceRouter;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class ApplicationController {
    private InterfaceRouter router;

    public ApplicationController(InterfaceRouter router) {
        this.router = router;
    }

    public void submitApplication(Application application, JobPost post) {

        ApplicationRepository.submitApplication(application);
    }

    public List<Application> getApplicationsByUsername(String username) {
        return ApplicationRepository.getApplicationsByUsername(username);
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
        return ApplicationRepository.getAllApplications();
    }

    public List<Application> getApplicationsByJobTitle(String jobTitle) {
        return ApplicationRepository.getApplicationsByJobTitle(jobTitle);
    }

    public void routeToLogin() {
        new LoginInterface(router);
    }

    public InterfaceRouter getRouter() {
        return router;
    }
}
