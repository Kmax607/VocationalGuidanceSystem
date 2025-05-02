package JobApplicationManagement.View;

import JobApplicationManagement.Controller.ApplicationController;
import JobApplicationManagement.Model.Application;
import JobPostingManagement.Controller.PostController;
import JobPostingManagement.Model.JobPost;
import JobPostingManagement.View.PostView;
import Main.InterfaceRouter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private JButton applicationsButton;
    private InterfaceRouter router;
    private PostController postController;
    private String recruiterUsername;

    public ManageJobPostsUI(InterfaceRouter router) {
        this.router = router;
        this.recruiterUsername = recruiterUsername;
        this.postController = new PostController(router);
        setTitle("Recruiter Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.controller = new ApplicationController(router);

        // Create post button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createNewPostButton = new JButton("Create New Job Post");
        topPanel.add(createNewPostButton);
        add(topPanel, BorderLayout.NORTH);
        // ActionListener for creating a new job post
//        createNewPostButton.addActionListener(e -> {
//            // Show the PostView for creating a new job post
//            PostController postController = new PostController();  // Pass router to the constructor
//            PostView postView = new PostView(this);
//            postView.setVisible(true);  // Make it visible
//        });

        // Logout button
        logoutButton = new JButton("Logout");
        topPanel.add(logoutButton);
        logoutButton.addActionListener(e -> handleLogout());
        createNewPostButton.addActionListener(e -> {
            this.dispose();  // Close current window
            PostView postView = new PostView(postController);  // Pass the controller
            postView.setVisible(true);
        });

        // Job post panel
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

        // Applicant panel
        applicantsModel = new DefaultTableModel(new String[]{"Post Title", "Resume", "Questions", "Question Response", "Date Completed", "Status", "Hidden Row"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Applicant table with button column for accept/deny (this is just for rendering)
        applicantsTable = new JTable(applicantsModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                if (column == 6) {
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Select a row and press Accept or Deny"));
                    return panel;
                }
                return super.prepareRenderer(renderer, row, column);
            }
        };
        applicantsTable.getColumnModel().getColumn(6).setMinWidth(0);
        applicantsTable.getColumnModel().getColumn(6).setMaxWidth(0);
        applicantsTable.getColumnModel().getColumn(6).setWidth(0);

        JScrollPane applicantsScroll = new JScrollPane(applicantsTable);
        applicantsScroll.setBorder(BorderFactory.createTitledBorder("Applicants"));

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

        List<Application> applications = controller.getAllApplications();
        for (Application app : applications) {
            applicantsModel.addRow(new Object[]{
                    app.getJobPostingTitle(),
                    app.getResume(),
                    app.getQuestions().toString(),
                    app.getQuestionResponses().toString(),
                    app.getDateCompleted(),
                    app.getStatus().toString(),
                    app
            });
        }
        List<JobPost> recruiterPosts = postController.getJobPostsByRecruiter(recruiterUsername);
        for (JobPost post : recruiterPosts) {
            jobPostsModel.addRow(new Object[]{post.getJobTitle(), post.getStatus()});
        }

        setVisible(true);
    }

    private void handleAccept() {
        int selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = applicantsTable.convertRowIndexToModel(selectedRow);
            Application app = (Application) applicantsModel.getValueAt(modelRow, 6);  // Get the application from the model
            controller.acceptApplication(app);

            applicantsModel.setValueAt(Application.Status.ACCEPTED.toString(), modelRow, 5);

            System.out.println("Application Accepted!");
        }
    }

    private void handleDeny() {
        int selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = applicantsTable.convertRowIndexToModel(selectedRow);
            Application app = (Application) applicantsModel.getValueAt(modelRow, 6);  // Get the application from the model
            controller.denyApplication(app);

            applicantsModel.setValueAt(Application.Status.DENIED.toString(), modelRow, 5);

            System.out.println("Application Denied!");
        }
    }

    private void handleLogout() {
        this.dispose();
        controller.routeToLogin();
    }



}
