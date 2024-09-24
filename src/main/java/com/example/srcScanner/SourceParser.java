package com.example.srcScanner;

public class SourceParser {

    public static String parseRule(String line) {
        // Split prémise et consequent
        String[] splitFleche = line.split("->");
        String consequent = splitFleche[1];

        // !!!! TEMPORAIRE !!!!
        StringBuilder result = new StringBuilder();
        result.append(consequent + " -: ");

        // Split prémisses
        String[] premisses = splitFleche[0].split(",");

        // Création des prémisses
        //List<Premisse> premisses = new ArrayList<>();
        for (String premisse : premisses) {
            // Split attribut et valeur
            String attribut = premisse.substring(0, premisse.indexOf('('));
            String valeur = premisse.substring(premisse.indexOf('(') + 1, premisse.indexOf(')'));

            // Création de l'attribut et sa valeur

            // Ajout de la prémise a la liste

            // !!!! TEMPORAIRE !!!!
            result.append(attribut+"{"+valeur+"}"+",");
        }

        // Création de la règle (lst premisses + consequent)
        // return new Regle(premisses,consequent)

        // !!!! TEMPORAIRE !!!!
        return result.toString();

    }


}
