package com.example.modele;

public class
Explication {

    private final int numInference;

    private final Regle regle;

    public Explication(int numInference, Regle regle) {
        this.numInference = numInference;
        this.regle = regle;
    }

    public int getNumInference() {
        return numInference;
    }

    public Regle getRegle() {
        return regle;
    }
}
