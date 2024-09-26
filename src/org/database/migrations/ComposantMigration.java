package org.database.migrations;

import org.database.migration.Migration;

public class ComposantMigration extends Migration {

    protected final static String TABLE = "composants";

    @Override
    public void create() {
        String sql = "CREATE TABLE IF NOT EXISTS "+ TABLE +" (" +
                "id SERIAL PRIMARY KEY," +
                "nom VARCHAR(255) NOT NULL," +
                "type_composant type_composant NOT NULL," + // Use the enum type here
                "taux_tva DECIMAL(10, 2) NOT NULL," +
                "quantite DECIMAL(15, 4) NOT NULL,"+
                "coutUnitaire DECIMAL(15, 4) NOT NULL,"+
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
