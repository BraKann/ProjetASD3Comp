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

    public Quadtree(int lum){
        this.f1 = null;
        this.f2 = null;
        this.f3 = null;
        this.f4 = null;
        this.lum = lum;
    }
    public void ReadImg() {
        try {

            File img = new File(this.path);
            Scanner scan = new Scanner(img);

            this.magicNumber = scan.nextLine();
            this.commentaire = scan.nextLine();
            //Verification reste des commentaire " commence par #"
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
        Quadtree sf1 = new Quadtree(5);
        Quadtree sf2= new Quadtree(6);
        Quadtree sf3 = new Quadtree(4);
        Quadtree sf4 = new Quadtree(8);
        Quadtree newTree = new Quadtree(sf1, sf2, sf3, sf4);
        return newTree;
    }

    //Regarde si tout les fils du quadtree ont une valeur entiere positive(feuille) dans ce cas c'est une brindille
    // la comparaison avec null ne marche pas car int n'est pas un objet
    public boolean estBrindille(){
        
        return false;
    }

    public boolean estFeuille(){
        return false;
    }

    //toString existe deja
    public void _toString(){
        System.out.print("(");
        if(this.f1.estBrindille()){
            System.out.print("("+this.f1.lum+" "+this.f2.lum+" "+this.f3.lum+" "+this.f4.lum+") ");
        } else {
            this.f1._toString();
            this.f2._toString();
            this.f3._toString();
            this.f4._toString();
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


