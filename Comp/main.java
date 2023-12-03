import java.io.*; 
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        System.out.println("Lancement du programme de Compression d'image PGM");

        String path = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\test.pgm";
        Quadtree A = new Quadtree(path,null,null,null,null);

        System.out.println("Compression de l'image PGM suivante : " + path + '\n');
        System.out.println("Lecture et sauvegarde des proprietés de l'image en cours ");
        A.ReadImg();
        System.out.println("Fin de la lecture " + '\n');

        System.out.println("Affichage des informations sauvegardées de l'image : ");
        A.printInfoPGM();
        A.printTabLum();

        System.out.println('\n' + "Création du QuadTree associé a l'image ");
        A = A.createQuadTree(A.getTabLum(),A.getHauteur(),A.getLargeur());
        System.out.println("Fin de la création du QuadTree " + '\n');
        System.out.println("Affichage du QuadTree : ");
        A._toString();
    }
}
