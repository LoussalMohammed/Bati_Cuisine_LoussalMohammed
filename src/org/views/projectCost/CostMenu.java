package org.views.projectCost;

import lib.ScanInput;
import lib.BC;
import lib.YesNo;
import services.CostService;
import orgg.dao.cost.PgCostDAO;

public class CostMenu {
    private static PgCostDAO model = new PgCostDAO();
    private static CostDisplay display = new CostDisplay();

    private static CostService costService = new CostService(model, display);

    public static void APPLY_VTA_AND_PROFIT(int projectId) {
        System.out.println("\n--- Calcul du coût total ---");

        String applyVta = YesNo.displayAlert("Souhaitez-vous appliquer une TVA au projet ?");
        Double vtaValue = null;
        if(applyVta.equals("y")) {
            System.out.print("Entrez le pourcentage de TVA (%) : ");
            vtaValue = ScanInput.scanner.nextDouble();
            BC.clean();
        }

        String applyProfitMargin = YesNo.displayAlert("Souhaitez-vous appliquer une marge bénéficiaire au projet ?");
        Double profitMarginValue = null;
        if(applyProfitMargin.equals("y")) {
            System.out.print("Entrez le pourcentage de marge bénéficiaire (%): ");
            profitMarginValue = ScanInput.scanner.nextDouble();
            BC.clean();
        }

        System.out.println("Calcul du coût en cours...");

        costService.APPLY_VTA_AND_PROFIT(vtaValue, profitMarginValue, projectId);
    }
}