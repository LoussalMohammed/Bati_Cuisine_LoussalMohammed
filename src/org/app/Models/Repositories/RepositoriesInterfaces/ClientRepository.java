package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Client;
import org.app.Models.Entities.Composant;

import java.util.ArrayList;

public interface clientRepository {
    public Client findById(int id);
    public ArrayList<Client> getAll();

    public boolean save(Client client);
    public boolean update(Client client);

    public boolean delete(int id);
    public boolean restore(int id);

    public int findLastId();
}
