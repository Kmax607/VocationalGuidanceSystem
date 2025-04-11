import java.sql.*;

// to be used as an example for when adding database connection
// connectivity should be done in the model class

public class MySQL_DB {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/user_auth",
                    "root",
                    "vocationalguidance"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while(resultSet.next()) {
                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("last_name"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
