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

    public List<JobPost> getJobPostsByRecruiter(String recruiterUsername) {
        List<JobPost> jobposts = JobPostRepository.getJobPostsByRecruiter(recruiterUsername);
        System.out.println("from controller: " + jobposts);
        return jobposts;
    }

    public static boolean createJobPost(JobPost newPost) {
        try {
            JobPostRepository.insertJobPost(newPost);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateJobPost(String postID) {
        return JobPostRepository.validatePost(postID);
    }


    public static boolean createJobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary) {
        JobPost newPost = new JobPost(postID, jobTitle, postDescription, recruiter, date, company, location, salary);
        return jobPostRepo.addJobPost(newPost);
    }

    public static JobPost getPostID(String id) {
        return jobPostRepo.getJobById(id);
    }

    public static List<JobPost> getAllJobPosts() {
        List<JobPost> jobposts = JobPostRepository.getAllJobPosts();
        System.out.println("from controller: " + jobposts);
        return jobposts;
    }

    // add Application obj as param
    public static void addApplicantToJobPost(JobPost job) {
        JobPostRepository.addApplicationToPost(job);
    }


    public void showManageJobPostsInterface() {
        router.showManageJobPostsInterface();
    }
}