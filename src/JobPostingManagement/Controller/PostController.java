package JobPostingManagement.Controller;

import JobPostingManagement.Model.JobPost;
import JobPostingManagement.Model.JobPostRepo;
import JobPostingManagement.View.Post;
import java.util.List;

public class PostController {
    Post jobPost;
//    public PostController() { jobPost = new Post(); }
    public static JobPostRepo jobPostRepo = new JobPostRepo();

    public PostController() {}

//    public PostController(JobPostRepo jobPostRepo) {
//        PostController.jobPostRepo = jobPostRepo;
//    }

    public static boolean createJobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary) {
        JobPost newPost = new JobPost(postID, jobTitle, postDescription, recruiter, date, company, location, salary);
        return jobPostRepo.addJobPost(newPost);
    }

    public static boolean createJobPost(JobPost newPost) {
        return jobPostRepo.addJobPost(newPost);
    }

    public static JobPost getPostID(String id) {
        return jobPostRepo.getJobById(id);
    }

    public static List<JobPost> getAllJobPosts() {
        return jobPostRepo.getAllJobs();
    }

}

//Specifically for Recruiters (Job Post Creation)
//Candidates can click apply which would take them to the Job Application Management