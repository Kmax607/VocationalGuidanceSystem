package AuthenticationManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class LoginInterface extends JFrame {

    private JTextField usernameField = new JTextField(15);
    private JTextField firstNameField = new JTextField(15);
    private JTextField lastNameField = new JTextField(15);
    private JTextField emailField = new JTextField(15);
    private JTextField dobField = new JTextField(15); // Format: yyyy-mm-dd
    private JPasswordField passwordField = new JPasswordField(15);
    private JTextField userTypeField = new JTextField(15);
    private JButton registerButton = new JButton("Register");

    private LoginController controller;

    public LoginInterface() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("Username:")); add(usernameField);
        add(new JLabel("First Name:")); add(firstNameField);
        add(new JLabel("Last Name:")); add(lastNameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Date of Birth (yyyy-mm-dd):")); add(dobField);
        add(new JLabel("Password:")); add(passwordField);
        add(new JLabel("User Type:")); add(userTypeField);
        add(new JLabel("")); add(registerButton);

        registerButton.addActionListener(e -> registerUser());
        controller = new LoginController(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerUser() {
        try {
            String username = usernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String userType = userTypeField.getText();
            Date dob = java.sql.Date.valueOf(dobField.getText()); // yyyy-mm-dd

            User user = new User(username, firstName, lastName, password, email, dob, userType);
            controller.registerUser(user);

            JOptionPane.showMessageDialog(this, "User registered successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginInterface();
    }
}
