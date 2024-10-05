package com.example.modele;

public class Consequent
{
    private Premisse premisse;

    public Consequent(Element element, BaseFaits baseFaits) {
        this.premisse = new Premisse(element);
    }

    @Override
    public String toString() {
        return premisse.toString();
    }
}
