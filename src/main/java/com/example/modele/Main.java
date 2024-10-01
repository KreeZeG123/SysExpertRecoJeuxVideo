package com.example.modele;

import com.example.srcScanner.SourceScanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("==== Launched ====");

        SourceScanner.chargerFichierRegles("rules.txt");
    }
}