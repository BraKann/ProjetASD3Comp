# Projet ASD3 : Arbres et compression d'image (Quadtree / PGM)

## Contexte

Projet réalisé dans le cadre du cours d'Algorithmique et Structures de Données 3. Objectif : représenter une image au format PGM (Portable Graymap) sous forme de quadtree, puis implémenter deux algorithmes de compression avec perte sur cette structure.

## Technologies utilisées

- **Java** (compatible Java 1.7, exécution en ligne de commande, sans IDE)
- **java.io.File** pour la lecture/écriture des fichiers image
- **Structures de données implémentées à la main** (pas de TreeSet, HashMap ni librairie externe : uniquement listes et tableaux)
- **Format PGM** (P2, niveaux de gris) en lecture et en écriture

## Démarche

1. **Parsing du format PGM** : lecture d'un fichier texte brut (header + valeurs de luminosité), en gérant l'absence de format fixe pour les lignes/valeurs.
2. **Construction du quadtree** : conversion d'une image carrée 2^n x 2^n en arbre quaternaire, chaque nœud interne ayant exactement 4 enfants, et chaque groupe de 4 feuilles ne pouvant être identique (contrainte de structure du cours).
3. **Compression Lambda (effeuillage)** : fusion systématique de chaque brindille (4 feuilles de même parent) en une seule feuille, via une moyenne logarithmique de luminosité.
4. **Compression Rho (compression dynamique)** : fusion itérative des brindilles par écart de luminosité croissant (les zones peu contrastées sont compressées en premier), jusqu'à atteindre un taux de compression cible.
5. **Régénération d'image** : reconstruction d'un fichier PGM à partir du quadtree (éventuellement compressé).
6. **Interface** : menu textuel interactif, et mode non-interactif (image + valeur rho en paramètres) pour lancer les deux compressions automatiquement avec affichage des statistiques.

## Compétences visées

- **Structures de données arborescentes** : conception et implémentation d'un quadtree respectant des invariants stricts (pas de bibliothèque, tout à la main)
- **Parsing de formats de fichiers** : lecture robuste d'un format texte peu contraint (espaces/retours à la ligne interchangeables)
- **Algorithmique récursive** : parcours et transformation d'arbres (fusion de sous-arbres, reconstruction)
- **Analyse de complexité** : justification de l'efficacité de chaque algorithme au pire cas, choix de structures adaptées
- **Compression avec perte** : compromis entre taux de compression et fidélité de l'image, à travers deux stratégies différentes (statique vs dynamique/gloutonne)
- **Rigueur de développement Java** : code portable, compilable et exécutable en ligne de commande, sans dépendance à un IDE
- **Rédaction technique** : documentation d'un algorithme sous forme de pseudo-code avec spécification des procédures
