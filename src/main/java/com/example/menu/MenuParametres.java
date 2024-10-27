package com.example.menu;

import java.util.Scanner;

public class MenuParametres {

    private static MenuParametres instance;

    public static MenuParametres getInstance() {
        if ( MenuParametres.instance == null ) {
            return new MenuParametres();
        } else {
            return MenuParametres.instance;
        }
    }

    private MenuParametres() {}

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Paramètres ====");
            System.out.println("1 - Groupement des règles par paquets");
            System.out.println("2 - Choisir critère de sélection de règle");
            System.out.println("3 - Définir Explication");
            System.out.println("4 - Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("Groupement des règles par paquets sélectionné.");
                    // TODO: Implémenter groupement de règles
                    break;
                case 2:
                    MenuSelectionRegles.getInstance().display();
                    break;
                case 3:
                    MenuExplications.getInstance().display();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
