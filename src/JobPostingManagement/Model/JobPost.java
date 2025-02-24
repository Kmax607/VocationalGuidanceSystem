package JobPostingManagement.Model;

public class JobPost {
    String postID;
    String jobTitle;
    String postDescription;
    String recruiter;
    String date;
    String company;
    String location;
    double salary;

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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
    //*IMPORTANT NOTE*
    //This is a basic getter and setter for this attribute. It's the same structure for every single attribute
}
