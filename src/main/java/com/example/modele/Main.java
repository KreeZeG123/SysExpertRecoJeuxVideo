package com.example.modele;

import com.example.srcScanner.SourceScanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BaseRegles BR = SourceScanner.chargerFichierRegles("rules.txt");
        BaseFaits BF = new BaseFaits();

        BaseConnaissances BC = new BaseConnaissances(BR,BF);

        MoteurInference moteurInference = new MoteurInference(BC);

        try {
            moteurInference.chainageAvant();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}