package JobApplicationManagement.View;

import javax.swing.*;
import java.awt.*;

public class PromoteUI extends JFrame {

    public PromoteUI() {
        setTitle("");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel jobNameLabel = new JLabel("Software Engineer", SwingConstants.CENTER);
        jobNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        jobNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup group = new ButtonGroup();
        JRadioButton tenBoosts = new JRadioButton("High tier promotion");
        JRadioButton fiveBoosts = new JRadioButton("Medium tier promotion");
        JRadioButton oneBoost = new JRadioButton("Low tier promotion");

        tenBoosts.setSelected(true);

        group.add(tenBoosts);
        group.add(fiveBoosts);
        group.add(oneBoost);

        centerPanel.add(jobNameLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(tenBoosts);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(fiveBoosts);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(oneBoost);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton backButton = new JButton("Back");
        JButton promoteButton = new JButton("Promote");
        bottomPanel.add(backButton);
        bottomPanel.add(promoteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PromoteUI::new);
    }
}
