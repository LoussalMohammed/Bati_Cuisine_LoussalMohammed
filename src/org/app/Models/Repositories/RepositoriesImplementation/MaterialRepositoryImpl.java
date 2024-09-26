package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.Material;
import org.app.Models.Enums.TypeComposant;
import org.app.Models.Repositories.RepositoriesInterfaces.MaterialRepository;
import org.app.tools.DatabaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialRepositoryImpl implements MaterialRepository {


    @Override
    public Optional<Material> findById(int id) throws SQLException{
        String sql = "SELECT * FROM materials WHERE id = ? AND deleted_at IS NULL";
        try(Connection connection = DatabaseC.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    Material material = new Material(
                            resultSet.getInt("id"),
                            resultSet.getString("clientNom"),
                            TypeComposant.fromString(resultSet.getString("type_composant")),
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("tauxTVA"),
                            resultSet.getDouble("cout_transport"),
                            resultSet.getDouble("coefficient_qualite"),
                            resultSet.getInt("projet_id")
                    );

                    return Optional.ofNullable(material);
                }
            }
        }
        return null;
    }

    @Override
    public Optional<List<Material>> getAll() throws SQLException{
        String sql = "SELECT * FROM materials WHERE deleted_at IS NULL";
        try(Connection connection = DatabaseC.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Material> Materials = new ArrayList<>();

            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Material material = new Material(
                            resultSet.getInt("id"),
                            resultSet.getString("clientNom"),
                            TypeComposant.fromString(resultSet.getString("type_composant")),
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("tauxTVA"),
                            resultSet.getDouble("cout_transport"),
                            resultSet.getDouble("coefficient_qualite"),
                            resultSet.getInt("projet_id")
                    );

                    Materials.add(material);
                }

                return Optional.ofNullable(Materials);
            }
        }
    }

    @Override
    public boolean save(Material material) throws SQLException {
        String sql = "INSERT INTO materials (id, nom, type_composant, taux_tva, quantite, coutunitaire, cout_transport, coefficient_qualite, projet_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, material.getId());
            statement.setString(2, material.getNom());
            statement.setObject(3, material.getTypeComposant().name(), java.sql.Types.OTHER);
            statement.setDouble(4, material.getTauxTVA());
            statement.setDouble(5, material.getQuantite());
            statement.setDouble(6, material.getCoutUnitaire());
            statement.setDouble(7, material.getCoutTransport());
            statement.setDouble(8, material.getCoefficientQualite());
            statement.setInt(9, material.getProjetId());

            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + material.getId() + ", " + material.getNom() + ", " + material.getTypeComposant() + ", " + material.getTauxTVA() + ", " + material.getQuantite() + ", " + material.getCoutUnitaire() + ", " + material.getCoutTransport() + ", " + material.getCoefficientQualite());

            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    @Override
    public boolean update(Material material) throws SQLException {

        String sql = "UPDATE materials SET nom = ?, type_composant = ?, taux_tva = ?, quantite = ?, coutunitaire = ?, cout_transport = ?, coefficient_qualite = ?, projet_id = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, material.getNom());
            statement.setObject(2, material.getTypeComposant());
            statement.setDouble(3, material.getTauxTVA());
            statement.setDouble(4, material.getQuantite());
            statement.setDouble(5, material.getCoutUnitaire());
            statement.setDouble(6, material.getCoutTransport());
            statement.setDouble(7, material.getCoefficientQualite());
            statement.setInt(8, material.getProjetId());

            statement.setInt(9, material.getId());
            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String sql = "UPDATE materials SET deleted_at = ? WHERE id = ?";
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
        String sql = "UPDATE materials SET deleted_at = ? WHERE id = ?";
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

        String sql = "SELECT MAX(id) AS id FROM materials";
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
