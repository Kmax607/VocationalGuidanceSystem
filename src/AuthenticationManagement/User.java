package AuthenticationManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private String username;
    private String name;
    private String password;
    private String email;
    private Date dob;
    private String userType;

    public User(String username, String name, String password, String email, Date dob, String userType) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.userType = userType;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() {
        return dob;
    }

    public String getUserType() {
        return userType;
    }

    // Setters
    public void setUserDetails(String username, String name, String password, String email, Date dob, String userType) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.userType = userType;
    }



}
