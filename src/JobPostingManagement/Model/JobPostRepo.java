package JobPostingManagement.Model;

import JobPostingManagement.View.Post;

import java.util.ArrayList;
import java.util.List;

public class JobPostRepo {
    public List<JobPost> jobPosts = new ArrayList<>();
    public JobPostRepo repo;
    public Post jobPostView;

    public JobPostRepo() {
        this.jobPosts = new ArrayList<>();
        this.jobPostView = new Post();
    }

    public void addJobPost(JobPost jobPost) {
        jobPosts.add(jobPost);
    }

    public void deleteJobPost(JobPost jobPost) {
        jobPosts.remove(jobPost);
    }
}