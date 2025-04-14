package AuthenticationManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class LoginInterface extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private LoginController controller;

    public LoginInterface() {
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        controller = new LoginController(this);

        mainPanel.add(buildRegisterPanel(), "register");
        mainPanel.add(buildLoginPanel(), "login");

        add(mainPanel);
        cardLayout.show(mainPanel, "register");
        setVisible(true);
    }

    private JPanel buildRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel baseFields = new JPanel(new GridLayout(3, 2, 10, 10));
        JPanel optionalFields = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField usernameField = new JTextField(15);
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField dobField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JTextField userTypeField = new JTextField(15);
        JButton registerButton = new JButton("Register");
        JButton switchToLogin = new JButton("Already have an account? Login");
        JButton moreOptionsButton = new JButton("More Options");

        baseFields.add(new JLabel("Username:")); baseFields.add(usernameField);
        baseFields.add(new JLabel("First Name:")); baseFields.add(firstNameField);
        baseFields.add(new JLabel("Last Name:")); baseFields.add(lastNameField);

        optionalFields.add(new JLabel("Email:")); optionalFields.add(emailField);
        optionalFields.add(new JLabel("Date of Birth (yyyy-mm-dd):")); optionalFields.add(dobField);
        optionalFields.add(new JLabel("User Type:")); optionalFields.add(userTypeField);
        optionalFields.add(new JLabel("Password:")); optionalFields.add(passwordField);
        optionalFields.setVisible(false);

        moreOptionsButton.addActionListener(e -> { // 👈 NEW BLOCK
            optionalFields.setVisible(!optionalFields.isVisible());
            moreOptionsButton.setText(optionalFields.isVisible() ? "Hide Options" : "More Options");
            this.revalidate();
            this.repaint();
    });


        registerButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String userType = userTypeField.getText();
                Date dob = java.sql.Date.valueOf(dobField.getText());

                User user = new User(username, firstName, lastName, password, email, dob, userType);
                controller.registerUser(user);
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Registration Error: " + ex.getMessage());
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(registerButton);
        buttonPanel.add(moreOptionsButton);
        buttonPanel.add(switchToLogin);
        panel.add(buttonPanel);

        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));
        combinedPanel.add(baseFields);
        combinedPanel.add(optionalFields);
        combinedPanel.add(buttonPanel);

        panel.add(combinedPanel, BorderLayout.CENTER);

        switchToLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    private JPanel buildLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField loginUsernameField = new JTextField(15);
        JPasswordField loginPasswordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton switchToRegister = new JButton("Need an account? Register");

        panel.add(new JLabel("Username:")); panel.add(loginUsernameField);
        panel.add(new JLabel("Password:")); panel.add(loginPasswordField);
        panel.add(loginButton); panel.add(switchToRegister);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());

            boolean valid = controller.validateLogin(username, password);
            if (valid) {
                JOptionPane.showMessageDialog(this, "Login successful!");

            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Invalid credentials.");
            }
        });

        switchToRegister.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        return panel;
    }

    public static void main(String[] args) {
        new LoginInterface();
    }
}
