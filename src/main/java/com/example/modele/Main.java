package com.example.modele;

import com.example.srcScanner.ExtracteurSource;
import com.example.srcScanner.SourceScanner;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Chargement des fichiers sources
        BaseConnaissances BC = SourceScanner.chargerFichiersSource(
                "rulestest.txt",
                "factstest.txt",
                "TODO"
        );
        if ( BC == null ) {
            System.out.println("Erreur : Chargement des fichiers sources impossible !");
            return;
        }

        System.out.println("==== Base Règles ====");
        for ( Regle regle : BC.getBaseRegles().listeTrieeParNumero()) {
            System.out.println(regle.toString());
        }

        System.out.println("==== Base Faits Initiale ====");
        for ( Fait fait : BC.getBaseFaits()) {
            System.out.println(fait.toString());
        }

        MoteurInference moteurInference = new MoteurInference(BC);

        try {
            BaseFaits result = moteurInference.chainageAvant();
            moteurInference.afficherTrace();
            System.out.println("==== Base Faits Finale ====");
            System.out.println(result.toString());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}