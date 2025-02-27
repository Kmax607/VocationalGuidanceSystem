package JobSearchingManagement.View;

import JobSearchingManagement.Model.JobListing;

import java.util.List;

public class JobListView {
    public void displayJobListings(List<JobListing> jobListings) {
        if (jobListings.isEmpty()) {
            System.out.println("No job listings found.");
        } else {
            System.out.println("Job Listings:");
            for (JobListing job : jobListings) {
                System.out.println();
            }
        }
    }
}

