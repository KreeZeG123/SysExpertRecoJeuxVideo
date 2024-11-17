package com.example.modele;

import com.example.modele.enumeration.ChoixRegle;

import java.util.HashMap;

public class BaseConnaissances {

    private final BaseRegles baseRegles;

    private final BaseFaits baseFaits;

    private final Coherence coherence;

    public ChoixRegle choixRegle = ChoixRegle.PLUS_DE_PREMISSES;

    public BaseConnaissances(BaseRegles baseRegles, BaseFaits baseFaits) {
        this.baseRegles = baseRegles;
        this.baseFaits = baseFaits;
        this.coherence = new Coherence();

        if (baseRegles != null) {
            this.baseRegles.setBC(this);
        }
    }

    public BaseRegles getBaseRegles() {
        return baseRegles;
    }

    public BaseFaits getBaseFaits() {
        return baseFaits;
    }

    public Coherence getCoherence() {
        return coherence;
    }

    public boolean verifierCoherenceFaitMonoValue() {
        HashMap<String, Integer> occurencesAttributs = this.getBaseFaits().listerOccurencesAttributs();
        boolean incoherence = false;
        for (String attribut : this.getCoherence().getAttributsMono()) {
            if (occurencesAttributs.containsKey(attribut)) {
                Integer occurences = occurencesAttributs.get(attribut);
                if (occurences > 1) {
                    System.out.println("Attention : L'attribut monovalué \"" + attribut + "\" a plusieurs valeurs en même temps");
                    incoherence = true;
                }
            }
        }
        return incoherence;
    }
}
