package com.example.menu;

import com.example.modele.*;

import java.util.Scanner;

public class MenuActions {

    private static MenuActions instance;

    private MenuActions() {
    }

    public static MenuActions getInstance() {
        if (MenuActions.instance == null) {
            return new MenuActions();
        } else {
            return MenuActions.instance;
        }
    }

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
            MoteurInference moteurInference = MenuLancement.getInstance().getMoteurInference();
            switch (choix) {
                case 1:
                    System.out.println("\nExécution du chainage avant ...\n");
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
                    System.out.println("Exécution du chainage arrière...\n");
                    try {
                        Consequent b = new Consequent(new Element("Neerlandais", new Valeur("true"), false));

                        boolean result = moteurInference.chainageArriere(b);
                        moteurInference.afficherTrace(moteurInference.getNivExplication());
                        System.out.println("\n---- Resultat ----");
                        if (result) {
                            System.out.println("La prémisse " + b + " est demandable");
                        } else {
                            System.out.println("La prémisse " + b + " n'est pas demandable");
                        }
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
