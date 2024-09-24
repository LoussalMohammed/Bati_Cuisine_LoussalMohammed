package org.views.projectCost;

import java.util.List;
import java.util.Map;

public class CostDisplay {

    public static void displayProjectCost(Map<String, Object> projectInfo) {
        System.out.println("--- Détail des Coûts ---");

        /*
         Project infos
         */
        Map<String, Object> project = (Map<String, Object>) projectInfo.get("projectInfo");
        String projectName = (String) project.get("name");
        Double profitMargin = (Double) project.get("profitMargin");
        Double vatRate = (Double) project.get("vatRate");
        String clientName = (String) project.get("userName");

        System.out.println("--- Résultat du Calcul ---");
        System.out.println("Nom du projet : " + projectName);
        if(clientName != null) System.out.println("Client : " + clientName);
        System.out.println("Adresse du chantier : ");
        System.out.println("Surface : ");

        /*
         Materials calculation
         */
        System.out.println("1. Matériaux : ");
        Double allMaterialsTotalCost = 0.0;
        List<Map<String, Object>> materials = (List<Map<String, Object>>) projectInfo.get("materials");
        for (Map<String, Object> material : materials) {
            String name = (String) material.get("name");
            Double quantity = (Double) material.get("quantity");
            Double unitCost = (Double) material.get("unitCost");
            Double transportCost = (Double) material.get("transportCost");
            Double qualityCoefficient = (Double) material.get("qualityCoefficient");

            // Calculate the material cost correctly:
            // Cost = (quantity * unitCost * qualityCoefficient) + transportCost
            Double individualMaterialTotalCost = (quantity * unitCost * qualityCoefficient) + transportCost;
            System.out.println("-" + name + ": " + String.format("%.2f", individualMaterialTotalCost) + "€ (quantité: " + quantity + " m², coût unitaire : " + unitCost + "€/m², qualité: " + qualityCoefficient + ", transport: " + transportCost + "€)");
            allMaterialsTotalCost += individualMaterialTotalCost;
        }
        System.out.println("**Coût total des matériaux avant TVA : " + String.format("%.2f", allMaterialsTotalCost));
        Double materialsCostWithVAT = allMaterialsTotalCost * (1 + vatRate / 100);
        System.out.println("**Coût total des matériaux avec TVA (" + vatRate + "%) : " + String.format("%.2f", materialsCostWithVAT));

        /*
         Labor calculation
         */
        System.out.println("2. Main-d'œuvre : ");
        Double allLaborsTotalCost = 0.0;
        List<Map<String, Object>> labors = (List<Map<String, Object>>) projectInfo.get("labors");
        for (Map<String, Object> labor : labors) {
            String name = (String) labor.get("name");
            Double quantity = (Double) labor.get("quantity");
            Double hourlyRate = (Double) labor.get("hourlyRate");
            Double workHours = (Double) labor.get("workHours");
            Double workerProductivity = (Double) labor.get("workerProductivity");

            // Calculate the labor cost correctly:
            // Cost = quantity * (hoursWorked * hourlyRate * productivity)
            Double individualLaborTotalCost = quantity * (workHours * hourlyRate * workerProductivity);
            System.out.println("-" + name + ": " + String.format("%.2f", individualLaborTotalCost) + "€ (le nombre de main d'œuvres: " + quantity + " , taux horaire : " + hourlyRate + "€/h, productivité : " + workerProductivity + ", heures travaillées : " + workHours + "h)");
            allLaborsTotalCost += individualLaborTotalCost;
        }
        System.out.println("**Coût total de la main-d'œuvre avant TVA : " + String.format("%.2f", allLaborsTotalCost) + "€");
        Double laborCostWithVAT = allLaborsTotalCost * (1 + vatRate / 100);
        System.out.println("**Coût total de la main-d'œuvre avec TVA (" + vatRate + "%) : " + String.format("%.2f", laborCostWithVAT));

        /*
         Total cost and profit
         */
        Double totalCostBeforeMargin = materialsCostWithVAT + laborCostWithVAT;
        System.out.println("3. Coût total avant marge : " + String.format("%.2f", totalCostBeforeMargin) + " €");
        Double profit = totalCostBeforeMargin * (profitMargin / 100);
        System.out.println("4. Marge bénéficiaire (" + profitMargin + "%): " + String.format("%.2f", profit));

        Double finalTotalCost = totalCostBeforeMargin + profit;
        System.out.println("5. Coût total: " + String.format("%.2f", finalTotalCost) + " €");
    }
}
