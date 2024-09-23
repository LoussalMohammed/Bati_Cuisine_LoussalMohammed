package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Projet;

import java.util.ArrayList;

public interface ProjectRespository {
    public Projet findById(int id);
    public ArrayList<Projet> getAll();

    public boolean save(Projet projet);
    public boolean update(Projet projet);

    public boolean delete(int id);
    public boolean restore(int id);

    public int findLastId();
}
