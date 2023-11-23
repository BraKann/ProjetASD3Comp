import java.io.*; 
import java.util.Scanner;

public class Quadtree {

    //Chemin du PGM compressé de type : imgPGM/[nom.pgm] ou chemin complet
    private String path;

    //Informations sur le PGM a garder pour le retourner une fois compressé
    private String magicNumber;
    private String commentaire; 
    private int largeur;
    private int hauteur;
    private int lumMax;            //La racine prendra la luminosité max ?

    //Permet de stocker les valeurs de luminosité lu durant le pacours du PGM
    private int[][] tabLum = new int[this.largeur][this.hauteur];
    
    //Valeurs de luminosité comprise dans le PGM
    private int lum;               

    //Les 4 fils(noeuds) du 4 tree soit tous null ou tous remplis
    private Quadtree f1;
    private Quadtree f2;
    private Quadtree f3;
    private Quadtree f4;

    //Constructeur diff pour racine ? avec lum max, dimension, comm et chemin ?
    public Quadtree(String path, Quadtree f1,Quadtree f2,Quadtree f3,Quadtree f4){
        this.path = path;
        this.f1 = f1;
        this.f1 = f1;
        this.f1 = f1;
        this.f1 = f1;
    }
    
    public void ReadImg() {
        try {

            File img = new File(this.path);
            Scanner scan = new Scanner(img);

            this.magicNumber = scan.nextLine();
            this.commentaire = scan.nextLine();
            this.commentaire += " ";
            this.commentaire += scan.nextLine();
            this.largeur = scan.nextInt();
            this.hauteur = scan.nextInt();
            this.lumMax = scan.nextInt(); 
            this.tabLum = new int[this.largeur][this.hauteur];

            for(int ligne = 0; ligne < getHauteur(); ligne++){
                for(int colonne = 0; colonne < getLargeur(); colonne++){
                    tabLum[ligne][colonne]= scan.nextInt();
                }
            }
    
            scan.close();

        } catch (IOException exception) {
		    exception.printStackTrace();
        }
    }

    public void printTabLum(){
        for(int ligne = 0; ligne < getHauteur(); ligne++){
            for(int colonne = 0; colonne < getLargeur(); colonne++){
                System.out.print(this.tabLum[ligne][colonne] + " ");
            }
            System.out.println();
        }
    }

    public void printInfoPGM(){
        System.out.print("Magic Number : " + this.magicNumber + '\n' + 
                        "Commentaires : " + this.commentaire + '\n' +
                        "Size : " + this.largeur + "x" + this.hauteur + '\n' + 
                        "Luminosité max : " + this.lumMax + '\n');
    }

    //Il manque bcp de get et les set

    public int getLum(){
        return this.lum;
    }
    public int getHauteur(){
        return this.hauteur;
    }

    public int getLargeur(){
        return this.largeur;
    }    

    public Quadtree getFils1(){
        return this.f1;
    }

    public Quadtree getFils2(){
        return this.f2;
    }
    
    public Quadtree getFils3(){
        return this.f3;
    }
    
    public Quadtree getFils4(){
        return this.f4;
    }
}


