package com.example.modele;

import java.util.Objects;

public class Fait extends Element {

    public Fait(String mot, Valeur valeur, boolean negation) {
        super(mot, valeur, negation);
    }

    public Fait(String mot, String valeur, boolean negation) {
        super(mot, new Valeur(valeur), negation);
    }

    public Fait(Element element) {
        super(
                element.getMot(),
                element.getValeur(),
                element.getNegation()
        );
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
