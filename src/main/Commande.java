package main;

import java.util.List;
import java.util.ArrayList;

public class Commande{

    private int idcommande;
    private List<Livre> panier;
    private Livre livre;

    public Commande(int idcommande){
        this.idcommande=idcommande;
        this.panier=new ArrayList<>();
    }

    public void ajouterLivre(Livre livre){}
    public void enleverLivre(Livre livre){}
    public List<Livre> consulterPanier(){
        return new ArrayList<>();
    }
   
    public int nbLivre(){
        return 0;
    }
    
    public void ajouterArticle(Livre livre){
        this.panier.add(livre);
    }

    public void enleverArticle(Livre livre){
        this.panier.remove(livre);
    }

    public double prixPanier(){
        double res=0.0;
        for (Livre livre : this.panier){
            res+=livre.getPrix();
        }
        return res;
    }

    public int nombreArticle(){
        int res=0;
        for (Livre livre : this.panier){
            res+=1;
        }
        return res;
    }
    
}
