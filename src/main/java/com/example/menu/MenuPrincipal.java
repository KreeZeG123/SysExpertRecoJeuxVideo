package com.example.menu;

import java.util.Scanner;

public class MenuPrincipal {

    private static MenuPrincipal instance;

    public static MenuPrincipal getInstance() {
        if ( MenuPrincipal.instance == null ) {
            return new MenuPrincipal();
        } else {
            return MenuPrincipal.instance;
        }
    }

    private MenuPrincipal() {}

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Principal ====");
            System.out.println("1 - Charger Source");
            System.out.println("2 - Actions");
            System.out.println("3 - Settings");
            System.out.println("4 - Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("Chargement des fichiers sources...");
                    // TODO: Appel de la méthode pour charger les fichiers sources
                    break;
                case 2:
                    MenuActions.getInstance().display();
                    break;
                case 3:
                    MenuParametres.getInstance().display();
                    break;
                case 4:
                    System.out.println("Fermeture du système expert. Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
