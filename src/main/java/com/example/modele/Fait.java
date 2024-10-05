package com.example.modele;

import java.util.Objects;

public class Fait {
    private Element element;

    public Fait(Element element) {
        this.element = element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fait fait)) return false;
        return Objects.equals(element, fait.element);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(element);
    }
}
