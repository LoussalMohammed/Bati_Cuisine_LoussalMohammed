package org.app.Models.Repositories.RepositoriesImplementation;

import org.app.Models.Entities.*;
import org.app.Models.Enums.StatusProjet;
import org.app.Models.Enums.TypeComposant;
import org.app.Models.Repositories.RepositoriesInterfaces.ProjectRespository;
import org.app.tools.DatabaseC;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjetRepositoryImpl implements ProjectRespository {


    @Override
    public Optional<Projet> findById(int id) {
        String sql = "SELECT * FROM projets WHERE deleted_at IS NULL AND id = ? LIMIT 1";
        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Fetch project details
                    int projetId = resultSet.getInt("id");
                    String projetNom = resultSet.getString("nom");
                    double margeBeneficiaire = resultSet.getDouble("margeBeneficiaire");
                    double coutTotal = resultSet.getDouble("coutTotal");
                    StatusProjet etatProjet = StatusProjet.fromString(resultSet.getString("etatprojet"));

                    // Create a list to store composants
                    List<Composant> composants = new ArrayList<>();
                    Client client = null;

                    // Fetch composants associated with this project
                    String composantsSql = "SELECT * FROM composants WHERE projet_id = ?";
                    PreparedStatement composantsStatement = connection.prepareStatement(composantsSql);
                    composantsStatement.setInt(1, projetId);

                    try (ResultSet composantsResultSet = composantsStatement.executeQuery()) {
                        while (composantsResultSet.next()) {
                            int composantId = composantsResultSet.getInt("id");
                            TypeComposant typeComposant = TypeComposant.fromString(composantsResultSet.getString("type_composant"));

                            if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                                // Fetch Main_Doeuver details
                                String maindoeuverSql = "SELECT *," +
                                        " clients.nom AS clientNom, clients.address, clients.phone, clients.estprofessionnel, clients.remise " +
                                        " FROM main_doeuvres " +
                                        " LEFT JOIN projets ON main_doeuvres.projet_id = projets.id " +
                                        " LEFT JOIN clients ON projets.clientid = clients.id " +
                                        " WHERE main_doeuvres.id = ?";
                                PreparedStatement maindoeuverStatement = connection.prepareStatement(maindoeuverSql);
                                maindoeuverStatement.setInt(1, composantId);

                                try (ResultSet maindoeuverResultSet = maindoeuverStatement.executeQuery()) {
                                    if (maindoeuverResultSet.next()) {
                                        // Populate Client object
                                        int clientId = maindoeuverResultSet.getInt("clientid");
                                        String clientNom = maindoeuverResultSet.getString("clientNom");
                                        String address = maindoeuverResultSet.getString("address");
                                        String phone = maindoeuverResultSet.getString("phone");
                                        boolean estProfessionnel = maindoeuverResultSet.getBoolean("estprofessionnel");
                                        double remise = maindoeuverResultSet.getDouble("remise");

                                        client = new Client(clientId, clientNom, address, phone, estProfessionnel, remise);

                                        // Create Main_Doeuver object and add to composants
                                        Main_Doeuver mainDoeuver = new Main_Doeuver(
                                                composantId,
                                                composantsResultSet.getString("nom"),
                                                typeComposant,
                                                composantsResultSet.getDouble("quantite"),
                                                composantsResultSet.getDouble("coutUnitaire"),
                                                composantsResultSet.getDouble("taux_TVA"),
                                                maindoeuverResultSet.getDouble("productivite_ouvrier"),
                                                resultSet.getInt("projet_id")
                                        );
                                        composants.add(mainDoeuver);
                                    }
                                }
                            } else if (TypeComposant.MATERIAL.equals(typeComposant)) {
                                // Fetch Material details
                                String materialSql = "SELECT *," +
                                        " clients.nom AS clientNom, clients.address, clients.phone, clients.estprofessionnel, clients.remise " +
                                        " FROM materials " +
                                        " LEFT JOIN projets ON materials.projet_id = projets.id " +
                                        " LEFT JOIN clients ON projets.clientid = clients.id " +
                                        " WHERE materials.id = ?";
                                PreparedStatement materialStatement = connection.prepareStatement(materialSql);
                                materialStatement.setInt(1, composantId);

                                try (ResultSet materialResultSet = materialStatement.executeQuery()) {
                                    if (materialResultSet.next()) {
                                        // Populate Client object (if not already done)
                                        if (client == null) {
                                            int clientId = materialResultSet.getInt("clientid");
                                            String clientNom = materialResultSet.getString("clientNom");
                                            String address = materialResultSet.getString("address");
                                            String phone = materialResultSet.getString("phone");
                                            boolean estProfessionnel = materialResultSet.getBoolean("estprofessionnel");
                                            double remise = materialResultSet.getDouble("remise");

                                            client = new Client(clientId, clientNom, address, phone, estProfessionnel, remise);
                                        }

                                        // Create Material object and add to composants
                                        Material material = new Material(
                                                composantId,
                                                composantsResultSet.getString("nom"),
                                                typeComposant,
                                                composantsResultSet.getDouble("quantite"),
                                                composantsResultSet.getDouble("coutUnitaire"),
                                                composantsResultSet.getDouble("taux_TVA"),
                                                materialResultSet.getDouble("cout_Transport"),
                                                materialResultSet.getDouble("coefficient_Qualite"),
                                                materialResultSet.getInt("projet_id")
                                        );
                                        composants.add(material);
                                    }
                                }
                            }
                        }
                    }

                    // Return the Projet object after processing all composants
                    Projet projet = new Projet(
                            projetId,
                            projetNom,
                            margeBeneficiaire,
                            coutTotal,
                            etatProjet,
                            composants,  // Pass the list of Composant objects
                            client
                    );

                    return Optional.ofNullable(projet);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    @Override
    public Optional<List<Projet>> getAll() {
        String sql = "SELECT *, clients.id AS clientId," +
                "clients.nom as clientNom, clients.*" +
                " FROM projets " +
                "LEFT JOIN clients ON projets.clientid = clients.id" +
                " WHERE projets.deleted_at IS NULL";
        List<Projet> projets = new ArrayList<>();

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int projetId = resultSet.getInt("id");
                String projetNom = resultSet.getString("nom");
                double margeBeneficiaire = resultSet.getDouble("margeBeneficiaire");
                double coutTotal = resultSet.getDouble("coutTotal");
                StatusProjet etatProjet = StatusProjet.fromString(resultSet.getString("etatProjet"));
                int clientId = resultSet.getInt("clientId");
                String clientNom = resultSet.getString("clientNom");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                boolean estProfessionnel = resultSet.getBoolean("estprofessionnel");
                double remise = resultSet.getDouble("remise");

                // Fetch composants for each project
                List<Composant> composants = new ArrayList<>();
                String composantsSql = "SELECT * FROM composants WHERE projet_id = ?";
                PreparedStatement composantsStatement = connection.prepareStatement(composantsSql);
                composantsStatement.setInt(1, projetId);

                try (ResultSet composantsResultSet = composantsStatement.executeQuery()) {
                    while (composantsResultSet.next()) {
                        TypeComposant typeComposant = TypeComposant.fromString(composantsResultSet.getString("type_composant"));
                        int composantId = composantsResultSet.getInt("id");

                        if (TypeComposant.MAINDOEUVER.equals(typeComposant)) {
                            String maindoeuverSql = "SELECT * FROM main_doeuvres WHERE id = ?";
                            PreparedStatement maindoeuverStatement = connection.prepareStatement(maindoeuverSql);
                            maindoeuverStatement.setInt(1, composantId);
                            try (ResultSet maindoeuverResultSet = maindoeuverStatement.executeQuery()) {
                                while (maindoeuverResultSet.next()) {
                                    Main_Doeuver mainDoeuver = new Main_Doeuver(
                                            composantId,
                                            composantsResultSet.getString("nom"),
                                            typeComposant,
                                            composantsResultSet.getDouble("quantite"),
                                            composantsResultSet.getDouble("coutUnitaire"),
                                            composantsResultSet.getDouble("taux_TVA"),
                                            maindoeuverResultSet.getDouble("productivite_ouvrier"),
                                            resultSet.getInt("id")
                                    );
                                    composants.add(mainDoeuver);
                                }
                            }
                        } else if (TypeComposant.MATERIAL.equals(typeComposant)) {
                            String materialSql = "SELECT * FROM materials WHERE id = ?";
                            PreparedStatement materialStatement = connection.prepareStatement(materialSql);
                            materialStatement.setInt(1, composantId);
                            try (ResultSet materialResultSet = materialStatement.executeQuery()) {
                                while (materialResultSet.next()) {
                                    Material material = new Material(
                                            composantId,
                                            composantsResultSet.getString("nom"),
                                            typeComposant,
                                            composantsResultSet.getDouble("quantite"),
                                            composantsResultSet.getDouble("coutUnitaire"),
                                            composantsResultSet.getDouble("taux_TVA"),
                                            materialResultSet.getDouble("cout_transport"),
                                            materialResultSet.getDouble("coefficient_qualite"),
                                            resultSet.getInt("id")
                                    );
                                    composants.add(material);
                                }
                            }
                        }
                    }
                }

                // Add the project with its composants to the list
                Projet projet = new Projet(
                        projetId,
                        projetNom,
                        margeBeneficiaire,
                        coutTotal,
                        etatProjet,
                        composants,
                        new Client(clientId, clientNom, address, phone, estProfessionnel, remise)
                );
                projets.add(projet);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(projets);
    }

    @Override
    public boolean save(Projet projet) throws SQLException {
        String sql = "INSERT INTO projets (nom, margebeneficiaire, couttotal, etatprojet, clientid) VALUES (?, ?, ?, ?, ?)";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, projet.getNomProjet());
            statement.setDouble(2, projet.getMargeBeneficiaire());
            statement.setDouble(3, projet.getCoutTotal());
            statement.setObject(4, projet.getEtatProjet().name(), Types.OTHER);
            statement.setObject(5, projet.getClient().getId());
            System.out.println("SQL: " + sql);
            System.out.println("Parameters: "+ projet.getNomProjet() + ", " + projet.getMargeBeneficiaire() + ", " + projet.getCoutTotal() + ", " + projet.getEtatProjet() + ", " + projet.getClient());

            statement.executeUpdate();
            result = true;


        }

        return result;
    }

    @Override
    public boolean update(Projet projet) throws SQLException {
        String sql = "UPDATE projets SET nom = ?, margebeneficiaire = ?, couttotal = ?, etatprojet = ?, clientid = ? WHERE id = ?";
        boolean result = false;

        try (Connection connection = DatabaseC.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, projet.getNomProjet());
            statement.setDouble(2, projet.getMargeBeneficiaire());
            statement.setDouble(3, projet.getCoutTotal());
            statement.setObject(4, projet.getEtatProjet().name(), Types.OTHER);
            statement.setObject(5, projet.getClient().getId());
            statement.setInt(6, projet.getId());


            statement.executeUpdate();

            result = true;
        }

        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "UPDATE projets SET deleted_at = ? WHERE id = ?";
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
        String sql = "UPDATE ONLY projets SET deleted_at = ? WHERE id = ?";
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
    public Integer getLastId() {
        String sql = "SELECT MAX(id) AS id FROM projets";
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
