import java.io.*;
import java.util.ArrayList;

public class Quadtree {
    
    //Objet PGM comportant les informations du fichier pgm et les methode utiles liées
    private PGM pgm = new PGM();
    
    //Valeurs de luminosité comprise dans le PGM
    private int lum;               

    //Valeurs de luminosité comprise dans le PGM
    private double eps; 

    //Les 4 fils(noeuds) du QuadTree
    private Quadtree f1;
    private Quadtree f2;
    private Quadtree f3;
    private Quadtree f4;

    private Quadtree pere;

    private int nbrNoeudsAvantComp;
    private int nbrNoeudsApresComp;


    ArrayList<Quadtree> listEps = new ArrayList<>();       

    //Constructeur initial
    //Chemin du PGM compressé de type : imgPGM/[nom.pgm] ou chemin complet
    //Image de taille 2^n*2^n (carré)
    public Quadtree(String path, Quadtree f1,Quadtree f2,Quadtree f3,Quadtree f4){
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.lum = -1;
        this.pgm.ReadImg(path);
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

    //Fonction retournant un QuadTree remplie avec les valeurs de lum du tableau 2D
    //Mettre en void [A VOIR]
    public Quadtree createQuadTree(int[][] tabLum, int hauteur, int largeur){
        //Si L'image est composer de la meme couleur alors pas besoin de decoupe
        if(this.pgm.sameColor(tabLum,hauteur,largeur)){
            Quadtree newTree = new Quadtree(tabLum[0][0]);
            newTree.pgm = this.pgm;
            return newTree;
        } 
        //Si on est sur un format 2*2 du tableau de lum alors on creer une brindille prenant 4 feuilles
        if(hauteur/2 == 1){
            Quadtree F1 = new Quadtree(tabLum[0][0]);
            Quadtree F2 = new Quadtree(tabLum[0][1]);
            Quadtree F3 = new Quadtree(tabLum[1][1]);
            Quadtree F4 = new Quadtree(tabLum[1][0]);
            Quadtree brindille = new Quadtree(F1, F2, F3, F4);
            brindille.pgm = this.pgm;
            return brindille;
        //Decoupe de l'image en 4 regions
        } else {
            int newHauteur = hauteur/2;
            int newLargeur = largeur/2;
            int[][] HG = this.pgm.decoupeTab(tabLum,0,0,newHauteur,newLargeur);
            int[][] HD = this.pgm.decoupeTab(tabLum,0,0+newLargeur,newHauteur,newLargeur);
            int[][] BD = this.pgm.decoupeTab(tabLum,0+newHauteur,0+newLargeur,newHauteur,newLargeur);
            int[][] BG = this.pgm.decoupeTab(tabLum,0+newHauteur,0,newHauteur,newLargeur);

            Quadtree newTree = new Quadtree(createQuadTree(HG, newHauteur, newLargeur), createQuadTree(HD, newHauteur, newLargeur), 
                                            createQuadTree(BD, newHauteur, newLargeur), createQuadTree(BG, newHauteur, newLargeur));
            newTree.pgm = this.pgm;
            return newTree;
        }
    }

    //Regarde si le fils a une valeur entiere positive dans ce cas c'est une feuille
    //la comparaison avec null ne marche pas car int est un type trivial
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

    //Methode de compression Lambda, prenant la moyenne logarithmique des valeurs des brindilles de l'arbre (fait perdre un niveau a l'arbre)
    public void compressLambda(){
        if(!this.estFeuille()){
            if(this.estBrindille()){
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

    
    //Methode de compression Lambda, sur un noeud de l'arbre
    public void compressLambdaNoeud(){
        double moyenneLum = Math.exp((Math.log(this.f1.lum + 0.1) + Math.log(this.f2.lum + 0.1) + Math.log(this.f3.lum + 0.1) + Math.log(this.f4.lum + 0.1)) / 4); //calcul de la moyenne des luminosité avec formule du tp
        int arrMoyenneLum = (int) Math.round(moyenneLum);
        this.lum = arrMoyenneLum; //arrMoyenneLum remplace -1
        //deference les 4 fils (feuilles) les sauvergarder quelques part ?
        this.f1 = null;
        this.f2 = null;
        this.f3 = null;
        this.f4 = null;               
    }

    //Procedure parcourant l'arbre en remplissant sur place le tableau 2D de nouvelles valeurs
    //Utile pour recréer un tableau 2D pour l'arbre compressé et pouvoir l'afficher en PGM plus facilement
    public void quadTreeToTab2D(Quadtree racine, int ligneDep, int colonneDep, int hauteur, int largeur){
        if(this.estFeuille()){
            for (int i = ligneDep; i < ligneDep + hauteur; i++) {
                for (int j = colonneDep; j < colonneDep + largeur; j++) {
                    racine.getPgm().setValTabLum(i,j, this.lum);
                }
            }
        } else {
            hauteur = hauteur/2;
            largeur = largeur/2;

            this.f1.quadTreeToTab2D(racine,ligneDep,colonneDep, hauteur, largeur);
            this.f2.quadTreeToTab2D(racine, ligneDep,colonneDep+largeur, hauteur, largeur);
            this.f3.quadTreeToTab2D(racine,ligneDep+hauteur,colonneDep+largeur, hauteur, largeur);
            this.f4.quadTreeToTab2D(racine,ligneDep+hauteur,colonneDep, hauteur, largeur);
        }
    }

    //Aucuns paramatres de l'image n'est sauvergarder, a cause du renvoie d'un quadtree dans le quadtree principale dans createQuadTree [A DEBUG]
    //Creer une fonction mettant les valeurs des feuilles dans un nouveau tableau 2D pour l'ecrire dans un fichier
    //Faire le toPGM sans passer par un nouveau tableau 2D, faire un parcour suffixe, des feuilles et les ecrire dans le fichier 
    public void toPGM(String path) {
        try {
            quadTreeToTab2D(this,0, 0, this.pgm.getHauteur(), this.pgm.getLargeur());
            //Créer un nouveau fichier
            File file = new File(path);
            FileWriter writer = new FileWriter(file);
    
            //Ecrit les infos du fichier PGM
            writer.write(this.pgm.getMagicNumber() + "\n");
            writer.write(this.pgm.getCommentaire() + "\n");
            writer.write(this.pgm.getLargeur() + " " + this.pgm.getHauteur() + "\n");
            writer.write(this.pgm.getLumMax() + "\n");

            //Ecrit le tableau de luminositée associé a l'arbre
            for (int i = 0; i < this.pgm.getHauteur(); i++) {
                for (int j = 0; j < this.pgm.getLargeur(); j++) {
                    writer.write(this.pgm.getLum(i,j) + " ");
                }
                writer.write("\n");
            }
            //Ferme le fichier
            writer.close();
        
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    //Insertion croisante de la liste
    public void insertionTriCroisante(ArrayList<Quadtree> liste, Quadtree elem){
        int i = 0;
        //Donne la place ou doit etre inserer notre elem
        while (i < liste.size() && liste.get(i).eps < elem.eps) {
            i++;
        }

        liste.add(i, elem);
    }

    //Creer un parametre de classe qui est une liste croisante (file)
    //creer une procedure remplissant la liste 
    //retrouver a quel noeuds appartient le epsilon ? soit liste de numero de quadtree et epsilon donc creer un num unique pour chaque quadtree ou une classe noeuds avec un epsilon et une liste de epsilon dans quadtree
    // un compLambda sur un noeud 

    public void remplirListeEpsilon(Quadtree pere, Quadtree racine){
        if(!estFeuille()){
            if(estBrindille()){
                double epsilon = Math.abs(Math.exp((Math.log(this.f1.lum + 0.1) + Math.log(this.f2.lum + 0.1) + Math.log(this.f3.lum + 0.1) + Math.log(this.f4.lum + 0.1)) / 4) - Math.max(Math.max(f1.lum, f2.lum),Math.max(f3.lum, f4.lum)));    
                this.eps = epsilon;
                this.pere = pere;

                //Stocker epsilon dans une liste decroissante
                if(racine.listEps.isEmpty()){
                    racine.listEps.add(this);
                } else {
                    insertionTriCroisante(racine.listEps, this);
                } 
            } else {
                this.pere = pere;
                f1.remplirListeEpsilon(this, racine);
                f2.remplirListeEpsilon(this, racine);
                f3.remplirListeEpsilon(this, racine);
                f4.remplirListeEpsilon(this, racine);
            }
        }
        
    }   

//Compression Rho, prenant en parametre un taux de compression limite, pour pouvoir choisir le taux de dégradation de l'image
    //Les regions avec des valeurs de lum proches seront prioritaire a la compression 
    public void compressRho(int p){ 
        remplirListeEpsilon(this, this);
        Quadtree temp = new Quadtree(null,null,null,null);
        temp.pgm = this.pgm;
        this.nbrNoeudsAvantComp = this.nbrNoeuds();
        this.nbrNoeudsApresComp = this.nbrNoeuds();
        while(tauxDeCompression() > p){
            this.listEps.get(0).compressLambdaNoeud();
            temp = this.listEps.get(0);
            this.nbrNoeudsApresComp = this.nbrNoeuds();
            this.listEps.remove(0);

            //regarder si le pere est une brindille si oui calcul esp et ajout liste (a la main)
            if(temp.pere.estBrindille()){
                temp.pere.eps = Math.abs(Math.exp((Math.log(this.pere.f1.lum + 0.1) + Math.log(this.pere.f2.lum + 0.1) + Math.log(this.pere.f3.lum + 0.1) + Math.log(this.pere.f4.lum + 0.1)) / 4) - Math.max(Math.max(pere.f1.lum, pere.f2.lum),Math.max(pere.f3.lum, pere.f4.lum)));
                temp.insertionTriCroisante(this.listEps, temp.pere);
            }

        }
    }

    //Fonction comptant le nombre de noeuds compris dans le quadTree
    public int nbrNoeuds(){
        if(!estFeuille()){
            int compt1 = f1.nbrNoeuds();
            int compt2 = f2.nbrNoeuds();
            int compt3 = f3.nbrNoeuds();
            int compt4 = f4.nbrNoeuds();
            return compt1+compt2+compt3+compt4+1;
        } else {
            return 1;            
        }
    }

    //Fonction calculant le taux de compression entre l'arbre et l'arbre compressé (0% = aucuns changement du nombres de noeuds / 100% = nombre de noeuds égal à 1 après compression)
    public void tauxDeCompressionPrint() {
        double tc = Math.round(((double)this.nbrNoeudsApresComp/(double)this.nbrNoeudsAvantComp) *100) ;
        System.out.println('\n' + "Le nombre de noeuds de l'arbre non-compressé est : " + this.nbrNoeudsAvantComp + '\n' + "Le nombre de noeuds de l'arbre compressé est : " + this.nbrNoeudsApresComp + '\n' + "Le taux de compression de l'image est de " + tc + "%");
    }

    public double tauxDeCompression() {
        double tc = Math.round((((double)this.nbrNoeudsApresComp/(double)this.nbrNoeudsAvantComp))*100);
        return tc;
    }

    //SET
    public void setNbrNoeudsAvantComp(int nbNoeuds){
        this.nbrNoeudsAvantComp = nbNoeuds;
    }

    public void setNbrNoeudsApresComp(int nbNoeuds){
        this.nbrNoeudsApresComp = nbNoeuds;
    }

    //GET
    public int getLum(){
        return this.lum;
    }

    public double getEps(){
        return this.eps;
    }

    public int getNbrNoeudsAvantComp(){
        return this.nbrNoeudsAvantComp;
    }

    public int getNbrNoeudsApresComp(){
        return this.nbrNoeudsApresComp;
    }

    public PGM getPgm(){
        return this.pgm;
    }

    public Quadtree getFils1(){
        return this.f2;
    }
    
    public Quadtree getFils2(){
        return this.f2;
    }
    
    public Quadtree getFils3(){
        return this.f4;
    }

    public Quadtree getFils4(){
        return this.f4;
    }
}


