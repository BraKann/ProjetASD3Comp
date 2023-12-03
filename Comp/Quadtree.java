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

    //Permet de stocker les valeurs de luminosité lu durant le pacours du PGM (hauteur = ligne ET largeur = colonne)
    private int[][] tabLum;
    
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

            for(int ligne = 0; ligne < this.hauteur; ligne++){
                for(int colonne = 0; colonne <this.largeur; colonne++){
                    tabLum[ligne][colonne]= scan.nextInt();
                }
            }
    
            scan.close();

        } catch (IOException exception) {
		    exception.printStackTrace();
        }
    }

    public void printTabLum(){
        for(int ligne = 0; ligne < this.hauteur; ligne++){
            for(int colonne = 0; colonne < this.largeur; colonne++){
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

    public boolean sameColor(int[][] tabLum, int hauteur, int largeur){
        boolean _isSameCol = true;
        
        for(int ligne = 0; ligne < hauteur; ligne++){
            for(int colonne = 0; colonne < largeur; colonne++){
                _isSameCol = _isSameCol && (tabLum[0][0] == tabLum[ligne][colonne]);
                if(!_isSameCol){
                    return _isSameCol;
                }
            }
        }
        return _isSameCol;
    }

    public int[][] decoupeTab(int[][] tabLum, int ligne, int colonne, int hauteur, int largeur){
        int[][] newTab = new int[hauteur][largeur];
        for(int posL = 0; posL < hauteur; posL++){
            for(int posC = 0; posC < largeur; posC++){
                newTab[posL][posC] = tabLum[posL + ligne][posC + colonne];
            }
        }
        return newTab;
    }

    public Quadtree createQuadTree(int[][]tabLum, int hauteur, int largeur){
        //Si L'image est composer de la meme couleur alors pas besoin de decoupe
        if(sameColor(tabLum,hauteur,largeur)){
            Quadtree newTree = new Quadtree(tabLum[0][0]);
            return newTree;
        }
        //Si on ne peut pas découper en 4 alors on est sur une brindille
        if(this.hauteur/2 == 1){
            System.out.println(tabLum[0][0]);
            Quadtree F1 = new Quadtree(tabLum[0][0]);
            System.out.println(tabLum[0][1]);
            Quadtree F2 = new Quadtree(tabLum[0][1]);
            System.out.println(tabLum[1][1]);
            Quadtree F3 = new Quadtree(tabLum[1][1]);
            System.out.println(tabLum[1][0]);
            Quadtree F4 = new Quadtree(tabLum[1][0]);
            Quadtree brindille = new Quadtree(F1, F2, F3, F4);
            return brindille;
        //Decoupe de l'image en 4 parties
        } else {
            int newHauteur = hauteur/2;
            int newLargeur = largeur/2;
            int[][] HG = decoupeTab(tabLum,0,0,newHauteur,newLargeur);
            int[][] HD = decoupeTab(tabLum,0,0+newLargeur,newHauteur,newLargeur);
            int[][] BD = decoupeTab(tabLum,0+newHauteur,0+newLargeur,newHauteur,newLargeur);
            int[][] BG = decoupeTab(tabLum,0+newHauteur,0,newHauteur,newLargeur);
        
            Quadtree newTree = new Quadtree(createQuadTree(HG, newHauteur, newLargeur), createQuadTree(HD, newHauteur, newLargeur), 
                                            createQuadTree(BD, newHauteur, newLargeur), createQuadTree(BG, newHauteur, newLargeur));
            return newTree;
        }
    }

//Sinon un booleen estFeuille en parametre du quadtree?
    //Regarde si tout les fils du quadtree ont une valeur entiere positive(feuille) dans ce cas c'est une brindille
    //la comparaison avec null ne marche pas car int n'est pas un objet qui peut etre nul, c'est un type trivial
    public boolean estBrindille(){
        if(this.f1.lum != -1 && this.f2.lum != -1 && this.f3.lum != -1 && this.f4.lum != -1) return true;
        return false;
    }

    //Regarde si le fils a une valeur entiere positive dans ce cas c'est une feuille
    //la comparaison avec null ne marche pas car int n'est pas un objet
    public boolean estFeuille(){
        if(this.lum != 1) return true;
        return false;
    }

    //Affiche l'arbre crée dans la forme parenthésée
    //toString existe deja dans le langage
    public void _toString(){
        System.out.print("( ");
        if(estBrindille()){
            System.out.print("("+f1.lum+" "+f2.lum+" "+f3.lum+" "+f4.lum+")");
        } else {
            f1._toString();
            f2._toString();
            f3._toString();
            f4._toString();
        }
        System.out.print(" )");
    }
    
    //Il manque bcp de get et les set

    public int getLum(){
        return this.lum;
    }

    public int[][] getTabLum(){
        return this.tabLum;
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


