package JobApplicationManagement.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ManageJobPostsUI extends JFrame {
    private JTable jobPostsTable;
    private JTable applicantsTable;
    private DefaultTableModel jobPostsModel;
    private DefaultTableModel applicantsModel;

    public ManageJobPostsUI() {
        setTitle("Recruiter Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create post button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createNewPostButton = new JButton("Create New Job Post");
        topPanel.add(createNewPostButton);
        add(topPanel, BorderLayout.NORTH);

        //post panel
        jobPostsModel = new DefaultTableModel(new String[]{"Title", "Status", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jobPostsTable = new JTable(jobPostsModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                if (column == 2) {
                    JPanel panel = new JPanel(new GridLayout(1, 2));
                    JButton editViewButton = new JButton("Edit/View");
                    JButton promoteButton = new JButton("Promote");
                    panel.add(editViewButton);
                    panel.add(promoteButton);
                    return panel;
                }
                return super.prepareRenderer(renderer, row, column);
            }
        };
        JScrollPane jobPostsScroll = new JScrollPane(jobPostsTable);
        jobPostsScroll.setBorder(BorderFactory.createTitledBorder("Your Job Posts"));

        //applicant panel
        applicantsModel = new DefaultTableModel(new String[]{"Applicant Name", "Post Title", "Status", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //applicant table
        applicantsTable = new JTable(applicantsModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                if (column == 3) {
                    JPanel panel = new JPanel(new GridLayout(1, 2));
                    JButton acceptButton = new JButton("Accept");
                    JButton denyButton = new JButton("Deny");
                    panel.add(acceptButton);
                    panel.add(denyButton);
                    return panel;
                }
                return super.prepareRenderer(renderer, row, column);
            }
        };
        JScrollPane applicantsScroll = new JScrollPane(applicantsTable);
        applicantsScroll.setBorder(BorderFactory.createTitledBorder("Applicants"));

        //split
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jobPostsScroll, applicantsScroll);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        //fake inputs
        jobPostsModel.addRow(new Object[]{"Software Engineer", "Open", ""});
        jobPostsModel.addRow(new Object[]{"Product Manager", "Closed", ""});

        applicantsModel.addRow(new Object[]{"Alice", "Software Engineer", "Pending", ""});
        applicantsModel.addRow(new Object[]{"Bob", "Product Manager", "Pending", ""});

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManageJobPostsUI::new);
    }
}