package JobPostingManagement;

import JobPostingManagement.Model.JobPost;

public interface Facade {
    void createPost(JobPost jobPost);
    void validatePost(JobPost jobPost);
}
