package com.example.srcScanner;

import com.example.modele.BaseRegles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SourceScanner {

    public static HashMap<String, Set<String>> attr = new HashMap<>();

    public static void loadSourceFiles(String folderPath) {
        // Ouvre le dossier

        // Charger les règles d'inférence

        // Chargle les règles d'incohérence

        // Charge les faits

        // Créeer la base de connaissance

        // Renvoie la base de connaissance
    }

    public static BaseRegles chargerFichierRegles(String cheminFichier) {
        // Ouvre le fichier
        List<String> reglesSrc;
        try {
            reglesSrc = lireFichier(cheminFichier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Création de la base de règle
        BaseRegles BR = new BaseRegles();
        for (String regleSrc : reglesSrc) {
            BR.ajouterRegle(ExtracteurSource.extraireRegle(regleSrc));
        }

        // Retourne la base de règle
        return BR;
    }

    // Méthode pour lire un fichier et retourner les lignes nettoyées
    private static List<String> lireFichier(String cheminFichier) throws IOException {
        List<String> lignes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String lignePropre = nettoyerLigne(ligne);
            if (!lignePropre.isEmpty()) {
                lignes.add(lignePropre);
            }
        }
        reader.close();
        return lignes;
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
