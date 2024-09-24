package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Composant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ComposantRepository {
    public Optional<Composant> findById(int id) throws SQLException;
    public Optional<List<Composant>> getAll() throws SQLException;

    public boolean save(Composant composant) throws SQLException;
    public boolean update(Composant composant) throws SQLException;

    public boolean delete(int id) throws SQLException;
    public boolean restore(int id) throws SQLException;

    public Integer findLastId();
}
