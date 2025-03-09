package JobApplicationManagement.Model;

import java.util.ArrayList;

public class PreviousApplications {
    ArrayList<Application> applicationsSent = new ArrayList<>();

    public PreviousApplications() {
    }

    public void addApplication(Application application) {
        applicationsSent.add(application);
    }

    public void deleteApplication(Application application) {
        applicationsSent.remove(application);
    }

}
