package JobSearchingManagement.Model;

public class JobListing {
    private String title;
    private String company;
    private String location;

    public JobListing(String title, String company, String location) {
        this.title = title;
        this.company = company;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return title + " at " + company + " (" + location + ")";
    }
}
