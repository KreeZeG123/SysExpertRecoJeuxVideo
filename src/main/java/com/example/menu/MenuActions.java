package com.example.menu;

import com.example.modele.BaseFaits;
import com.example.modele.MoteurInference;

import java.util.Scanner;

public class MenuActions {

    private static MenuActions instance;

    public static MenuActions getInstance() {
        if ( MenuActions.instance == null ) {
            return new MenuActions();
        } else {
            return MenuActions.instance;
        }
    }

    private MenuActions() {}

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Action ====");
            System.out.println("1 - Chainage Avant");
            System.out.println("2 - Chainage Arrière");
            System.out.println("3 - Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("\nExécution du chainage avant ...\n");

                    MoteurInference moteurInference = MenuLancement.getInstance().getMoteurInference();

                    try {
                        BaseFaits result = moteurInference.chainageAvant();
                        moteurInference.afficherTrace(moteurInference.getNivExplication());
                        System.out.println("---- Base Faits Finale ----");
                        System.out.println(result.toString());
                        System.out.println("---------------------------");
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 2:
                    System.out.println("Exécution du chainage arrière...");
                    // TODO: Appel de la méthode pour le chainage arrière
                    break;
                case 3:
                    return;
                case 4:
                    try {
                        MenuLancement.getInstance().getMoteurInference().test();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
