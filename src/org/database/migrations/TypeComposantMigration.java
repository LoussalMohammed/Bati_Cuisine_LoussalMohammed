package org.database.migrations;

import org.database.migration.Migration;

public class TypeComposantMigration extends Migration {

    protected final static String TYPE = "type_composant";

    @Override
    public void create() {
        createEnumType(TYPE, new String[] {"MARERIAL", "MAINDOEUVER"});
    }

    @Override protected void dropTable() {

    }

    @Override
    public void dropType() {
        String sql = "DROP TYPE " + TYPE + ";";
        executeUpdate(sql, TYPE + " type dropped successfully.");
    }


}
