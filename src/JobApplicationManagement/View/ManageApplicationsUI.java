package JobApplicationManagement.View;

import JobApplicationManagement.Controller.ApplicationController;
import JobApplicationManagement.Model.Application;
import Main.InterfaceRouter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageApplicationsUI extends JFrame {
    private JTable applicationTable;
    private DefaultTableModel tableModel;
    private ApplicationController controller;
    private JButton backButton;
    private InterfaceRouter router;

    public ManageApplicationsUI(InterfaceRouter router) {
        if (router == null) {
            throw new IllegalArgumentException("Router cannot be null");
        }
        this.router = router;
        this.controller = new ApplicationController(router);

        setTitle("My Applications");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadApplications();

        setVisible(true);
    }

    private void initUI() {
        tableModel = new DefaultTableModel(new String[]{"Post ID", "Job Title", "Resume", "Date Completed", "Status", "Application ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        applicationTable = new JTable(tableModel);
        applicationTable.setEnabled(false);
        applicationTable.getColumnModel().getColumn(5).setMinWidth(0);
        applicationTable.getColumnModel().getColumn(5).setMaxWidth(0);
        applicationTable.getColumnModel().getColumn(5).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(applicationTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        backButton.addActionListener(e -> handleBack());
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadApplications() {
        try {
            List<Application> apps = controller.getApplicationsByUsername(router.getCurrentUsername());
            if (apps != null && !apps.isEmpty()) {
                for (Application app : apps) {
                    tableModel.addRow(new Object[]{
                            app.getPostID(),
                            app.getJobPostingTitle(),
                            app.getResume(),
                            app.getDateCompleted(),
                            app.getStatus().toString(),
                            app.getApplicationId() // Hidden column for applicationId
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "No applications found for this user.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading applications: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBack() {
        this.dispose();
        router.showJobSearchInterface(); // Use router directly for consistency
    }
}