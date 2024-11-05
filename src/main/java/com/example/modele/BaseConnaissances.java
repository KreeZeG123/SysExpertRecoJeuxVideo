package com.example.modele;

import com.example.modele.enumeration.ChoixRegle;

public class BaseConnaissances {

    private BaseRegles baseRegles;

    private BaseFaits baseFaits;

    public ChoixRegle choixRegle = ChoixRegle.PLUS_DE_PREMISSES;

    public BaseConnaissances(BaseRegles baseRegles, BaseFaits baseFaits) {
        this.baseRegles = baseRegles;
        this.baseFaits = baseFaits;

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
}
