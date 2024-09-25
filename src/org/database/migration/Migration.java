package org.database.migration;

import org.app.tools.DatabaseC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Migration {

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
     * Creates a table in the database.
     * @param table The name of the table to create.
     * @param sql The SQL statement defining the table structure.
     */
    protected void createTable(String table, String sql) {
        String successMessage = table + " created successfully.";
        executeUpdate(sql, successMessage);
    }

    /**
     * Abstract method to be implemented by subclasses for specific migrations.
     */
    protected abstract void create();

    /**
     * Creates an enum type in the database.
     * @param typeName The name of the enum type to create.
     * @param values The possible values of the enum type.
     */
    protected void createEnumType(String typeName, String[] values) {
        String sql = generateEnumTypeSQL(typeName, values);
        executeUpdate(sql, "Enum type " + typeName + " created successfully.");
    }

    /**
     * Generates the SQL for creating an enum type.
     * @param typeName The name of the enum type.
     * @param values The values for the enum type.
     * @return The SQL statement for creating the enum type.
     */
    private String generateEnumTypeSQL(String typeName, String[] values) {
        StringBuilder sql = new StringBuilder("CREATE TYPE " + typeName + " AS ENUM (");
        for (int i = 0; i < values.length; i++) {
            sql.append("'").append(values[i]).append("'");
            if (i < values.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(");");
        return sql.toString();
    }

    /**
     * Drops a table from the database.
     */
    protected abstract void dropTable();

    /**
     * Drops a type from the database.
     */
    protected abstract void dropType();


    /**
     * Drops a column from a specified table.
     * @param table The name of the table.
     * @param column The name of the column to drop.
     */
    protected void dropColumn(String table, String column) {
        String sql = "ALTER TABLE " + table + " DROP COLUMN " + column;
        executeUpdate(sql, "Column " + column + " dropped from table " + table + " successfully.");
    }


}
