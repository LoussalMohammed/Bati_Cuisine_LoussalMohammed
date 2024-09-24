package org.database.migrations;

import org.database.migration.Migration;

public class StatusProjetMigration extends Migration {

    protected final static String TYPE = "status_projet";

    @Override
    public void create() {
        createEnumType(TYPE, new String[] {"ENCOURS", "TERMINE", "ANNULE"});
    }

    @Override protected void dropTable() {

    }

    @Override
    public void dropType() {
        String sql = "DROP TYPE " + TYPE + ";";
        executeUpdate(sql, TYPE + " type dropped successfully.");
    }
}
