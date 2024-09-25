package org.database.Triggers;

import org.database.Trigger.Trigger;

public class DevisTrigger extends Trigger {

    @Override
    protected void create() {

    }

    @Override
    public void createFunction() {
        String sql = "CREATE OR REPLACE FUNCTION add_Devis_On_Projet()\n" +
                "RETURNS TRIGGER AS $$ \n" +
                "BEGIN\n" +
                "   INSERT INTO devis (montantEstime, projet_id, dateEstime) VALUES (NEW.coutTotal, NEW.id, CURRENT_TIMESTAMP);" +
                "   RETURN NEW;" +
                "END;" +
                "$$ LANGUAGE plpgsql;";
        createFunctionE("add_Devis_On_Projet", sql);
    }
    @Override
    public void createTrigger() {
        String sql = "CREATE TRIGGER trigger_add_devis\n" +
                "AFTER INSERT ON projets\n" +
                "FOR EACH ROW\n" +
                "EXECUTE FUNCTION add_Devis_On_Projet();";
        createTriggerE("trigger_add_devis", sql);
    }

}
