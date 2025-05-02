package JobPostingManagement.View;

import JobApplicationManagement.View.ManageJobPostsUI;
import JobPostingManagement.Controller.PostController;
import JobPostingManagement.Model.JobPost;
import Main.InterfaceRouter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PostView extends JFrame {
    private JTextField postIDField, titleField, descriptionField, recruiterField, dateField, companyField, locationField, salaryField;
    private JButton submitButton;
    private JButton backButton;
    private final PostController controller;
    private InterfaceRouter router;

    public PostView(PostController controller) {
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

        postIDField = new JTextField("Enter Post ID");
        titleField = new JTextField("Enter Job Title");
        descriptionField = new JTextField("Enter Description");
        recruiterField = new JTextField("Enter Recruiter");
        dateField = new JTextField("YYYY-MM-DD");
        companyField = new JTextField("Enter Company Name");
        locationField = new JTextField("Enter Location");
        salaryField = new JTextField("Enter Salary");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            if (controller != null) {
                controller.showManageJobPostsInterface();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Navigation error: Controller not available",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
        submitButton = new JButton("Submit");


        panel.add(new JLabel("Post ID:")); panel.add(postIDField);
        panel.add(new JLabel("Job Title:")); panel.add(titleField);
        panel.add(new JLabel("Description:")); panel.add(descriptionField);
        panel.add(new JLabel("Recruiter:")); panel.add(recruiterField);
        panel.add(new JLabel("Date:")); panel.add(dateField);
        panel.add(new JLabel("Company:")); panel.add(companyField);
        panel.add(new JLabel("Location:")); panel.add(locationField);
        panel.add(new JLabel("Salary:")); panel.add(salaryField);
        panel.add(submitButton);

        add(panel);


        submitButton.addActionListener((ActionEvent e) -> {
            try {
                String postID = postIDField.getText();
                String title = titleField.getText();
                String description = descriptionField.getText();
                String recruiter = recruiterField.getText();
                String date = dateField.getText();
                String company = companyField.getText();
                String location = locationField.getText();
                double salary = Double.parseDouble(salaryField.getText());

                JobPost newPost = new JobPost(postID, title, description, recruiter, date, company, location, salary);
                boolean success = PostController.createJobPost(newPost);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Job Post Created!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create Job Post.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void handleBack() {
        try {
            this.dispose();
            controller.showManageJobPostsInterface();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Navigation error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            // Fallback - at least close the window
            this.dispose();
        }
    }
}
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(PostView::new);
//    }
//}
