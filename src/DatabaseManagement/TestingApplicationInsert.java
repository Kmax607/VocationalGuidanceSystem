package DatabaseManagement;


import JobApplicationManagement.Model.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TestingApplicationInsert {

    public static void main(String[] args) {

        Application dummyApp = new Application(
                1,
                1001,
                "Junior Developer",
                "resume.pdf",
                new ArrayList<>(Arrays.asList("Why do you want this job?")),
                new ArrayList<>(Arrays.asList("Because I love coding!")),
                new Date(),
                Application.Status.UNDER_CONSIDERATION
        );

        Application dummyApp2 = new Application(
                2,
                200,
                "WebDev",
                "resume.pdf",
                new ArrayList<>(Arrays.asList("Do you require visa sponsorship?")),
                new ArrayList<>(Arrays.asList("No")),
                new Date(),
                Application.Status.UNDER_CONSIDERATION
        );

        ApplicationRepository.insertApplication(dummyApp);
        ApplicationRepository.insertApplication(dummyApp2);
    }

}
