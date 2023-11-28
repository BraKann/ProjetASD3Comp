import java.io.*; 
import java.util.Scanner;

public class Quadtree {

    //Chemin du PGM compressé de type : imgPGM/[nom.pgm] ou chemin complet
    //Image de taille 2^n*2^n (carré)
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
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
    }

    public Quadtree(Quadtree f1,Quadtree f2,Quadtree f3,Quadtree f4){
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
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
            this.tabLum = new int[this.hauteur][this.largeur];

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
                        "Size : " + this.hauteur + "x" + this.largeur + '\n' + 
                        "Luminosité max : " + this.lumMax + '\n');
    }

    public Quadtree createQuadTree(){
        int nbNivCreer = 0;
        int nbNivMax = this.hauteur/2;
        int hauteurDiv = this.hauteur/2;
        int largeurDiv = this.largeur/2;
        int[][] tabLumDiv = new int[hauteurDiv][largeurDiv];

        if(nbNivCreer == nbNivMax){
            this.f1 = new Quadtree(null,null,null,null);
            this.f2 = new Quadtree(null,null,null,null);
            this.f3 = new Quadtree(null,null,null,null);
            this.f4 = new Quadtree(null,null,null,null);

            this.f1.lum = tabLumDiv[0][0];
            this.f2.lum = tabLumDiv[0][1];
            this.f3.lum = tabLumDiv[1][0];
            this.f4.lum = tabLumDiv[1][1];
        } else {
            nbNivCreer++;
            for(int ligne = 0; ligne < hauteurDiv; ligne++){
                for(int colonne = 0; colonne < largeurDiv; colonne++){
                    tabLumDiv[ligne][colonne] = this.tabLum[ligne][colonne];
                }
            }
            this.hauteur = hauteurDiv;
            this.largeur = largeurDiv;

            this.f1 = new Quadtree(null,null,null,null);
            return this.f1.createQuadTree();
            this.f2 = new Quadtree(null,null,null,null);
            return this.f2.createQuadTree();
            this.f3 = new Quadtree(null,null,null,null);
            return this.f3.createQuadTree();
            this.f4 = new Quadtree(null,null,null,null);
            return this.f4.createQuadTree();
        }
    }

    public String toString(){
        System.out.print("(");
        if(this.f1 == null){
            System.out.print( "("+this.f1.lum+" "+this.f2.lum+" "+this.f3.lum+" "+this.f4.lum+")");
        } else {
            return this.f1.toString();
            return this.f2.toString();
            return this.f3.toString();
            return this.f4.toString();
        }
        System.out.print(")");
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


