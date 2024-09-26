package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Devis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DevisRepository {
    public Optional<Devis> findById(int id) throws SQLException;
    public Optional<Devis> findByProjet(int projet_id) throws SQLException;
    public Optional<List<Devis>> getAll() throws SQLException;

    public boolean save(Devis devis) throws SQLException;
    public boolean update(Devis devis) throws SQLException;

    public boolean delete(int id) throws SQLException;
    public boolean restore(int id) throws SQLException;

    public Integer findLastId();
}
