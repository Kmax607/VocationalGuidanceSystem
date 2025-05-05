package JobPostingManagement.Controller;

import DatabaseManagement.JobPostRepository;
import DatabaseManagement.ApplicationRepository;
import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import Main.InterfaceRouter;

import java.util.List;
import java.util.Objects;

public class PostController {
    private final InterfaceRouter router;

    public PostController(InterfaceRouter router) {
        if (router == null) {
            throw new IllegalArgumentException("Router cannot be null");
        }
        this.router = router;
    }

    public List<JobPost> getJobPostsByRecruiter(String recruiterUsername) {
        if (recruiterUsername == null || recruiterUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Recruiter username cannot be null or empty");
        }
        try {
            List<JobPost> jobPosts = JobPostRepository.getJobPostsByRecruiter(recruiterUsername);
            System.out.println("from controller: " + jobPosts);
            return jobPosts;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch job posts for recruiter " + recruiterUsername + ": " + e.getMessage(), e);
        }
    }

    public void createJobPost(JobPost newPost) {
        if (newPost == null) {
            throw new IllegalArgumentException("JobPost cannot be null");
        }
        try {
            JobPostRepository.insertJobPost(newPost);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create job post with ID " + newPost.getPostID() + ": " + e.getMessage(), e);
        }
    }

    public boolean validateJobPost(String postID) {
        if (postID == null || postID.trim().isEmpty()) {
            throw new IllegalArgumentException("Post ID cannot be null or empty");
        }
        try {
            return JobPostRepository.validatePost(postID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate job post with ID " + postID + ": " + e.getMessage(), e);
        }
    }

    public JobPost getJobById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Job post ID cannot be null or empty");
        }
        try {
            List<JobPost> allJobs = JobPostRepository.getAllJobPosts();
            for (JobPost job : allJobs) {
                if (Objects.equals(job.getPostID(), id)) {
                    return job;
                }
            }
            throw new IllegalStateException("Job post with ID " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch job post with ID " + id + ": " + e.getMessage(), e);
        }
    }

    public List<JobPost> getAllJobPosts() {
        try {
            List<JobPost> jobPosts = JobPostRepository.getAllJobPosts();
            System.out.println("from controller: " + jobPosts);
            return jobPosts;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all job posts: " + e.getMessage(), e);
        }
    }

    public void addApplicantToJobPost(JobPost job, Application application) {
        if (job == null) {
            throw new IllegalArgumentException("JobPost cannot be null");
        }
        if (application == null) {
            throw new IllegalArgumentException("Application cannot be null");
        }
        if (!job.getPostID().equals(application.getPostID())) {
            throw new IllegalArgumentException("Application post ID does not match JobPost ID");
        }
        try {
            // Store the application in the applications collection
            ApplicationRepository.insertApplication(application);
            // Optionally, add the application to the JobPost for display purposes
            // Note: Consider removing this if applications are primarily tracked in the applications collection
            JobPostRepository.addApplicationToPost(job, application);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add applicant to job post with ID " + job.getPostID() + ": " + e.getMessage(), e);
        }
    }

    public List<Application> getApplicationsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        try {
            return ApplicationRepository.getApplicationsByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch applications for user " + username + ": " + e.getMessage(), e);
        }
    }

    public void showManageJobPostsInterface() {
        try {
            router.showManageJobPostsInterface();
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to manage job posts interface: " + e.getMessage(), e);
        }
    }
}