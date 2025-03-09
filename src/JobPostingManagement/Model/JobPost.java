package JobPostingManagement.Model;

import JobApplicationManagement.Model.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JobPost {
    static String postID;
    String jobTitle;
    String postDescription;
    String recruiter;
    String date;
    String company;
    String location;
    double salary;

    ArrayList<Application> applications = new ArrayList<>();

//    public JobPost JobPostRepo;
//}

    public JobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary) {
        this.postID = postID;
        this.jobTitle = jobTitle;
        this.postDescription = postDescription;
        this.recruiter = recruiter;
        this.date = date;
        this.company = company;
        this.location = location;
        this.salary = salary;
    }

    public static String getPostID() {
        return postID;
    }

    public String postID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public static JobPost getPostID(String id) {
        return JobPostRepo.getJobById(id);
    }
    //*IMPORTANT NOTE*
    //This is a basic getter and setter for this attribute. It's the same structure for every single attribute

    public void addApplication(Application application) {
        applications.add(application);
    }

    public void getApplications() {
        for (int i = 0; i < applications.size(); i++) {
            System.out.println(applications.get(i));
        }
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
