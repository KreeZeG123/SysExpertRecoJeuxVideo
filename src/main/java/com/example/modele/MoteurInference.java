package com.example.modele;

public class MoteurInference {

    private BaseConnaissances BC;

    public MoteurInference(BaseConnaissances BC) {
        this.BC = BC;
    }

    public BaseFaits chainageAvant() throws CloneNotSupportedException {
        boolean inf = true;
        int nbinf = 0;
        BaseRegles BR = (BaseRegles) this.BC.getBaseRegles().clone();
        BaseFaits BF = (BaseFaits) this.BC.getBaseFaits().clone();

        // Boucle qui fait des inférences jusqu'à saturer la BF
        while (inf) {
            inf = false;
            // Parcoure les règles de la BR
            for (Regle r : BR) {
                boolean declanchable = true;
                // Vérifie si une règle est déclenchable
                while (declanchable) {
                    for (Element m : r.getPremisse()) {
                        if ( !BF.contient(m) ) {
                            // ? Conditions du if à revoir ?
                            declanchable = false;
                        }
                    }
                    if (declanchable) {
                        // A finir
                    }
                }
            }
        }

        return BF;
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
