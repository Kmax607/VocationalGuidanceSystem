package ProfileManagement.Controller;

import java.io.File;
import java.util.ArrayList;

import ProfileManagement.Model.Profile;
import ProfileManagement.View.ProfileEditInterface;

public class UpdateProfile {
    private Profile profile;
    private ProfileEditInterface editInterface;
    
    public UpdateProfile(Profile profile, ProfileEditInterface editInterface) {
        this.profile = profile;
        this.editInterface = editInterface;
    }


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
    }

    public void processEdit() {
        //simulates getting data from the view and updating the profile
        String newDescription = editInterface.getUpdatedDescription();
        File newResume = editInterface.getUpdatedResume();
        ArrayList<String> newSkills = (ArrayList<String>) editInterface.getUpdatedSkills();
        File newPicture = editInterface.getUpdatedPicture();

        updateDescription(newDescription);
        updateResume(newResume);
        updateSkills(newSkills);
        updatePicture(newPicture);
    }

    public Profile getProfile() {
        return profile;
    }


}

//we can decide if we want these in one function and separated by if/else or not. I think this way might be better.