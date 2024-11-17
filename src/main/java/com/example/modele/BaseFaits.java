package com.example.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BaseFaits implements Cloneable, Iterable<Fait> {

    private List<Fait> faits;

    public BaseFaits() {
        this.faits = new ArrayList<>();
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

    public boolean contient(List<Element> elements) {
        for (Element e : elements) {
            if (!contient(e)) {
                return false;
            }
        }
        return true;
    }

    public BaseFaits ajouterFait(Fait fait) {
        if (!this.faits.contains(fait)) {
            this.faits.add(fait);
        }
        return this;
    }

    public void ajouterFait(Consequent consequent) {
        for (Element element : consequent) {
            this.ajouterFait(new Fait(element));
        }
    }

    public HashMap<String, Integer> listerOccurencesAttributs() {
        HashMap<String, Integer> occurencesAttributs = new HashMap<>();
        for (Fait fait : faits) {
            int oldValue = occurencesAttributs.getOrDefault(fait.getMot(), 0);
            occurencesAttributs.put(fait.getMot(), oldValue + 1);
        }
        return occurencesAttributs;
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
