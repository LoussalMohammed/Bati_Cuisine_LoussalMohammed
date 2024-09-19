package org.app.Models.Repositories.RepositoriesImplementations;

import org.app.Models.Entities.User;
import org.app.Models.Enums.UserType;
import org.app.Models.Helpers.PasswordUtil;
import org.app.Tools.databaseC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepositoryImpl {

    public User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND deleted_at IS NULL";

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("hashedPassword");
                    if(PasswordUtil.checkPassword(password, hashedPassword)) {
                        // Create Admin object from the retrieved data
                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getString("email"),
                                resultSet.getString("phone"),
                                UserType.fromString(resultSet.getString("userType")),
                                resultSet.getString("hashedPassword"),  // This should be adjusted if storing hashed passwords
                                resultSet.getTimestamp("created_at").toLocalDateTime()
                        );
                    } else {
                        // No matching admin found
                        return null;
                    }
                }
                return null;


            }
        } catch (SQLException e) {
            // Handle SQL exception (e.g., log the error)
            System.err.println("SQL error during authentication: " + e.getMessage());
            throw e;  // Rethrow or handle the exception as needed
        }
    }

}
