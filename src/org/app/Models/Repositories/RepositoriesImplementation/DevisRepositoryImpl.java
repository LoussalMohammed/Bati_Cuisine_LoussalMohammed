package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.*;
import org.app.Models.Enums.StatusProjet;
import org.app.Models.Enums.TypeComposant;
import org.app.Models.Repositories.RepositoriesInterfaces.DevisRepository;
import org.app.tools.DatabaseC;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class DevisRepositoryImpl implements DevisRepository {

    @Override
    public Optional<Devis> findById(int id) throws SQLException {
        String sql = "SELECT *, " +
                "projets.id AS projetId, projets.nom AS projetNom, " +
                "composants.id AS composantId, composants.nom AS composantNom, " +
                "clients.id AS clientId, clients.nom AS clientNom " +
                "FROM devis " +
                "LEFT JOIN projets ON devis.projet_id = projets.id " +
                "LEFT JOIN composants ON projets.id = composants.projet_id " +
                "LEFT JOIN materials ON composants.id = materials.id " +
                "LEFT JOIN main_doeuvres md ON composants.id = md.id " +
                "LEFT JOIN clients ON projets.clientid = clients.id " +
                "WHERE devis.id = ? AND devis.deleted_at IS NULL";

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            List<Composant> composants = new ArrayList<>();
            Devis devis = null;

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                Client client = new Client(
                        resultSet.getInt("clientId"),
                        resultSet.getString("clientNom"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("estProfessionnel"),
                        resultSet.getDouble("remise")
                );

                Projet projet = new Projet(
                        resultSet.getInt("projetId"),
                        resultSet.getString("nom"),
                        resultSet.getDouble("margebeneficiaire"),
                        resultSet.getDouble("coutTotal"),
                        StatusProjet.fromString(resultSet.getString("etatProjet")),
                        composants,
                        client
                );

                do {
                    int composantId = resultSet.getInt("composantId");
                    if (composantId == 0) continue;

                    TypeComposant typeComposant = TypeComposant.fromString(resultSet.getString("type_composant"));
                    Composant newComposant;

                    if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                        newComposant = new Main_Doeuver(
                                composantId,
                                resultSet.getString("composantNom"),
                                typeComposant,
                                resultSet.getDouble("quantite"),
                                resultSet.getDouble("coutUnitaire"),
                                resultSet.getDouble("taux_TVA"),
                                resultSet.getDouble("productivite_ouvrier"),
                                resultSet.getInt("projet_id")
                        );
                    } else if (TypeComposant.MATERIAL.equals(typeComposant)) {
                        newComposant = new Material(
                                composantId,
                                resultSet.getString("composantNom"),
                                typeComposant,
                                resultSet.getDouble("quantite"),
                                resultSet.getDouble("coutUnitaire"),
                                resultSet.getDouble("taux_TVA"),
                                resultSet.getDouble("cout_Transport"),
                                resultSet.getDouble("coefficient_Qualite"),
                                resultSet.getInt("projet_id")
                        );
                    } else {
                        continue;
                    }

                    composants.add(newComposant);

                    LocalDateTime dateValidite = resultSet.getTimestamp("dateValidite") != null ? resultSet.getTimestamp("dateValidite").toLocalDateTime() : LocalDateTime.now();
                    devis = new Devis(
                            resultSet.getInt("id"), // <= the line returning the error
                            resultSet.getDouble("montantEstime"),
                            resultSet.getTimestamp("dateEstime").toLocalDateTime(),
                            dateValidite,
                            resultSet.getTimestamp("dateValidite") != null,
                            projet
                    );
                } while (resultSet.next());

            }

            return Optional.of(devis);
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
                "LEFT JOIN materials ON composants.id = materials.id " +
                "LEFT JOIN main_doeuvres md on composants.id = md.id " +
                "LEFT JOIN clients ON projets.clientid = clients.id " +
                "WHERE devis.deleted_at IS NULL";

        List<Devis> devisList = new ArrayList<>();

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            Map<Integer, Devis> devisMap = new HashMap<>();

            while (resultSet.next()) {
                int devisId = resultSet.getInt("id");

                Devis devis = devisMap.get(devisId);
                if (devis == null) {
                    Client client = new Client(
                            resultSet.getInt("clientId"),
                            resultSet.getString("clientNom"),
                            resultSet.getString("address"),
                            resultSet.getString("phone"),
                            resultSet.getBoolean("estProfessionnel"),
                            resultSet.getDouble("remise")
                    );

                    Projet projet = new Projet(
                            resultSet.getInt("projetId"),
                            resultSet.getString("nom"),
                            resultSet.getDouble("margebeneficiaire"),
                            resultSet.getDouble("coutTotal"),
                            StatusProjet.fromString(resultSet.getString("etatProjet")),
                            new ArrayList<>(),
                            client
                    );

                    LocalDateTime dateValidite = resultSet.getTimestamp("dateValidite") != null ? resultSet.getTimestamp("dateValidite").toLocalDateTime() : LocalDateTime.now();
                    devis = new Devis(
                            resultSet.getInt("id"), // <= the line returning the error
                            resultSet.getDouble("montantEstime"),
                            resultSet.getTimestamp("dateEstime").toLocalDateTime(),
                            dateValidite,
                            resultSet.getTimestamp("dateValidite") != null,
                            projet
                    );

                    devisMap.put(devisId, devis);
                    devisList.add(devis);
                }

                if (resultSet.getInt("composantId") == 0) continue;

                TypeComposant typeComposant = TypeComposant.fromString(resultSet.getString("type_composant"));
                Composant newComposant;

                if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                    newComposant = new Main_Doeuver(
                            resultSet.getInt("composantId"),
                            resultSet.getString("composantNom"),
                            typeComposant,
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("taux_TVA"),
                            resultSet.getDouble("productivite_ouvrier"),
                            resultSet.getInt("projet_id")
                    );
                } else if (TypeComposant.MATERIAL.equals(typeComposant)) {
                    newComposant = new Material(
                            resultSet.getInt("composantId"),
                            resultSet.getString("composantNom"),
                            typeComposant,
                            resultSet.getDouble("quantite"),
                            resultSet.getDouble("coutUnitaire"),
                            resultSet.getDouble("taux_TVA"),
                            resultSet.getDouble("cout_Transport"),
                            resultSet.getDouble("coefficient_Qualite"),
                            resultSet.getInt("projet_id")
                    );
                } else {
                    continue;
                }

                devis.getProjet().getComposants().add(newComposant);
            }
        }

        return Optional.of(devisList);
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
            statement.setObject(4, devis.getProjet().getId());
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

    @Override
    public Optional<Devis> findByProjet(int projet_id) throws SQLException {
        String sql = "SELECT *, " +
                "projets.id AS projetId , projets.nom AS projetNom, projets.* ," +
                "composants.id AS composantId, composants.nom AS composantNom ," +
                "clients.id AS clientId, clients.nom AS clientNom " +
                "FROM devis " +
                "LEFT JOIN projets ON devis.projet_id = projets.id " +
                "LEFT JOIN composants ON projets.id = composants.projet_id " +
                "LEFT JOIN clients ON projets.clientid = clients.id " +
                "WHERE devis.projet_id = ? AND devis.deleted_at IS NULL";

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projet_id);
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
                        resultSet.getDouble("margebeneficiaire"),
                        resultSet.getDouble("coutTotal"),
                        StatusProjet.fromString(resultSet.getString("etatProjet")),
                        composants,
                        client
                );

                do {
                    TypeComposant typeComposant = TypeComposant.fromString(resultSet.getString("type_composant"));
                    if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                        Main_Doeuver mainDoeuver = new Main_Doeuver(
                                resultSet.getInt("composantId"),
                                resultSet.getString("composantNom"),
                                typeComposant,
                                resultSet.getDouble("quantite"),
                                resultSet.getDouble("coutUnitaire"),
                                resultSet.getDouble("tauxTVA"),
                                resultSet.getDouble("productivite_ouvrier"),
                                resultSet.getInt("projet_id")
                        );
                        composants.add(mainDoeuver);
                    } else if (TypeComposant.MATERIAL.equals(typeComposant)) {
                        Material material = new Material(
                                resultSet.getInt("composantId"),
                                resultSet.getString("composantNom"),
                                typeComposant,
                                resultSet.getDouble("quantite"),
                                resultSet.getDouble("coutUnitaire"),
                                resultSet.getDouble("tauxTVA"),
                                resultSet.getDouble("coutTransport"),
                                resultSet.getDouble("coefficient_Qualite"),
                                resultSet.getInt("projet_id")
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
}
