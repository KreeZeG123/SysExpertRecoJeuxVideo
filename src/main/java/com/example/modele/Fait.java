package com.example.modele;

public class Fait extends Element {

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
