import java.io.*; 
import java.util.Scanner;

public class main {
    public static void main(String[] args){

        System.out.println("Lancement du programme de Compression d'image PGM");

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
        
        /*
        System.out.println('\n' + "Debut de la compression Lambda...");
        A.compressLambda();
        System.out.println("Fin de la compression Lambda");
        //A.getPgm().printTabLum();
        System.out.println("Affichage du QuadTree compressé par la methode Lambda : ");
        //A._toString();
        A.setNbrNoeudsApresComp(A.nbrNoeuds());
        System.out.println('\n' + "Nombre de noeuds de l'arbre = " + A.getNbrNoeudsApresComp());

        A.tauxDeCompression();
        */

        System.out.println('\n' + "Debut de la compression Rho...");
        A.compressRho(25);
        System.out.println("Fin de la compression Rho");
        //A.getPgm().printTabLum();
        System.out.println("Affichage du QuadTree compressé par la methode Rho : ");
        //A._toString();
        System.out.println('\n' + A.listEps.get(0).getEps() + " " + A.listEps.get(1).getEps() + " "+ A.listEps.get(2).getEps() + " " + A.listEps.get(3).getEps() );
        A.tauxDeCompressionPrint();

        String pathQuadTreetoPGMcomp = "Comp/imgPGM/imgComp.pgm";
        A.toPGM(pathQuadTreetoPGMcomp);
    }
}
