package com.example.modele;

import java.util.Set;

public class Premisse {
    private Set<Element> elements;
    private BaseFaits baseFaits;

    public Premisse(Set<Element> elements,Boolean negatif, BaseFaits baseFaits){
        this.elements = elements;
        this.baseFaits = baseFaits;
    }

    public boolean premisseValide(){
        return true;
    }
}
