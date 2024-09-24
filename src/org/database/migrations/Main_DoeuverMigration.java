package org.database.migrations;

import org.database.migration.Migration;

public class Main_DoeuverMigration extends Migration {

    protected final static String TABLE = "main_doeuvres";

    @Override
    public void create() {
        String sql =  "CREATE TABLE IF NOT EXISTS "+ TABLE +" (" +
                "id SERIAL PRIMARY KEY REFERENCES composants(id) ON DELETE CASCADE,"+
                "productivite_ouvrier DECIMAL(10, 2) NOT NULL," +
                "projet_id INT REFERENCES projets(id) ON DELETE CASCADE"+
                ") INHERITS (composants);";
        createTable(TABLE, sql);
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS " + TABLE + " CASCADE;";
        executeUpdate(sql, TABLE + " table dropped successfully.");
    }

    @Override
    public void dropType() {

    }
}
