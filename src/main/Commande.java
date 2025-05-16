package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    
    public static void changerModeReception(){
        List<String> maListe = new ArrayList<>();
        maListe.add("Magasin");
        maListe.add("Livraison");
        maListe.add("Retour");
        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Choix de Livraison",maListe)); 
            System.out.println("Ou souhaite tu récuperer ta commande ? : ");
            String commande_brute=System.console().readLine();
            String commande = commande_brute.strip().toLowerCase();
            if (commande.equals("1")){
                choisirMagasin();

            }



            else if(commande.equals("3")){
                commande_faite=true;
            }


        }
        
        
    }

    public static void choisirMagasin(){
        List<String> maListe = new ArrayList<>();
        maListe.add("Magasin Robin");
        maListe.add("Magasin Coco");
        maListe.add("Magasin Raphael");
        maListe.add("Magasin Jorris");
        maListe.add("Retour");
        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Choix Magasins",maListe));
            System.out.println("Dans quel magasin ? : ");
            String commande_brute=System.console().readLine();
            String commande = commande_brute.strip().toLowerCase();
            if (commande.equals("5")){
                commande_faite=true;

            }

        

        }
    }

}
