package org.app.Services;

import org.app.Models.Entities.Client;
import org.app.Models.Entities.Main_Doeuver;
import org.app.Models.Entities.Material;
import org.app.Models.Entities.Projet;
import org.app.Models.Repositories.RepositoriesImplementation.ClientRepositoryImpl;
import org.app.Models.Repositories.RepositoriesImplementation.Main_doeuverRepositoryImpl;
import org.app.Models.Repositories.RepositoriesImplementation.MaterialRepositoryImpl;
import org.app.Models.Repositories.RepositoriesImplementation.ProjetRepositoryImpl;
import org.views.projet.ProjetView;

import java.sql.SQLException;
import java.util.*;

public class ProjetService {

    public static final ClientRepositoryImpl clientModel = new ClientRepositoryImpl();

    public ProjetRepositoryImpl model;

    public static final MaterialRepositoryImpl materialModel = new MaterialRepositoryImpl();
    public static final Main_doeuverRepositoryImpl mainDoeuverModel = new Main_doeuverRepositoryImpl();
    public ProjetView view;

    public ProjetService(ProjetRepositoryImpl model, ProjetView view) {
        this.model = model;
        this.view = view;
    }

    public void createProjet() throws SQLException {
        int rcO = recherchClientOptions();
        if(rcO == 1) {
            String clientNom = view.recherchClientExistant();
            Optional<Client> client = clientModel.getClientByName(clientNom);
            if(client.isPresent()) {
                Client client1 = client.get();
                String answer = clientTrouver(client1);
                if(answer.equalsIgnoreCase("y")) {
                    Map<String, Object> projectComponents = creationProjet(client1);
                    Double surfaceCuisine = (Double) projectComponents.get("surfaceCuisine");
                    Projet projet = view.afficheProjet((Projet) projectComponents.get("projet"), surfaceCuisine);
                    model.save(projet);
                }
            }
        } else if(rcO == 2) {

        }
    }

    public int recherchClientOptions() {
        return view.recherchClientOptions();
    }

    public String clientTrouver(Client client) {
        return view.clientTrouver(client);
    }

    public Map<String, Object> creationProjet(Client client) throws SQLException {
        int projectId = model.getLastId() + 1;
        int materialId = materialModel.findLastId() + 1;
        int mainDouverId = mainDoeuverModel.findLastId() + 1;

         Map<String, Object> projectComponents = view.creationProjet(projectId, materialId, mainDouverId, client);
         Double coutTVA = (Double) projectComponents.get("coutTVA");
         Projet projet = (Projet) projectComponents.get("projet");
         List<Material> materials = (List<Material>) projectComponents.get("material");
         List<Main_Doeuver> main_doeuvers = (List<Main_Doeuver>)  projectComponents.get("main_douver");
        materials.stream()
                .forEach(material -> {
                    material.setTauxTVA(coutTVA);
                    try {
                        materialModel.save(material);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
        main_doeuvers.stream()
                .forEach(main_doeuver -> {
                    main_doeuver.setTauxTVA(coutTVA);
                    try {
                        mainDoeuverModel.save(main_doeuver);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });


         return projectComponents;
    }

    public void afficherProjets() {
        Optional<List<Projet>> projets = model.getAll();
        view.afficherProjets(projets);
    }
}
