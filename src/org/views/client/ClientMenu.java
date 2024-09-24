package org.views.client;

import lib.ScanInput;
import orgg.dao.client.PgClientDAO;
import services.ClientService;
import orgg.entities.Client;

import java.util.List;

public class ClientMenu {
    private static PgClientDAO model = new PgClientDAO();
    private static ClientDisplay display = new ClientDisplay();
    private static ClientService clientService = new ClientService(model, display);

    public static int addClient() {
        System.out.println("---Ajout d'un client---");
        System.out.print("    # nom: ");
        String name =  ScanInput.scanner.nextLine();
        System.out.print("    # addresse: ");
        String address =  ScanInput.scanner.nextLine();
        System.out.print("    # téléphone: ");
        String phone =  ScanInput.scanner.nextLine();

        String isProfessionalOption;
        do {
            System.out.print("    # est professionnel? (y/n): ");
            isProfessionalOption = ScanInput.scanner.nextLine();
        }while(!isProfessionalOption.equals("y") && !isProfessionalOption.equals("n"));
        boolean isProfessional;
        if(isProfessionalOption.equals("y")) isProfessional = true;
        else isProfessional = false;

        Client client = new Client(0, name, address, phone, isProfessional);
        return clientService.addClient(client);
    }

    public static int searchClients() {
        System.out.println("---recherche d'un client---");
        System.out.print("    # le nom du client: ");
        String name = ScanInput.scanner.nextLine();

        List<Client> foundClients = clientService.searchClients(name);
        if(foundClients.size() == 0) {
            System.out.println("    Aucun client trouvé avec ce nom.");
        }else if(foundClients.size() == 1) {
            Client client = foundClients.get(0);
            System.out.println("    Client trouvé !");
            System.out.println("    Nom: "+ client.getName());
            System.out.println("    Addresse: "+ client.getAddress());
            System.out.println("    Est Professionnel: "+ client.isProfessional());

            return client.getId();
        }else {
            System.out.println("    Il y'a "+ foundClients.size()+" client compatible avec le nom saisie, Comme le suivant: " );
            for(Client client : foundClients) {
                System.out.println("    + (ID: "+ client.getId() + "), Nom: " + client.getName());
            }

            int returnedClientId;
            boolean checker;
            do {
                System.out.print("    Saisir le ID de la personne que vous voulez avoir: ");
                int clientId = ScanInput.scanner.nextInt();
                ScanInput.scanner.nextLine();
                returnedClientId = clientId;

                checker = foundClients.stream().anyMatch(client -> client.getId() == clientId);
            }while(!checker);
            return returnedClientId;
        }

        return 0; // that's mean there is no found client with that ID,
    }

    public static void listAllClients() {}
}