package JobPostingManagement.View;

import JobPostingManagement.Model.JobPost;

import javax.swing.*;

public class PostView extends JFrame {
    public void displayJobPost(JobPost jobPost) {}
}

//I will create the form where job posts will appear from the Job Search page
//This page will be slightly different depending if the authentication is for Recruiter or Candidate
//For recruiter they can create/edit their post, and for Candidate they are able to apply which takes them to the Job Application Management