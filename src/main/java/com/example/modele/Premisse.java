package com.example.modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Premisse implements Iterable<Element>{
    private Set<Element> elements;
    private BaseFaits baseFaits;

    public Premisse() {
        this.elements = new HashSet<>();
        this.baseFaits = null;
    }

    public Premisse(Element element) {
        this.elements = new HashSet<>();
        this.elements.add(element);
        this.baseFaits = null;
    }

    public Premisse(Set<Element> elements,Boolean negatif, BaseFaits baseFaits){
        this.elements = elements;
        this.baseFaits = baseFaits;
    }

    public void ajouterElement(Element element){
        this.elements.add(element);
    }

    public boolean premisseValide(){
        return true;
    }

    public Set<Element> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : elements) {
            stringBuilder.append(element.toString());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    @Override
    public Iterator<Element> iterator() {
        return this.elements.iterator();
    }
}
