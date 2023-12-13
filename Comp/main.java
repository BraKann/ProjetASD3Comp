import java.io.*; 
import java.util.Scanner;

public class main {
    public static void main(String[] args){

        System.out.println("Lancement du programme de Compression d'image PGM");

        //Chemin du PGM compressé de type : Comp/imgPGM/[nom.pgm] ou chemin complet pour CIE
        String pathPGMtoQuadTree = "Comp/imgPGM/flower_small.pgm";
        System.out.println("Compression de l'image PGM suivante : " + pathPGMtoQuadTree + '\n');
        System.out.println("Lecture et sauvegarde des proprietés de l'image en cours... ");
        Quadtree A = new Quadtree(pathPGMtoQuadTree,null,null,null,null);
        System.out.println("Fin de la lecture " + '\n');

        System.out.println("Affichage des informations sauvegardées de l'image : ");
        A.getPgm().printInfoPGM();
        //A.getPgm().printTabLum();

        System.out.println('\n' + "Création du QuadTree associé a l'image... ");
        A = A.createQuadTree(A.getPgm().getTabLum(),A.getPgm().getHauteur(),A.getPgm().getLargeur());
        System.out.println("Fin de la création du QuadTree " + '\n');
        A.setNbrNoeudsAvantComp(A.nbrNoeuds());
        System.out.println("Affichage du QuadTree : ");
        //A._toString();
        System.out.println('\n' + "Nombre de noeuds de l'arbre = " + A.getNbrNoeudsAvantComp());
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voudriez vous faire rho ou lambda ? appuyez L pour Lambda et R pour Rho");
        String comp = scanner.nextLine();
        
        if (comp.equalsIgnoreCase("L")) {
            System.out.println('\n' + "Debut de la compression Lambda...");
            A.compressLambda();
            System.out.println("Fin de la compression Lambda");
            A.tauxDeCompression();
            String pathQuadTreetoPGMcomp = "Comp/imgPGM/imgLambda.pgm";
            A.toPGM(pathQuadTreetoPGMcomp);


            System.out.println("Voulez-vous afficher le tableau ? appuyez O pour Oui et N pour Non");
            String voirTab = scanner.nextLine();
            if (voirTab.equalsIgnoreCase("O")) {
                A.getPgm().printTabLum();
            }

            System.out.println("Voulez-vous afficher l'arbre ? appuyez O pour Oui et N pour Non");
            String voirArbre = scanner.nextLine();
            if (voirArbre.equalsIgnoreCase("O")) {
                A._toString();
            }

            A.setNbrNoeudsApresComp(A.nbrNoeuds());
            System.out.println('\n' + "Nombre de noeuds de l'arbre = " + A.getNbrNoeudsApresComp());
        
        
            System.out.println("Voulez-vous voir aussi la compression Rho ? appuyez O pour Oui et N pour Non");
            String voirRho = scanner.nextLine();
            if (voirRho.equalsIgnoreCase("O")) {
                // Réinitialisez l'arbre à son état initial
                A = new Quadtree(pathPGMtoQuadTree,null,null,null,null);
                A = A.createQuadTree(A.getPgm().getTabLum(),A.getPgm().getHauteur(),A.getPgm().getLargeur());
        
                System.out.println('\n' + "Debut de la compression Rho...");
                System.out.println("Veuillez entrer le taux de compression pour la méthode Rho : ");
                int tauxCompression = scanner.nextInt();
                scanner.nextLine();  
                A.compressRho(tauxCompression);
                System.out.println("Fin de la compression Rho");
                String pathQuadTreetoPGMcomp1 = "Comp/imgPGM/imgRho.pgm";
                A.toPGM(pathQuadTreetoPGMcomp1);
                A.tauxDeCompressionPrint();

                System.out.println("Voulez-vous afficher le tableau ? appuyez O pour Oui et N pour Non");
                voirTab = scanner.nextLine();
                if (voirTab.equalsIgnoreCase("O")) {
                    A.getPgm().printTabLum();
                }

                System.out.println("\nVoulez-vous afficher l'arbre ? appuyez O pour Oui et N pour Non");
                voirArbre = scanner.nextLine();
                if (voirArbre.equalsIgnoreCase("O")) {
                    A._toString();
                    System.out.println('\n');
                }

                A.tauxDeCompressionPrint();

                pathQuadTreetoPGMcomp = "Comp/imgPGM/imgRho.pgm";
                A.toPGM(pathQuadTreetoPGMcomp);
            }
        } else if (comp.equalsIgnoreCase("R")) {
            System.out.println('\n' + "Debut de la compression Rho...");
            System.out.println("Veuillez entrer le taux de compression pour la méthode Rho : ");
            int tauxCompression = scanner.nextInt();
            scanner.nextLine(); 
            A.compressRho(tauxCompression);
        
            System.out.println("Fin de la compression Rho");
            String pathQuadTreetoPGMcomp = "Comp/imgPGM/imgRho.pgm";
            A.toPGM(pathQuadTreetoPGMcomp);
            A.tauxDeCompressionPrint();

            System.out.println("Voulez-vous afficher le tableau ? appuyez O pour Oui et N pour Non");
            String voirTab = scanner.nextLine();
            if (voirTab.equalsIgnoreCase("O")) {
                A.getPgm().printTabLum();
            }

            System.out.println("Voulez-vous afficher l'arbre ? appuyez O pour Oui et N pour Non");
            String voirArbre = scanner.nextLine();
            if (voirArbre.equalsIgnoreCase("O")) {
                A._toString();
            }

            System.out.println("Voulez-vous voir aussi la compression Lambda ? appuyez O pour Oui et N pour Non");
            String voirLambda = scanner.nextLine();
            if (voirLambda.equalsIgnoreCase("O")) {
                // Réinitialisez l'arbre à son état initial
                A = new Quadtree(pathPGMtoQuadTree,null,null,null,null);
                A = A.createQuadTree(A.getPgm().getTabLum(),A.getPgm().getHauteur(),A.getPgm().getLargeur());

                System.out.println('\n' + "Debut de la compression Lambda...");
                A.compressLambda();
                System.out.println("Fin de la compression Lambda");
                A.tauxDeCompression();
                String pathQuadTreetoPGMcomp2 = "Comp/imgPGM/imgLambda.pgm";
                A.toPGM(pathQuadTreetoPGMcomp2);

                System.out.println("Voulez-vous afficher le tableau ? appuyez O pour Oui et N pour Non");
                voirTab = scanner.nextLine();
                if (voirTab.equalsIgnoreCase("O")) {
                    A.getPgm().printTabLum();
                }

                System.out.println("Voulez-vous afficher l'arbre ? appuyez O pour Oui et N pour Non");
                voirArbre = scanner.nextLine();
                if (voirArbre.equalsIgnoreCase("O")) {
                    A._toString();
                }

                A.setNbrNoeudsApresComp(A.nbrNoeuds());
                System.out.println('\n' + "Nombre de noeuds de l'arbre = " + A.getNbrNoeudsApresComp());
            
            }
        } else {
            System.out.println("Option non reconnue. Veuillez entrer L pour Lambda ou R pour Rho.");
        }
    }
}

