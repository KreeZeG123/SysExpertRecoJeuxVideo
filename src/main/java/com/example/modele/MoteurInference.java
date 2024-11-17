package com.example.modele;

import java.util.*;

public class MoteurInference {

    private BaseConnaissances BC;

    private List<Explication> explications;

    // Temporaire
    private int nivExplication = 1;

    public MoteurInference(BaseConnaissances BC) {
        this.BC = BC;
        this.explications = new ArrayList<Explication>();
    }

    public BaseFaits chainageAvant() throws CloneNotSupportedException {
        boolean inf = true;
        int nbinf = 0;
        int nbIteration = 0;

        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();
        this.explications.clear();

        // Boucle qui fait des inférences jusqu'à saturer la BF
        Set<Regle> regleSupprimable = new HashSet<Regle>();
        Set<Consequent> consequentsAjoutable = new HashSet<>();
        while (inf) {
            inf = false;
            // Parcoure les règles de la BR
            for (Regle r : BR) {
                boolean declanchable = true;
                int indiceAntecedant = 0;
                List<Element> antecedant = r.getAntecedants();
                // Vérifie si une règle est déclenchable
                for (int i = 0; i < antecedant.size() && declanchable; i++) {
                    if (!BF.contient(antecedant.get(i))) {
                        declanchable = false;
                    }
                }
                // Applique la règle si elle est déclancheable
                if (declanchable) {
                    regleSupprimable.add(r);
                    inf = true;
                    nbinf++;
                    if (r.estCoherent(BF, BC)) {
                        consequentsAjoutable.add(r.getConsequent());
                        if (nivExplication > 0) {
                            this.explications.add(new Explication(nbIteration, r));
                        }
                    } else {
                        System.out.println("Attention : Incohérence detectée via la règle \"" + r.toString() + "\"");
                        System.out.print("Souhaitez vous continuez l'inférence (oui/non) : ");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.nextLine().contains("non")) {
                            inf = false;
                            System.out.println("\nL'inférence a été arrêté, voici la base de connaisance lors de l'arrêt :\n");
                        } else {
                            System.out.println();
                        }
                    }

                }
            }
            // Ajouter les nouveaux faits à la BF
            if (!consequentsAjoutable.isEmpty()) {
                for (Consequent c : consequentsAjoutable) {
                    BF.ajouterFait(c);
                }
                consequentsAjoutable.clear();
            }
            // Supprimer les règles déja appliquée de la BR
            if (!regleSupprimable.isEmpty()) {
                for (Regle r : regleSupprimable) {
                    BR.supprimerRegle(r);
                }
                regleSupprimable.clear();
            }
            nbIteration++;
        }

        return BF;
    }

    public void afficherTrace(int nivExplication) {

        switch (nivExplication) {
            case 1 -> {
                System.out.println("----- Trace Complete -----");
                for (int i = 0; i < this.explications.size(); i++) {
                    Explication explication = this.explications.get(i);
                    System.out.println("Iteration " + explication.getNumInference() + " - " + explication.getRegle().toString());
                }
            }
            case 2 -> {
                System.out.println("----- Trace Abrégées -----");
                for (int i = 0; i < this.explications.size(); i++) {
                    Explication explication = this.explications.get(i);
                    System.out.println("Iteration " + explication.getNumInference() + " - " + explication.getRegle().toStringNumRegle());
                }
            }
            default -> {
            }
        }
    }

    public Boolean chainageArriere(Consequent b) throws CloneNotSupportedException {
        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();
        Iterator<Regle> iterateurBR = BR.iterator();
        Iterator<Element> iterateurPremisse = b.getElements().iterator();
        ArrayList<Boolean> results = new ArrayList<>();
        this.explications.clear();
        System.out.println("On recherche si la prémisse " + b + " est demandable\n");
        return chainageArriereRecursif(b, BR, BF, 0);
    }

    public boolean chainageArriereRecursif(Consequent b, BaseRegles BR, BaseFaits BF, int nbIteration) throws CloneNotSupportedException {
        nbIteration++;
        Iterator<Regle> iterateurBR = BR.iterator();
        //cas 1
        if (BF.contient(b.getElements())) {
            return true;
        }
        while (iterateurBR.hasNext()) {
            //On regarde regle par regle
            Regle r = iterateurBR.next();
            //Si le consequent de r contient tous les elements de b, on regarde si l'antecedent est dans la BF. Si oui b est demandable sinon on cherche les antécédents de r
            if (r.getConsequent().contient(b.getElements())) {
                //Si la BF contient tous les antécédents, cas 2
                if (BF.contient(r.getAntecedants())) {
                    this.explications.add(new Explication(nbIteration, r));
                    return true;
                }
                //Si la BF ne contient pas tous les antécédents, on les cherche, cas 3
                else {
                    this.explications.add(new Explication(nbIteration, r));
                    ArrayList<Boolean> antecedentsDemandable = new ArrayList<>();
                    //On recherche si les antecedents de la regle sont demandables
                    for (Element elem : r.getAntecedants()) {
                        if (!chainageArriereRecursif(new Consequent(elem), BR, BF, nbIteration)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public void modifierGroupementPaquet(boolean etat) {

    }

    public void modifierChoixRegle(String choix) {

    }

    public void modifierModeExplication(String mode) {

    }

    public int getNivExplication() {
        return nivExplication;
    }

    public void setNivExplication(int nivExplication) {
        this.nivExplication = nivExplication;
    }

    public BaseConnaissances getBC() {
        return BC;
    }

    public void test() throws CloneNotSupportedException {
        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();

        for (Regle r : BR) {
            System.out.println(r.toString());
        }
    }
}
