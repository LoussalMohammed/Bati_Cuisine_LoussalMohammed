package org.app.Models.Entities;

import org.app.Models.Enums.StatusProjet;
import java.util.List;

public class Projet {

    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;

    private StatusProjet etatProjet;

    private List<Composant> composants;
    private int clientId;

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public StatusProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(StatusProjet statusProjet) {
        etatProjet = statusProjet;
    }

    public List<Composant> getComposants() {
        return composants;
    }

    public void setComposants(List<Composant> composants) {
        this.composants = composants;
    }

    public void addComposant(Composant composant) {
        composants.add(composant);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

}
