package com.example.srcScanner;

import com.example.modele.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SourceScanner {

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

        // Création de la base de connaissance
        BaseConnaissances BC = new BaseConnaissances(BR,BF);

        // Chargement Règles de Cohérences
        if ( !cheminCoherences.isEmpty() ) {
            try {
                chargerFichierCoherence(cheminCoherences, BC);
            } catch (IOException e) {
                System.out.println("Erreur : Le fichier de cohérence "+cheminCoherences+" n'a pas pu être chargé !");
                return null;
            }
        }

        // Création de la Base de Connaissance
        return BC;
    }

    public static BaseRegles chargerFichierRegles(String cheminFichier) throws IOException {
        // Ouvre le fichier de règles
        List<String>[] reglesSrc = lireFichier(cheminFichier);

        // Création de la base de règle
        BaseRegles BR = new BaseRegles();
        for (int i = 0; i < reglesSrc[1].size(); i++) {
            String numLigne = reglesSrc[0].get(i);
            String regleSrc = reglesSrc[1].get(i);
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
        // Ouvre le fichier de faits
        List<String>[] faitsSrc = lireFichier(cheminFichier);

        // Création de la base de faits
        BaseFaits BF = new BaseFaits();
        for (int i = 0; i < faitsSrc[1].size(); i++) {
            String numLigne = faitsSrc[0].get(i);
            String faitSrc = faitsSrc[1].get(i);
            try {
                BF.ajouterFait(ExtracteurSource.extraireFait(faitSrc));
            } catch (IllegalArgumentException e) {
                System.out.println("ligne "+numLigne+" : "+ e.getMessage());
            }
        }

        // Retourne la base de faits
        return BF;
    }

    private static void chargerFichierCoherence(String cheminFichier, BaseConnaissances BC) throws IOException {
        // Ouvre le fichier de faits
        List<String>[] coherencesSrc = lireFichier(cheminFichier);

        for (int i = 0; i < coherencesSrc[1].size(); i++) {
            String numLigne = coherencesSrc[0].get(i);
            String coherenceSrc = coherencesSrc[1].get(i);
            try {
                if (coherenceSrc.contains("MONO") || coherenceSrc.contains("MULTI")) {
                    ExtracteurSource.extraireTypeAttributCoherence(coherenceSrc, BC);
                    BC.getCoherence().ajouterInformation(coherenceSrc);
                }
                else {
                    BC.getBaseRegles().ajouterRegle(ExtracteurSource.extraireRegle(coherenceSrc,BC.getBaseRegles()));
                    BC.getCoherence().ajouterInformation(coherenceSrc);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ligne "+numLigne+" : "+ e.getMessage());
            }
        }
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

    public static void detecterMotsSimilaires(BaseConnaissances BC) {
        Set<String> mots = extrairesTousLesMots(BC);

        // Comparer chaque mot avec tous les autres dans le Set
        List<String> motsList = new ArrayList<>(mots);
        for (int i = 0; i < motsList.size(); i++) {
            String mot1 = motsList.get(i);
            for (int j = i + 1; j < motsList.size(); j++) {
                String mot2 = motsList.get(j);
                if (stringSontSimilaires(mot1, mot2)) {
                    System.out.println("Attention : Mots similaires détectés, possible erreur de frappe : '" + mot1 + "' et '" + mot2+"'");
                }
            }
        }
    }

    public static void detecterValeursSimilaires(BaseConnaissances BC) {
        Map<String, Set<String>> valeursParAttribut = extrairesToutesLesValeurs(BC);

        // Comparer les valeurs pour chaque attribut
        for (Map.Entry<String, Set<String>> entry : valeursParAttribut.entrySet()) {
            String attribut = entry.getKey();
            Set<String> valeurs = entry.getValue();

            List<String> valeursList = new ArrayList<>(valeurs);
            for (int i = 0; i < valeursList.size(); i++) {
                String valeur1 = valeursList.get(i);
                for (int j = i + 1; j < valeursList.size(); j++) {
                    String valeur2 = valeursList.get(j);
                    if (stringSontSimilaires(valeur1, valeur2)) {
                        System.out.println("Attention : Valeurs similaires détectées pour l'attribut '" + attribut + "', possible erreur de frappe ou de syntaxe : '" + valeur1 + "' et '" + valeur2+"'");
                    }
                }
            }
        }
    }

    private static Map<String, Set<String>> extrairesToutesLesValeurs(BaseConnaissances BC) {
        BaseRegles baseRegles = BC.getBaseRegles();
        BaseFaits baseFaits = BC.getBaseFaits();

        Map<String, Set<String>> valeursParAttribut = new HashMap<>();

        // Ajouter les valeurs des règles par attribut
        for (Regle regle : baseRegles) {
            for (Element e : regle.getPremisse()) {
                String attribut = e.getMot();
                String valeur = e.getValeur().toString();
                valeursParAttribut.computeIfAbsent(attribut, k -> new HashSet<>()).add(valeur);
            }
            for (Element e : regle.getConsequent()) {
                String attribut = e.getMot();
                String valeur = e.getValeur().toString();
                valeursParAttribut.computeIfAbsent(attribut, k -> new HashSet<>()).add(valeur);
            }
        }

        // Ajouter les valeurs des faits par attribut
        for (Fait fait : baseFaits) {
            String attribut = fait.getMot();
            String valeur = fait.getValeur().toString();
            valeursParAttribut.computeIfAbsent(attribut, k -> new HashSet<>()).add(valeur);
        }

        return valeursParAttribut;
    }

    private static Set<String> extrairesTousLesMots(BaseConnaissances BC) {
        BaseRegles baseRegles = BC.getBaseRegles();
        BaseFaits baseFaits = BC.getBaseFaits();

        // Rassembler tous les mots uniques dans un Set
        Set<String> tousLesMots = new HashSet<>();

        // Ajouter tous les mots des faits
        for (Fait fait : baseFaits) {
            tousLesMots.add(fait.getMot());
        }

        // Ajouter tous les mots des règles (dans prémisses et conséquents)
        for (Regle regle : baseRegles) {
            for (Element e : regle.getPremisse()) {
                tousLesMots.add(e.getMot());
            }
            for (Element e : regle.getConsequent()) {
                tousLesMots.add(e.getMot());
            }
        }
        return tousLesMots;
    }

    // Méthode pour comparer deux mots et vérifier leur similarité
    private static boolean stringSontSimilaires(String mot1, String mot2) {
        int seuil = 2; // Maximum de 2 modifications pour être considéré comme similaire
        int longueurMinimale = 3; // Longueur minimale des mots à comparer

        // Vérification de la longueur minimale
        if (mot1.length() < longueurMinimale || mot2.length() < longueurMinimale) {
            return false;
        }

        return calculerDistanceLevenshtein(mot1, mot2) <= seuil;
    }

    // Calcule la distance de Levenshtein entre deux mots
    private static int calculerDistanceLevenshtein(String mot1, String mot2) {
        int[][] dp = new int[mot1.length() + 1][mot2.length() + 1];

        for (int i = 0; i <= mot1.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= mot2.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= mot1.length(); i++) {
            for (int j = 1; j <= mot2.length(); j++) {
                int cost = (mot1.charAt(i - 1) == mot2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1, // Suppression
                        Math.min(dp[i][j - 1] + 1, // Insertion
                                dp[i - 1][j - 1] + cost)); // Substitution
            }
        }
        return dp[mot1.length()][mot2.length()];
    }

}
