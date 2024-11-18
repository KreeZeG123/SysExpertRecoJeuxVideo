package com.example.modele;

import java.util.Objects;

public class Valeur {

    private final String valeur;

    public Valeur(String valeur) {
        this.valeur = valeur;
    }

    public Boolean evaluer(Valeur autreValeur) {
        try {
            // Si les deux valeurs sont numériques, utiliser une comparaison numérique
            if (isNumericOrOperator(this.valeur) && isNumericOrOperator(autreValeur.valeur)) {
                return compareWithOperator(this.valeur, autreValeur.valeur);
            } else {
                // Sinon, comparer comme des chaînes de caractères
                return this.valeur.equals(autreValeur.valeur);
            }
        } catch (NumberFormatException e) {
            return false; // En cas d'erreur, la comparaison échoue
        }
    }

    @Override
    public String toString() {
        return this.valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Valeur autreValeur)) return false;

        // Si les deux valeurs sont strictement égales (texte brut), pas besoin de traitement
        if (Objects.equals(valeur, autreValeur.valeur)) return true;

        // Vérifie si les deux valeurs respectent la condition
        return evaluer(autreValeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    private boolean compareWithOperator(String v1, String v2) throws NumberFormatException {
        Double valeur1 = parseValue(v1);
        Double valeur2 = parseValue(v2);

        // Vérifie chaque combinaison d'opérateurs
        if (v1.startsWith(">=")) {
            return valeur2 >= valeur1;
        } else if (v2.startsWith(">=")) {
            return valeur1 >= valeur2;
        } else if (v1.startsWith("<=")) {
            return valeur2 <= valeur1;
        } else if (v2.startsWith("<=")) {
            return valeur1 <= valeur2;
        } else if (v1.startsWith(">")) {
            return valeur2 > valeur1;
        } else if (v2.startsWith(">")) {
            return valeur1 > valeur2;
        } else if (v1.startsWith("<")) {
            return valeur2 < valeur1;
        } else if (v2.startsWith("<")) {
            return valeur1 < valeur2;
        }

        // Si aucun opérateur, comparaison stricte d'égalité
        return Objects.equals(valeur1, valeur2);
    }

    private Double parseValue(String value) throws NumberFormatException {
        // Supprime les opérateurs pour extraire la valeur numérique
        String numericPart = value.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numericPart);
    }

    private boolean isNumericOrOperator(String value) {
        // Vérifie si la chaîne est entièrement numérique ou contient un opérateur suivi de chiffres
        return value.matches("^(>=|<=|>|<)?\\d+(\\.\\d+)?$");
    }
}
