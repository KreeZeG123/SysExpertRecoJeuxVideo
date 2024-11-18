package com.example.menu;

import com.example.modele.*;
import com.example.srcScanner.ExtracteurSource;

import java.util.Objects;
import java.util.Scanner;

public class MenuActions {

    private static MenuActions instance;

    private MenuActions() {
    }

    public static MenuActions getInstance() {
        return Objects.requireNonNullElseGet(MenuActions.instance, MenuActions::new);
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Menu Action ====");
            System.out.println("1 - Chainage Avant");
            System.out.println("2 - Chainage Arrière");
            System.out.println("3 - Retour au menu principal");
            System.out.print("Choisissez une option : ");

            String choix = scanner.nextLine();
            MoteurInference moteurInference = MenuLancement.getInstance().getMoteurInference();
            switch (choix) {
                case "1":
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
                case "2":

                    try {
                        // Demander à l'utilisateur d'entrer le but à déduire
                        System.out.print("\nVeuillez taper votre but à déduire :");
                        Scanner scannerBut = new Scanner(System.in);

                        // Extraire le conséquent à partir de l'entrée utilisateur
                        Consequent but = ExtracteurSource.extraireConsequent(scannerBut.nextLine());

                        System.out.println("\nExécution du chainage arrière...\n");

                        // Effectuer le chaînage arrière
                        boolean result = moteurInference.chainageArriere(but);

                        // Afficher le résultat final
                        moteurInference.afficherTrace(moteurInference.getNivExplication());
                        System.out.println("\n---- Resultat ----");
                        if (result) {
                            System.out.println("Le conséquent " + but + " est demandable");
                        } else {
                            System.out.println("Le conséquent " + but + " n'est pas demandable");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erreur : "+ e.getMessage());
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException("Une erreur inattendue s'est produite lors du chaînage arrière.", e);
                    }


                    break;
                case "3":
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }
}
