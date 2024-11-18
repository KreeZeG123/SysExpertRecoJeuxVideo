package com.example.menu;

import com.example.modele.enumeration.ChoixRegle;

import java.util.Objects;
import java.util.Scanner;

public class MenuSelectionRegles {

    private static boolean[] optionsSelecionnees = new boolean[]{true, false};

    private static MenuSelectionRegles instance;

    private MenuSelectionRegles() {
    }

    public static MenuSelectionRegles getInstance() {
        return Objects.requireNonNullElseGet(MenuSelectionRegles.instance, MenuSelectionRegles::new);
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Sélection Regles ====");
            System.out.println("1 - " + obtenirOptionSelectionnees(1) + " Règle ayant le plus de prémisses à satisfaire");
            System.out.println("2 - " + obtenirOptionSelectionnees(2) + " Règle comportant comme prémisses les faits déduits les plus récents");
            System.out.println("3 - Retour au menu des paramètres");
            System.out.print("Choisissez une option : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.println("Critère sélectionné : règle ayant le plus de prémisses à satisfaire.");
                    MenuLancement.getInstance().getMoteurInference().getBC().choixRegle = ChoixRegle.PLUS_DE_PREMISSES;
                    selectionnerOptions(1);
                    break;
                case "2":
                    System.out.println("Critère sélectionné : règle avec prémisses les faits déduits récents.");
                    MenuLancement.getInstance().getMoteurInference().getBC().choixRegle = ChoixRegle.FAIT_RECENT;
                    selectionnerOptions(2);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    private String obtenirOptionSelectionnees(int index) {
        if (index < 1 || index > 2) {
            return "[?]";
        }
        return optionsSelecionnees[index - 1] ? "[X]" : "[ ]";
    }

    private void selectionnerOptions(int index) {
        if (index < 1 || index > 2) {
            return;
        }
        optionsSelecionnees = new boolean[]{false, false, false};
        optionsSelecionnees[index - 1] = true;
    }
}
