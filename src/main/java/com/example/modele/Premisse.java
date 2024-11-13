package com.example.modele;

import java.util.*;

public class Premisse implements Iterable<Element>{
    private List<Element> elements;
    private BaseFaits baseFaits;

    public Premisse() {
        this.elements = new ArrayList<>();
        this.baseFaits = null;
    }

    public Premisse(Element element) {
        this.elements = new ArrayList<>();
        this.elements.add(element);
        this.baseFaits = null;
    }

    public Premisse(ArrayList<Element> elements,Boolean negatif, BaseFaits baseFaits){
        this.elements = elements;
        this.baseFaits = baseFaits;
    }

    public boolean equalsListElement(List<Element> elements){
        boolean egal = true;
        if(this.elements.size()!=elements.size()){
            egal = false;
        }
        for(Element e: this.elements){
            if(!elements.contains(e)){
                egal=false;
            }
        }
        return egal;
    }

    public void ajouterElement(Element element){
        this.elements.add(element);
    }

    public boolean premisseValide(){
        return true;
    }

    public List<Element> getElements() {
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
