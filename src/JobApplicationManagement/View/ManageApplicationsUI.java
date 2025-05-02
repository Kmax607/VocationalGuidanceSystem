package JobApplicationManagement.View;

import DatabaseManagement.ApplicationRepository;
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

    public ManageApplicationsUI(InterfaceRouter router) {
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
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Post ID", "Job Title", "Resume", "Date Completed", "Status"});

        applicationTable = new JTable(tableModel);
        applicationTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(applicationTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(e -> handleBack());
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadApplications() {
        List<Application> apps = controller.getAllApplications();

        if (apps != null && !apps.isEmpty()) {
            for (Application app : apps) {
                tableModel.addRow(new Object[]{
                        app.getPostID(),
                        app.getJobPostingTitle(),
                        app.getResume(),
                        app.getDateCompleted(),
                        app.getStatus().toString()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No applications found for this user.", "No Data", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleBack() {
        controller.routeToJobPostings();
    }


}

