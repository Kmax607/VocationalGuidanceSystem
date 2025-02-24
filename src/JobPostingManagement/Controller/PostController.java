package JobPostingManagement.Controller;

import JobPostingManagement.Model.JobPost;
import JobPostingManagement.Model.JobPostRepo;
import JobPostingManagement.View.Post;

;

public class PostController {
    Post jobPost;
    public PostController() { jobPost = new Post(); }
    public JobPostRepo jobPostRepo;

    public PostController(JobPostRepo jobPostRepo) {
        this.jobPostRepo = jobPostRepo;
    }

    public void createJobPost (String postID, String jobTitle, String postDescription, String recruiter,
                               String date, String company, String location, double salary) {
        JobPost newPost = new JobPost(postID, jobTitle, postDescription, recruiter, date, company, location, salary);
        jobPostRepo.addJobPost(newPost);
    }
}

//Specifically for Recruiters (Job Post Creation)
//Candidates can click apply which would take them to the Job Application Management