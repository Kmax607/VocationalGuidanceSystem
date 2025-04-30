package AuthenticationManagement;

import JobApplicationManagement.View.ManageApplicationsUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

        JTextField usernameField = new JTextField("Enter your username", 15);
        JTextField firstNameField = new JTextField("Enter first name", 15);
        JTextField lastNameField = new JTextField("Enter last name", 15);
        JTextField emailField = new JTextField("Enter email", 15);
        JTextField dobField = new JTextField("yyyy-mm-dd", 15);
        JPasswordField passwordField = new JPasswordField("password", 15);
        JTextField userTypeField = new JTextField("Enter user type", 15);
        JButton registerButton = new JButton("Register");
        JButton switchToLogin = new JButton("Already have an account? Login");

        JTextField[] fields = {usernameField, firstNameField, lastNameField, emailField, dobField, userTypeField};
        String[] placeholders = {"Enter your username", "Enter first name", "Enter last name", "Enter email", "yyyy-mm-dd", "Enter user type"};

        for (int i = 0; i < fields.length; i++) {
            final int index = i;
            fields[i].addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (fields[index].getText().equals(placeholders[index])) {
                        fields[index].setText("");
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (fields[index].getText().isEmpty()) {
                        fields[index].setText(placeholders[index]);
                    }
                }
            });
        }

        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("password")) {
                    passwordField.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("password");
                }
            }
        });

        baseFields.add(new JLabel("Username:")); baseFields.add(usernameField);
        baseFields.add(new JLabel("First Name:")); baseFields.add(firstNameField);
        baseFields.add(new JLabel("Last Name:")); baseFields.add(lastNameField);

        optionalFields.add(new JLabel("Email:")); optionalFields.add(emailField);
        optionalFields.add(new JLabel("Date of Birth (yyyy-mm-dd):")); optionalFields.add(dobField);
        optionalFields.add(new JLabel("User Type:")); optionalFields.add(userTypeField);
        optionalFields.add(new JLabel("Password:")); optionalFields.add(passwordField);

        registerButton.setEnabled(false);

        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
                boolean allValid = true;
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].getText().isEmpty() || fields[i].getText().equals(placeholders[i])) {
                        allValid = false;
                        break;
                    }
                }
                String pwd = String.valueOf(passwordField.getPassword());
                if (pwd.isEmpty() || pwd.equals("password")) allValid = false;
                registerButton.setEnabled(allValid);
            }));
        }

        passwordField.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            boolean allValid = true;
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getText().isEmpty() || fields[i].getText().equals(placeholders[i])) {
                    allValid = false;
                    break;
                }
            }
            String pwd = String.valueOf(passwordField.getPassword());
            if (pwd.isEmpty() || pwd.equals("password")) allValid = false;
            registerButton.setEnabled(allValid);
        }));

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
        buttonPanel.add(switchToLogin);

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

                if (controller.getCurrentUser().getUserType().equalsIgnoreCase("candidate")) {
                    new ManageApplicationsUI();
                }

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

    public void showLoginError(String error) {
        JOptionPane.showMessageDialog(this, error);
    }

    interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
        void update();
        @Override default void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override default void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override default void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
    }
}
