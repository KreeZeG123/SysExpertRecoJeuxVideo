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

    public ArrayList<Boolean> chainageArriere(ArrayList<Element> b) throws CloneNotSupportedException {
        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();
        ArrayList<Boolean> results = new ArrayList<>();
        //Consequent consequent = new Consequent(new Element("Neerlandais", new Valeur("true"),false));
        //Element e = new Element("Allemand", new Valeur("false"), false);
        //consequent.ajouterElement(e);
        //.ajouterRegle(new Premisse(new Element("Netherland", new Valeur("true"), false)), consequent);
        this.explications.clear();
        System.out.println("On recherche si " + b.toString() + " est demandable\n");
        for(Element e : b){
            System.out.println("----------------------------------------");
            System.out.println("On recherche si " + e + " est demandable");
            System.out.println("----------------------------------------");
            results.add(chainageArriereRecursif(e, BR, BF, 0));
        }
        return results;
    }

    public boolean chainageArriereRecursif(Element b, BaseRegles BR, BaseFaits BF, int nbIteration) throws CloneNotSupportedException {
        nbIteration++;
        Iterator<Regle> iterateurBR = BR.iterator();
        BaseFaits BFtemp= (BaseFaits) this.BC.getBaseFaits().clone();
        BFtemp.ajouterFait(new Fait(b.getMot(),b.getValeur(),b.getNegation()));
        if(BF.contient(b)){
            System.out.println(b + "est déjà dans la BF");
            return true;
        }else{
            System.out.println(b + " n'est pas dans la BF, on doit le rechercher\n");
        }
        //Deuxième cas : si b est demandable avec une règle
        while (iterateurBR.hasNext()) {
            Regle r = iterateurBR.next();
            if(r.getConsequent().contient(b) && BFtemp.contient(r.getConsequent().getElements())){
                System.out.println("La regle " + r.toStringSansNomRegle() + " a pour consequent " + r.getConsequent().getElements().toString());
                if(BFtemp.contient(r.getAntecedants())) {
                    System.out.println(b + " est demandable avec la regle " + r.toStringSansNomRegle() + "\n");
                    this.explications.add(new Explication(nbIteration, r));
                    return true;
                }
                else{
                    this.explications.add(new Explication(nbIteration, r));
                    for (Element e : r.getAntecedants()){
                        System.out.println("On recherche si " + e + " est demandable\n");
                        return chainageArriereRecursif(e, BR, BF, nbIteration);

                    }

                }
            }
        }
        System.out.println("Aucune règle permet de demander " + b);
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
