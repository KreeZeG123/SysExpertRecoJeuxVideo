package com.example.modele;

public class BaseConnaissances {

    private BaseRegles baseRegles;

    private BaseFaits baseFaits;

    public BaseConnaissances(BaseRegles baseRegles, BaseFaits baseFaits) {
        this.baseRegles = baseRegles;
        this.baseFaits = baseFaits;
    }

    public BaseRegles getBaseRegles() {
        return baseRegles;
    }

    public BaseFaits getBaseFaits() {
        return baseFaits;
    }
}
