package org.app.Services;

import org.app.Models.Entities.*;
import org.app.Models.Repositories.RepositoriesImplementation.*;
import org.views.projet.ProjetView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
                        obtenirValidationStetusDevis();

                    }
                }
            }
        } else if(rcO == 2) {
            Client client = addClient();
            clientAjouter(client);
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

    public Client addClient() {
        int clientId = clientModel.getLastId()+1;
        Map<String, Object> clientMap = view.addClient();

        Client client = new Client(clientId, (String) clientMap.get("nom"), (String) clientMap.get("address"),
                (String) clientMap.get("phone"),
                (boolean) clientMap.get("estProfessionnel"),
                (double) clientMap.get("remise"));
        try {
            clientModel.save(client);
            return clientModel.findById(clientId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
     }

    public void calculProjetCout() throws SQLException {
        Optional<List<Devis>> devisOptional = devisModel.getAll();

        if(devisOptional.isPresent() && !devisOptional.get().isEmpty()) {
            List<Devis> devis = devisOptional.get();

            AtomicInteger count = new AtomicInteger(1);
            devis.stream()
                    .forEach(devisE -> {
                        System.out.println("devis " + count + ":");
                        view.afficheDevis(devisE);  // Check if this method works properly
                        count.getAndIncrement();
                    });
        } else {
            view.noDevisFound();
        }
    }


    public void clientAjouter(Client client) {
        view.clientAjouter(client);
    }

    public void obtenirValidationStetusDevis() throws SQLException {
        Map<String, Object> devisStatus = view.devisStatus();
        Optional<Devis> dernierDevisOptional = devisModel.findById(devisModel.findLastId());
        if(dernierDevisOptional.isPresent()) {
            Devis dernierDevis = dernierDevisOptional.get();
            dernierDevis.setAcceptStatus((boolean) devisStatus.get("accept"));
            dernierDevis.setDateValidite((LocalDateTime) devisStatus.get("dateValidite"));
            devisModel.update(dernierDevis);
        } else {
            view.noDevisFound();
        }

    }
}
