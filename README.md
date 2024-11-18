# **README - Système Expert**

---

## **Description du Projet**
Ce projet implémente un **système expert polyvalent** capable de déduire des conclusions à partir de faits et de règles dans un domaine donné. Il utilise un moteur d'inférence prenant en charge le **chaînage avant** et **chaînage arrière** pour effectuer des déductions logiques.

### **Domaines d'Utilisation Choisis**
Ce système expert n'est pas limité à un domaine spécifique, cependant, le domaine de **recommandation de jeux vidéo** est un exemple utilisé pour tester et illustrer ses fonctionnalités.

---

## **Fonctionnalités**

1. **Système polyvalent** : Applicable à tout domaine grâce à des fichiers de configuration modifiables.
2. **Moteur d'inférence** :
    - **Chaînage avant** : Déduit de nouveaux faits à partir de faits existants jusqu'à épuisement.
    - **Chaînage arrière** : Recherche si un objectif peut être prouvé à partir des faits connus.
3. **Gestion des conflits** :
    - Résolution par priorité des règles (nombre de prémisses, faits les plus récents).
4. **Cohérence des règles et faits** :
    - Détection et gestion des incohérences dans les faits et les déductions.
5. **Explicabilité** :
    - Traces détaillées du raisonnement.
    - Explications sur les incohérences détectées.
    - Choix de plusieurs traces en paramètre
6. **Vérification syntaxique** :
    - Détection d’erreurs de syntaxe dans les fichiers sources.
    - Correction des fautes de frappe ou des valeurs similaires.
7. **Prise en charge de comparaisons numériques** :
   - Exemple : Le fait A(3) déclanche la règle A(>2) -> Resultat(Valeur)
7. **Groupement des règles (chaînage avant)** :
    - **Exécution ordonnée des paquets de règles** :
      1. Paquet Numérique en ordre croissant (ex : "1", "4", etc)
      2. Paquet Alpha-Numérique en ordre alphabétique (ex : "a", "b", "a12", etc)
      3. Autres en ordre alphabétique (ex : "?", "!", "#", etc)
    - **On passe au paquet suivant quand le paquet actuel est saturé**

---

## **Structure des Fichiers Sources**

### 1. **Faits**
Le fichier de faits contient les informations de base sur lesquelles le système se base.  
**Syntaxe** :
```plaintext
# Commentaire
Attribut(Valeur)
!Attribut(Valeur)  # Fait nié
```

**Exemple** :
```plaintext
# Données utilisateur
Age(25)
Plateforme(PC)
Difficulte(Facile)
```

---

### 2. **Règles**
Les règles définissent les relations logiques entre attributs et valeurs.  
**Syntaxe** :
```plaintext
# Règle simple
Attribut1(Valeur1) -> Conclusion1(Valeur2)

# Règle multiple
Attribut1(Valeur1), Attribut2(Valeur2) -> Conclusion1(Valeur3), Conclusion2(Valeur4)

# Paquets de règles
Paquet1 : Attribut1(Valeur1) -> Conclusion1(Valeur2)
```

**Exemple** :
```plaintext
# Règles de déduction
Age(<18) -> TypeJoueur(Familial)
Plateforme(PC) -> PrefGenre(Action)
PrefGenre(Action) -> Recommande(CallOfDuty)
```

---

### 3. **Cohérence**
Le fichier de cohérence définit les règles empêchant des contradictions.  
**Syntaxe** :
```plaintext
# Attributs mono-valués (un seul possible)
MONO(Attribut)

# Attributs multi-valués (plusieurs possibles)
MULTI(Attribut)

# Attributs mono-valués multi-valués pour tous les autres faits
MULTI(ALL)
# ou
MONO(ALL)

# Règles d'incohérence
Attribut1(Valeur1), Attribut2(Valeur2) -> INCOHERENT(anyValue)
```

**Exemple** :
```plaintext
# Déclaration des attributs
MONO(TypeJoueur)
MULTI(PrefGenre)

# Incohérences
TypeJoueur(Familial), TypeJoueur(Hardcore) -> INCOHERENT(anyValue)
```

---

## **Étapes pour Utiliser le Système**

1. **Configurer les fichiers** :
    - Renseignez les faits dans un fichier `.txt` (ex: `fait.txt`).
    - Écrivez les règles dans un fichier `.txt` (ex : `regles.txt`).
    - Définissez les contraintes de cohérence dans un fichier `.txt` (ex : `coherence.txt`).

2. **Exécuter le programme** :
    - Compilez et exécutez le programme principal (main.Main).
    - ou
    - Exécutez le fichier .jar

3. **Interagir avec le menu** :
    - Options disponibles :
        - Lancer une inférence (chaînage avant ou arrière).
        - Vérifier les incohérences.
        - Afficher les explications.

4. **Analyser les résultats** :
    - Les recommandations ou conclusions s'affichent avec leurs traces explicatives.
    - Les incohérences détectées sont signalées pour correction.

---

## **Exemple de Domaine : Recommandation de Jeux Vidéo**

### **Exemple de Configuration**
1. **Faits** :
   ```plaintext
   Age(22)
   Plateforme(PC)
   Difficulte(Difficile)
   ```
2. **Règles** :
   ```plaintext
   Plateforme(PC) -> TypeJoueur(Hardcore)
   TypeJoueur(Hardcore) -> PrefGenre(Action)
   PrefGenre(Action) -> Recommande(CallOfDuty)
   ```
3. **Cohérence** :
   ```plaintext
   TypeJoueur(Familial), TypeJoueur(Hardcore) -> INCOHERENT(anyValue)
   ```

### **Exécution et Résultat**
Commande :
```bash
java Main
```

Menu :
```plaintext
Parcourir le menu du programme
```

Sortie :
```plaintext
Recommandations :
- CallOfDuty

Traçabilité :
1. Fait : Plateforme(PC)
2. Déduit : TypeJoueur(Hardcore)
3. Déduit : PrefGenre(Action)
4. Recommandé : CallOfDuty
```

---

## **Licence**
Ce projet est distribué sous la **Licence MIT**.

**Extrait de la Licence MIT** :
```plaintext
Copyright (c) [Année] [Votre Nom]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```