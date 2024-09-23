package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.*;
import org.app.Models.Enums.StatusProjet;
import org.app.Models.Enums.TypeComposant;
import org.app.Tools.databaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class projetRepositoryImpl {

    public Projet findById(int id) {
        String sql = "SELECT * FROM projets WHERE deleted_at = NULL AND id = ? LIMIT 1 OFFSET 1";
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String composantsSql = "SELECT * FROM composants WHERE projet_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    try(ResultSet resultSet1 = preparedStatement.executeQuery()) {
                        if(resultSet1.next()) {

                            int composantId = resultSet1.getInt("id");
                            double tauxTVA = resultSet1.getDouble("tauxTVA");
                            double quantite = resultSet1.getDouble("quantite");
                            double coutUnitaire = resultSet1.getDouble("coutUnitaire");
                            int projectId = resultSet1.getInt("project_id");

                            TypeComposant typeComposant = TypeComposant.fromString(resultSet1.getString("type_composant"));
                            if(TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                                String maindoeuverSql = "SELECT * FROM main_doeuvres WHERE id = ?";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(maindoeuverSql);
                                statement.setInt(1, composantId);
                                try(ResultSet resultSet2 = preparedStatement1.executeQuery()) {
                                    if(resultSet2.next()) {
                                        Main_Doeuver main_doeuver = new Main_Doeuver(
                                            resultSet2.getDouble("productivite_ouvrier")
                                        );
                                    }
                                }
                            } else if(TypeComposant.MATERIEL.equals(typeComposant)) {
                                String materialSql = "SELECT * FROM materials WHERE id = ?";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(materialSql);
                                statement.setInt(1, composantId);
                                try(ResultSet resultSet2 = preparedStatement1.executeQuery()) {
                                    if(resultSet2.next()) {
                                        Material material = new Material(
                                                resultSet2.getDouble("coutTransport"),
                                                resultSet2.getDouble("coefficientQualte")
                                        );
                                    }
                                }
                            }
                        }
                    }

                    return new Projet(
                            resultSet.getInt("id"),
                            resultSet.getString("nomProjet"),
                            resultSet.getDouble("margeBeneficiaire"),
                            resultSet.getDouble("coutTotal"),
                            StatusProjet.fromString(resultSet.getString("StatusProjet")),
                            new Composant(composantId),
                            resultSet.getDouble("remise")
                    );
                }
                else {
                    return null;
                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public ArrayList<Client> getAll() throws SQLException {
        ArrayList<Client> Clients = new ArrayList<>(10);
        try (Connection connection = databaseC.getInstance().getConnection()) {
            String sql = "SELECT * FROM clients WHERE deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Client client = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("estProfessionnel"),
                        resultSet.getDouble("remise")
                );
                Clients.add(client);
            }

        }
        return Clients;
    }

    public boolean save(Client client) throws SQLException {
        String sql = "INSERT INTO clients (id, nom, address, phone, estprofessionnel, remise) VALUES (?, ?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, client.getId());
            statement.setString(2, client.getNom());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getPhone());
            statement.setBoolean(5, client.getEstProfessionnel());
            statement.setDouble(6, client.getRemise());
            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + client.getId() + ", " + client.getNom() + ", " + client.getAddress() + ", " + client.getPhone() + ", " + client.getEstProfessionnel() + ", " + client.getRemise());

            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    public boolean update(Client client) throws SQLException {
        String sql = "INSERT INTO clients (id, nom, address, phone, estprofessionnel, remise) VALUES (?, ?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, client.getId());
            statement.setString(2, client.getNom());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getPhone());
            statement.setBoolean(5, client.getEstProfessionnel());
            statement.setDouble(6, client.getRemise());
            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + client.getId() + ", " + client.getNom() + ", " + client.getAddress() + ", " + client.getPhone() + ", " + client.getEstProfessionnel() + ", " + client.getRemise());


            statement.executeUpdate();

            result = true;
        }

        return result;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "UPDATE clients SET deleted_at = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setInt(2, id);

            statement.executeUpdate();

            result = true;
        }

        return result;
    }

    public boolean restore(int id) throws SQLException {
        String sql = "UPDATE ONLY clients SET deleted_at = ? WHERE id = ?";
        boolean result = false;
        try (Connection connection = databaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, null);
            statement.setInt(2, id);

            statement.executeUpdate();

            result = true;
        }

        return result;
    }

    public Integer getLastId() {
        String sql = "SELECT MAX(id) AS id FROM clients";
        try (Connection connection = databaseC.getInstance().getConnection();
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
