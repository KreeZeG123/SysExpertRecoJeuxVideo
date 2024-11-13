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
                for ( int i = 0; i < antecedant.size() && declanchable; i++) {
                    if ( !BF.contient(antecedant.get(i))) {
                        declanchable = false;
                    }
                }
                // Applique la règle si elle est déclancheable
                if (declanchable) {
                    regleSupprimable.add(r);
                    inf = true;
                    nbinf++;
                    if ( r.estCoherent(BF, BC) ) {
                        consequentsAjoutable.add(r.getConsequent());
                        if (nivExplication > 0) {
                            this.explications.add(new Explication(nbIteration, r));
                        }
                    } else {
                        System.out.println("Attention : Incohérence detectée via la règle \""+r.toString()+"\"");
                        System.out.print("Souhaitez vous continuez l'inférence (oui/non) : ");
                        Scanner scanner = new Scanner(System.in);
                        if ( scanner.nextLine().contains("non") ) {
                            inf = false;
                            System.out.println("\nL'inférence a été arrêté, voici la base de connaisance lors de l'arrêt :\n");
                        }else {
                            System.out.println();
                        }
                    }

                }
            }
            // Ajouter les nouveaux faits à la BF
            if ( !consequentsAjoutable.isEmpty() ) {
                for (Consequent c : consequentsAjoutable ) {
                    BF.ajouterFait(c);
                }
                consequentsAjoutable.clear();
            }
            // Supprimer les règles déja appliquée de la BR
            if ( !regleSupprimable.isEmpty() ) {
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
                    System.out.println("Iteration "+explication.getNumInference()+" - "+explication.getRegle().toString());
                }
            }
            case 2 -> {
                System.out.println("----- Trace Abrégées -----");
                for (int i = 0; i < this.explications.size(); i++) {
                    Explication explication = this.explications.get(i);
                    System.out.println("Iteration "+explication.getNumInference()+" - "+explication.getRegle().toStringNumRegle());
                }
            }
            default -> {
            }
        }
    }

    public void chainageArriere(Premisse b) throws CloneNotSupportedException {
        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();

        //Antecedent
        Consequent consequent = new Consequent(new Element("Cool", new Valeur("true"), false));
        Element elem1 = new Element("Accepte", new Valeur("true"), false);
        consequent.ajouterElement(elem1);

        //Consequent
        Premisse antecedent = new Premisse(new Element("Adaptabilite", new Valeur("true"), false));
        Element elem2 = new Element("Leadership", new Valeur("true"), false);
        antecedent.ajouterElement(elem2);

        BR.ajouterRegle(antecedent, consequent);

        Iterator<Regle> iterateurBR = BR.iterator();
        Iterator<Element> iterateurElements = b.iterator();
        this.explications.clear();
        System.out.println("Démarrage arrière");
        boolean demandable;
        //Premier cas : b est dans BF
        demandable = BF.contient(b.getElements());
        if (demandable) {
            System.out.println("b est dans BF");
        }

        //2ème cas : si b est déductible à partir de BR U BF
        while (iterateurBR.hasNext() && !demandable) {
            Regle r = iterateurBR.next();
            System.out.println(r);
            //Si les elements de b sont les consequent d'une règle, on recherche si les antécédents de cette règles sont dans la BF
            if (r.getConsequent().equalsListElement(b.getElements())) { //On vérifie si b est un consequent d'une règle
                demandable = BF.contient(r.getAntecedants());
            }
            if (demandable) {
                System.out.println("\nb est demandable avec la règle" + r.toStringSansNomRegle());
            }
        }

        //3ème cas : si b est demandable
        boolean regleActivable = true;
        //Tant qu'on n'a pas trouvé et qu'une règle est applicable
        while (!demandable && regleActivable) {
            iterateurBR = BR.iterator();
            //On parcourt les règles et on regarde si chaque element de chaque antécédents est dans la BF
            while (iterateurBR.hasNext() && !demandable) {
                Regle r = iterateurBR.next();
                //Si la base de fait contient les antécédents de la regle et que les consequent de la regle est b, alors b est demandable;
                demandable = BF.contient(r.getAntecedants()) && r.getConsequent().equalsListElement(b.getElements());
                //Si la base de fait contient les antécédents de la regle, alors une règle est applicable
                regleActivable = BF.contient(r.getAntecedants());

                BF.ajouterFait(r.getConsequent());
            }
        }

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
