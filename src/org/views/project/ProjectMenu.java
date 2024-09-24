package org.views.project;

import lib.ScanInput;
import lib.BC;
import lib.YesNo;
import UI.client.ClientMenu;
import orgg.dao.cost.PgCostDAO;
import orgg.dao.project.PgProjectDAO;
import services.ProjectService;
import orgg.entities.Project;
import orgg.enums.ProjectStatus;
import UI.material.MaterialMenu;
import UI.labor.LaborMenu;
import UI.projectCost.CostMenu;
import UI.projectCost.CostDisplay;
import services.CostService;

public class ProjectMenu {
    static private PgProjectDAO model = new PgProjectDAO();
    static private ProjectDisplay display = new ProjectDisplay();
    static ProjectService projectService = new ProjectService(model, display);

    static private PgCostDAO costModel = new PgCostDAO();
    static private CostDisplay costDisplay = new CostDisplay();
    static CostService costService = new CostService(costModel, costDisplay);

    public static void displayMenu() {
        int option;
        do {
            System.out.println("--- Recherche de client ---");
            System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
            System.out.println("    1. Chercher un client existant");
            System.out.println("    2. Ajouter un nouveau client");
            System.out.println("    3. retour");
            System.out.print("\nChoisissez une option : ");
            option = ScanInput.scanner.nextInt();
            ScanInput.scanner.nextLine();

            int clientId;
            switch(option) {
                case 1:
                     clientId = ClientMenu.searchClients();
                    if(clientId != 0) {
                        if(continueProcess()) addProject(clientId);
                    }
                    break;
                case 2:
                     clientId = ClientMenu.addClient();
                     if(continueProcess()) addProject(clientId);
                    break;
                case 3:
                    System.out.println("    ...");
                default:
                    System.out.println("    Aucune option pour le chiffre saisie.");
                    break;
            }
        }while(option != 3);
    }

    public static boolean continueProcess() {
        String choise = YesNo.displayAlert("Souhaitez-vous continuer avec ce client ?");
        if(choise.equals("y")) return true;
        else return false;
    }

    public static void addProject(Integer clientId) {
        System.out.println("");
        if(clientId == null) System.out.println("--- Infos à propos de Projet ---");
        else System.out.println("--- Création d'un Nouveau Projet ---");
        System.out.print("    Entrez le nom du projet : ");
        String projectName = ScanInput.scanner.nextLine();
        System.out.print("\n    Entrez la surface de de l'espace (en m²) : ");
        Double surface = ScanInput.scanner.nextDouble();
        BC.clean();

        Project project = new Project(0, clientId, projectName, null, null, ProjectStatus.PENDING, surface, null);
        int projectId = projectService.addProject(project);

        /*
        Adding Materials to the project
         */
        MaterialMenu.addMaterial(projectId);

        /*
        Adding Labors to the project
         */
        LaborMenu.addLabor(projectId);

        /*
        Applaying VTA and Profit to the project
         */
        CostMenu.APPLY_VTA_AND_PROFIT(projectId);

        /*
        Project cost displaying
         */
        costService.DISPLAY_PROJECT_COST(projectId);
    }
}