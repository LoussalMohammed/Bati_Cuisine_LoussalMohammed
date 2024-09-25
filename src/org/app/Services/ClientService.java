package org.app.Services;

import org.app.Models.Repositories.RepositoriesImplementation.ClientRepositoryImpl;
import org.views.client.ClientView;
import org.app.Models.Entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private ClientRepositoryImpl model;
    private ClientView view;

    public ClientService(ClientRepositoryImpl model, ClientView view) {
        this.model = model;
        this.view = view;
    }

    public boolean addClient(Client client) throws SQLException {
        return model.save(client);
    }

    public Optional<Client> searchClients(String name) {
        return model.getClientByName(name);
    }

    public void displayClient(String name) {
        Optional<Client> client = searchClients(name);
    }

}