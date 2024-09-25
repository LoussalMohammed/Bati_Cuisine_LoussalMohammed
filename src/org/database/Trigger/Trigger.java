package org.database.Trigger;

import org.app.tools.DatabaseC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract public class  Trigger {

    /**
     * Executes a SQL update statement.
     * @param sql The SQL statement to execute.
     * @param successMessage The message to print upon successful execution.
     */
    protected void executeUpdate(String sql, String successMessage) {
        try (Connection conn = DatabaseC.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println(successMessage);
        } catch (SQLException e) {
            System.err.println("Error executing SQL: " + sql);
            e.printStackTrace();
        }
    }

    /**
     * Creates a trigger in the database.
     * @param trigger The name of the trigger created.
     * @param sql The SQL statement defining the trigger structure.
     */
    protected void createTriggerE(String trigger, String sql) {
        String successMessage = trigger + " function created successfully.";
        executeUpdate(sql, successMessage);
    }

    /**
     * Creates a function in the database.
     * @param function The name of the function created.
     * @param sql The SQL statement defining the function structure.
     */
    protected void createFunctionE(String function, String sql) {
        String successMessage = function + " trigger created successfully.";
        executeUpdate(sql, successMessage);
    }

    /**
     * Abstract method to be implemented by subclasses for specific migrations.
     */
    protected abstract void create();
    protected abstract void createFunction();
    protected abstract void createTrigger();

}
