package AuthenticationManagement;

import java.util.Date;

public class User {
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String email;
    private Date dob;
    private String userType;

    public User(String username, String first_name, String last_name, String password, String email, Date dob, String userType) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.userType = userType;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }
    public String getEmail() { return email; }
    public Date getDob() { return dob; }
    public String getUserType() { return userType; }

    // Setters
    public void setUserDetails(String username, String name, String password, String email, Date dob, String userType) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.userType = userType;
    }

}
