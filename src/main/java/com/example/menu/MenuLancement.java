package com.example.menu;

import com.example.modele.BaseConnaissances;
import com.example.modele.Fait;
import com.example.modele.MoteurInference;
import com.example.modele.Regle;
import com.example.srcScanner.SourceScanner;

import java.util.Scanner;

public class MenuLancement {

    private static MenuLancement instance;

    public MoteurInference moteurInference;

    public static synchronized MenuLancement getInstance() {
        if ( MenuLancement.instance == null ) {
            MenuLancement.instance = new MenuLancement();
        }
        return MenuLancement.instance;
    }

    private MenuLancement() {}

    public void display() {
        Scanner scanner = new Scanner(System.in);
        while (moteurInference == null) {
            System.out.println("\n==== Système Expert ====");
            System.out.println("1 - Charger les fichiers sources");
            System.out.println("2 - Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("");
                    // Demander les chemins de fichiers à l'utilisateur
                    System.out.print("Veuillez entrer le chemin du fichier de règles : ");
                    String fichierRegles = scanner.nextLine();

                    System.out.print("Veuillez entrer le chemin du fichier de faits : ");
                    String fichierFaits = scanner.nextLine();

                    System.out.print("Veuillez entrer le chemin du fichier de cohérence (ou appuyez sur Entrée pour ignorer) : ");
                    String fichierCoherences = scanner.nextLine();

                    System.out.println("\nChargement des fichiers sources...");
                    BaseConnaissances BC = SourceScanner.chargerFichiersSource(
                            fichierRegles,
                            fichierFaits,
                            fichierCoherences
                    );
                    if (BC == null) {
                        System.out.println("\nErreur : Chargement des fichiers sources impossible ! Veuillez réessayer.");
                    } else {
                        System.out.println("\nLes fichiers sources ont été chargés avec succès.\n");

                        System.out.println("---- Base Règles ----");
                        for ( Regle regle : BC.getBaseRegles().listeTrieeParNumero()) {
                            System.out.println(regle.toString());
                        }

                        System.out.println("---- Base Faits Initiale ----");
                        for ( Fait fait : BC.getBaseFaits()) {
                            System.out.println(fait.toString());
                        }
                        System.out.println("-----------------------------");

                        this.moteurInference = new MoteurInference(BC);
                        MenuPrincipal.getInstance().display();
                    }
                    break;

                case 2:
                    System.out.println("Fermeture du système expert. Au revoir !");
                    return;

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    public MoteurInference getMoteurInference() {
        return this.moteurInference;
    }
}
