package modele;

import java.util.List;

public class BaseRegles {
    private List<Regle> regles;

    public void ajouterRegle(Regle regle) {
        regles.add(regle);
    }

    public void supprimerRegle(Regle regle) {
        regles.remove(regle);
    }
}
