package com.example.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Premisse implements Iterable<Element> {
    private List<Element> elements;

    public Premisse() {
        this.elements = new ArrayList<>();
    }

    public Premisse(Element element) {
        this.elements = new ArrayList<>();
        this.elements.add(element);
    }

    public boolean contient(Element e) {
        for (Element elem : elements) {
            if (elem.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public boolean contient(ArrayList<Element> elements) {
        for (Element elem : elements) {
            if (!contient(elem)) {
                return false;
            }
        }
        return true;
    }

    public void ajouterElement(Element element) {
        this.elements.add(element);
    }

    public ArrayList<Element> getElements() {
        return (ArrayList<Element>) elements;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : elements) {
            stringBuilder.append(element.toString());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public Iterator<Element> iterator() {
        return this.elements.iterator();
    }
}
