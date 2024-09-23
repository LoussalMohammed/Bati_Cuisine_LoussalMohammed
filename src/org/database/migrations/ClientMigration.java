package org.database.migrations;

import org.database.migration.Migration;

public class ClientMigration extends Migration {

    protected final static String TABLE = "clients";

    @Override
    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS "+ TABLE +" (" +
                "id SERIAL PRIMARY KEY," +
                "nom VARCHAR(255) NOT NULL," +
                "address VARCHAR(255) NOT NULL," + // Use the enum type here
                "phone VARCHAR(255) NOT NULL," +
                "estProfessionnel BOOLEAN DEFAULT FALSE NOT NULL,"+
                "remise DECIMAL(10, 2) DEFAULT NULL,"+
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
