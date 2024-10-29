package com.example.modele;

import java.util.*;

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

    public int tailleBr() {
        return this.regles.size();
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

    public Iterable<Regle> listeTrieeParNumero() {
        // Créer une liste a partir du set de regles
        List<Regle> reglesTriees = new ArrayList<>(regles);

        // Trie la liste en fonction du numéro de la regle dans son nom
        reglesTriees.sort(Comparator.comparing(this::extraireNumeroRegle)
                .thenComparing(Regle::getNom));

        // Retourner une liste qui est itérable
        return reglesTriees;
    }

    // Méthode pour extraire le numéro d'une règle, renvoie Integer.MAX_VALUE si le format ne correspond pas
    private int extraireNumeroRegle(Regle regle) {
        String nom = regle.getNom();
        if (nom.startsWith("R")) {
            try {
                return Integer.parseInt(nom.substring(1));
            } catch (NumberFormatException e) {
                // Si le numéro n'est pas un entier valide
                return Integer.MAX_VALUE;
            }
        }
        // Si la règle ne suit pas le format "R"+nombre
        return Integer.MAX_VALUE;
    }
}
