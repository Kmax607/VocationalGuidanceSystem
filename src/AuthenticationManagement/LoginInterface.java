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
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));

        JTextField usernameField = new JTextField("Enter your username", 15);
        JTextField firstNameField = new JTextField("Enter first name", 15);
        JTextField lastNameField = new JTextField("Enter last name", 15);
        JTextField emailField = new JTextField("Enter email", 15);
        JTextField dobField = new JTextField("yyyy-mm-dd", 15);
        JPasswordField passwordField = new JPasswordField("password", 15);
        JTextField userTypeField = new JTextField("Enter user type", 15);
        JButton registerButton = new JButton("Register");
        JButton switchToLogin = new JButton("Already have an account? Login");

        panel.add(new JLabel("Username:")); panel.add(usernameField);
        panel.add(new JLabel("First Name:")); panel.add(firstNameField);
        panel.add(new JLabel("Last Name:")); panel.add(lastNameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Date of Birth (yyyy-mm-dd):")); panel.add(dobField);
        panel.add(new JLabel("Password:")); panel.add(passwordField);
        panel.add(new JLabel("User Type:")); panel.add(userTypeField);
        panel.add(registerButton); panel.add(switchToLogin);

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

        switchToLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    private JPanel buildLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField loginUsernameField = new JTextField("Enter your username", 15);
        JPasswordField loginPasswordField = new JPasswordField("Enter password", 15);
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
