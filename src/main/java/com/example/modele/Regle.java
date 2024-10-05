package com.example.modele;

public class Regle {
    private Premisse premisse;

    private Consequent consequent;

    private String nom;

    private BaseRegles baseRegles;

    public Regle(BaseRegles baseRegles,Premisse premisse,Consequent consequent,String nom) {
        this.baseRegles = baseRegles;
        this.premisse = premisse;
        this.consequent = consequent;
        this.nom = nom;
    }

    public boolean regleApplicable(){
        return true;
    }

    public Premisse getPremisse() {
        return premisse;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nom);
        stringBuilder.append(" : ");
        stringBuilder.append(premisse.toString());
        stringBuilder.append(" => ");
        stringBuilder.append(consequent.toString());
        return stringBuilder.toString();
    }
}
