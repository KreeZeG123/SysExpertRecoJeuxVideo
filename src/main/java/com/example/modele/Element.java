package com.example.modele;

import java.util.Objects;

public class Element {

    private String mot;

    private Valeur valeur;

    private boolean negation;

    public Element(String mot, Valeur valeur, boolean negation) {
        this.mot = mot;
        this.valeur = valeur;
        this.negation = negation;
    }

    public String toString(){
        return (mot+"("+valeur.toString()+")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element element)) return false;
        return negation == element.negation && Objects.equals(mot, element.mot) && Objects.equals(valeur, element.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mot, valeur, negation);
    }
}
