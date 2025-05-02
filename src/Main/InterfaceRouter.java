package Main;

import AuthenticationManagement.LoginInterface;
import JobApplicationManagement.View.ManageJobPostsUI;
import JobSearchingManagement.View.JobView;

import javax.swing.*;

public class InterfaceRouter {
    private LoginInterface loginInterface;
    private ManageJobPostsUI manageJobPostsInterface;
    private JobView jobSearchInterface;

    public InterfaceRouter() {
        loginInterface = new LoginInterface(this);
        manageJobPostsInterface = new ManageJobPostsUI(this);
        jobSearchInterface = new JobView(this);

        loginInterface.setVisible(true);
        manageJobPostsInterface.setVisible(false);
    }

    public void showLoginInterface() {
        manageJobPostsInterface.setVisible(false);
        loginInterface.setVisible(true);
    }

    public void showManageJobPostsInterface() {
        loginInterface.setVisible(false);
        manageJobPostsInterface.setVisible(true);
    }

    public void showJobSearchInterface() {
        loginInterface.setVisible(false);
        jobSearchInterface.setVisible(true);
    }

    public static void main(String[] args) {
        new InterfaceRouter();
    }
}
