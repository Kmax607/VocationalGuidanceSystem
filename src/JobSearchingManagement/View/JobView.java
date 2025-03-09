package JobSearchingManagement.View;

import JobSearchingManagement.Model.JobListing;
import java.util.List;

public class JobView {
    // Display a list of job listings
    public void displayJobs(List<JobListing> jobs) {
        if (jobs.isEmpty()) {
            System.out.println("No jobs found.");
        } else {
            System.out.println("Job Listings:");
            for (JobListing job : jobs) {
                System.out.println(job);
            }
        }
    }

    // Display an error message
    public void displayError(String message) {
        System.out.println("Error: " + message);
    }
}