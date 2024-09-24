package org.database.migrations;

import org.database.migration.Migration;

public class DevisMigration extends Migration {

    protected final static String TABLE = "devis";

    @Override
    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS "+ TABLE +" (" +
                "id SERIAL PRIMARY KEY," +
                "montantEstime DECIMAL(10, 2) DEFAULT NULL," +
                "dateEstime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "dateValidite TIMESTAMP DEFAULT NULL," +
                "projet_id INT REFERENCES projets(id) ON DELETE CASCADE,"+
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,"+
                "deleted_at TIMESTAMP DEFAULT NULL"+
                ");";
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
