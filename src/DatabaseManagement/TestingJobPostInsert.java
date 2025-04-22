package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import JobPostingManagement.Controller.PostController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TestingJobPostInsert {

    public static void main(String[] args) {

        // Dummy Application
        Application dummyApp = new Application(
                1,
                1001,
                "Junior Developer",
                "resume.pdf",
                new ArrayList<>(Arrays.asList("Why do you want this job?")),
                new ArrayList<>(Arrays.asList("Because I love coding!")),
                new Date(),
                Application.Status.UNDER_CONSIDERATION
        );

        ArrayList<Application> applications = new ArrayList<>();
        applications.add(dummyApp);

        // Dummy JobPost
        JobPost dummyJob = new JobPost(
                "1001",
                "Junior Developer",
                "Exciting opportunity to join our dev team.",
                "recruiter123",
                "2025-04-13",
                "TechCorp",
                "Remote",
                60000.00,
                applications
        );

//        JobPostRepository.insertJobPost(dummyJob);

        PostController postController = new PostController();

        boolean created = PostController.createJobPost(dummyJob);
        if (created) {
            System.out.println("Job Post Created through Post Controller");
        } else {
            System.out.println("Job Post Creation Failed");
        }

        boolean validated = postController.validateJobPost("1001");
        if (validated) {
            System.out.println("Job Post Validated through Post Controller");
        } else {
            System.out.println("Job Post Validation Failed");
        }
    }
}
