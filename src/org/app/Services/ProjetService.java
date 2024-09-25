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
         Projet projet = (Projet) projectComponents.get("projet");
         Material material = (Material) projectComponents.get("material");
         Main_Doeuver main_doeuver = (Main_Doeuver) projectComponents.get("main_douver");
         model.save(projet);
         materialModel.save(material);
         mainDoeuverModel.save(main_doeuver);

         return projectComponents;
    }
}
