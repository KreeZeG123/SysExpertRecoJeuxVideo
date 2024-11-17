package com.example.menu;

import com.example.modele.BaseConnaissances;

import java.util.Objects;
import java.util.Scanner;

public class MenuParametres {

    private static boolean optionsSelecionnee = false;

    private static MenuParametres instance;

    private MenuParametres() {
    }

    public static MenuParametres getInstance() {
        return Objects.requireNonNullElseGet(MenuParametres.instance, MenuParametres::new);
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Paramètres ====");
            System.out.println("1 - " + obtenirOptionSelectionnee() + " Groupement des règles par paquets");
            System.out.println("2 - Choisir critère de sélection de règle");
            System.out.println("3 - Définir Explication");
            System.out.println("4 - Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    BaseConnaissances BC = MenuLancement.getInstance().getMoteurInference().getBC();
                    if (!optionsSelecionnee) {
                        BC.getBaseRegles().groupementParPaquet = true;
                        System.out.println("\nGroupement des règles par paquets sélectionné.");
                    } else {
                        BC.getBaseRegles().groupementParPaquet = false;
                        System.out.println("\nGroupement des règles par paquets désélectionné.");
                    }
                    selectionnerOptions();
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

    private String obtenirOptionSelectionnee() {
        return optionsSelecionnee ? "[X]" : "[ ]";
    }

    private void selectionnerOptions() {
        optionsSelecionnee = !optionsSelecionnee;
    }
}
