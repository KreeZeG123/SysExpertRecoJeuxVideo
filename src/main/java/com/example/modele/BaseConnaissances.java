package com.example.modele;

import com.example.modele.enumeration.ChoixRegle;
import com.example.modele.enumeration.TypeAttribut;

import java.util.*;

public class BaseConnaissances {

    private final BaseRegles baseRegles;

    private final BaseFaits baseFaits;

    private final Coherence coherence;

    public ChoixRegle choixRegle = ChoixRegle.PLUS_DE_PREMISSES;

    public BaseConnaissances(BaseRegles baseRegles, BaseFaits baseFaits) {
        this.baseRegles = baseRegles;
        this.baseFaits = baseFaits;
        this.coherence = new Coherence(this);

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
}
