package main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
            Long isbn = bd.getMaxISBN() + 1;
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

    public void updateStock(ActionBD bd) {
        try{
            Scanner recherche = new Scanner(System.in);
            System.out.println("Quel est le nom du livre ? :" );
            String nomLivre = recherche.nextLine();
            HashSet<Long> setIsbn = new HashSet<>();
            List<Livre> liste = bd.cherhcherLivreApproximative(nomLivre);
            for (Livre l : liste)
            {
                System.out.println(l);
                setIsbn.add(l.getISBN());
            }
            
            boolean bonIsbn = false;
            Long isbn = null;
            while (!bonIsbn)
            {
                try {
                    System.out.println("Entrez l'isbn du livre a modifier :");
                    isbn = Long.parseLong(recherche.nextLine());
                    if (setIsbn.contains(isbn))
                    {
                        bonIsbn = true;
                    }
                    else
                    {
                        System.out.println("Veuillez entrer un isbn valide ");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un isbn valide ");
                }
            }
            boolean bonQte = false;
            Integer qte = null;
            while (!bonQte)
            {
                try{
                    System.out.println("Entrez la nouvelle quantite du livre :");
                    qte = Integer.parseInt(recherche.nextLine());
                    if (qte < 0)
                    {
                        System.out.println("Veuillez entrer une quantite positive");
                    }
                    else
                    {
                        bonQte = true;
                    }
                }
                catch (NumberFormatException e) 
                {
                    System.out.println("Veuillez entrer un numbre valide ");
                }
            }  
            recherche.close();
            Livre l = bd.getLivreParId(isbn);
            bd.UpdateStock(l, mag, qte);

        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public void disponibilites(ActionBD bd) {
        try {
            HashMap<Livre, Integer> stock = bd.VoirStockMag(this.mag);
            for (Livre l : stock.keySet())
            {
                System.out.println(l.getTitre() + " | qte : " + stock.get(l));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

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

    public void Transfer(ActionBD bd) {
        try
        {
            Scanner recherche = new Scanner(System.in);
            System.out.println("Quel est le nom du livre ? :" );
            String nomLivre = recherche.nextLine();
            HashSet<Long> setIsbn = new HashSet<>();
            List<Livre> liste = bd.cherhcherLivreApproximative(nomLivre);
            for (Livre l : liste)
            {
                System.out.println(l);
                setIsbn.add(l.getISBN());
            }
            boolean bonIsbn = false;
            Long isbn = null;
            while (!bonIsbn)
            {
                try {
                    System.out.println("Entrez l'isbn du livre a modifier :");
                    isbn = Long.parseLong(recherche.nextLine());
                    if (setIsbn.contains(isbn))
                    {
                        bonIsbn = true;
                    }
                    else
                    {
                        System.out.println("Veuillez entrer un isbn valide ");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un isbn valide ");
                }
            }
            Livre l = bd.getLivreParId(isbn);
            List<Magasin> mags = bd.getMagasinOuLivreDispo(l);
            HashSet<Integer> setIdMag = new HashSet<>();
            for (Magasin m : mags)
            {
                System.out.println(m);
                setIdMag.add(m.getIdmag());
            }

            boolean bonIdmag = false;
            Integer idmag = null;
            while (!bonIdmag)
            {
                try{
                    System.out.println("Identifiant du magasin de depart :");
                    idmag = Integer.parseInt(recherche.nextLine());
                    if (!setIdMag.contains(idmag))
                    {
                        System.out.println("Le magasin n'est pas dans la liste");
                    }
                    else
                    {
                        bonIdmag = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            boolean bonQte = false;
            Integer qte = null;
            while (!bonQte)
            {
                try{
                    System.out.println("Quantitee a transferer :");
                    qte = Integer.parseInt(recherche.nextLine());
                    if (qte < 0)
                    {
                        System.out.println("Veuillez entrer une quantite positive");
                    }
                    else
                    {
                        bonQte = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            Magasin m = bd.getMagasinParId(idmag);
            bd.Transfer(isbn, m, this.mag, qte);

        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
        catch (PasAssezLivreException e)
        {
            System.out.println("Le magasin receveur n'a pas assez d'exemplaires du livre");
        }
    }

}
