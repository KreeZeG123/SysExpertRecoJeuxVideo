package com.example.srcScanner;

import com.example.modele.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SourceScanner {

    public static HashMap<String, Set<String>> attr = new HashMap<>();

    public static void chargerFichiersSourceDepuisDossier(String cheminDossier) {
        // Ouvre le dossier

        // Charger les règles d'inférence

        // Chargle les règles d'incohérence

        // Charge les faits

        // Créeer la base de connaissance

        // Renvoie la base de connaissance
    }

    public static BaseConnaissances chargerFichiersSource(String cheminRegles, String cheminFaits, String cheminCoherences) {
        // Chargement Base Regles
        BaseRegles BR = null;
        try {
            BR = chargerFichierRegles(cheminRegles);
        } catch (IOException e) {
            System.out.println("Erreur : Le fichier de règles "+cheminRegles+" n'a pas pu être chargé !");
            return null;
        }

        // Chargement Base Faits
        BaseFaits BF = null;
        try {
            BF = chargerFichierFaits(cheminFaits);
        } catch (IOException e) {
            System.out.println("Erreur : Le fichier de faits "+cheminFaits+" n'a pas pu être chargé !");
            return null;
        }

        // Chargement Règles de Cohérences

        // Création de la Base de Connaissance
        return new BaseConnaissances(BR,BF);
    }

    public static BaseRegles chargerFichierRegles(String cheminFichier) throws IOException {
        // Ouvre le fichier
        List<String>[] reglesSrc = lireFichier(cheminFichier);

        // Création de la base de règle
        BaseRegles BR = new BaseRegles();
        for (int i = 0; i < reglesSrc[1].size(); i++) {
            String numLigne = reglesSrc[0].get(i);
            String regleSrc = reglesSrc[1].get(i);
            //System.out.println("l "+numLigne+" : "+regleSrc);
            try {
                Regle regle = ExtracteurSource.extraireRegle(regleSrc, BR);
                BR.ajouterRegle(regle);
            } catch (IllegalArgumentException e) {
                System.out.println("ligne "+numLigne+" : "+ e.getMessage());
            }
        }

        // Retourne la base de règle
        return BR;
    }

    public static BaseFaits chargerFichierFaits(String cheminFichier) throws IOException {
        // Ouvre le fichier
        List<String>[] reglesSrc = lireFichier(cheminFichier);

        // Création de la base de faits
        BaseFaits BF = new BaseFaits();
        for (int i = 0; i < reglesSrc[1].size(); i++) {
            String numLigne = reglesSrc[0].get(i);
            String regleSrc = reglesSrc[1].get(i);
            try {
                Element element = ExtracteurSource.extraireElement(regleSrc);
                BF.ajouterFait(new Fait(element));
            } catch (IllegalArgumentException e) {
                System.out.println("ligne "+numLigne+" : "+ e.getMessage());
            }
        }

        // Retourne la base de faits
        return BF;
    }

    // Méthode pour lire un fichier et retourner les lignes nettoyées
    private static List<String>[] lireFichier(String cheminFichier) throws IOException {
        List<String> lignesNettoyees = new ArrayList<>();
        List<String> numerosLignesSrc = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
        String ligne;
        int numeroLigne = 0;

        while ((ligne = reader.readLine()) != null) {
            numeroLigne++; // Incrémente le numéro de ligne
            String lignePropre = nettoyerLigne(ligne);
            if (!lignePropre.isEmpty()) {
                lignesNettoyees.add(lignePropre);
                numerosLignesSrc.add(String.valueOf(numeroLigne));
            }
        }
        reader.close();

        // Crée un tableau de listes pour renvoyer les résultats
        List<String>[] resultat = new List[2];
        resultat[0] = numerosLignesSrc; // Liste des numéros de ligne
        resultat[1] = lignesNettoyees; // Liste des lignes nettoyées
        return resultat;
    }

    // Nettoie une ligne en supprimant les commentaires et les espaces superflus
    private static String nettoyerLigne(String ligne) {
        ligne = ligne.trim();
        if (ligne.startsWith("#") || ligne.isEmpty()) {
            return "";
        }
        ligne = ligne.replaceAll(" ","");
        return ligne;
    }

}
