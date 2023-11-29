import java.io.*; 
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        //System.out.println("ALLO");

        String path = "C:\\Users\\Chris\\Documents\\Cours\\L3\\asd3\\ProjetASD3Comp\\imgPGM\\test.pgm";
        Quadtree A = new Quadtree(path,null,null,null,null);
   
        A.ReadImg();
        A.printInfoPGM();
        A.printTabLum();
        A.createQuadTree();
        A._toString();
    }
}
