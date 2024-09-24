package modele;

public class Premisse {
    private Element[] elements;
    private Boolean negatif;
    private BaseFaits baseFaits;

    public Premisse(Element[] elements,Boolean negatif, BaseFaits baseFaits){
        this.elements = elements;
        this.negatif = negatif;
        this.baseFaits = baseFaits;
    }
}
