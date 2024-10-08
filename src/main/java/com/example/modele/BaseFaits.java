package com.example.modele;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BaseFaits implements Cloneable, Iterable<Fait> {

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
        for (Fait fait : faits) {
            if (
                    fait.getMot().equals(element.getMot()) &&
                    fait.getValeur().equals(element.getValeur()) &&
                    fait.getNegation() == element.getNegation()
            ) {
                return true;
            }
        }
        return false;
    }

    public BaseFaits ajouterFait(Fait fait) {
        this.faits.add(fait);
        return this;
    }

    public void ajouterFait(Consequent consequent) {
        for (Element element : consequent) {
            this.ajouterFait(new Fait(element));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Fait fait : faits) {
            sb.append(fait).append("\n");
        }
        return sb.toString();
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

    @Override
    public Iterator<Fait> iterator() {
        return this.faits.iterator();
    }
}
