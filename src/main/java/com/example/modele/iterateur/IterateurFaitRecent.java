package com.example.modele.iterateur;

import com.example.modele.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IterateurFaitRecent implements Iterator<Regle> {

    private final Iterator<Regle> iterateurInterne;

    public IterateurFaitRecent(List<Regle> listRegles, BaseFaits BF) {
        List<Regle> sortedList = listRegles.stream()
                .sorted((r1, r2) -> {
                    long scoreR1 = calculerScoreDeRecence(r1.getPremisse(), BF);
                    long scoreR2 = calculerScoreDeRecence(r2.getPremisse(), BF);
                    return Long.compare(scoreR2, scoreR1);
                })
                .toList();
        this.iterateurInterne = sortedList.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterateurInterne.hasNext();
    }

    @Override
    public Regle next() {
        return iterateurInterne.next();
    }

    private long calculerScoreDeRecence(Premisse premisses, BaseFaits BF) {
        long score = 0;

        List<Element> elements = new ArrayList<>(premisses.getElements());
        List<Fait> faitsRecents = new ArrayList<>(BF.getFaits());
        Collections.reverse(faitsRecents);
        
        int size = faitsRecents.size();
        for (int i = 0; i < size; i++) {
            Fait fait = faitsRecents.get(i);
            if (elements.contains(fait)) {
                score += (size - i);
            }
        }
        return score;
    }
}