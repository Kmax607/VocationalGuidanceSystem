package JobApplicationManagement.Model;

import java.util.ArrayList;

public class PreviousApplications {
    private ArrayList<Application> previousApplications;

    // Constructor initializes the list
    public PreviousApplications() {
        this.previousApplications = new ArrayList<>();
    }

    public void addApplication(Application application) {
        if (previousApplications == null) {
            previousApplications = new ArrayList<>();
        }
        previousApplications.add(application);
    }

    public ArrayList<Application> getApplications() {
        return previousApplications;
    }
}

