package org.app.Models.Enums;

public enum StatusProjet {
    ENCOURS("ENCOURS"),
    TERMINE("TERMINE"),
    ANNULE("ANNULE");

    private String statusProjet;

    StatusProjet(String statusProjet) {
        this.statusProjet = statusProjet;
    }

    public String getStatusProjet() {
        return statusProjet;
    }

    public static StatusProjet fromString(String statusProjet) {
        for(StatusProjet s: StatusProjet.values()) {
            if(s.getStatusProjet().equalsIgnoreCase(statusProjet)) {
                return s;
            }
        } throw new IllegalArgumentException("no enum constant for statusProjet:"+ statusProjet);
    }


}
