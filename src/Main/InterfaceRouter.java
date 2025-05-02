package Main;

import AuthenticationManagement.LoginInterface;
import JobApplicationManagement.View.ManageApplicationsUI;
import JobApplicationManagement.View.ManageJobPostsUI;
import JobSearchingManagement.View.JobView;
import org.bson.types.ObjectId;

import javax.swing.*;

public class InterfaceRouter {
    private LoginInterface loginInterface;
    private ManageJobPostsUI manageJobPostsInterface;
    private JobView jobSearchInterface;
    private ManageApplicationsUI userApplicationsInterface;
    private String currentUsername;

    public InterfaceRouter() {
        loginInterface = new LoginInterface(this);
        loginInterface.setVisible(true);

        manageJobPostsInterface = new ManageJobPostsUI(this);
        manageJobPostsInterface.setVisible(false);

        jobSearchInterface = new JobView(this);
        jobSearchInterface.setVisible(false);

        userApplicationsInterface = new ManageApplicationsUI(this);
        userApplicationsInterface.setVisible(false);
    }

    public void showLoginInterface() {
        manageJobPostsInterface.setVisible(false);
        jobSearchInterface.setVisible(false);
        loginInterface.setVisible(true);
    }

    public void showManageJobPostsInterface() {
        loginInterface.setVisible(false);
        manageJobPostsInterface.setVisible(true);
        userApplicationsInterface.dispose();
    }

    public void showJobSearchInterface() {
        loginInterface.setVisible(false);
        userApplicationsInterface.setVisible(false);
        jobSearchInterface.setVisible(true);
    }

    public void showUserApplicationsInterface() {
        jobSearchInterface.setVisible(false);
        userApplicationsInterface.refreshApplications();
        userApplicationsInterface.setVisible(true);
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
}
