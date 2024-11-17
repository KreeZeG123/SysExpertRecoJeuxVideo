package com.example.menu;

import java.util.Objects;
import java.util.Scanner;

public class MenuPrincipal {

    private static MenuPrincipal instance;

    private MenuPrincipal() {
    }

    public static MenuPrincipal getInstance() {
        return Objects.requireNonNullElseGet(MenuPrincipal.instance, MenuPrincipal::new);
    }

    public void display() {
        boolean debut = true;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== Menu Principal ====");
            if (debut) {
                System.out.println("Par Défaut :");
                System.out.println(" - Critère sélectionné : règle ayant le plus de prémisses à satisfaire.");
                System.out.println(" - Explication sélectionnée : sous forme de traces complètes.");
                System.out.println(" - Groupement des règles par paquets : désactivé.");
                System.out.println("------------------------");
                debut = false;
            }
            System.out.println("1 - Actions");
            System.out.println("2 - Paramètres");
            System.out.println("3 - Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    MenuActions.getInstance().display();
                    break;
                case 2:
                    MenuParametres.getInstance().display();
                    break;
                case 3:
                    System.out.println("Fermeture du système expert. Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
