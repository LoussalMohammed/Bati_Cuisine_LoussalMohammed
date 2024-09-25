package org.app.tools;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {


    public static void main(String[] args) {

        DatabaseC db = null;

        try {
            db = DatabaseC.getInstance();
            Connection conn = db.getConnection();

            System.out.println("database connected....");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (db != null) {
                db.closeConnection();

            }
        }


    }


}