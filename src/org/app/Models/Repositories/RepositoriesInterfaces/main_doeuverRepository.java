package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Main_Doeuver;
import org.app.Models.Entities.Projet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface main_doeuverRepository {
    public Main_Doeuver findById(int id);
    public ArrayList<Main_Doeuver> getAll();

    public boolean save(Main_Doeuver main_doeuver);
    public boolean update(Main_Doeuver main_doeuver);

    public boolean delete(int id);
    public boolean restore(int id);

    public int findLastId();
}
