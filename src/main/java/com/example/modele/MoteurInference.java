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
        int nbinf = 0;
        int nbIteration = 0;
        boolean stop = false;
        this.explications.clear();

        // Cloner la base de règles et la base de faits pour éviter les modifications directes
        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();

        // Vérification des incohérences crée par les règles de monovaluation sur la base de fait
        if ( this.BC.verifierCoherenceFaitMonoValue() ) {
            System.out.println("\nUne ou plusieurs incohérences on été détectées dans la base fait !");
            System.out.print("Souhaitez vous continuez l'inférence (oui/non) : ");

            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().contains("non")) {
                stop = true;
                System.out.println("\nL'inférence a été arrêté, voici la base de connaissance lors de l'arrêt :\n");
            }
            else {
                System.out.println();
            }
        }

        // Parcourt des différents groupes de règles
        List<BaseRegles> BRparPaquet = BR.getBrParPaquet();
        for (int indicePaquet = 0; indicePaquet < BRparPaquet.size() && !stop; indicePaquet++) {
            boolean inf = true;

            BaseRegles ensembleRegles = BRparPaquet.get(indicePaquet);
            if ( ensembleRegles.groupementParPaquet ) {
                System.out.println("Inférence sur le paquet ["+ensembleRegles.getRegles().get(0).getPaquet()+"]\n");
            }

            // Ensembles pour les règles à supprimer et les faits à ajouter
            Set<Regle> regleSupprimable = new HashSet<Regle>();
            Set<Consequent> consequentsAjoutable = new HashSet<>();

            // Boucle d'inférence jusqu'à saturation de la BF
            while (inf && !stop) {
                inf = false;

                // Parcoure les règles de l'ensemble de regles
                for (Regle r : ensembleRegles) {
                    boolean declanchable = true;

                    // Vérifie si une règle est déclenchable
                    List<Element> antecedant = r.getAntecedants();
                    for (int indiceAntecedant = 0; indiceAntecedant < antecedant.size() && declanchable; indiceAntecedant++) {
                        if (!BF.contient(antecedant.get(indiceAntecedant))) {
                            declanchable = false;
                            break;
                        }
                    }

                    // Applique la règle si elle est déclancheable
                    if (declanchable) {
                        inf = true;               // Marque qu'une inférence a eu lieu
                        regleSupprimable.add(r);
                        nbinf++;

                        // Vérifie la cohérence de la règle avant de l'appliquer
                        if (r.estCoherent(BF, BC)) {
                            consequentsAjoutable.add(r.getConsequent());
                            // Ajoute l'explication si le niveau d'explication est activé
                            if (nivExplication > 0) {
                                this.explications.add(new Explication(nbIteration, r));
                            }
                        } else {
                            // Gérer une incohérence détectée et demander confirmation pour continuer
                            System.out.println("Attention : Incohérence detectée via la règle \"" + r.toString() + "\"");
                            System.out.print("Souhaitez vous continuez l'inférence (oui/non) : ");

                            Scanner scanner = new Scanner(System.in);
                            if (scanner.nextLine().contains("non")) {
                                inf = false;
                                stop = true;
                                System.out.println("\nL'inférence a été arrêté, voici la base de connaissance lors de l'arrêt :\n");
                                break;
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
                        ensembleRegles.supprimerRegle(r);
                    }
                    regleSupprimable.clear();
                }

                nbIteration++;
            }
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
        this.explications.clear();
        System.out.println("On recherche si la prémisse " + b + " est demandable\n");
        return chainageArriereRecursif(b, BR, BF, 0);
    }

    public boolean chainageArriereRecursif(Consequent b, BaseRegles BR, BaseFaits BF, int nbIteration) {
        nbIteration++;
        Iterator<Regle> iterateurBR = BR.iterator();
        //cas 1
        if (BF.contient(b.getElements())) {
            explications.add(new Explication(nbIteration, new Regle(BR, new Premisse(new Element("BF", new Valeur("Contient"), false)), b, "#")));
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
