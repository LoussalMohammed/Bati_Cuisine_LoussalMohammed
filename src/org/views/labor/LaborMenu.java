package org.views.labor;

import lib.BC;
import lib.ScanInput;
import lib.YesNo;
import orgg.dao.labor.PgLaborDAO;
import orgg.entities.Labor;
import services.LaborService;


public class LaborMenu {
    private static PgLaborDAO model = new PgLaborDAO();
    private static LaborDisplay display = new LaborDisplay();
    private static LaborService laborService = new LaborService(model, display);

    public static void addLabor(int projectId) {
        System.out.println("\n--- Ajout de la main-d'œuvre ---");

        String checker;
        do {
            System.out.print("    Entrez le nom de main-d'œuvre  : ");
            String name = ScanInput.scanner.nextLine();

            System.out.print("\n    Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : ");
            String type = ScanInput.scanner.nextLine();

            System.out.print("\n    Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            double hourlyRate = ScanInput.scanner.nextDouble();
            BC.clean();

            System.out.print("\n    Entrez le nombre des main-d'œuvres : ");
            double workersNumber = ScanInput.scanner.nextDouble();
            BC.clean();

            System.out.print("\n    Entrez le nombre d'heures travaillées : ");
            double workHours = ScanInput.scanner.nextDouble();
            BC.clean();

            System.out.print("\n    Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
            double workerProductivity =  ScanInput.scanner.nextDouble();
            BC.clean();

            Labor labor = new Labor(0, projectId, name, workersNumber , type, hourlyRate, workHours, workerProductivity);

            laborService.addLabor(labor);
            System.out.println("Main-l'œuvre ajoutée avec succès !");

            checker = YesNo.displayAlert("Voulez-vous ajouter un autre type de main-d'œuvre ?");
        }while(checker.equals("y"));
    }
}
