package com.example.menu;

import java.util.Scanner;

public class MenuExplications {

    private static boolean[] optionsSelecionnees = new boolean[]{false,false,true,false,false};

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
            System.out.println("1 - "+obtenirOptionSelectionnees(1)+" Aucune explications");
            System.out.println("2 - "+obtenirOptionSelectionnees(2)+" En cas de problème");
            System.out.println("3 - "+obtenirOptionSelectionnees(3)+" Sous forme de traces complètes");
            System.out.println("4 - "+obtenirOptionSelectionnees(4)+" Sous forme de traces abrégées");
            System.out.println("5 - "+obtenirOptionSelectionnees(5)+" Explication à partir d'informations complémentaires ajoutées aux règles");
            System.out.println("6 - Retour au menu des paramètres");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("\nExplication sélectionnée : Aucune explication");
                    MenuLancement.getInstance().getMoteurInference().setNivExplication(0);
                    selectionnerOptions(1);
                    break;
                case 2:
                    System.out.println("\nExplication sélectionnée : en cas de problème.");
                    // TODO: Implémenter explication en cas de problème
                    break;
                case 3:
                    System.out.println("\nExplication sélectionnée : sous forme de traces complètes.");
                    MenuLancement.getInstance().getMoteurInference().setNivExplication(1);
                    selectionnerOptions(3);
                    break;
                case 4:
                    System.out.println("\nExplication sélectionnée : sous forme de traces abrégées.");
                    MenuLancement.getInstance().getMoteurInference().setNivExplication(2);
                    selectionnerOptions(4);
                    break;
                case 5:
                    System.out.println("\nExplication sélectionnée : à partir d'informations complémentaires.");
                    // TODO: Implémenter explication avec informations complémentaires
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    private String obtenirOptionSelectionnees(int index) {
        if ( index < 1 || index > 5) {
            return "[?]";
        }
        return optionsSelecionnees[index-1] ? "[X]" : "[ ]";
    }

    private void selectionnerOptions(int index) {
        if ( index < 1 || index > 5) {
            return;
        }
        optionsSelecionnees = new boolean[]{false,false,false,false,false};
        optionsSelecionnees[index-1] = true;
    }
}
