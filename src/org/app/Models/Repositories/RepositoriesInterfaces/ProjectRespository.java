package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Projet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProjectRespository {


    public Optional<Projet> findById(int id) throws SQLException;
    public Optional<List<Projet>> getAll() throws SQLException;

    public boolean save(Projet projet) throws SQLException;
    public boolean update(Projet projet) throws SQLException;

    public boolean delete(int id) throws SQLException;
    public boolean restore(int id) throws SQLException;

    public Integer getLastId();
}
