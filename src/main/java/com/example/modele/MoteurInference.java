package com.example.modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoteurInference {

    private BaseConnaissances BC;

    private List<Regle> explications;

    public MoteurInference(BaseConnaissances BC) {
        this.BC = BC;
        this.explications = new ArrayList<Regle>();
    }

    public BaseFaits chainageAvant() throws CloneNotSupportedException {
        boolean inf = true;
        int nbinf = 0;

        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();
        this.explications.clear();

        // Boucle qui fait des inférences jusqu'à saturer la BF
        Set<Regle> regleSupprimable = new HashSet<Regle>();
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
                    BF.ajouterFait(r.getConsequent());
                    regleSupprimable.add(r);
                    inf = true;
                    nbinf++;
                    this.explications.add(r);
                }
            }
            // Suprimer les regles applicables
            for (Regle r : regleSupprimable) {
                BR.supprimerRegle(r);
            }
        }

        return BF;
    }

    public void afficherTrace() {
        System.out.println("===== Trace =====");
        for (int i = 0; i < this.explications.size(); i++) {
            System.out.println(i+" : "+this.explications.get(i).toStringSansNomRegle());
        }
    }

    public String chainageArriere() {
        return "";
    }

    public void modifierGroupementPaquet(Boolean etat) {

    }

    public void modifierChoixRegle(String choix) {

    }

    public void modifierModeExplication(String mode) {

    }

    public void chargerBC() {

    }
}
