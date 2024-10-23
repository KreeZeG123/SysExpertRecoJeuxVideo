package com.example.srcScanner;

import com.example.modele.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ExtracteurSource {

    public static Regle extraireRegle(String line, BaseRegles BR) throws IllegalArgumentException {
        // Split prémisses et conséquent
        if (!line.contains("->")) {
            throw new IllegalArgumentException("La ligne doit contenir une flèche (->) pour séparer les prémisses et le conséquent.");
        }
        String[] splitFleche = line.split("->",-1);
        // Vérifie que nous avons bien deux parties après la séparation
        if (splitFleche.length != 2) {
            throw new IllegalArgumentException("La ligne doit contenir une partie prémisses et une partie conséquent.");
        }

        // Vérifie que les prémisses et le conséquent ne sont pas vides
        String premisseStr = splitFleche[0].trim();
        if (premisseStr.isEmpty()) {
            throw new IllegalArgumentException("La partie prémisses ne doit pas être vides.");
        }
        String consequentStr = splitFleche[1].trim();
        if (consequentStr.isEmpty()) {
            throw new IllegalArgumentException("La partie conséquent ne doit pas être vide.");
        }

        // Création du conséquent
        Consequent consequent = new Consequent(
                ExtracteurSource.extraireElement(consequentStr)
        );

        // Split éléments prémisses
        String[] premisseArray = premisseStr.split(",",-1);

        // Création des prémisses
        Premisse premisse = new Premisse();
        for (String elementStr : premisseArray) {
            if ( !elementStr.isEmpty()) {
                premisse.ajouterElement(ExtracteurSource.extraireElement(elementStr));
            }
            else {
                throw new IllegalArgumentException("Un element est vide après une virgule dans la partie prémisse !");
            }
        }

        // Nom de la regle
        String nomRegle = "R?";
        if ( BR != null) {
            nomRegle = "R"+BR.tailleBr();
        }

        // Création de la règle (lst prémisses + conséquent)
        return new Regle(
                BR,
                premisse,
                consequent,
                nomRegle
        );
    }

    public static Element extraireElement(String elementStrSrc) throws IllegalArgumentException {

        String elementStr = elementStrSrc.trim();

        // Vérifier s'il y a une négation (si l'élément commence par '!')
        boolean negation = false;
        if (elementStr.startsWith("!")) {
            negation = true;
            elementStr = elementStr.substring(1).trim();  // Retirer le '!' du début et trim les espaces
        }

        // Vérifier qu'il n'y a pas d'autre '!' dans la chaîne après avoir retiré celui au début
        if (elementStr.contains("!")) {
            throw new IllegalArgumentException("L'élément '" + elementStr + "' ne doit pas contenir plus d'un caractère '!' en début de chaîne.");
        }

        // Vérifier qu'il y a exactement une parenthèse ouvrante et une parenthèse fermante
        int nbParentheseOuverte = elementStr.length() - elementStr.replace("(", "").length();
        int nbParentheseFermee = elementStr.length() - elementStr.replace(")", "").length();
        if (nbParentheseOuverte != 1 || nbParentheseFermee != 1) {
            throw new IllegalArgumentException("L'élément '" + elementStr + "' doit contenir exactement une parenthèse ouvrante et une parenthèse fermante au format 'attribut(valeur)' !");
        }

        // Vérifie que la parenthèse ouvrante vient avant la parenthèse fermante
        int openParenIndex = elementStr.indexOf('(');
        int closeParenIndex = elementStr.indexOf(')');
        if (openParenIndex < 0 || closeParenIndex < 0 || openParenIndex > closeParenIndex) {
            throw new IllegalArgumentException("L'element '"+elementStr+"' doit être au format 'attribut(valeur)' !");
        }

        // Split attribut et valeur
        String attribut = elementStr.substring(0, openParenIndex).trim();
        String valeur = elementStr.substring(openParenIndex + 1, closeParenIndex).trim();

        // Vérifie que l'attribut et la valeur ne sont pas vides
        if (attribut.isEmpty()) {
            throw new IllegalArgumentException("L'attribut de l'element '"+elementStr+"' ne doit pas être vide.");
        }
        if (valeur.isEmpty()) {
            throw new IllegalArgumentException("La valeur de l'element '"+elementStr+"' ne doit pas être vide.");
        }

        // Création de l'élément
        return new Element(
                attribut,
                new Valeur(valeur),
                negation
        );
    }
}
