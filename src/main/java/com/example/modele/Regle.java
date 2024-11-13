package com.example.modele;

import com.example.modele.enumeration.TypeAttribut;

import java.util.ArrayList;
import java.util.List;

public class Regle {
    private Premisse premisse;

    private Consequent consequent;

    private String nom;

    private String paquet;

    private BaseRegles baseRegles;

    public Regle(BaseRegles baseRegles,Premisse premisse,Consequent consequent,String nom) {
        this.baseRegles = baseRegles;
        this.premisse = premisse;
        this.consequent = consequent;
        this.nom = nom;
        this.paquet = "?";
    }

    public void setPaquet(String paquet) {
        this.paquet = paquet;
    }

    public String getPaquet() {
        return paquet;
    }

    public boolean regleApplicable(){
        return true;
    }

    public Premisse getPremisse() {
        return premisse;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Element> getAntecedants() {
        return new ArrayList<>(this.premisse.getElements());
    }

    public Consequent getConsequent() {
        return consequent;
    }

    @Override
    public String toString() {
        return nom +
                " : " +
                premisse.toString() +
                " => " +
                consequent.toString();
    }

    public String toStringSansNomRegle() {
        return premisse.toString() +
                " => " +
                consequent.toString();
    }

    public String toStringNumRegle() {
        return this.nom;
    }

    public int getNombreDePremisses() {
        return this.premisse.getElements().size();
    }

    public boolean estCoherent(BaseFaits BF, BaseConnaissances BC) {
        List<Element> elements = new ArrayList<>(this.consequent.getElements());
        for (Element e : elements) {;
            String attribut = e.getMot();
            Valeur valeur = e.getValeur();
            // Detection d'une règle d'incohérence
            if (attribut.contains("INCOHERENT") ) {
                return false;
            }
            // Detection de fait avec d'autres valeurs alors que l'attribut est monovalué
            if (BC.getCoherence().obtenirTypeAttribut(attribut) == TypeAttribut.MONO) {
                Fait faitRecherchee = BF.contientAttribut(attribut);
                boolean erreurMonovaluation = (faitRecherchee != null) && !(valeur.equals(faitRecherchee.getValeur()));
                if ( erreurMonovaluation ) {
                    System.out.println("Attention : L'attribut monovalué \""+attribut+"\" a plusieurs valeurs en meme temps");
                }
                return !erreurMonovaluation;
            }
        }

        return true;
    }
}
