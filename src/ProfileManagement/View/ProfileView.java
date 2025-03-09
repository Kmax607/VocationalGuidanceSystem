package ProfileManagement.View;

import ProfileManagement.Model.Profile;

public class ProfileView {
    
    private Profile profile;
    
    
    public void displayProfile(){
        System.out.println("Uesr Profile Display:");
        System.out.println("Description: " + profile.getDescription());
        System.out.println("Resume: " + (profile.getResume() != null ? profile.getResume().getName() : "None"));
        System.out.println("Skills: " + profile.getSkills());
        System.out.println("Picture: " + (profile.getPicture() != null ? profile.getPicture().getName() : "None"));

    }
    public void refreshProfileData(){
        System.out.println("Refreshing profile data...");
        displayProfile();
    }
}
