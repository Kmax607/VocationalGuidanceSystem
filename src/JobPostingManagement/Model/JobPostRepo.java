package JobPostingManagement.Model;

import JobPostingManagement.View.PostView;

import java.util.ArrayList;
import java.util.List;

public class JobPostRepo {
    public static List<JobPost> jobPosts = new ArrayList<>();
    public JobPostRepo repo;
    public PostView jobPostView;

//    public JobPostRepo() {
//        this.jobPosts = new ArrayList<>();
//        this.jobPostView = new PostView(this);
//    }

    public static JobPost getJobById(String id) {
        for (JobPost job : jobPosts) {
            if (job.postID().equals(id)) {
                return job;
            }
        }
        return null;
    }

    public boolean addJobPost(JobPost jobPost) {
        jobPosts.add(jobPost);
        return false;
    }

    public void deleteJobPost(JobPost jobPost) {
        jobPosts.remove(jobPost);
    }

    public List<JobPost> getAllJobs() {
        return jobPosts;
    }

}