package Main;

import AuthenticationManagement.LoginInterface;
import JobApplicationManagement.View.ManageApplicationsUI;
import JobApplicationManagement.View.ManageJobPostsUI;
import JobSearchingManagement.View.JobView;
import JobApplicationManagement.View.ApplicationFormUI;
import JobPostingManagement.Model.JobPost;

public class InterfaceRouter {
    private LoginInterface loginInterface;
    private ManageJobPostsUI manageJobPostsInterface;
    private JobView jobSearchInterface;
    private ManageApplicationsUI userApplicationsInterface;
    private String currentUsername;


    public InterfaceRouter() {
        loginInterface = new LoginInterface(this);
        loginInterface.setVisible(true);
    }

    public void showManageJobPostsInterface() {
        if (manageJobPostsInterface == null) {
            manageJobPostsInterface = new ManageJobPostsUI(this);
        } else {
            manageJobPostsInterface.reloadJobPostsTable();
            manageJobPostsInterface.setVisible(true);
            manageJobPostsInterface.toFront();
            manageJobPostsInterface.requestFocus();
        }
    }

    public void showJobSearchInterface() {
        if (jobSearchInterface == null) {
            jobSearchInterface = new JobView(this);
        } else {
            jobSearchInterface.setVisible(true);
            jobSearchInterface.toFront();
            jobSearchInterface.requestFocus();
        }
    }

    public void showUserApplicationsInterface() {
        userApplicationsInterface = new ManageApplicationsUI(this);
        userApplicationsInterface.setVisible(true);
    }

    public void showApplicationForm(JobPost job, String username) {
        new ApplicationFormUI(job, username);
    }

    public void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public static void main(String[] args) {
        new InterfaceRouter();
    }

    public void showLoginInterface() {
        if (loginInterface == null) {
            loginInterface = new LoginInterface(this);
        }
        loginInterface.setVisible(true);
        loginInterface.toFront();
        loginInterface.requestFocus();
    }
}
