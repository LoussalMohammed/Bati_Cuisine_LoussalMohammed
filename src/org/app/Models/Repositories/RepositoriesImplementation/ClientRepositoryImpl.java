package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.Client;
import org.app.tools.DatabaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ClientRepositoryImpl {
    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ? LIMIT 1 ";
        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                      resultSet.getInt("id"),
                      resultSet.getString("nom"),
                      resultSet.getString("address"),
                      resultSet.getString("phone"),
                      resultSet.getBoolean("estProfessionnel"),
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

    public Optional<Client> getClientByName(String name) {
        String sql = "SELECT * FROM clients ORDER BY levenshtein(lower(nom), lower(?)) LIMIT 1";

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getBoolean("estProfessionnel"),
                            resultSet.getDouble("remise")
                    );
                    return Optional.of(client);
                }


            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public ArrayList<Client> getAll() throws SQLException {
        ArrayList<Client> Clients = new ArrayList<>(10);
        try (Connection connection = DatabaseC.getInstance().getConnection()) {
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

        try (Connection connection = DatabaseC.getInstance().getConnection();
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

        try (Connection connection = DatabaseC.getInstance().getConnection();
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

        try (Connection connection = DatabaseC.getInstance().getConnection();
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
        try (Connection connection = DatabaseC.getInstance().getConnection();
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
