import java.io.*; 
import java.util.Scanner;

public class main {
    public static void main(String[] args){

        System.out.println("Lancement du programme de Compression d'image PGM");

        String pathPGMtoQuadTree = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\test8_8.pgm";

        System.out.println("Compression de l'image PGM suivante : " + pathPGMtoQuadTree + '\n');
        System.out.println("Lecture et sauvegarde des proprietés de l'image en cours... ");
        Quadtree A = new Quadtree(pathPGMtoQuadTree,null,null,null,null);
        System.out.println("Fin de la lecture " + '\n');

        System.out.println("Affichage des informations sauvegardées de l'image : ");
        A.getPgm().printInfoPGM();
        A.getPgm().printTabLum();

        System.out.println('\n' + "Création du QuadTree associé a l'image... ");
        A = A.createQuadTree(A.getPgm().getTabLum(),A.getPgm().getHauteur(),A.getPgm().getLargeur());
        System.out.println("Fin de la création du QuadTree " + '\n');
        A.setNbrNoeudsAvantComp(A.nbrNoeuds());
        System.out.println("Affichage du QuadTree : ");
        A._toString();
        System.out.println('\n' + "Nombre de noeuds de l'arbre = " + A.getNbrNoeudsAvantComp());

        String pathQuadTreetoPGM = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\imgNonComp.pgm";
        A.toPGM(pathQuadTreetoPGM);
        
        /*
        System.out.println('\n' + "Debut de la compression Lambda...");
        A.compressLambda();
        A.quadTreeToTab2D(0,0,A.getPgm().getHauteur(),A.getPgm().getLargeur());
        System.out.println("Fin de la compression Lambda");
        A.getPgm().printTabLum();
        System.out.println("Affichage du QuadTree compressé par la methode Lambda : ");
        A._toString();
        A.setNbrNoeudsApresComp(A.nbrNoeuds());
        System.out.println('\n' + "Nombre de noeuds de l'arbre = " + A.getNbrNoeudsApresComp());

        A.tauxDeCompression();
        */

        System.out.println('\n' + "Debut de la compression Rho...");
        A.compressRho(25);
        A.quadTreeToTab2D(0,0,A.getPgm().getHauteur(),A.getPgm().getLargeur());
        System.out.println("Fin de la compression Lambda");
        A.getPgm().printTabLum();
        System.out.println("Affichage du QuadTree compressé par la methode Rho : ");
        A._toString();
        System.out.println(A.listEps.get(0) + " " + A.listEps.get(1) + " "+ A.listEps.get(2) + " " + A.listEps.get(3) );
        A.tauxDeCompression();

        String pathQuadTreetoPGMcomp = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\imgComp.pgm";
        A.toPGM(pathQuadTreetoPGMcomp);
    }
}
