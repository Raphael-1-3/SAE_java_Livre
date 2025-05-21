package main;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

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

            else if(commande.equals("2")){
                livraison();
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


    public static void livraison(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel est le numéro de votre adresse ? :" );
        String num = scanner.nextLine();
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Quel est le nom de la rue ? :" );
        String rue = scanner.nextLine();
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Quel est votre adresse postale :" );
        String adp = scanner.nextLine();
        Scanner scanner4 = new Scanner(System.in);
        System.out.println("Votre adresse est bien : " +num+" "+rue+" "+adp+" ? Y/N");
        String verif = scanner.nextLine();
        verifLivraison(verif);

    }

    public static void verifLivraison(String verif){
        if(verif.equals("Y")){
            System.out.println("Vos informations sont bien enregistré dans notre base de donné. Votre colis arriveras d'ici peu");
        }
        else{
            System.out.println("Veuillez retaper vos informations");
            livraison();
        }
    }

}


