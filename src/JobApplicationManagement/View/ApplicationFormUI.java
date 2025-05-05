package JobApplicationManagement.View;

import JobApplicationManagement.Controller.ApplicationController;
import JobApplicationManagement.Model.Application;
import JobPostingManagement.Model.JobPost;
import Main.InterfaceRouter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationFormUI extends JFrame {
    private JTextField applicationIDField;
    private JTextField resumeField;
    private JButton browseButton;
    private JTextArea questionsArea;
    private JTextArea responsesArea;
    private JButton submitButton;
    private InterfaceRouter router;
    private ApplicationController applicationController;
    private JobPost jobPost;
    private String username;

    public ApplicationFormUI(JobPost jobPost, String username, InterfaceRouter router) {
        this.jobPost = jobPost;
        this.username = username;
        this.router = router;
        this.applicationController = new ApplicationController(router);

        setTitle("Submit Application for: " + jobPost.getJobTitle());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        applicationIDField = new JTextField();
        resumeField = new JTextField("Select resume file...");
        resumeField.setEditable(false); // Prevent manual editing of the file path
        browseButton = new JButton("Browse...");
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Resume File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                resumeField.setText(selectedFile.getAbsolutePath());
            }
        });
        questionsArea = new JTextArea("Enter questions separated by new lines", 5, 20);
        responsesArea = new JTextArea("Enter responses in same order as questions", 5, 20);

        submitButton = new JButton("Submit Application");
        submitButton.addActionListener(this::handleSubmit);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            try {
                this.dispose();
                router.showJobSearchInterface();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Navigation error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Application ID:"));
        panel.add(applicationIDField);
        panel.add(new JLabel("Resume File:"));
        panel.add(resumeField);
        panel.add(new JLabel(""));
        panel.add(browseButton);
        panel.add(new JLabel("Questions:"));
        panel.add(new JScrollPane(questionsArea));
        panel.add(new JLabel("Responses:"));
        panel.add(new JScrollPane(responsesArea));
        panel.add(new JLabel(""));
        panel.add(submitButton);
        panel.add(new JLabel(""));
        panel.add(backButton);

        add(panel);
    }

    private void handleSubmit(ActionEvent e) {
        try {
            String applicationIDText = applicationIDField.getText().trim();
            String resume = resumeField.getText().trim();
            String questionsText = questionsArea.getText().trim();
            String responsesText = responsesArea.getText().trim();

            // Validate inputs
            if (applicationIDText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Application ID cannot be empty.");
                return;
            }
            if (resume.isEmpty() || resume.equals("Select resume file...")) {
                JOptionPane.showMessageDialog(this, "Please select a resume file.");
                return;
            }
            // Optional: Validate that the file exists
            File resumeFile = new File(resume);
            if (!resumeFile.exists()) {
                JOptionPane.showMessageDialog(this, "Resume file does not exist: " + resume);
                return;
            }
            if (questionsText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Questions cannot be empty.");
                return;
            }
            if (responsesText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Responses cannot be empty.");
                return;
            }

            int applicationID = Integer.parseInt(applicationIDText);
            String postID = jobPost.getPostID();

            String[] questionsSplit = questionsText.split("\n");
            String[] responsesSplit = responsesText.split("\n");

            if (questionsSplit.length != responsesSplit.length) {
                JOptionPane.showMessageDialog(this, "Questions and responses count must match.");
                return;
            }

            ArrayList<String> questions = new ArrayList<>();
            ArrayList<String> responses = new ArrayList<>();

            for (String q : questionsSplit) questions.add(q.trim());
            for (String r : responsesSplit) responses.add(r.trim());

            // Create the Application with the correct username
            Application application = new Application(
                    applicationID,
                    postID,
                    username,
                    jobPost.getJobTitle(),
                    resume,
                    questions,
                    responses,
                    new Date(),
                    Application.Status.UNDER_CONSIDERATION
            );

            applicationController.submitApplication(application, username);
            JOptionPane.showMessageDialog(this, "Application submitted successfully!");
            this.dispose();
            router.showUserApplicationsInterface();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Application ID format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error submitting application: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}