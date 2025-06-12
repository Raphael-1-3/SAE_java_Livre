package main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Vendeur extends User{
    private String prenom;
    private Magasin mag;
    public Vendeur(int idve, String email, String nom, String mdp, String role, String prenom, Magasin mag)
    {
        super(idve, email,nom, mdp, role);
        this.prenom = prenom;
        this.mag = mag;
    }

    public String getPrenom() {return this.prenom;}
    public Magasin getMagasin() {return this.mag;}
    public int getIdMag() {return this.mag.getIdmag();}

    public void ajouterLivre(ActionBD bd){ 
        try {
            Long isbn = bd.getMaxISBN();
            Scanner scan = new Scanner(System.in);
            System.out.println("Titre du livre : ");
            String titre = scan.nextLine();
            
            boolean bonNbPages = false;
            Integer nbPages = null;
            while (!bonNbPages)
            {
                System.out.println("Nombre de pages : ");
                try {
                    nbPages = Integer.parseInt(scan.nextLine());
                    bonNbPages = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            boolean bonneDate = false;
            Integer datepubli = null;
            while (!bonneDate)
            {
                System.out.println("Date de publication : ");
                try {
                    datepubli = Integer.parseInt(scan.nextLine());
                    bonneDate = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            boolean bonPrix = false;
            Double prix = null;
            while (!bonPrix)
            {
                System.out.println("Prix : ");
                try {
                    prix = Double.parseDouble(scan.nextLine());
                    bonneDate = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            scan.close();
            Livre l = new Livre(isbn, titre, nbPages, datepubli, prix);
            bd.AddLivre(l);
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public void updateStock() {}

    public void disponibilites() {}

    public void passerCommande(ActionBD bd) {
        try {
            Scanner scan = new Scanner(System.in);
            boolean commande_faite = false;
            while (!commande_faite)
            {
                System.out.println("Nom du client : ");
                String nom = scan.nextLine();
                System.out.println("Prenom du client");
                String prenom = scan.nextLine();
                List<Client> listeC = bd.getClientNonPrenom(nom, prenom);
                
                    if (listeC.isEmpty())
                    {
                        System.out.println("Aucun client n'a tel nom/prenom.");
                    }
                    else
                    {
                        for (Client c : bd.getClientNonPrenom(nom, prenom))
                        {
                            System.out.println(c);
                        }
                    }
            System.out.println("Selectioner l'identifiant de l'utilisateur");
            Integer id = Integer.parseInt(scan.nextLine());
            Client c = bd.getClientParId(id);
            Commande commande = new Commande(bd.getMaxNumCom(), bd);
            commande.menu_rechercher(bd);
            bd.PasserCommande(c, commande, mag);
            scan.close();
        }
        }
            catch (SQLException e)
            {
                System.out.println("Erreure SQL");
            }
    }

    public void Transfer() {}

}
