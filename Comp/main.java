import java.io.*; 
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        System.out.println("Lancement du programme de Compression d'image PGM");

        String pathPGMtoQuadTree = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\test4_4.pgm";
        Quadtree A = new Quadtree(pathPGMtoQuadTree,null,null,null,null);
        Quadtree Acomp = new Quadtree(null,null,null,null);

        System.out.println("Compression de l'image PGM suivante : " + pathPGMtoQuadTree + '\n');
        System.out.println("Lecture et sauvegarde des proprietés de l'image en cours... ");
        A.ReadImg();
        System.out.println("Fin de la lecture " + '\n');

        System.out.println("Affichage des informations sauvegardées de l'image : ");
        A.printInfoPGM();
        //A.printTabLum();

        System.out.println('\n' + "Création du QuadTree associé a l'image... ");
        A = A.createQuadTree(A.getTabLum(),A.getHauteur(),A.getLargeur());
        System.out.println("Fin de la création du QuadTree " + '\n');
        System.out.println("Affichage du QuadTree : ");
        A._toString();
        System.out.println('\n' + "Debut de la compression Lambda...");
        Acomp = A;
        Acomp.compressLambda();
        System.out.println("Fin de la compression Lambda");
        System.out.println("Affichage du QuadTree compressé par la methode Lambda : ");
        Acomp._toString();
        Acomp.tauxDeCompression(Acomp.nbrNoeuds(0),A.nbrNoeuds(0));
        String pathQuadTreetoPGM = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\img.pgm";
        A.toPGM(pathQuadTreetoPGM);
        String pathQuadTreetoPGMcomp = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\Comp.pgm";
        Acomp.toPGM(pathQuadTreetoPGMcomp);
    }
}
