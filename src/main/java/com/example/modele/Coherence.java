package com.example.modele;

import com.example.modele.enumeration.TypeAttribut;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Coherence {

    private final List<String> infosCoherence;
    private HashSet<String> attributsMono;
    private HashSet<String> attributsMulti;
    private TypeAttribut all = TypeAttribut.INCONNU;

    public Coherence() {
        this.attributsMono = new HashSet<>();
        this.attributsMulti = new HashSet<>();
        this.infosCoherence = new ArrayList<>();
    }

    public static TypeAttribut invert(TypeAttribut type) {
        switch (type) {
            case MONO -> {
                return TypeAttribut.MULTI;
            }
            case MULTI -> {
                return TypeAttribut.MONO;
            }
            default -> {
                return TypeAttribut.INCONNU;
            }
        }
    }

    public void ajouterTypeAttribut(String mot, TypeAttribut type) {
        switch (type) {
            case MONO -> {
                if (this.attributsMono == null) {
                    this.attributsMono = new HashSet<>();
                }
                this.attributsMono.add(mot);
            }
            case MULTI -> {
                if (this.attributsMulti == null) {
                    this.attributsMulti = new HashSet<>();
                }
                this.attributsMulti.add(mot);
            }
            default -> {
                System.out.println("Attention : le type d'attribut \"" + type + "\" n'existe pas");
            }
        }
    }

    public TypeAttribut obtenirTypeAttribut(String mot) {

        if (this.attributsMono != null) {
            if (this.attributsMono.contains(mot)) {
                return TypeAttribut.MONO;
            }
        }

        if (this.attributsMulti != null) {
            if (this.attributsMulti.contains(mot)) {
                return TypeAttribut.MULTI;
            }
        }

        if (this.all != TypeAttribut.INCONNU) {
            return this.all;
        }

        return TypeAttribut.INCONNU;
    }

    public void ajouterInformation(String info) {
        this.infosCoherence.add(info);
    }

    public List<String> getInfosCoherence() {
        return infosCoherence;
    }

    public TypeAttribut getAll() {
        return all;
    }

    public void setAll(TypeAttribut all) {
        this.all = all;
    }

    public HashSet<String> getAttributsMono() {
        return attributsMono;
    }
}
