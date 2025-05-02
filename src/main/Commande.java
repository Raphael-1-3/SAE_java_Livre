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
    public double prixPanier(){
        return 0.0;
    }
    public int nbLivre(){
        return 0;
    }
    
}
