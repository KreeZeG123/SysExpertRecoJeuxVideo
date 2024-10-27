package com.example.menu;

import java.util.Scanner;

public class MenuSelectionRegles {

    private static MenuSelectionRegles instance;

    public static MenuSelectionRegles getInstance() {
        if ( MenuSelectionRegles.instance == null ) {
            return new MenuSelectionRegles();
        } else {
            return MenuSelectionRegles.instance;
        }
    }

    private MenuSelectionRegles() {}

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Sélection Regles ====");
            System.out.println("1 - Règle ayant le plus de prémisses à satisfaire");
            System.out.println("2 - Règle comportant comme prémisses les faits déduits les plus récents");
            System.out.println("3 - Retour au menu des paramètres");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("Critère sélectionné : règle ayant le plus de prémisses à satisfaire.");
                    // TODO: Implémenter critère de sélection des règles
                    break;
                case 2:
                    System.out.println("Critère sélectionné : règle avec prémisses les faits déduits récents.");
                    // TODO: Implémenter critère de sélection des règles
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
