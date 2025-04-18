package JobPostingManagement.Model;

import JobApplicationManagement.Model.Application;
import java.util.ArrayList;

public class JobPost {
    String postID;
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

    public JobPost(String postID, String jobTitle, String postDescription, String recruiter, String date, String company, String location, double salary, ArrayList<Application> applications) {
        this.postID = postID;
        this.jobTitle = jobTitle;
        this.postDescription = postDescription;
        this.recruiter = recruiter;
        this.date = date;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.applications = applications;
    }

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



    public String postID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostID() {return postID;}

    public String getJobTitle() {return jobTitle;}

    public String getPostDescription() {return postDescription;}

    public String getRecruiter() {return recruiter;}

    public String getDate() {return date;}

    public String getCompany() {return company;}

    public String getLocation() {return location;}

    public double getSalary() {return salary;}

    public ArrayList<Application> getApplications() {return applications;}

    public void addApplication(Application application) {
        applications.add(application);
    }

}
