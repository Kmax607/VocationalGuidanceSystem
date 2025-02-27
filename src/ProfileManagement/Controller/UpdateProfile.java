package ProfileManagement.Controller;

import java.io.File;
import java.util.ArrayList;

import ProfileManagement.Model.Profile;

public class UpdateProfile {
    Profile profile;


public void updateDescription(String newDescription) {
    profile.setDescription(newDescription);
}

public void updateResume(File newResume) {
    profile.setResume(newResume);
}

public void updateSkills(ArrayList<String> newSkills) {
    profile.setSkills(newSkills);
}

public void updatePicture(File newPicture) {
    profile.setPicture(newPicture);
}

public boolean saveProfile() {
    return profile.getDescription() != null && profile.getResume() != null && profile.getSkills() != null && profile.getPicture() != null;
}}

//we can decide if we want these in one function and separated by if/else or not. I think this way might be better.