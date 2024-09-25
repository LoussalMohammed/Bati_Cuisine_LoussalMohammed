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
        int option = PrincipalMenuView.getOption();

        switch(option) {
            case 1:
                projetService.createProjet();
                break;
            case 2:
        }
    }
}
