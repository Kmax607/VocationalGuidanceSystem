package JobApplicationManagement.Controller;

import AuthenticationManagement.LoginInterface;
import JobApplicationManagement.Model.Application;
import DatabaseManagement.ApplicationRepository;
import Main.InterfaceRouter;

import java.util.List;

public class ApplicationController {
    private final InterfaceRouter router;

    public ApplicationController(InterfaceRouter router) {
        this.router = router;
    }

    public void submitApplication(Application application, String username) {
        if (application == null) {
            throw new IllegalArgumentException("Application cannot be null");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        // Set the username on the application to ensure consistency
        if (application.getUsername() == null || application.getUsername().trim().isEmpty()) {
            // If username is not set, set it to the provided username
            try {
                java.lang.reflect.Field usernameField = Application.class.getDeclaredField("username");
                usernameField.setAccessible(true);
                usernameField.set(application, username);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to set username on application: " + e.getMessage(), e);
            }
        } else if (!username.equals(application.getUsername())) {
            // If username is set but doesn't match, throw an exception
            throw new IllegalArgumentException("Username mismatch: provided username does not match application's username");
        }
        try {
            ApplicationRepository.insertApplication(application);
        } catch (Exception e) {
            throw new RuntimeException("Failed to submit application: " + e.getMessage(), e);
        }
    }

    public List<Application> getApplicationsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return ApplicationRepository.getApplicationsByUsername(username);
    }

    public List<Application> getApplicationsByJobTitle(String jobTitle) {
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be null or empty");
        }
        return ApplicationRepository.getApplicationsByJobTitle(jobTitle);
    }

    public boolean withdrawApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }
        try {
            return ApplicationRepository.deleteApplication(applicationId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to withdraw application: " + e.getMessage(), e);
        }
    }

    public void acceptApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }
        try {
            ApplicationRepository.acceptApplication(applicationId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to accept application: " + e.getMessage(), e);
        }
    }

    public void denyApplication(String applicationId) {
        if (applicationId == null || applicationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Application ID cannot be null or empty");
        }
        try {
            ApplicationRepository.denyApplication(applicationId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deny application: " + e.getMessage(), e);
        }
    }

    public void routeToLogin() {
        new LoginInterface(router);
    }

    public InterfaceRouter getRouter() {
        return router;
    }
}