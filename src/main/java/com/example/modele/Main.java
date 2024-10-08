package com.example.modele;

import com.example.srcScanner.SourceScanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BaseRegles BR = SourceScanner.chargerFichierRegles("rulestest.txt");
        BaseFaits BF = new BaseFaits();
        BF
            .ajouterFait(new Fait("Slave","true",false))
            .ajouterFait(new Fait("Responsabilite","true",false));

        BaseConnaissances BC = new BaseConnaissances(BR,BF);

        for ( Fait fait : BC.getBaseFaits()) {
            System.out.println(fait.toString());
        }

        MoteurInference moteurInference = new MoteurInference(BC);

        try {
            BaseFaits result = moteurInference.chainageAvant();
            moteurInference.afficherTrace();
            System.out.println("==== Base Fait Resultat ====");
            System.out.println(result.toString());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}