package com.example.modele;

import com.example.srcScanner.SourceScanner;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Chargement des règles
        String fichierRegles = "rulestest.txt";
        BaseRegles BR = null;
        try {
            BR = SourceScanner.chargerFichierRegles(fichierRegles);
        } catch (IOException e) {
            System.out.println("Erreur : Le fichier de règles "+fichierRegles+" n'a pas pu être chargé !");
            return;
        }

        // Chargement des faits
        String fichierFaits = "";
        BaseFaits BF = null;
        try {
            BF = SourceScanner.chargerFichierFaits(fichierFaits);
        } catch (IOException e) {
            System.out.println("Erreur : Le fichier de faits "+fichierFaits+" n'a pas pu être chargé !");
            return;
        }

        BF.ajouterFait(new Fait("Slave","true",false))
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