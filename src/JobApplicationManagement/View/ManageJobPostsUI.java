package JobApplicationManagement.View;

import JobApplicationManagement.Controller.ApplicationController;
import JobApplicationManagement.Model.Application;
import JobPostingManagement.Controller.PostController;
import JobPostingManagement.Model.JobPost;
import JobPostingManagement.View.PostView;
import Main.InterfaceRouter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageJobPostsUI extends JFrame {
    private JTable jobPostsTable;
    private JTable applicantsTable;
    private DefaultTableModel jobPostsModel;
    private DefaultTableModel applicantsModel;
    private ApplicationController controller;
    private JButton acceptButton;
    private JButton denyButton;
    private JButton logoutButton;
    private InterfaceRouter router;
    private PostController postController;
    private String recruiterUsername;

    public ManageJobPostsUI(InterfaceRouter router) {
        this.router = router;
        this.recruiterUsername = router.getCurrentUsername();
        this.postController = new PostController(router);
        this.controller = new ApplicationController(router);

        setTitle("Recruiter Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel with buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createNewPostButton = new JButton("Create New Job Post");
        logoutButton = new JButton("Logout");
        topPanel.add(createNewPostButton);
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        // Action listeners
        createNewPostButton.addActionListener(e -> {
            this.dispose();
            PostView postView = new PostView(postController, router);
            postView.setVisible(true);
        });
        logoutButton.addActionListener(e -> handleLogout());

        // Job posts table
        jobPostsModel = new DefaultTableModel(new String[]{"Title", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jobPostsTable = new JTable(jobPostsModel);
        JScrollPane jobPostsScroll = new JScrollPane(jobPostsTable);
        jobPostsScroll.setBorder(BorderFactory.createTitledBorder("Your Job Posts"));

        List<JobPost> jobPosts = postController.getJobPostsByRecruiter(recruiterUsername);
        for (JobPost jobPost : jobPosts) {
            jobPostsModel.addRow(new Object[]{jobPost.getJobTitle(), "Open"});
        }

        // Selection listener for job posts table
        jobPostsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jobPostsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedTitle = jobPostsModel.getValueAt(selectedRow, 0).toString();
                    refreshApplicationTable(selectedTitle);
                }
            }
        });

        // Applicants table
        applicantsModel = new DefaultTableModel(new String[]{"Post Title", "Resume", "Questions", "Question Response", "Date Completed", "Status", "Application ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        applicantsTable = new JTable(applicantsModel);
        applicantsTable.getColumnModel().getColumn(6).setMinWidth(0);
        applicantsTable.getColumnModel().getColumn(6).setMaxWidth(0);
        applicantsTable.getColumnModel().getColumn(6).setWidth(0);

        JScrollPane applicantsScroll = new JScrollPane(applicantsTable);
        applicantsScroll.setBorder(BorderFactory.createTitledBorder("Applicants"));

        // Button panel for accept/deny
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acceptButton = new JButton("Accept");
        denyButton = new JButton("Deny");
        buttonPanel.add(acceptButton);
        buttonPanel.add(denyButton);

        acceptButton.addActionListener(e -> handleAccept());
        denyButton.addActionListener(e -> handleDeny());

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jobPostsScroll, applicantsScroll);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void refreshApplicationTable(String jobTitle) {
        applicantsModel.setRowCount(0);
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid job title.");
            return;
        }

        try {
            List<Application> applications = controller.getApplicationsByJobTitle(jobTitle);
            if (applications.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No applications found for " + jobTitle);
            }
            for (Application app : applications) {
                applicantsModel.addRow(new Object[]{
                        app.getJobPostingTitle(),
                        app.getResume(),
                        app.getQuestions().toString(),
                        app.getQuestionResponses().toString(),
                        app.getDateCompleted(),
                        app.getStatus().toString(),
                        app.getApplicationId() // Store applicationId as String
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching applications: " + e.getMessage());
        }
    }

    private void handleAccept() {
        int selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an application.");
            return;
        }

        try {
            int modelRow = applicantsTable.convertRowIndexToModel(selectedRow);
            String applicationId = (String) applicantsModel.getValueAt(modelRow, 6); // Get applicationId
            controller.acceptApplication(applicationId);

            applicantsModel.setValueAt(Application.Status.ACCEPTED.toString(), modelRow, 5);
            JOptionPane.showMessageDialog(this, "Application accepted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error accepting application: " + e.getMessage());
        }
    }

    private void handleDeny() {
        int selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an application.");
            return;
        }

        try {
            int modelRow = applicantsTable.convertRowIndexToModel(selectedRow);
            String applicationId = (String) applicantsModel.getValueAt(modelRow, 6); // Get applicationId
            controller.denyApplication(applicationId);

            applicantsModel.setValueAt(Application.Status.DENIED.toString(), modelRow, 5);
            JOptionPane.showMessageDialog(this, "Application denied successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error denying application: " + e.getMessage());
        }
    }

    private void handleLogout() {
        this.dispose();
        router.showLoginInterface();
    }

    public void reloadJobPostsTable() {
        jobPostsModel.setRowCount(0);
        try {
            List<JobPost> recruiterPosts = postController.getJobPostsByRecruiter(recruiterUsername);
            for (JobPost post : recruiterPosts) {
                jobPostsModel.addRow(new Object[]{post.getJobTitle(), "Open"});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reloading job posts: " + e.getMessage());
        }
    }
}