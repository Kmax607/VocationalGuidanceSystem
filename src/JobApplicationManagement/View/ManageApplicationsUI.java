package JobApplicationManagement.View;

import DatabaseManagement.ApplicationRepository;
import JobApplicationManagement.Model.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageApplicationsUI extends JFrame {
    private JTable applicationTable;
    private DefaultTableModel tableModel;
    private String currentUsername;

    public ManageApplicationsUI(String username) {
        this.currentUsername = username;

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
    }

    private void loadApplications() {
        List<Application> apps = ApplicationRepository.getApplicationsByUsername(currentUsername);

        for (Application app : apps) {
            tableModel.addRow(new Object[]{
                    app.getPostID(),
                    app.getJobPostingTitle(),
                    app.getResume(),
                    app.getDateCompleted(),
                    app.getStatus().toString()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageApplicationsUI("testUser"));
    }
}

