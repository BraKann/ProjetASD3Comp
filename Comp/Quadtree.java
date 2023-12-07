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
    private int lumMax;            

    //Permet de stocker les valeurs de luminosité lu durant le pacours du PGM (hauteur = ligne ET largeur = colonne)
    private int[][] tabLum;
    
    //Valeurs de luminosité comprise dans le PGM
    private int lum;               

    //Les 4 fils(noeuds) du QuadTree
    private Quadtree f1;
    private Quadtree f2;
    private Quadtree f3;
    private Quadtree f4;

    //Constructeur diff pour racine ? avec info du pgm ? [A VOIR]

    //Constructeur initial (racine)
    public Quadtree(String path, Quadtree f1,Quadtree f2,Quadtree f3,Quadtree f4){
        this.path = path;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.lum = -1;
    }

    //Constructeur de noeud
    public Quadtree(Quadtree f1,Quadtree f2,Quadtree f3,Quadtree f4){
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.lum = -1;
    }
        
    //Constructeur de feuille
    public Quadtree(int lum){
        this.f1 = null;
        this.f2 = null;
        this.f3 = null;
        this.f4 = null;
        this.lum = lum;
    }
    
    //Procedure permetant la lectrue d'un fichier PGM et la sauvegarde de ses données
    public void ReadImg() {
        try {
            
            //Créer un nouveau fichier et l'objet scanner
            File img = new File(this.path);
            Scanner scan = new Scanner(img);

            //Sauvergarde des données du PGM
            this.magicNumber = scan.nextLine();
            this.commentaire = scan.nextLine();
            //Verification reste des commentaire " commence par #" [A FAIRE]
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
            
            //Quitte le fichier
            scan.close();

        } catch (IOException exception) {
		    exception.printStackTrace();
        }
    }

    //Procedure permettant l'affichage du tableau 2D contenant les valeur de luminositée
    public void printTabLum(){
        for(int ligne = 0; ligne < this.hauteur; ligne++){
            for(int colonne = 0; colonne < this.largeur; colonne++){
                System.out.print(this.tabLum[ligne][colonne] + " ");
            }

            System.out.println();
        }
    }

    //Procedure permettant l'affichage des infos sauvergardé du fichier PGM
    public void printInfoPGM(){
        System.out.print("Magic Number : " + this.magicNumber + '\n' + 
                        "Commentaires : " + this.commentaire + '\n' +
                        "Size : " + this.hauteur + "x" + this.largeur + '\n' + 
                        "Luminosité max : " + this.lumMax + '\n');
    }

    //Fonction booleen comparant les lum d'un tableau, retourne faux si au moin un élément du tableau est différent, retourne vrai si tout les éléments du tableau sont égaux
    //Possiblement, regarder dans quelle partie(HG,HD...) du tableau il y a des non egaux et decouper seulement ces parties (gain de temps et de place ?)[A VOIR] 
    //Les print nous montre que les comparaison bug, on ne compare pas tout le tableau mais qu'une partie [A debug]
    public boolean sameColor(int[][] tabLum, int hauteur, int largeur){
        boolean _isSameCol = false;
        
        for(int ligne = 0; ligne < hauteur; ligne++){
            for(int colonne = 0; colonne < largeur; colonne++){
                _isSameCol = (tabLum[0][0] == tabLum[ligne][colonne]);
                if(!_isSameCol){
                    return _isSameCol;
                }
            }
        }
        return _isSameCol;
    }

    //Fonction retournant un tab 2D qui est un sous tableau du tab d'entrée
    //Represente une partie d'une d'écoupe de notre QuadTree (HG,HD...) 
    public int[][] decoupeTab(int[][] tabLum, int ligne, int colonne, int hauteur, int largeur){
        int[][] newTab = new int[hauteur][largeur];
        for(int posL = 0; posL < hauteur; posL++){
            for(int posC = 0; posC < largeur; posC++){
                newTab[posL][posC] = tabLum[posL + ligne][posC + colonne];
            }
        }
        return newTab;
    }

    //Fonction retournant un QuadTree remplie davec les valeur de lum du tableau 2D
    //Mettre en void [A VOIR]
    public Quadtree createQuadTree(int[][]tabLum, int hauteur, int largeur){
        //Si L'image est composer de la meme couleur alors pas besoin de decoupe
        if(sameColor(tabLum,hauteur,largeur)){
            Quadtree newTree = new Quadtree(tabLum[0][0]);
            newTree.commentaire = this.commentaire;
            newTree.hauteur = this.hauteur;
            newTree.largeur = this.largeur;
            newTree.magicNumber = this.magicNumber;
            newTree.lumMax = this.lumMax;
            newTree.tabLum = this.tabLum;
            return newTree;
        } 
        //Si on est sur un format 2*2 du tableau de lum alors on creer une brindille prenant 4 feuilles
        if(hauteur/2 == 1){
            Quadtree F1 = new Quadtree(tabLum[0][0]);
            Quadtree F2 = new Quadtree(tabLum[0][1]);
            Quadtree F3 = new Quadtree(tabLum[1][1]);
            Quadtree F4 = new Quadtree(tabLum[1][0]);
            Quadtree brindille = new Quadtree(F1, F2, F3, F4);
            return brindille;
        //Decoupe de l'image en 4 regions
        } else {
            int newHauteur = hauteur/2;
            int newLargeur = largeur/2;
            int[][] HG = decoupeTab(tabLum,0,0,newHauteur,newLargeur);
            int[][] HD = decoupeTab(tabLum,0,0+newLargeur,newHauteur,newLargeur);
            int[][] BD = decoupeTab(tabLum,0+newHauteur,0+newLargeur,newHauteur,newLargeur);
            int[][] BG = decoupeTab(tabLum,0+newHauteur,0,newHauteur,newLargeur);
        
            Quadtree newTree = new Quadtree(createQuadTree(HG, newHauteur, newLargeur), createQuadTree(HD, newHauteur, newLargeur), 
                                            createQuadTree(BD, newHauteur, newLargeur), createQuadTree(BG, newHauteur, newLargeur));
            newTree.commentaire = this.commentaire;
            newTree.hauteur = this.hauteur;
            newTree.largeur = this.largeur;
            newTree.magicNumber = this.magicNumber;
            newTree.lumMax = this.lumMax;
            newTree.tabLum = this.tabLum;
            return newTree;
        }
    }


    //Regarde si le fils a une valeur entiere positive dans ce cas c'est une feuille
    //la comparaison avec null ne marche pas car int n'est pas un objet
    //La comparaison a -1 nous provoque des erreurs car la valeur renvoyer lors de la verif (this.lum) sur un arbre non feuille renvoie 0
    //PB AVEC LES VAL DE LUM = A 0,; trouver un autre moyen de verif [A debug] initialiser les constructeur avec -1 en lum sauf le constructeur de feuille
    public boolean estFeuille(){
        if(this.lum != -1) return true;
        return false;
    }

    //Sinon un booleen estFeuille en parametre du quadtree?
    //Regarde si tout les fils du quadtree ont une valeur entiere positive(feuille) dans ce cas c'est une brindille
    public boolean estBrindille(){
        if(!this.estFeuille() && this.f1.estFeuille() && this.f2.estFeuille() && this.f3.estFeuille() && this.f4.estFeuille()) return true;
        return false;
    }

    //Affiche l'arbre crée dans la forme parenthésée
    public void _toString(){
        if(this.estBrindille()){
            System.out.print("("+this.f1.lum+" "+this.f2.lum+" "+this.f3.lum+" "+this.f4.lum+")");
        }else if(this.estFeuille()){
            System.out.print("("+this.lum+")");
        } else {
            this.f1._toString();
            this.f2._toString();
            this.f3._toString();
            this.f4._toString();
        }
    }

    //[A FAIRE] creer un nouveau tableau de lum pour l'arbre compressé et ainsi retoutner une image compressé avec toPGM
    //Methode de compression Lambda, prenant la moyenne logarithmique des valeurs des brindilles de l'arbre (fait perdre un niveau a l'arbre)
    public void compressLambda() {
        if(!this.estFeuille()){
            if(this.estBrindille()) {
                double moyenneLum = Math.exp((Math.log(this.f1.lum + 0.1) + Math.log(this.f2.lum + 0.1) + Math.log(this.f3.lum + 0.1) + Math.log(this.f4.lum + 0.1)) / 4); //calcul de la moyenne des luminosité avec formule du tp
                int arrMoyenneLum = (int) Math.round(moyenneLum);
                this.lum = arrMoyenneLum; //arrMoyenneLum remplace -1
                //deference les 4 fils (feuilles) les sauvergarder quelques part ?
                this.f1 = null;
                this.f2 = null;
                this.f3 = null;
                this.f4 = null;            
            } else { 
                this.f1.compressLambda();
                this.f2.compressLambda();
                this.f3.compressLambda();
                this.f4.compressLambda();
            }
        }
    }
    
    //Aucuns paramatres de l'image n'est sauvergarder, a cause du renvoie d'un quadtree dans le quadtree principale dans createQuadTree [A DEBUG]
    //Creer une fonction mettant les valeurs des feuilles dans un nouveau tableau 2D pour l'ecrire dans un fichier
    public void toPGM(String path) {
        try {

            //Créer un nouveau fichier
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
    
            //Ecrit les infos du fichier PGM
            writer.write(this.magicNumber + "\n");
            writer.write(this.commentaire + "\n");
            writer.write(this.largeur + " " + this.hauteur + "\n");
            writer.write(this.lumMax + "\n");

            for (int i = 0; i < this.hauteur; i++) {
                for (int j = 0; j < this.largeur; j++) {
                    writer.write(this.tabLum[i][j] + " ");
                }
                writer.write("\n");
            }
            //Ferme le fichier
            writer.close();
        
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    //Fonction comptant le nombre de noeuds compris dans le quadTree
    //Initialise le compteur a 1  (la racine)
    public int nbrNoeuds(int compt){
        if(estFeuille()){
            compt = compt + 1;
            return compt;
        } else if(estBrindille()) {
            compt += f1.nbrNoeuds(compt);
            compt += f2.nbrNoeuds(compt);
            compt += f3.nbrNoeuds(compt);
            compt += f4.nbrNoeuds(compt);
            return compt;
        } else {
            compt = compt + 1;
            compt += f1.nbrNoeuds(compt);
            compt += f2.nbrNoeuds(compt);
            compt += f3.nbrNoeuds(compt);
            compt += f4.nbrNoeuds(compt);
            return compt;            
        }
    }

    //Fonction calculant le taux de compression entre l'arbre et l'arbre compressé
    //-100 ? si aucune diff alors taux a 0 ?
    public void tauxDeCompression(int nbrNoeudsComp, int nbrNoeuds) {
        float tc = ( nbrNoeudsComp / nbrNoeuds ) * 100;
        System.out.println('\n' + "Le nombre de noeuds de l'arbre non-compressé est : " + nbrNoeuds + '\n' + "Le nombre de noeuds de l'arbre compressé est : " + nbrNoeudsComp + '\n' + "Le taux de compression de l'image est de " + tc + "%");
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


