package org.app.Models.Entities;

import org.app.Models.Enums.StatusProjet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Projet {

    private int id;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;

    private StatusProjet etatProjet;

    private List<Composant> composants;
    private Client client;


    public Projet(int id, String nomProjet, double margeBeneficiaire, double coutTotal, StatusProjet etatProjet, List<Composant> composants, Client client) {
        this.id = id;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
        this.composants = composants;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double coutTotal(double coutTVA, double margeBeneficiaire) {

        double coutTotal;

        List<Composant> composants = getComposants();
        coutTotal = composants.stream()
                .mapToDouble(composant -> {
                    composant.setTauxTVA(coutTVA);
                    return coutTVA > 0 ? composant.calculeCout() : composant.calculCoutWithoutTva();
                })
                .sum();

        coutTotal += coutTotal * margeBeneficiaire;

        return coutTotal;
    }





}
