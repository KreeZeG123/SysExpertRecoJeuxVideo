package com.example.modele;

import java.util.List;
import java.util.Set;

public class BaseRegles {
    private Set<Regle> regles;

    public void ajouterRegle(Regle regle) {
        regles.add(regle);
    }

    public void supprimerRegle(Regle regle) {
        regles.remove(regle);
    }

    public void creerRegle(){

    }
}
