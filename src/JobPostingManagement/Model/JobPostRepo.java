package JobPostingManagement.Model;

import java.util.ArrayList;
import java.util.List;

public class JobPostRepo {
    private List<JobPost> jobPosts = new ArrayList<>();

    public JobPostRepo() {
        // Initialize with sample data if needed
    }

    public JobPost getJobById(int id) {
        for (JobPost job : jobPosts) {
            if (job.getPostID().equals(id)) {
                return job;
            }
        }
        return null;
    }

    public boolean addJobPost(JobPost jobPost) {
        if (jobPost != null && !jobPosts.contains(jobPost)) {
            return jobPosts.add(jobPost);
        }
        return false;
    }

    public void deleteJobPost(JobPost jobPost) {
        if (jobPost != null) {
            jobPosts.remove(jobPost);
        }
    }

    public List<JobPost> getAllJobs() {
        return new ArrayList<>(jobPosts); // Return a copy to prevent external modification
    }
}