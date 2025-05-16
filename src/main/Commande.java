package main;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Commande{

    private int idcommande;
    private HashMap<Livre, Integer> panier;
    private Livre livre;

    public Commande(int idcommande){
        this.idcommande=idcommande;
        this.panier=new HashMap<>();
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
        if (this.panier.containsKey(livre))
        {
            this.panier.put(livre, this.panier.get(livre) + 1);
        }
        else
        {
            this.panier.put(livre, 1);
        }
    }

    public void ajouterArticle(Livre livre, Integer qte){
        if (this.panier.containsKey(livre))
        {
            this.panier.put(livre, this.panier.get(livre) + qte);
        }
        else
        {
            this.panier.put(livre, qte);
        }
    }
    public void enleverArticle(Livre livre){
        this.panier.remove(livre);
    }
    
    public void enleverArticle(Livre livre, Integer qte){
        this.panier.put(livre, this.panier.get(livre) - qte);
    }
    
    public double prixPanier(){
        double res=0.0;
        for (Livre livre : this.panier.keySet()){
            res+=livre.getPrix() * this.panier.get(livre);
        }
        return res;
    }

    public int nombreArticle(){
            int res=0;
            for (Livre livre : this.panier.keySet()){
                res+=this.panier.get(livre);
            }
            return res;
    }
    
    public void changerModeReception(){}
}
