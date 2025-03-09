package ProfileManagement.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileEditInterface {

    public void showEditScreen(){
        System.out.println("Displaying edit screen:");

    }
    public String getUpdatedDescription(){
        return "Description updated";

    }
    public File getUpdatedResume(){
        return new File("updated_resume.pdf");

    }
    public List<String> getUpdatedSkills(){
        ArrayList<String> skills = new ArrayList<>();
        skills.add("Team Management");
        skills.add("Figma Design");
        return skills;

    }
    public File getUpdatedPicture(){
        return new File("updated_picture.png");

    }
}
