package JobSearchingManagement.Controller;

import JobSearchingManagement.Model.JobListing;  // Add this import
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchController {
    private List<JobListing> jobListings; // List to store job listings

    // Constructor
    public SearchController() {
        this.jobListings = new ArrayList<>();
        // Add some sample job listings
        jobListings.add(new JobListing("Software Engineer", "Google", "Remote"));
        jobListings.add(new JobListing("Data Scientist", "Amazon", "Seattle, WA"));
        jobListings.add(new JobListing("Frontend Developer", "Facebook", "Menlo Park, CA"));
        jobListings.add(new JobListing("Backend Developer", "Netflix", "Remote"));
    }

    // Search for jobs based on a query
    public List<JobListing> searchJobs(String query) {
        // Use streams to filter job listings based on the query
        return jobListings.stream()
                .filter(job -> job.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        job.getCompany().toLowerCase().contains(query.toLowerCase()) ||
                        job.getLocation().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Fetch all jobs
    public List<JobListing> getAllJobs() {
        return jobListings;
    }
}