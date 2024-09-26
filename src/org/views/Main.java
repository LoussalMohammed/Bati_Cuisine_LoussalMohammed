package org.views;

import org.app.Models.Repositories.RepositoriesImplementation.ProjetRepositoryImpl;
import org.app.Services.ProjetService;
import org.views.PricipalMenu.PrincipalMenuView;
import org.views.projet.ProjetView;

import java.sql.SQLException;

public class Main {
    public static ProjetRepositoryImpl model = new ProjetRepositoryImpl();
    public static ProjetView view = new ProjetView();

    public final static ProjetService projetService = new ProjetService(model, view);

    public static void main(String[] args) throws SQLException {
        boolean continueSelecter = true;
        while(continueSelecter) {
            int option = PrincipalMenuView.getOption();

            switch (option) {
                case 1 -> projetService.createProjet();
                case 2 -> projetService.afficherProjets();
                case 3 -> projetService.calculProjetCout();
                case 0 -> {
                    continueSelecter = false;
                    System.out.println("Exiter la process de selection.");
                }
                default -> System.out.println("option Invalid. s'il te plait selecter un nomber entre 0 est 3.");
            }
        }
    }
}
