package app;


import java.util.List;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Scanner;

import Affichage.AfficherMenu;
import BD.ActionBD;

import java.util.Map;

public class Commande{

    private int idcommande;
    private HashMap<Livre, Integer> panier;
    private Date datecom;
    private String enligne;
    private String livraison;

    public Commande(int idcommande, ActionBD bd){
        this.idcommande=idcommande;
        this.panier=new HashMap<>();
        try
        {
            this.datecom = bd.getCurrentDate();
        }
        catch (SQLException e)
        {

        }
        this.enligne = "O";
        this.livraison = "M";
    }

    public void setEnLigne(String enligne)
    {
        this.enligne = enligne;
    }

    public int getIdCommande() {return this.idcommande;}

    public String getLivraison()
    {
        return this.livraison;
    }

    public String getEnLigne()
    {
        return this.enligne;
    }
   
    public int nbLivre(){
        return 0;
    }

    public Date getDate()
    {
        return this.datecom;
    }

    public HashMap<Livre, Integer> getPanier()
    {
        return this.panier;
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
    
    public void changerModeReception(ActionBD bd){
        boolean commande_faite = false;
        Scanner scan = new Scanner(System.in);
        while (!commande_faite)
        {
            System.out.println(AfficherMenu.Menu("Choisir Mode Livraison", Arrays.asList("A domicile", "En Magasin", "Retour")));
            String commande_brute = scan.nextLine();
            String commande = commande_brute.strip().toLowerCase();
            switch (commande) {
                case "1":
                    this.livraison = "C";
                    commande_faite = true;
                    break;
                case "2":
                    this.livraison = "M";
                    commande_faite = true;
                    break;
                case "3":
                    commande_faite = true;
            }
        }
        scan.close();
    }

    public static void rechercher(ActionBD bd){
        try{
            Scanner recherche = new Scanner(System.in);
            System.out.println("Quel est le nom du livre ? :" );
            String nomLivre = recherche.nextLine();
            List<Livre> liste = bd.cherhcherLivreApproximative(nomLivre);
            for (Livre l : liste)
            {
                System.out.println(l);
            }
            recherche.close();
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
        
    }

    public void AjouterAuPanier(ActionBD bd)
    {
        try{
        boolean commande_faite = false;
        Scanner scan = new Scanner(System.in);
        while (!commande_faite)
        {
            System.out.println(AfficherMenu.Menu("Ajout de livre au panier", Arrays.asList("Par identifiant", "Retour")));
            String commande_brute = scan.nextLine();
            String commande = commande_brute.strip().toLowerCase();
            switch (commande) {
                case "1":
                    Long isbn = null;
                    Integer qteL = null;
                    boolean bonIsbn = false;
                    while(!bonIsbn)
                    {
                        Scanner scanIsbn = new Scanner(System.in);
                        System.out.println("Entrez l'isbn du livre a ajouter au panier");
                        try{
                        isbn = Long.parseLong(scanIsbn.nextLine());
                        bonIsbn = true;
                    }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Veuillez entrer un nombre");
                        }
                        scanIsbn.close();
                    }
                    boolean bonneQte = false;
                    while (!bonneQte)
                    {
                        Scanner qte = new Scanner(System.in);
                        System.out.println("Selectionnez la quantite a ajouter : ");
                        try{
                            qteL = Integer.parseInt(qte.nextLine());
                            bonneQte = true;
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Veuillez entrer une quantitee correcte");
                        }
                        if (isbn != null && qteL != null)
                        {
                            Livre l = bd.getLivreParId(isbn);
                        this.ajouterArticle(l, qteL);
                        }
                        qte.close();
                    }
                    
                    commande_faite = true;
                    break;
                case "2":
                    commande_faite = true;
            }
        }
        scan.close();
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public void menu_rechercher(ActionBD bd){
        List<String> maListe = new ArrayList<>();
        maListe.add("Chercher un livre");
        maListe.add("Panier");
        maListe.add("Ajouter un livre");
        maListe.add("Retour");

        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Application",maListe));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = System.console().readLine();
            String commande = commande_brute.strip().toLowerCase();

            switch (commande) {
                case "1":
                    rechercher(bd);
                    commande_faite = true;
                    break;
                case "2":
                    this.Panier(bd);
                    commande_faite = true;
                    break;
                case "3":
                    this.AjouterAuPanier(bd);
                    commande_faite = true;
                    break;
                case "4":
                    commande_faite = true;
                    break;
            }
            if (commande.equals("1")){
                rechercher(bd);
                commande_faite = true;

            }

            if (commande.equals("3")){
                this.Panier(bd);
                commande_faite=true;
            }

        }  
    }

    public void SupprimerLivre(ActionBD bd)
    {
        HashSet<Long> ISBNPres = new HashSet<>();
        for (Livre l : this.getPanier().keySet())
        {
            System.out.println(l.getISBN() + " | " +l.getTitre() + " | Qte : " + this.getPanier().get(l));
            ISBNPres.add(l.getISBN());
        }
        Scanner recupISBN = new Scanner(System.in);
        System.out.println("Entrez l'identifiant du livre a supprimer");
        Long isbn = null;
        boolean bonIsbn = false;
        while (!bonIsbn)
        {
            try
            {
                isbn = Long.parseLong(recupISBN.nextLine());
                if (!ISBNPres.contains(isbn))
                {
                    System.out.println("Veuillez entrer un nombre dans le panier");
                }
                else
                {
                    bonIsbn = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Veuillez entrer un nombre dans le panier");
            }
        }
        recupISBN.close();
        Livre l = null;
        try{
        l = bd.getLivreParId(isbn);
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
        Integer qte = null;
        Scanner recupQte = new Scanner(System.in);
        
        boolean bonneQte = false;
        while (!bonneQte)
        {
            try {
                System.out.println("Entrez la quantitee a supprimer");
                qte = Integer.parseInt(recupQte.nextLine());
                if (qte > this.panier.get(l) || qte < 0)
                {
                    System.out.println("Veuillez entrer une quantitee correcte");
                }
                else
                {
                    bonneQte = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Veuillez mettre une quantitee correcte");
            }
        }
        recupQte.close();
        
    }

    public void Panier(ActionBD bd){
        List<String> maListe = new ArrayList<>();
        maListe.add("Afficher articles");
        maListe.add("Afficher prix total");
        maListe.add("Supprimer un Livre");
        maListe.add("Retour");

        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Application",maListe));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = System.console().readLine();
            String commande = commande_brute.strip().toLowerCase();

            switch (commande) {
                case "1":
                    for (Livre l : this.getPanier().keySet())
                    {
                        System.out.println(l.getTitre() + " | Qte : " + this.getPanier().get(l));
                    }
                    commande_faite=true;
                    break;
                case "2":
                    System.out.println("Prix total : " + this.prixPanier());
                    commande_faite=true;
                    break;
                case "3":
                    this.SupprimerLivre(bd);
                    commande_faite = true;
                    break;
                case "4":
                    commande_faite = true;
                    break;
            }
        }  
    }

}


