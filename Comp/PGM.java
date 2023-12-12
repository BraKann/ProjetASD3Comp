import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PGM {

    //Informations sur le PGM a garder pour le retourner une fois compressé
    private String magicNumber;
    private String commentaire; 
    private int largeur;
    private int hauteur;
    private int lumMax;            
    
    //Permet de stocker les valeurs de luminosité lu durant le pacours du PGM (hauteur = ligne ET largeur = colonne)
    private int[][] tabLum;

    //SET
    public void setTabLum(int[][] tabLumNew){
        this.tabLum = tabLumNew;
    }   

    public void setValTabLum(int i, int j, int lum){
        this.tabLum[i][j] = lum;
    }   

    //GET
    public int[][] getTabLum(){
        return this.tabLum;
    }

    public String getMagicNumber(){
        return this.magicNumber;
    }   

    public String getCommentaire(){
        return this.commentaire;
    }   

    public int getLum(int i, int j){
        return this.tabLum[i][j];
    }

    public int getLumMax(){
        return this.lumMax;
    }

    public int getHauteur(){
        return this.hauteur;
    }

    public int getLargeur(){
        return this.largeur;
    }    

    //Procedure permetant la lectrue d'un fichier PGM et la sauvegarde de ses données
    public void ReadImg(String path) {
        try {
            
            //Créer un nouveau fichier et l'objet scanner
            File img = new File(path);
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


    //Procedure permettant l'affichage du tableau 2D contenant les valeurs de luminositée
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
    public int[][] decoupeTab(int[][] tabLum, int ligneDep, int colonneDep, int hauteur, int largeur){
        int[][] newTab = new int[hauteur][largeur];
        for(int posL = 0; posL < hauteur; posL++){
            for(int posC = 0; posC < largeur; posC++){
                newTab[posL][posC] = tabLum[posL + ligneDep][posC + colonneDep];
            }
        }
        return newTab;
    }
}
