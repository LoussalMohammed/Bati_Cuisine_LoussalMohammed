package org.app.Models.Entities;

public class Client {

    private String nom;
    private String address;
    private String phone;
    private boolean estProfessionnel;
    private double remise;

    public Client(String nom, String address, String phone, boolean estProfessionnel, double remise) {
        this.nom = nom;
        this.address = address;
        this.phone = phone;
        this.estProfessionnel = estProfessionnel;
        this.remise = remise;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String setPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getEstProfessionnel() {
        return estProfessionnel;
    }

    public void setEstProfessionnel(boolean estProfessionnel) {
        this.estProfessionnel = estProfessionnel;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }
}
