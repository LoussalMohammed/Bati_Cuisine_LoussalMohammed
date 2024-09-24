package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Devis;
import org.app.Models.Entities.Projet;

import java.util.ArrayList;

public interface devisRepository {
    public Devis findById(int id);
    public ArrayList<Devis> getAll();

    public boolean save(Devis devis);
    public boolean update(Devis devis);

    public boolean delete(int id);
    public boolean restore(int id);

    public int findLastId();
}
