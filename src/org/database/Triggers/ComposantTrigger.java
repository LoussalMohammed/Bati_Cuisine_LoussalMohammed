package org.database.Triggers;

import org.database.Trigger.Trigger;

public class ComposantTrigger extends Trigger {

    @Override
    protected void create() {

    }

    @Override
    public void createFunction() {
        String sql = "CREATE OR REPLACE FUNCTION insert_composant_on_material_insert()\n" +
                "RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "    IF NOT EXISTS (SELECT 1 FROM composants WHERE id = NEW.id) THEN\n" +
                "        INSERT INTO composants (id, nom, type_composant, taux_tva, quantite, coutUnitaire, projet_id)\n" +
                "        VALUES (NEW.id, NEW.nom, NEW.type_composant, NEW.taux_tva, NEW.quantite, NEW.coutUnitaire, NEW.projet_id);" +
                "    END IF;" +
                "\n" +
                "    RETURN NEW;" +
                "END;" +
                "$$ LANGUAGE plpgsql;";
        createFunctionE("insert_composant_on_material_insert", sql);
    }

    @Override
    public void createTrigger() {
        String sql = "CREATE TRIGGER trigger_composant_insert\n" +
                "BEFORE INSERT ON materials\n" +
                "FOR EACH ROW\n" +
                "EXECUTE FUNCTION insert_composant_on_material_insert();";
        createTriggerE("trigger_composant_insert", sql);
    }
}
