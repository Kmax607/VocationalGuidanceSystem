package JobSearchingManagement.View;

import JobPostingManagement.Model.JobPost;
import JobSearchingManagement.Controller.SearchController;
import Main.InterfaceRouter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JobView extends JFrame {
    private InterfaceRouter router;
    private SearchController controller;
    private String recruiterUsername;

    public JobView(InterfaceRouter router) {
        this.controller = new SearchController(this, router);
        this.router = router;
        this.recruiterUsername = recruiterUsername;
        setTitle("Available Jobs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        List<JobPost> jobList = controller.getJobsByRecruiter(recruiterUsername);

        JPanel jobPanel = new JPanel();
        jobPanel.setLayout(new BoxLayout(jobPanel, BoxLayout.Y_AXIS));
        jobPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (JobPost job : jobList) {
            JPanel jobCard = new JPanel();
            jobCard.setLayout(new BoxLayout(jobCard, BoxLayout.Y_AXIS));
            jobCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(job.getJobTitle()),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            jobCard.setAlignmentX(Component.LEFT_ALIGNMENT);
            jobCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

            jobCard.add(new JLabel("Company: " + job.getCompany()));
            jobCard.add(new JLabel("Location: " + job.getLocation()));
            jobCard.add(new JLabel("Salary: $" + job.getSalary()));
            jobCard.add(new JLabel("Posted on: " + job.getDate()));
            jobCard.add(new JLabel("Description: " + job.getPostDescription()));

            JButton applyButton = new JButton("Apply");
            applyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            applyButton.addActionListener(e -> handleApply(job));
            jobCard.add(Box.createRigidArea(new Dimension(0, 5)));
            jobCard.add(applyButton);

            jobPanel.add(jobCard);
            jobPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(jobPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            controller.routeToLogin();
        });

        JButton applicationsButton = new JButton("See Applications");
        applicationsButton.addActionListener(e -> {
            controller.routeToApplications();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutButton);
        bottomPanel.add(applicationsButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleApply(JobPost job) {
        JOptionPane.showMessageDialog(this,
                "You applied to: " + job.getJobTitle() + " at " + job.getCompany(),
                "Application Submitted",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
