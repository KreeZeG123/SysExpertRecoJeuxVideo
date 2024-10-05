package com.example.modele;

import java.io.Serializable;

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
}
