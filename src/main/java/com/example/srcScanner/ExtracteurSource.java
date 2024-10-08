package com.example.srcScanner;

import com.example.modele.*;

public class ExtracteurSource {

    public static Regle extraireRegle(String line) {
        // Split prémisses et consequent
        String[] splitFleche = line.split("->");
        String consequentStr = splitFleche[1];

        // Création du consequent
        Consequent consequent = new Consequent(
                ExtracteurSource.extraireElement(consequentStr)
        );

        // Split elements prémisses
        String[] premisseStr = splitFleche[0].split(",");

        // Création des prémisses
        Premisse premisse = new Premisse();
        for (String elementStr : premisseStr) {
            premisse.ajouterElement(ExtracteurSource.extraireElement(elementStr));
        }

        // Création de la règle (lst premisses + consequent)
        return new Regle(
                null,
                premisse,
                consequent,
                "regle"
        );

    }

    public static Element extraireElement(String elementStr) {
        // Split attribut et valeur
        String attribut = elementStr.substring(0, elementStr.indexOf('('));
        String valeur = elementStr.substring(elementStr.indexOf('(') + 1, elementStr.indexOf(')'));

        // Création de l'élément
        return new Element(
                attribut,
                new Valeur(valeur),
                false
        );
    }
}
