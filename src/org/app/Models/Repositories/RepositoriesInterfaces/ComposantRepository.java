package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Composant;
import org.app.Models.Entities.Projet;

import java.util.ArrayList;

public interface composantRepository {
    public Composant findById(int id);
    public ArrayList<Composant> getAll();

    public boolean save(Composant composant);
    public boolean update(Composant composant);

    public boolean delete(int id);
    public boolean restore(int id);

    public int findLastId();
}
