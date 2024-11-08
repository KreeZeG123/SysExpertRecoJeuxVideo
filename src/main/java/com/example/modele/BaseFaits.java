package com.example.modele;

import java.util.*;

public class BaseFaits implements Cloneable, Iterable<Fait> {

    private List<Fait> faits;

    public BaseFaits() {
        this.faits = new ArrayList<>();
    }

    public BaseFaits(List<Fait> faits) {
        this.faits = faits;
    }

    public boolean contient(Fait fait) {
        return faits.contains(fait);
    }

    public boolean contient(Element element) {
        for (Fait fait : faits) {
            if (
                fait.getMot().equals(element.getMot()) &&
                fait.getValeur().equals(element.getValeur())
            ) {
                return element.getNegation() == fait.getNegation();
            }
        }
        return element.getNegation();
    }

    public BaseFaits ajouterFait(Fait fait) {
        if ( !this.faits.contains(fait) ) {
            this.faits.add(fait);
        }
        return this;
    }

    public void ajouterFait(Consequent consequent) {
        for (Element element : consequent) {
            this.ajouterFait(new Fait(element));
        }
    }

    public List<Fait> getFaits() {
        return faits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Fait> iterator = faits.iterator();

        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append("\n");
            }
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
        copie.faits = new ArrayList<>(this.faits);
        return copie;
    }

    @Override
    public Iterator<Fait> iterator() {
        return this.faits.iterator();
    }

    public Fait contientAttribut(String mot) {
        for (Fait fait : faits) {
            if (fait.getMot().equals(mot)) {
                return fait;
            }
        }
        return null;
    }
}
