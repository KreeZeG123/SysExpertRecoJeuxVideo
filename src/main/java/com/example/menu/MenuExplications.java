package com.example.menu;

import java.util.Scanner;

public class MenuExplications {

    private static MenuExplications instance;

    public static MenuExplications getInstance() {
        if ( MenuExplications.instance == null ) {
            return new MenuExplications();
        } else {
            return MenuExplications.instance;
        }
    }

    private MenuExplications() {}

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Explications ====");
            System.out.println("1 - À la demande de l'utilisateur");
            System.out.println("2 - En cas de problème");
            System.out.println("3 - Sous forme de traces complètes");
            System.out.println("4 - Sous forme de traces abrégées");
            System.out.println("5 - Explication à partir d'informations complémentaires ajoutées aux règles");
            System.out.println("6 - Retour au menu des paramètres");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("\nExplication sélectionnée : Aucune explication");
                    MenuLancement.getInstance().getMoteurInference().setNivExplication(0);
                    return;
                case 2:
                    System.out.println("\nExplication sélectionnée : en cas de problème.");
                    // TODO: Implémenter explication en cas de problème
                    return;
                case 3:
                    System.out.println("\nExplication sélectionnée : sous forme de traces complètes.");
                    MenuLancement.getInstance().getMoteurInference().setNivExplication(1);
                    return;
                case 4:
                    System.out.println("\nExplication sélectionnée : sous forme de traces abrégées.");
                    MenuLancement.getInstance().getMoteurInference().setNivExplication(2);
                    return;
                case 5:
                    System.out.println("\nExplication sélectionnée : à partir d'informations complémentaires.");
                    // TODO: Implémenter explication avec informations complémentaires
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
