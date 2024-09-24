package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    public Optional<Client> findById(int id) throws SQLException;
    public Client getClientByName(String name) throws SQLException;
    public Optional<List<Client>> getAll() throws SQLException;

    public boolean save(Client client) throws SQLException;
    public boolean update(Client client) throws SQLException;

    public boolean delete(int id) throws SQLException;
    public boolean restore(int id) throws SQLException;

    public Integer findLastId();
}
