package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Material;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository {
    public Optional<Material> findById(int id) throws SQLException;
    public Optional<List<Material>> getAll() throws SQLException;

    public boolean save(Material material) throws SQLException;
    public boolean update(Material material) throws SQLException;

    public boolean delete(int id) throws SQLException;
    public boolean restore(int id) throws SQLException;

    public Integer findLastId();
}
