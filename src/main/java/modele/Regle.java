package modele;

import java.util.List;

public class Regle {
    private List<Premisse> premisses;
    private Consequent consequent;
    private int numero;
    private BaseRegles baseRegles;
    public Regle(BaseRegles baseRegles,List<Premisse> premisses,Consequent consequent,int numero) {
        this.baseRegles = baseRegles;
        this.premisses = premisses;
        this.consequent = consequent;
        this.numero = numero;
    }
}
