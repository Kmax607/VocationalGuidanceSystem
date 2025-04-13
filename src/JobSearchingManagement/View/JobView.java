package JobSearchingManagement.View;

import JobSearchingManagement.Model.JobListing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JobView {

    // the applied filters for job search
    private static ArrayList<String> filters = new ArrayList<>();

    // if toggled in view, filters get applied and sent to controller for query
    public void applyFilters(boolean toggled) {
        if (toggled) {
            filters.add("Job Title");
            filters.add("Location");
            filters.add("Company");
            filters.add("Experience Level");
            filters.add("Salary Range");
            filters.add("Job Type");
            filters.add("Industry");
            filters.add("Skills Required");
            filters.add("Date Posted");
            filters.add("Company Size");
        }
    }


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