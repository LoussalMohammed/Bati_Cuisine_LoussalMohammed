package org.database.migrations;

import org.database.migration.Migration;

public class ProjetsMigration extends Migration {

    protected final static String TABLE = "projets";

    @Override
    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE+" (" +
                "id SERIAL PRIMARY KEY," +
                "nom VARCHAR(255) NOT NULL,"+
                "margeBeneficiaire VARCHAR(255) NOT NULL," +
                "coutTotal DECIMAL(10, 2) NOT NULL," + // Use the enum type here
                "etatProjet status_projet NOT NULL DEFAULT 'ENCOURS'::status_projet," +
                "clientId INT NOT NULL,"+
                "FOREIGN KEY (clientId) REFERENCES clients(id),"+
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
