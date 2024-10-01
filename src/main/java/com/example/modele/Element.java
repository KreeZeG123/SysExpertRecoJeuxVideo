package com.example.modele;

public class Element {
    private String mot;
    private int valeur;
    private boolean negation;

    public String toString(){
        return (mot+"("+valeur+")");
    }
}
