package ProfileManagement.Model;

import java.io.File;
import java.util.ArrayList;

public class Profile {
    private String description;
    private File resume; //PDF
    private ArrayList<String> skills;
    private File picture; //PNG

public Profile(String description, File resume, ArrayList<String> skills, File picture) {
    this.description = description;
    this.resume = resume;
    this.skills = skills;
    this.picture = picture;
}

// Getters
public String getDescription() {
    return description;
}

public File getResume() {
    return resume;
}

public ArrayList<String> getSkills() {
    return skills;
}

public File getPicture() {
    return picture;
}

// Setters
public void setDescription(String description) {
    this.description = description;
}

public void setResume(File resume) {
    this.resume = resume;
}

public void setSkills(ArrayList<String> skills) {
    this.skills = skills;
}

public void setPicture(File picture) {
    this.picture = picture;
}
}
