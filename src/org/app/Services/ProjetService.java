package org.app.Services;

import org.app.Models.Entities.*;
import org.app.Models.Repositories.RepositoriesImplementation.*;
import org.views.projet.ProjetView;

import java.sql.SQLException;
import java.util.*;

public class ProjetService {

    public static final ClientRepositoryImpl clientModel = new ClientRepositoryImpl();

    public ProjetRepositoryImpl model;

    public static final MaterialRepositoryImpl materialModel = new MaterialRepositoryImpl();
    public static final Main_doeuverRepositoryImpl mainDoeuverModel = new Main_doeuverRepositoryImpl();
    public static final DevisRepositoryImpl devisModel = new DevisRepositoryImpl();
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
                    List<Material> materials = (List<Material>) projectComponents.get("material");
                    List<Main_Doeuver> main_doeuvers = (List<Main_Doeuver>) projectComponents.get("main_douver");
                    model.save(projet);
                    Optional<Projet> projetOptional = model.findById(model.getLastId());
                    if(projetOptional.isPresent()) {
                        Projet projet1 = projetOptional.get();
                        materials.stream()
                                .forEach(material -> {
                                    try {
                                        material.setProjetId(projet1.getId());
                                        materialModel.save(material);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                        main_doeuvers.stream()
                                .forEach(main_doeuver -> {
                                    try {
                                        main_doeuver.setProjetId(projet1.getId());
                                        mainDoeuverModel.save(main_doeuver);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                    }
                }
            }
        } else if(rcO == 2) {
            addClient();
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
                });
        main_doeuvers.stream()
                .forEach(main_doeuver -> {
                    main_doeuver.setTauxTVA(coutTVA);
                });
        projectComponents.put("material", materials);
        projectComponents.put("main_douver", main_doeuvers);


         return projectComponents;
    }

    public void afficherProjets() {
        Optional<List<Projet>> projets = model.getAll();
        view.afficherProjets(projets);
    }

    public void addClient() {
        view.addClient();
    }

    public void calculProjetCout() throws SQLException {
        int projetId = view.calculProjetCout();
        Optional<Devis> devisOptional = devisModel.findByProjet(projetId);
        if(devisOptional.isPresent()) {
            Devis devis = devisOptional.get();
            view.afficheDevis(devis);
        } else {
            view.noDevisFound();
        }
    }
}
