package com.example.modele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BaseRegles implements Iterable<Regle>, Cloneable {
    private Set<Regle> regles;

    public BaseRegles() {
        this.regles = new HashSet<Regle>();
    }

    public void ajouterRegle(Regle regle) {
        regles.add(regle);
    }

    public void supprimerRegle(Regle regle) {
        regles.remove(regle);
    }

    public void creerRegle(){

    }

    @Override
    public Iterator<Regle> iterator() {
        return  this.regles.iterator();
    }

    /**
     * Méthode qui fait une copie superficielle d'une base de règles
     *
     * @return la copie superficielle de la base de règles
     * @throws CloneNotSupportedException clone non supporté
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseRegles copie = (BaseRegles) super.clone();
        copie.regles = new HashSet<>(this.regles);
        return copie;
    }
}
