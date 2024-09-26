package org.app.Models.Repositories.RepositoriesInterfaces;

import org.app.Models.Entities.Main_Doeuver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Main_doeuverRepository {
    public Optional<Main_Doeuver> findById(int id) throws SQLException;
    public Optional<List<Main_Doeuver>> getAll() throws SQLException;

    public boolean save(Main_Doeuver main_doeuver) throws SQLException;
    public boolean update(Main_Doeuver main_doeuver) throws SQLException;

    public boolean delete(int id) throws SQLException;
    public boolean restore(int id) throws SQLException;

    public Integer findLastId();
}
