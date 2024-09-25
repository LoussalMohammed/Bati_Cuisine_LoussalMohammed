package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.*;
import org.app.Models.Enums.StatusProjet;
import org.app.Models.Enums.TypeComposant;
import org.app.Models.Repositories.RepositoriesInterfaces.DevisRepository;
import org.app.Tools.DatabaseC;

import java.lang.reflect.Type;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class DevisRepositoryImpl implements DevisRepository {
    @Override
    public Optional<Devis> findById(int id) throws SQLException {
        String sql = "SELECT *, " +
                "projets.id AS projetId , projets.nom AS projetNom, projets.* ," +
                "composants.id AS composantId, composants.nom AS composantNom ," +
                "clients.id AS clientId, clients.nom AS clientNom " +
                "FROM devis " +
                "LEFT JOIN projets ON devis.projet_id = projets.id " +
                "LEFT JOIN composants ON projets.id = composants.projet_id " +
                "LEFT JOIN clients ON projets.clientid = clients.id " +
                "WHERE devis.id = ? AND devis.deleted_at IS NULL";

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            List<Composant> composants = new ArrayList<>();
            Devis devis = null;

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    // If no results, return an empty Optional
                    return Optional.empty();
                }

                // Retrieve client details
                Client client = new Client(
                        resultSet.getInt("clientId"),
                        resultSet.getString("clientNom"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("estProfessionnel"),
                        resultSet.getDouble("remise")
                );

                // Create Projet and gather composants
                Projet projet = new Projet(
                        resultSet.getInt("projetId"),
                        resultSet.getString("nom"),
                        resultSet.getDouble("margeBeneficiair"),
                        resultSet.getDouble("coutTotal"),
                        StatusProjet.fromString(resultSet.getString("etatProjet")),
                        composants,
                        client
                );

                // Do this in a loop to fetch all composants
                do {
                    // Retrieve and add composants to the list
                    TypeComposant typeComposant = TypeComposant.valueOf(resultSet.getString("type_composant"));
                    if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                        Main_Doeuver mainDoeuver = new Main_Doeuver(
                                resultSet.getInt("composantId"),
                                resultSet.getString("composantNom"),
                                typeComposant,
                                resultSet.getDouble("quantite"),
                                resultSet.getDouble("coutUnitaire"),
                                resultSet.getDouble("tauxTVA"),
                                resultSet.getDouble("productivite_ouvrier")
                        );
                        composants.add(mainDoeuver);
                    } else if (TypeComposant.MATERIEL.equals(typeComposant)) {
                        Material material = new Material(
                                resultSet.getInt("composantId"),
                                resultSet.getString("composantNom"),
                                typeComposant,
                                resultSet.getDouble("quantite"),
                                resultSet.getDouble("coutUnitaire"),
                                resultSet.getDouble("tauxTVA"),
                                resultSet.getDouble("coutTransport"),
                                resultSet.getDouble("coefficientQualte")
                        );
                        composants.add(material);
                    }
                } while (resultSet.next());

                // Create and return the Devis object
                devis = new Devis(
                        resultSet.getInt("id"),
                        resultSet.getDouble("montantEstime"),
                        resultSet.getTimestamp("dateEstime").toLocalDateTime(),
                        resultSet.getTimestamp("dateValidite").toLocalDateTime(),
                        resultSet.getBoolean("accept"),
                        projet
                );
            }

            return Optional.ofNullable(devis);
        }
    }


    @Override
    public Optional<List<Devis>> getAll() throws SQLException {
        String sql = "SELECT *, " +
                "projets.id AS projetId, projets.nom AS projetNom, projets.*, " +
                "composants.id AS composantId, composants.nom AS composantNom, " +
                "clients.id AS clientId, clients.nom AS clientNom " +
                "FROM devis " +
                "LEFT JOIN projets ON devis.projet_id = projets.id " +
                "LEFT JOIN composants ON projets.id = composants.projet_id " +
                "LEFT JOIN clients ON projets.clientid = clients.id " +
                "WHERE devis.deleted_at IS NULL";

        List<Devis> devisList = new ArrayList<>();

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Using a Map to avoid duplicate Devis entries for the same Projet
            Map<Integer, Devis> devisMap = new HashMap<>();

            while (resultSet.next()) {
                // Retrieve the Devis ID to handle duplicates
                int devisId = resultSet.getInt("id");

                // Check if this Devis already exists in the map
                Devis devis = devisMap.get(devisId);
                if (devis == null) {
                    // Create client details
                    Client client = new Client(
                            resultSet.getInt("clientId"),
                            resultSet.getString("clientNom"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getBoolean("estProfessionnel"),
                            resultSet.getDouble("remise")
                    );

                    // Create the Projet
                    Projet projet = new Projet(
                            resultSet.getInt("projetId"),
                            resultSet.getString("nom"),
                            resultSet.getDouble("margeBeneficiair"),
                            resultSet.getDouble("coutTotal"),
                            StatusProjet.fromString(resultSet.getString("etatProjet")),
                            new ArrayList<>(), // Initialize empty list for composants
                            client
                    );

                    // Create a new Devis object
                    devis = new Devis(
                            devisId,
                            resultSet.getDouble("montantEstime"),
                            resultSet.getTimestamp("dateEstime").toLocalDateTime(),
                            resultSet.getTimestamp("dateValidite").toLocalDateTime(),
                            resultSet.getBoolean("accept"),
                            projet
                    );

                    // Store the Devis in the map
                    devisMap.put(devisId, devis);
                    devisList.add(devis); // Add the Devis to the list
                }

                // Now process the Composant
                TypeComposant typeComposant = TypeComposant.valueOf(resultSet.getString("type_composant"));
                Composant newComposant;

                if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                    newComposant = new Main_Doeuver(
                            resultSet.getInt("composantId"),
                            resultSet.getString("composantNom"),
                            typeComposant,
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("tauxTVA"),
                            resultSet.getDouble("productivite_ouvrier")
                    );
                } else if (TypeComposant.MATERIEL.equals(typeComposant)) {
                    newComposant = new Material(
                            resultSet.getInt("composantId"),
                            resultSet.getString("composantNom"),
                            typeComposant,
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("tauxTVA"),
                            resultSet.getDouble("coutTransport"),
                            resultSet.getDouble("coefficientQualte")
                    );
                } else {
                    continue; // Skip if type is not recognized
                }

                // Add the new Composant to the Devis' Projet
                devis.getProjet().getComposants().add(newComposant);
            }
        }

        return Optional.ofNullable(devisList);
    }


    @Override
    public boolean save(Devis devis) throws SQLException {

        String sql = "INSERT INTO devis (id, montantestime, dateestime, datevalidite, projet_id) VALUES (?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, devis.getId());
            statement.setDouble(2, devis.getMonantEstime());
            statement.setTimestamp(3, Timestamp.valueOf(devis.getDateEstime()));
            statement.setTimestamp(4, Timestamp.valueOf(devis.getDateValidite()));
            statement.setInt(5, devis.getProjet().getId());

            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + devis.getId() + ", " + devis.getMonantEstime() + ", " + devis.getDateEstime() + ", " + devis.getDateValidite());

            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    @Override
    public boolean update(Devis devis) throws SQLException {
        String sql = "UPDATE devis SET montantestime = ?, dateEstime = ?,  datevalidite = ?,  projet_id = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setDouble(1, devis.getMonantEstime());
            statement.setTimestamp(2, Timestamp.valueOf(devis.getDateEstime()));
            statement.setTimestamp(3, Timestamp.valueOf(devis.getDateValidite()));
            statement.setObject(4, devis.getProjet());
            statement.setInt(5, devis.getId());


            statement.executeUpdate();
            result = true;


        }
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "UPDATE devis SET deleted_at = ? WHERE id = ?";
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
        String sql = "UPDATE devis SET deleted_at = ? WHERE id = ?";
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
        String sql = "SELECT MAX(id) AS id FROM devis";
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
