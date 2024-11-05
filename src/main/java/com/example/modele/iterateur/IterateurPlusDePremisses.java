package com.example.modele.iterateur;

import com.example.modele.Regle;

import java.util.Iterator;
import java.util.List;

public class IterateurPlusDePremisses implements Iterator<Regle> {

    private final Iterator<Regle> iterateurInterne;

    public IterateurPlusDePremisses(List<Regle> listRegles) {
        List<Regle> listTriee = listRegles.stream()
                .sorted((r1, r2) -> Integer.compare(r2.getNombreDePremisses(), r1.getNombreDePremisses()))
                .toList();

        this.iterateurInterne = listTriee.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterateurInterne.hasNext();
    }

    @Override
    public Regle next() {
        return iterateurInterne.next();
    }

}
