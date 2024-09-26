package org.app.Commands;

import org.database.Triggers.ComposantTrigger;
import org.database.Triggers.DevisTrigger;
import org.database.migrations.*;

public class CommandsRunner {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide a migration name to run.");
            return;
        }

        String command = args[0];
        switch (command.toLowerCase()) {
            case "create-project-status" -> new StatusProjetMigration().create();
            case "create-type-composant" -> new TypeComposantMigration().create();
            case "create-clients" -> new ClientMigration().create();
            case "create-projets" -> new ProjetsMigration().create();
            case "create-composants" -> new ComposantMigration().create();
            case "create-materials" -> new MaterialMigration().create();
            case "create-main-doeuver" -> new Main_DoeuverMigration().create();
            case "create-devis" -> new DevisMigration().create();
            case "create-devis-function" -> new DevisTrigger().createFunction();
            case "create-devis-trigger" -> new DevisTrigger().createTrigger();
            case "create-composant-function" -> new ComposantTrigger().createFunction();
            case "create-composant-trigger" -> new ComposantTrigger().createTrigger();

            case "drop-project-status" -> new StatusProjetMigration().dropType();
            case "drop-type-composant" -> new TypeComposantMigration().dropType();
            case "drop-clients" -> new ClientMigration().dropTable();
            case "drop-projets" -> new ProjetsMigration().dropTable();
            case "drop-composants" -> new ComposantMigration().dropTable();
            case "drop-materials" -> new MaterialMigration().dropTable();
            case "drop-main-doeuver" -> new Main_DoeuverMigration().dropTable();
            case "drop-devis" -> new DevisMigration().dropTable();


            // Add more migrations here as needed
            default ->
                    System.out.println("Invalid command. Available commands: create-clients, create-projets, create-composants, etc.");
        }
    }
}
