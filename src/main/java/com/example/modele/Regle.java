package com.example.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public List<Element> getAntecedants() {
        return new ArrayList<>(this.premisse.getElements());
    }

    public Consequent getConsequent() {
        return consequent;
    }

    @Override
    public String toString() {
        return nom +
                " : " +
                premisse.toString() +
                " => " +
                consequent.toString();
    }

    public String toStringSansNomRegle() {
        return premisse.toString() +
                " => " +
                consequent.toString();
    }

    public String toStringNumRegle() {
        return this.nom;
    }
}
