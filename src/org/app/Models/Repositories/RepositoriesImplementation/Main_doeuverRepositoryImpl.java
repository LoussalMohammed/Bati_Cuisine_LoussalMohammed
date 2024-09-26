package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.Main_Doeuver;
import org.app.Models.Enums.TypeComposant;
import org.app.Models.Repositories.RepositoriesInterfaces.Main_doeuverRepository;
import org.app.tools.DatabaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main_doeuverRepositoryImpl implements Main_doeuverRepository {

    @Override
    public Optional<Main_Doeuver> findById(int id) throws SQLException {
        String sql = "SELECT * FROM main_doeuvres WHERE id = ? AND deleted_at IS NULL";
        try(Connection connection = DatabaseC.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    Main_Doeuver main_doeuver = new Main_Doeuver(
                            resultSet.getInt("id"),
                            resultSet.getString("clientNom"),
                            TypeComposant.fromString(resultSet.getString("type_composant")),
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("tauxTVA"),
                            resultSet.getDouble("productivite_ouvrier"),
                            resultSet.getInt("projet_id")
                    );

                    return Optional.ofNullable(main_doeuver);
                }
            }
        }
        return null;
    }

    @Override
    public Optional<List<Main_Doeuver>> getAll() throws SQLException{
        String sql = "SELECT * FROM main_doeuvres WHERE deleted_at IS NULL";
        try(Connection connection = DatabaseC.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Main_Doeuver> Main_doeuvers = new ArrayList<>();

            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Main_Doeuver main_doeuver = new Main_Doeuver(
                            resultSet.getInt("id"),
                            resultSet.getString("clientNom"),
                            TypeComposant.fromString(resultSet.getString("type_composant")),
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("tauxTVA"),
                            resultSet.getDouble("productivite_ouvrier"),
                            resultSet.getInt("projet_id")
                    );

                    Main_doeuvers.add(main_doeuver);
                }

                return Optional.ofNullable(Main_doeuvers);
            }
        }
    }

    @Override
    public boolean save(Main_Doeuver main_doeuver) throws SQLException {
        String sql = "INSERT INTO main_doeuvres (id, nom, type_composant, taux_tva, quantite, coutunitaire, productivite_ouvrier, projet_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, main_doeuver.getId());
            statement.setString(2, main_doeuver.getNom());
            statement.setObject(3, main_doeuver.getTypeComposant().name(), Types.OTHER);
            statement.setDouble(4, main_doeuver.getTauxTVA());
            statement.setDouble(5, main_doeuver.getQuantite());
            statement.setDouble(6, main_doeuver.getCoutUnitaire());
            statement.setDouble(7, main_doeuver.getProductiviteOuvrier());
            statement.setInt(8, main_doeuver.getProjetId());

            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + main_doeuver.getId() + ", " + main_doeuver.getNom() + ", " + main_doeuver.getTypeComposant() + ", " + main_doeuver.getTauxTVA() + ", " + main_doeuver.getQuantite() + ", " + main_doeuver.getCoutUnitaire() + ", " + main_doeuver.getProductiviteOuvrier());

            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    @Override
    public boolean update(Main_Doeuver main_doeuver) throws SQLException {

        String sql = "UPDATE main_doeuvres SET nom = ?, type_composant = ?, taux_tva = ?, quantite = ?, coutunitaire = ?, productivite_ouvrier = ?, projet_id = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, main_doeuver.getNom());
            statement.setObject(2, main_doeuver.getTypeComposant().name(), Types.OTHER);
            statement.setDouble(3, main_doeuver.getTauxTVA());
            statement.setDouble(4, main_doeuver.getQuantite());
            statement.setDouble(5, main_doeuver.getCoutUnitaire());
            statement.setDouble(6, main_doeuver.getProductiviteOuvrier());
            statement.setInt(7, main_doeuver.getProjetId());

            statement.setInt(8, main_doeuver.getId());
            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String sql = "UPDATE main_doeuvres SET deleted_at = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setInt(2, id);

            statement.executeUpdate();

            result = true;
        }

        return result;
    }

    @Override
    public boolean restore(int id) throws SQLException {
        String sql = "UPDATE main_doeuvres SET deleted_at = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setInt(2, id);

            statement.executeUpdate();

            result = true;
        }

        return result;
    }

    @Override
    public Integer findLastId() {

        String sql = "SELECT MAX(id) AS id FROM main_doeuvres";
        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            try {
                if(resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    return id;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
