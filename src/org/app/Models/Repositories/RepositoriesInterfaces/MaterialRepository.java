package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Material;
import org.app.Models.Entities.Projet;

import java.util.ArrayList;

public interface materialRepository {
    public Material findById(int id);
    public ArrayList<Material> getAll();

    public boolean save(Material material);
    public boolean update(Material material);

    public boolean delete(int id);
    public boolean restore(int id);

    public int findLastId();
}
