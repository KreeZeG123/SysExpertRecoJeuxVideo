package com.example.modele;

import java.util.HashSet;
import java.util.Set;

public class BaseFaits implements Cloneable {

    private Set<Fait> faits;

    public BaseFaits() {
        this.faits = new HashSet<>();
    }

    public BaseFaits(Set<Fait> faits) {
        this.faits = faits;
    }

    public boolean contient(Fait fait) {
        return faits.contains(fait);
    }

    public boolean contient(Element element) {
        return faits.contains(new Fait(element));
    }

    /**
     * Méthode qui fait une copie superficielle d'une base de faits
     *
     * @return la copie superficielle de la base de faits
     * @throws CloneNotSupportedException clone non supporté
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseFaits copie = (BaseFaits) super.clone();
        copie.faits = new HashSet<>(this.faits);
        return copie;
    }
}
