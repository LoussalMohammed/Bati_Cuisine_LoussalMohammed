package org.app.Models.Enums;

public enum TypeComposant {

    MAINDOEUVER("MAINDOEUVER"),
    MATERIAL("MATERIAL");

    private String typeComposant;

    TypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }

    public String getTypeComposant() {
        return typeComposant;
    }

    public static TypeComposant fromString(String typeComposant) {
        for (TypeComposant t: TypeComposant.values()) {
            if(t.getTypeComposant().equalsIgnoreCase(typeComposant)) {
                return t;
            }
        }
        throw new IllegalArgumentException("there is no enum constant typeComposant for: "+ typeComposant);
    }
}
