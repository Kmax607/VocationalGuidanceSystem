package JobPostingManagement.Controller;

import DatabaseManagement.JobPostRepository;
import JobPostingManagement.Model.JobPost;
import JobPostingManagement.Model.JobPostRepo;
import JobPostingManagement.View.PostView;
import Main.InterfaceRouter;

import java.util.List;

public class PostController {
    private PostView jobPostView;
    private InterfaceRouter router;
    private PostView view;
    public static JobPostRepo jobPostRepo = new JobPostRepo();

    public PostController(InterfaceRouter router) {
        this.view = view;
        this.router = this.router;
        if (router == null) {
            throw new IllegalArgumentException("Router cannot be null");
        }
        this.router = router;
    }


    public static boolean createJobPost(JobPost newPost) {
        System.out.println("Creating new Job Post: " + newPost);

        if (!newPost.getPostID().isEmpty() && !newPost.getJobTitle().isEmpty() && !newPost.getRecruiter().isEmpty()) {
            System.out.println("Successful Post Creation: " + newPost);
            JobPostRepository.insertJobPost(newPost);
            return true;
        }

        System.out.println("Post Creation Failed. Missing required details: " + newPost.getPostID());
        return false;
    }

    public boolean validateJobPost(String postID) {
        System.out.println("Validating Post with ID: " + postID);

        if (JobPostRepository.validatePost(postID)) {
            System.out.println("Post Successfully Identified: " + postID);
            return true;
        }

        System.out.println("No Post found with ID: " + postID);
        return false;
    }


    public static boolean createJobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary) {
        JobPost newPost = new JobPost(postID, jobTitle, postDescription, recruiter, date, company, location, salary);
        return jobPostRepo.addJobPost(newPost);
    }

    public static JobPost getPostID(String id) {
        return jobPostRepo.getJobById(id);
    }

    public static List<JobPost> getAllJobPosts() {
        return jobPostRepo.getAllJobs();
    }


    public void showManageJobPostsInterface() {
        if (router == null) {
            throw new IllegalStateException("Router is null in PostController");
        }
        router.showManageJobPostsInterface();
    }
}


//Specifically for Recruiters (Job Post Creation)
//Candidates can click apply which would take them to the Job Application Management