package AuthenticationManagement;

public interface AuthMediator {
    void login(String username, String password);
    void register(User user);
    void logout();
}
