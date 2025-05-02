package AuthenticationManagement;

import JobApplicationManagement.View.ManageApplicationsUI;
import Main.InterfaceRouter;

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
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField dobField;
    private JPasswordField passwordField;
    private JTextField userTypeField;
    private JButton registerButton;

    private JTextField[] fields;
    private String[] placeholders;
    private InterfaceRouter router;

    public LoginInterface(InterfaceRouter router) {
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        this.router = router;
        this.controller = new LoginController(this, router);

        mainPanel.add(buildLoginPanel(), "login");
        mainPanel.add(buildRegisterPanel(), "register");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
        setVisible(true);
    }
    private JPanel buildRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel baseFields = new JPanel(new GridLayout(3, 2, 10, 10));
        JPanel optionalFields = new JPanel(new GridLayout(4, 2, 10, 10));

        usernameField = new JTextField("Enter your username", 15);
        firstNameField = new JTextField("Enter first name", 15);
        lastNameField = new JTextField("Enter last name", 15);
        emailField = new JTextField("Enter email", 15);
        dobField = new JTextField("yyyy-mm-dd", 15);
        passwordField = new JPasswordField("password", 15);
        userTypeField = new JTextField("Enter user type", 15);
        registerButton = new JButton("Need an account? Register");

        fields = new JTextField[]{usernameField, firstNameField, lastNameField, emailField, dobField, userTypeField};
        placeholders = new String[]{"Enter your username", "Enter first name", "Enter last name", "Enter email", "yyyy-mm-dd", "Enter user type"};

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
        optionalFields.add(new JLabel("User Type:"));
        JComboBox<String> userTypeField = new JComboBox<>(new String[] { "job seeker", "recruiter" });
        optionalFields.add(userTypeField);
        optionalFields.add(new JLabel("Password:")); optionalFields.add(passwordField);

        registerButton.setEnabled(false);

        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(new SimpleDocumentListener() {
                public void update() {
                    validateForm();
                }
            });
        }

        passwordField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            public void update() {
                validateForm();
            }
        });

        JButton switchToLogin = new JButton("Already have an account? Login");

        registerButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String userType = (String) userTypeField.getSelectedItem();
                Date dob = java.sql.Date.valueOf(dobField.getText());

                User user = new User(username, firstName, lastName, password, email, dob, userType);
                controller.registerUser(user);
                JOptionPane.showMessageDialog(this, "User registered successfully!");

                // After registration, go to the login screen
                cardLayout.show(mainPanel, "login");

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

    private void validateForm() {
        boolean allValid = true;

        String pwd = String.valueOf(passwordField.getPassword());
        if (pwd.isEmpty() || pwd.equals("password")) allValid = false;
        registerButton.setEnabled(allValid);
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
                router.setCurrentUsername(username);
                JOptionPane.showMessageDialog(this, "Login successful!");

                String type = controller.getUserType(username, password);

                if (type.equals("recruiter")) {
                    this.dispose();
                    controller.routeToManageJobPosts(username);
                } else if (type.equals("job seeker")) {
                    this.dispose();
                    controller.routeToJobListings(username);
                } else {
                    JOptionPane.showMessageDialog(this, "Unknown user type.");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Invalid credentials.");
            }
        });

        switchToRegister.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        return panel;
    }

    public void showLoginError(String error) {
        JOptionPane.showMessageDialog(this, error);
    }

    @FunctionalInterface
    interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
        void update();

        @Override
        default void insertUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        @Override
        default void removeUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        @Override
        default void changedUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }
    }
}
