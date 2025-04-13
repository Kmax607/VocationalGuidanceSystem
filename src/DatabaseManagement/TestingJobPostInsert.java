package DatabaseManagement;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;

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
                false,
                false
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

        JobPostRepository.insertJobPost(dummyJob);
    }
}
