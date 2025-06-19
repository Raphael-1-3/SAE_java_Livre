package main.app;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import main.Affichage.AfficherMenu;
import main.BD.ActionBD;
import main.Exceptions.PasAssezLivreException;

public class Vendeur extends User{
    private String prenom;
    private Magasin mag;
    public Vendeur(Integer idve, String email, String nom, String mdp, String role, String prenom, Magasin mag)
    {
        super(idve, email,nom, mdp, role);
        this.prenom = prenom;
        this.mag = mag;
    }

    public void setMag(Magasin m)
    {
        this.mag = m;
    }

    public String getPrenom() {return this.prenom;}
    public Magasin getMagasin() {return this.mag;}
    public int getIdMag() {return this.mag.getIdmag();}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vendeur other = (Vendeur) obj;
        return super.equals(obj)
            && prenom.equals(other.prenom)
            && mag.equals(other.mag);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + prenom.hashCode();
        result = 31 * result + mag.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vendeur{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + prenom + '\'' +
                ", magasin=" + mag +
                '}';
    }

    public static void ajouterLivre(ActionBD bd, Scanner scan){ 
        try {
            Long isbn = bd.getMaxISBN() + 1;
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
                    bonPrix = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }
            Livre l = new Livre(isbn, titre, nbPages, datepubli, prix);
            //bd.AddLivre(l);
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public static void updateStock(ActionBD bd, Vendeur v, Scanner scan) {
        try{
            System.out.println("Quel est le nom du livre ? :" );
            String nomLivre = scan.nextLine();
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
                    isbn = Long.parseLong(scan.nextLine());
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
                    qte = Integer.parseInt(scan.nextLine());
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
            Livre l = bd.getLivreParId(isbn);
            bd.UpdateStock(l, v.getMagasin(), qte);

        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public static void disponibilites(ActionBD bd, Vendeur v) {
        try {
            HashMap<Livre, Integer> stock = bd.VoirStockMag(v.getMagasin());
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

    public static void passerCommande(ActionBD bd, Scanner scan) {
        try {
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
            System.out.println("Passage en mode client pour effectuer la commande");
            Client.application(bd, c, new Scanner(System.in));
            commande_faite = true;
        }
        }
            catch (SQLException e)
            {
                System.out.println("Erreure SQL");
            }
    }

    public static void Transfer(ActionBD bd, Vendeur v, Scanner scan) {
        try
        {
            System.out.println("Quel est le nom du livre ? :" );
            String nomLivre = scan.nextLine();
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
                    isbn = Long.parseLong(scan.nextLine());
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
                    idmag = Integer.parseInt(scan.nextLine());
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
                    qte = Integer.parseInt(scan.nextLine());
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
            Magasin m = bd.magAPartirId(idmag);
            bd.Transfer(isbn, m, v.getMagasin(), qte);

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

    public static void FacturesMag(ActionBD bd, Vendeur v, Scanner scan)
    {
        try {
            Integer mois = null;
            Integer annee = null;
            boolean bonMois = false;
            while (!bonMois)
            {
                try
                {
                    System.out.println("Veuillez entrer un le mois des factures");
                    mois = Integer.parseInt(scan.nextLine());
                    if (mois <=0 || mois > 12)
                    {
                        System.out.println("Veuillez entrer un mois entre 1 et 12");
                    }
                    else
                    {
                        bonMois = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre etnre 1 et 12");
                }
            }

            boolean bonneAn = false;
            while (!bonneAn)
            {
                try
                {
                    System.out.println("Veuillez entrer l'annee des factures");
                    annee = Integer.parseInt(scan.nextLine());
                    if (annee < 476)
                    {
                        System.out.println("Veuillez entrer une annee apres l'empire romain");
                    }
                    else
                    {
                        bonneAn = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }

            System.out.println(bd.factureMagasin(v.getMagasin(), mois, annee));
    
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }
    
    public static void FacturesCli(ActionBD bd, Vendeur v, Scanner scan)
    {
        try {
            Integer mois = null;
            Integer annee = null;
            boolean bonMois = false;
            while (!bonMois)
            {
                try
                {
                    System.out.println("Veuillez entrer un le mois des factures");
                    mois = Integer.parseInt(scan.nextLine());
                    if (mois <=0 || mois > 12)
                    {
                        System.out.println("Veuillez entrer un mois entre 1 et 12");
                    }
                    else
                    {
                        bonMois = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre etnre 1 et 12");
                }
            }

            boolean bonneAn = false;
            while (!bonneAn)
            {
                try
                {
                    System.out.println("Veuillez entrer l'annee des factures");
                    annee = Integer.parseInt(scan.nextLine());
                    if (annee < 476)
                    {
                        System.out.println("Veuillez entrer une annee apres l'empire romain");
                    }
                    else
                    {
                        bonneAn = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Veuillez entrer un nombre");
                }
            }

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

            System.out.println(bd.factureClient(c, mois, annee));
    
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public static void Factures(ActionBD bd, Vendeur v, Scanner scan)
    {
        List<String> options = Arrays.asList("Obtenir les factures d'un magasin",
                                            "Obtenir les factures d'un client",
                                            "Quitter");
        boolean commande_faite = false;
        while (!commande_faite)
        {
            System.out.println(AfficherMenu.Menu("Vendeur", options));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = scan.nextLine();
            String commande = commande_brute.strip().toLowerCase();
            switch (commande) {
                case "1":
                    FacturesMag(bd, v, scan);
                    commande_faite = true;
                    break;
                case "2":
                    FacturesCli(bd, v, scan);
                    commande_faite = true;
                    break;
                case "3":
                    commande_faite = true;
                    break;
                default:
                    System.out.println("Veuillez entrer un nombre parmis les choixs precedents");
                    break;
            }
        }

    }

    public static void application(ActionBD bd, User vendeur, Scanner scanner) throws SQLException
    {
        Vendeur v = (Vendeur) vendeur;
        List<String> options = Arrays.asList("Ajouter un livre",
                                                "Modifier le stock",
                                                "Regarder les disponibilites",
                                                "Passer une commande pour un Client",
                                                "Tranferer un livre",
                                                "Obtenir les factures",
                                                "Quitter");
        boolean commande_faite = false;
        while (!commande_faite)
        {
            System.out.println(AfficherMenu.Menu("Vendeur", options));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = scanner.nextLine();
            String commande = commande_brute.strip().toLowerCase();
            switch (commande) {
                case "1":
                    ajouterLivre(bd, scanner);
                    break;
                case "2":
                    updateStock(bd, v, scanner);
                    break;
                case "3":
                    disponibilites(bd, v);
                    break;
                case "4":
                    passerCommande(bd, scanner);
                    break;
                case "5":
                    Transfer(bd, v, scanner);
                    break;
                case "6":
                    Factures(bd, v, scanner);
                    break;
                case "7":
                    System.out.println("Vous avez choisi de quitter.");
                    commande_faite = true;
                    break;
                default:
                    System.out.println("Veuillez entrer un nombre entre 1 et " + options.size());
                    break;
            }
        }
    }
}
