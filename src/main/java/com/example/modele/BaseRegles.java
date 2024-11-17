package com.example.modele;

import com.example.modele.iterateur.IterateurFaitRecent;
import com.example.modele.iterateur.IterateurPlusDePremisses;

import java.util.*;

public class BaseRegles implements Iterable<Regle>, Cloneable {

    private List<Regle> regles;

    private BaseConnaissances BC = null;

    public boolean groupementParPaquet = false;

    public BaseRegles() {
        this.regles = new ArrayList<>();
    }

    public BaseRegles(boolean groupementParPaquet) {
        this.regles = new ArrayList<>();
        this.groupementParPaquet = groupementParPaquet;
    }

    public List<Regle> getRegles() {
        return regles;
    }

    public BaseConnaissances getBC() {
        return BC;
    }

    public void setBC(BaseConnaissances BC) {
        this.BC = BC;
    }

    public void ajouterRegle(Regle regle) {
        regles.add(regle);
    }

    public void ajouterRegle(List<Regle> regles) {
        this.regles.addAll(regles);
    }

    public void supprimerRegle(Regle regle) {
        regles.remove(regle);
    }

    public void creerRegle(){

    }

    @Override
    public Iterator<Regle> iterator() {
        if ( this.BC == null ) {
            return regles.iterator();
        } else {
            switch (this.BC.choixRegle) {
                case PLUS_DE_PREMISSES -> {
                    return new IterateurPlusDePremisses(this.regles);
                }
                case FAIT_RECENT -> {
                    return new IterateurFaitRecent(this.regles,this.BC.getBaseFaits());
                }
                default -> {
                    return regles.iterator();
                }
            }
        }
    }

    public BaseRegles ajouterRegle(Premisse antecedent, Consequent consequent){
        regles.add(new Regle(this, antecedent, consequent, "R?"));
        return this;
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
        copie.regles = new ArrayList<>(this.regles);
        copie.BC = this.BC;
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

    @Override
    public String toString() {
        return "BaseRegles{" +
                "regles=" + regles +
                ", BC=" + BC +
                ", groupementParPaquet=" + groupementParPaquet +
                '}';
    }

    public List<BaseRegles> getBrParPaquet() {

        if ( this.BC != null && !this.groupementParPaquet ) {
            return List.of(this);
        }

        // Map pour regrouper les règles par paquet
        Map<String, List<Regle>> reglesParPaquet = new HashMap<>();

        // Parcourir les règles et les organiser par paquet
        for (Regle regle : this.regles) {
            String paquet = regle.getPaquet();

            // Si le paquet n'existe pas encore dans la Map, on crée une nouvelle liste
            reglesParPaquet.computeIfAbsent(paquet, k -> new ArrayList<>()).add(regle);
        }

        // Créer la liste de BaseRegles pour chaque paquet
        List<BaseRegles> baseReglesParPaquet = new ArrayList<>();
        for (Map.Entry<String, List<Regle>> entry : reglesParPaquet.entrySet()) {
            BaseRegles br = new BaseRegles(this.groupementParPaquet);
            br.ajouterRegle(entry.getValue());
            baseReglesParPaquet.add(br);
        }

        // Tri de la liste baseReglesParPaquet selon les règles spécifiées
        baseReglesParPaquet.sort(new Comparator<BaseRegles>() {
            @Override
            public int compare(BaseRegles br1, BaseRegles br2) {
                String paquet1 = br1.getRegles().get(0).getPaquet();
                String paquet2 = br2.getRegles().get(0).getPaquet();

                // Détection du type de paquet
                boolean isNumeric1 = paquet1.matches("\\d+");
                boolean isAlpha1 = paquet1.matches("[a-zA-Z]+");
                boolean isNumeric2 = paquet2.matches("\\d+");
                boolean isAlpha2 = paquet2.matches("[a-zA-Z]+");

                // Si les deux paquets sont numériques, on les compare numériquement
                if (isNumeric1 && isNumeric2) {
                    return Integer.compare(Integer.parseInt(paquet1), Integer.parseInt(paquet2));
                }
                // Si l'un est numérique et l'autre ne l'est pas, on priorise le numérique
                if (isNumeric1) return -1;
                if (isNumeric2) return 1;

                // Si les deux paquets sont alphabétiques, on les compare alphabétiquement
                if (isAlpha1 && isAlpha2) {
                    return paquet1.compareToIgnoreCase(paquet2);
                }
                // Si l'un est alphabétique et l'autre non, on priorise l'alphabétique
                if (isAlpha1) return -1;
                if (isAlpha2) return 1;

                // Comparaison alphabétique des symboles pour le reste
                return paquet1.compareTo(paquet2);
            }
        });

        return baseReglesParPaquet;
    }

    public boolean isPaquetUsed() {
        boolean paquetUsed = false;
        for (Regle regle : this.regles) {
            if ( regle.getPaquet() != null & !regle.getPaquet().contains("?") ) {
                paquetUsed = true;
            }
        }
        return paquetUsed;
    }
}
