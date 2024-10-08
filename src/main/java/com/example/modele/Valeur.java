package com.example.modele;

import java.io.Serializable;
import java.util.Objects;

public class Valeur implements InterfaceValeur{

    private String valeur;

    public Valeur(String valeur) {
        this.valeur = valeur;
    }

    public Boolean evaluer(Valeur valeur){
        return this.valeur.equals(valeur.getValeur());
    }

    public String getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return this.valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Valeur valeur1)) return false;
        return Objects.equals(valeur, valeur1.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valeur);
    }
}
