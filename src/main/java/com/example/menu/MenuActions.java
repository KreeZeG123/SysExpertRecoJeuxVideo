package com.example.menu;

import com.example.modele.*;

import java.sql.Array;
import java.util.ArrayList;
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
                    System.out.println("Exécution du chainage arrière...");
                    try {
                        ArrayList<Element> elements = new ArrayList<>();
                        elements.add(new Element("Neerlandais", new Valeur("true"), false));
                        elements.add(new Element("Allemand", new Valeur("false"), false));
                        ArrayList<Boolean> result = moteurInference.chainageArriere(elements);
                        moteurInference.afficherTrace(moteurInference.getNivExplication());
                        System.out.println("---- Resultat ----");
                        int i =0;
                        for(Boolean b : result){
                            if(b){
                                System.out.println(elements.get(i) + " est demandable");
                            }else{
                                System.out.println(elements.get(i) + " n'est pas demandable");
                            }
                            i++;
                        }
                        System.out.println("---------------------------");
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
