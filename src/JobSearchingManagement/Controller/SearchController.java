package JobSearchingManagement.Controller;

import DatabaseManagement.JobPostRepository;
import JobPostingManagement.Model.JobPost;
import JobSearchingManagement.Model.JobListing;  // Add this import
import JobSearchingManagement.View.JobView;
import Main.InterfaceRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchController {
    private JobView view;
    private InterfaceRouter router;

    public SearchController(JobView view, InterfaceRouter router) {
        this.view = view;
        this.router = router;
    }

    public List<JobPost> getJobsByRecruiter(String recruiter) {
        return JobPostRepository.getJobPostsByRecruiter(recruiter);
    }


    public void routeToLogin() {
        view.dispose();
        router.showLoginInterface();
    }

    public List<JobPost> getAllJobs() {
        List<JobPost> jobs = JobPostRepository.getAllJobPosts();
        return jobs;
    }

    public void routeToApplications() { router.showUserApplicationsInterface(); }
}

