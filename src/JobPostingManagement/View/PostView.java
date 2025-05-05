package JobPostingManagement.View;

import JobApplicationManagement.Model.Application;
import JobPostingManagement.Controller.PostController;
import JobPostingManagement.Model.JobPost;
import Main.InterfaceRouter;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PostView extends JFrame {
    private JTextField postIDField, titleField, descriptionField, recruiterField, dateField, companyField, locationField, salaryField;
    private JButton submitButton;
    private JButton backButton;
    private final PostController controller;
    private InterfaceRouter router;

    public PostView(PostController controller, InterfaceRouter router) {
        this.router = router;
        this.controller = controller;
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        setTitle("Create Job Post");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildUI();
    }

    private void buildUI() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        postIDField = new JTextField("Enter Post ID");
        titleField = new JTextField("Enter Job Title");
        descriptionField = new JTextField("Enter Description");
        recruiterField = new JTextField(router.getCurrentUsername());
        recruiterField.setEditable(false);
        dateField = new JTextField("YYYY-MM-DD");
        companyField = new JTextField("Enter Company Name");
        locationField = new JTextField("Enter Location");
        salaryField = new JTextField("Enter Salary");

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            try {
                controller.showManageJobPostsInterface();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Navigation error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        bottomPanel.add(backButton);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                String postID = postIDField.getText().trim();
                String title = titleField.getText().trim();
                String description = descriptionField.getText().trim();
                String recruiter = recruiterField.getText().trim();
                String date = dateField.getText().trim();
                String company = companyField.getText().trim();
                String location = locationField.getText().trim();
                String salaryText = salaryField.getText().trim();

                // Validate inputs
                if (postID.isEmpty()) {
                    throw new IllegalArgumentException("Post ID cannot be empty.");
                }
                if (title.isEmpty() || description.isEmpty() || recruiter.isEmpty() || date.isEmpty() ||
                        company.isEmpty() || location.isEmpty() || salaryText.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }

                // Validate date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(date);
                } catch (ParseException ex) {
                    throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
                }

                // Parse and validate salary
                double salary = Double.parseDouble(salaryText);
                if (salary < 0) {
                    throw new IllegalArgumentException("Salary cannot be negative.");
                }

                // Create a new JobPost with an empty applications list
                ArrayList<Application> applications = new ArrayList<>();
                JobPost newPost = new JobPost(postID, title, description, recruiter, date, company, location, salary, applications);
                controller.createJobPost(newPost);
                JOptionPane.showMessageDialog(this, "Job Post Created!");
                this.dispose();
                controller.showManageJobPostsInterface();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Salary format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to create Job Post: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Post ID:")); panel.add(postIDField);
        panel.add(new JLabel("Job Title:")); panel.add(titleField);
        panel.add(new JLabel("Description:")); panel.add(descriptionField);
        panel.add(new JLabel("Recruiter:")); panel.add(recruiterField);
        panel.add(new JLabel("Date:")); panel.add(dateField);
        panel.add(new JLabel("Company:")); panel.add(companyField);
        panel.add(new JLabel("Location:")); panel.add(locationField);
        panel.add(new JLabel("Salary:")); panel.add(salaryField);
        panel.add(new JLabel()); panel.add(submitButton);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH); // Fixed typo: changed 'bottomPanel' to 'bottomPanel'
    }
}