package org.views.projet;

import org.app.Models.Entities.*;
import org.app.Models.Enums.StatusProjet;
import org.app.Models.Enums.TypeComposant;
import org.app.Models.Helpers.InputValidator;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class ProjetView {

    public static final Scanner scanner = new Scanner(System.in);

public int recherchClientOptions() {
    int option;
    System.out.println("--- Recherche de client ---");
    do {
    System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
    System.out.println("    1. Chercher un client existant");
    System.out.println("    2. Ajouter un nouveau client");
    option = scanner.nextInt();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    } while(option < 1 || option > 2);
    System.out.println("Choisissez une option :"+ option);

    return option;
}

public String recherchClientExistant() {
    System.out.println("--- Recherche de client existant ---");
    String clientNom;
    do{
        System.out.println("    Entrez le nom du client : ");
        clientNom = scanner.nextLine();
    } while(!InputValidator.validateName(clientNom));

    return clientNom;
}

public String clientTrouver(Client client) {
    String answer;
    System.out.println("    Client trouvé!");
    System.out.println("    Nom: "+ client.getNom());
    System.out.println("    Addresse: "+ client.getAddress());
    System.out.println("    Numéro de téléphone : "+ client.getPhone());

    do {
        System.out.println("Souhaitez-vous continuer avec ce client ? (y/n) :");
        answer = scanner.nextLine();
    } while(!InputValidator.validateYN(answer));

    return answer;
}

public Map<String, Object> creationProjet(int projetId, int materialId, int mainDouver, Client client) {
    String projetNom;
    double surfaceCuisine;
    System.out.println("--- Création d'un Nouveau Projet ---");
    do {
        System.out.println("    Entrez le nom du projet :");
        projetNom = scanner.nextLine();
        System.out.println("    Entrez la surface de la cuisine (en m²) : ");
        surfaceCuisine = scanner.nextDouble();
    } while(!InputValidator.validateName(projetNom) && surfaceCuisine > 0.00);
    List<Material> materials = askCreationMaterial(materialId);
    List<Main_Doeuver> main_doeuvers = askCreationMainDouver(mainDouver);

    List<Composant> composants = new ArrayList<>();
    composants.addAll(materials);
    composants.addAll(main_doeuvers);
    Map<String, Object> total = calculTotal();
    Projet projet = new Projet(projetId, projetNom, (Double) total.get("margeBeneficiaire"), 0.0, StatusProjet.ENCOURS, composants, client);


    HashMap<String, Object> projectComponents = new HashMap<>();
    projectComponents.put("surfaceCuisine", surfaceCuisine);
    projectComponents.put("coutTVA", total.get("coutTVA"));
    projectComponents.put("projet", projet);
    projectComponents.put("material", materials);
    projectComponents.put("main_douver", main_doeuvers);

    return projectComponents;
}

public Map<String, Object> calculTotal() {
    String answer;
    double coutTVA = 0.00;
    double margeBenificiaire = 0.00;
    System.out.println("--- Calcul du coût total ---");
    do {
        System.out.println("Souhaitez-vous appliquer une TVA au projet ? (y/n) :");
        answer = scanner.nextLine();
    } while(!InputValidator.validateYN(answer));

    if(answer.equalsIgnoreCase("y")) {
        do {
            System.out.println("Entrez le pourcentage de TVA (%) :");
            coutTVA = scanner.nextDouble();
            if(scanner.hasNextLine()) {
                scanner.nextLine();
            }

        } while(coutTVA < 0);
    }

    answer = "";
    do {
        System.out.println("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) :");
        answer = scanner.nextLine();
    } while(!InputValidator.validateYN(answer));

    if(answer.equalsIgnoreCase("y")) {
        do {
            System.out.println("Entrez le pourcentage de marge bénéficiaire (%) :");
            margeBenificiaire = scanner.nextDouble();
            if(scanner.hasNextLine()) {
                scanner.nextLine();
            }

        } while(margeBenificiaire < 0);
    }

    Map<String, Object> total = new HashMap<>();
    total.put("coutTVA", coutTVA);
    total.put("margeBeneficiaire", margeBenificiaire);


    System.out.println("Calcul du coût en cours...");

    return total;
}

public Material ajoutMaterial(int materialId) {
    String materialNom;
    double quantite;
    double coutUnitaire;
    double coutTransport;
    double coefficientQualite;

    System.out.println("--- Ajout des matériaux ---");

    System.out.println("    Entrez le nom du matériau :");
    materialNom = scanner.nextLine();
    System.out.println("    Entrez la quantité de ce matériau (en m²) :");
    quantite = scanner.nextDouble();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    System.out.println("    Entrez le coût unitaire de ce matériau (€/m²) :");
    coutUnitaire = scanner.nextDouble();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    System.out.println("    Entrez le coût de transport de ce matériau (€) :");
    coutTransport = scanner.nextDouble();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    do {
        System.out.println("    Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) :");
        coefficientQualite = scanner.nextDouble();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    } while(coefficientQualite < 1.0 && coefficientQualite > 2.0);

    System.out.println("Matériau ajouté avec succès !");

    return new Material(materialId, materialNom, TypeComposant.MATERIAL, quantite, coutUnitaire, 0.00,coutTransport, coefficientQualite, null);
}

public List<Material> askCreationMaterial(int materialId) {
    String answer;
    List<Material> materials = new ArrayList<>();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    do {
        materials.add(ajoutMaterial(materialId));
        System.out.println("Voulez-vous ajouter un autre matériau ? (y/n) :");
        answer = scanner.nextLine();
    } while(!InputValidator.validateYN(answer) && answer.equalsIgnoreCase("y"));

    return materials;
}



public Main_Doeuver ajoutMainDoeuver(int mainDouverId) {
    int typeOuvrier;
    String mainDouverNom;
    double coutUnitaire;
    double quantite;
    double produciviteOuvrier;

    System.out.println("--- Ajout de la main-d'œuvre ---");
    do{
        System.out.println("    Entrez le type de main-d'œuvre (e.g., Ouvrier de base -> 1, Spécialiste -> 2) :");
        typeOuvrier = scanner.nextInt();
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
    } while(typeOuvrier < 1 || typeOuvrier > 2);

    do{
        System.out.println("    Entrez le nom de main-d'œuvre :");
        mainDouverNom = scanner.nextLine();
    } while(!InputValidator.validateName(mainDouverNom));

    System.out.println("    Entrez le taux horaire de cette main-d'œuvre (€/h) :");
    coutUnitaire = scanner.nextDouble();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    System.out.println("    Entrez le nombre d'heures travaillées :");
    quantite = scanner.nextDouble();
    if(scanner.hasNextLine()) {
        scanner.nextLine();
    }
    do {
        System.out.println("    Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : 1");
        produciviteOuvrier = scanner.nextDouble();
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
    } while(produciviteOuvrier < 1.0 || produciviteOuvrier > 2.0);
    System.out.println("Main-d'œuvre ajoutée avec succès !");

    return new Main_Doeuver(
        mainDouverId, mainDouverNom, TypeComposant.MAINDOEUVER, quantite, coutUnitaire, 0.00, produciviteOuvrier, null);
}

    public List<Main_Doeuver> askCreationMainDouver(int mainDouverId) {
        String answer;
        List<Main_Doeuver> main_doeuvers = new ArrayList<>();
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }
        do {
            main_doeuvers.add(ajoutMainDoeuver(mainDouverId));
            System.out.println("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) :");
            answer = scanner.nextLine();
        } while(!InputValidator.validateYN(answer) && answer.equalsIgnoreCase("y"));

        return main_doeuvers;
    }

    public Projet afficheProjet(Projet projet, Double surfaceCuisine) {
        System.out.println("--- Résultat du Calcul ---");
        System.out.println("Nom du projet :"+ projet.getNomProjet());
        System.out.println("Client :"+ projet.getClient().getNom());
        System.out.println("Adresse du chantier :"+ projet.getClient().getAddress());
        System.out.println("Surface :"+ surfaceCuisine);
        System.out.println("--- Détail des Coûts ---");
        List<Material> materials = projet.getComposants().stream()
                .filter(composant -> composant.getTypeComposant().equals(TypeComposant.MATERIAL))
                .map(composant -> (Material) composant)
                .toList();

        List<Main_Doeuver> main_doeuvers = projet.getComposants().stream()
                .filter(composant -> composant.getTypeComposant().equals(TypeComposant.MAINDOEUVER))
                .map(composant -> (Main_Doeuver) composant)
                .toList();

        double tvaRate = materials.get(0).getTauxTVA();

        double materialsTotalCostBeforeTVA = materials.stream()
                .mapToDouble(Material::calculeCout)
                .sum();

        System.out.println("1. Matériaux :");
        materials.stream()
                .forEach(material -> System.out.println("- " + material.getNom() + ":" + material.calculeCout() + "€(quantité:" + material.getQuantite() + "m², coût unitaire: " + material.getCoutUnitaire() + " €/m², qualité :" + material.getCoefficientQualite() + ", transport:" + material.getCoutTransport() + "€)"));
        System.out.println("    **Coût total des matériaux avant TVA : " + materialsTotalCostBeforeTVA + " €**");
        double materialsTotalCostWithTVA = materialsTotalCostBeforeTVA * (1 + tvaRate);

        System.out.println("    **Coût total des matériaux avec TVA (" + (tvaRate * 100) + "%) : " + materialsTotalCostWithTVA + " €**");

        double mainDoueversTotalCostBeforeTVA = main_doeuvers.stream()
                .mapToDouble(Main_Doeuver::calculeCout)
                .sum();

        System.out.println("1. Main-d'œuvre :");
        main_doeuvers.stream()
                .forEach(main_doeuver -> System.out.println("- " + main_doeuver.getNom() + ":" + main_doeuver.calculeCout() + "€(taux horaire:" + main_doeuver.getCoutUnitaire() + "€/h, heures travaillées : " + main_doeuver.getQuantite() + " h, productivit :" + main_doeuver.getProductiviteOuvrier()));
        System.out.println("    **Coût total des main-d'œuvre avant TVA : " + mainDoueversTotalCostBeforeTVA + " €**");
        double mainDoueversTotalCostWithTVA = mainDoueversTotalCostBeforeTVA * (1 + tvaRate);

        System.out.println("    **Coût total des main-d'œuvre avec TVA (" + (tvaRate * 100) + "%) : " + mainDoueversTotalCostWithTVA + " €**");
        double totalAvantMarge = mainDoueversTotalCostWithTVA+materialsTotalCostWithTVA;
        System.out.println("3. Coût total avant marge :"+ totalAvantMarge +"€");
        double marge = totalAvantMarge * projet.getMargeBeneficiaire();
        System.out.println("4. Marge bénéficiaire ("+projet.getMargeBeneficiaire()+"%): "+ marge+"€");
        double projetCoutTotal = totalAvantMarge + marge;
        System.out.println("**Coût total final du projet : "+projetCoutTotal+" €**");
        projet.setCoutTotal(projetCoutTotal);

        return projet;
    }

    public void addClient() {
        System.out.println("--- ajouter nouveau client ---");
        System.out.println("    Entrez le nom du client:");
        System.out.println("    Entrez l'address du client':");
        System.out.println("    Entrez le nomber telephone du client:");
        System.out.println("    Entrez le remise du client:");

    }

    public void afficherProjets(Optional<List<Projet>> projets) {
    if(projets.isPresent() && projets != null) {
        projets.get().stream()
                .forEach(projet -> {
                    afficherProjet(projet);
                });
    } else{
        System.out.println("no projet exist!");
    }

    }

    public void afficherProjet(Projet projet) {
        System.out.println("Le nom du Projet:"+ projet.getNomProjet());
        System.out.println("le nom du client:"+ projet.getClient().getNom().toLowerCase());
        System.out.println("l'address du client:"+ projet.getClient().getAddress());
    }

    public int calculProjetCout() {
        int projet_id;
        System.out.println("Entrer le id de projet:");
        projet_id = scanner.nextInt();
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        return projet_id;
    }


    public void afficheDevis(Devis devis) {
        System.out.println("le montant estime:"+ devis.getMonantEstime());
        System.out.println("le date validite:"+ devis.getDateValidite());
        System.out.println("le date estime:"+ devis.getDateEstime());
    }

    public void noDevisFound() {
        System.out.println("No devis trouver pour projet specifie!");
    }
}