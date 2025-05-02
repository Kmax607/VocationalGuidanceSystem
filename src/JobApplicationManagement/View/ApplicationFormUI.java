package JobApplicationManagement.View;

import JobApplicationManagement.Controller.ApplicationController;
import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationFormUI extends JFrame {
    private JTextField applicationIDField;
    private JTextField resumeField;
    private JTextArea questionsArea;
    private JTextArea responsesArea;
    private JButton submitButton;

    private ApplicationController applicationController;
    private JobPost jobPost;
    private String username;

    public ApplicationFormUI(JobPost jobPost, String username) {
        this.jobPost = jobPost;
        this.username = username;
        this.applicationController = new ApplicationController();

        setTitle("Submit Application for: " + jobPost.getJobTitle());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        applicationIDField = new JTextField();
        resumeField = new JTextField("src/resume.txt");
        questionsArea = new JTextArea("Enter questions separated by new lines");
        responsesArea = new JTextArea("Enter responses in same order as questions");

        submitButton = new JButton("Submit Application");
        submitButton.addActionListener(this::handleSubmit);

        panel.add(new JLabel("Application ID:"));
        panel.add(applicationIDField);
        panel.add(new JLabel("Resume File Path:"));
        panel.add(resumeField);
        panel.add(new JLabel("Questions:"));
        panel.add(new JScrollPane(questionsArea));
        panel.add(new JLabel("Responses:"));
        panel.add(new JScrollPane(responsesArea));
        panel.add(submitButton);

        add(panel);
    }

    private void handleSubmit(ActionEvent e) {
        try {
            int applicationID = Integer.parseInt(applicationIDField.getText().trim());
            int postID = Integer.parseInt(jobPost.getPostID());
            String resume = resumeField.getText().trim();

            String[] questionsSplit = questionsArea.getText().split("\n");
            String[] responsesSplit = responsesArea.getText().split("\n");

            if (questionsSplit.length != responsesSplit.length) {
                JOptionPane.showMessageDialog(this, "Questions and responses count must match.");
                return;
            }

            ArrayList<String> questions = new ArrayList<>();
            ArrayList<String> responses = new ArrayList<>();

            for (String q : questionsSplit) questions.add(q);
            for (String r : responsesSplit) responses.add(r);

            Application application = new Application(
                    applicationID,
                    postID,
                    jobPost.getJobTitle(),
                    resume,
                    questions,
                    responses,
                    new Date(),
                    Application.Status.UNDER_CONSIDERATION
            );

            applicationController.submitApplication(application, jobPost);
            JOptionPane.showMessageDialog(this, "Application submitted successfully!");
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting application: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JobPost dummyJob = new JobPost("1", "Software Engineer", "Entry-level development role", "recruiter1", "2025-04-01", "Tech Corp", "Remote", 70000);
        SwingUtilities.invokeLater(() -> new ApplicationFormUI(dummyJob, "testUser"));
    }
}
