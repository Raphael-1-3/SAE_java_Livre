package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    private int codePostal;
    private String villeCli;
    private String adresseCli;
    private String prenom;
    private List<Commande> commandes;
    private HashMap<Livre, Integer> panier;

    // Ajout des paramètres manquants pour User : email, motDePasse, role
    public Client(int id, String email, String nom, String prenom, String motDePasse, String role, int codePostal, String villeCli, String adresseCli) {
        super(id, email, nom, motDePasse, role);
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = new ArrayList<>();
        this.panier = new HashMap<>();
    }

    public Client(int id, String email, String nom, String prenom, String motDePasse, String role, int codePostal, String villeCli, String adresseCli, List<Commande> commandes) {
        super(id, email, nom, motDePasse, role);
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = commandes;
        this.panier = new HashMap<>();
    }

    public int getCodePostal() {
        return codePostal;
    }

    public String getVilleCli() {
        return villeCli;
    }

    public String getAdresseCli() {
        return adresseCli;
    }

    // Ajout du getter pour le prénom (hérité du parent)
    public String getPrenom() {
        return this.prenom;
    }

    // Ajoute un livre au panier (quantité +1 si déjà présent)
    public void ajoutePanier(Livre livre) {
        this.panier.put(livre, this.panier.getOrDefault(livre, 0) + 1);
    }

    // Ajoute un livre au panier avec quantité précise
    public void ajoutePanier(Livre livre, int quantite) {
        this.panier.put(livre, this.panier.getOrDefault(livre, 0) + quantite);
    }
    public void suppPanier(Livre livre)
    {
        this.panier.remove(livre);
    }

    public HashMap<Livre, Integer> getPanier() {
        return this.panier;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return   this.codePostal == client.codePostal &&
                this.villeCli.equals(client.villeCli) &&
                this.adresseCli.equals(client.adresseCli);
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + codePostal + villeCli.hashCode() + adresseCli.hashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", codePostal=" + codePostal +
                ", villeCli='" + villeCli + '\'' +
                ", adresseCli='" + adresseCli + '\'' +
                '}';
    }

    public static boolean creerCompteClient(ActionBD bd) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez votre prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Entrez votre code postal : ");
        int codePostal = Integer.parseInt(scanner.nextLine());

        System.out.print("Entrez votre ville : ");
        String villeCli = scanner.nextLine();

        System.out.print("Entrez votre adresse : ");
        String adresseCli = scanner.nextLine();

        System.out.print("Entrez votre email : ");
        String email = scanner.nextLine();

        System.out.print("Entrez votre mot de passe : ");
        String mdp = scanner.nextLine();

        boolean creationCompte = bd.creerClient(nom, prenom, codePostal, villeCli, adresseCli, email, mdp);
        if (!creationCompte) {
            System.out.println("Un probleme rencontré lors de la création de compte");
            return false;
        } else {
            System.out.println("Compte créé avec succès !");
            return true;
        }
    }

    // Les méthodes suivantes sont des exemples ou des stubs, à compléter selon les besoins
    public void CommanderCommande() {
        System.out.println("╭────────────────────────────╮");
        System.out.println("│       Menu                 │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ 1 : exemple                │");
        System.out.println("│ 2 : exemple                │");
        System.out.println("│ Q : quitter                │");
        System.out.println("╰────────────────────────────╯");
    }

    public static void application(ActionBD bd, User client, Scanner scanner) throws SQLException
    {
        Client clientC = (Client) client;
        List<String> menuListe = new ArrayList<>();
        menuListe.add("Rechercher un Livre");
        menuListe.add("Consulter Panier");
        menuListe.add("Paramètres");
        menuListe.add("Quitter");

        boolean commande_faite = false;
        while (!commande_faite) {
            System.out.println(AfficherMenu.Menu("Application", menuListe));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = scanner.nextLine();
            String commande = commande_brute.strip().toLowerCase();

            // On suppose que l'utilisateur entre un numéro de menu (1, 2, 3, ...)
            switch (commande) {
            case "1":
                System.out.println("emmene vers le catalogue");
                choixDisponibilite(bd, clientC, scanner);
                break;
            case "2":
                System.out.println("emmene vers consulter Panier");
                menuPanier(bd, clientC, scanner);
                break;
            case "3":
                System.out.println("Vous etres maintenant dans le menu panier");
                // TODO: afficher les paramètres
                break;
            case "4":
            case "q":
            case "quitter":
                System.out.println("la tu quitte lappli client");
                commande_faite = true;
                break;
            default:
                System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }
    public static void choixDisponibilite(ActionBD bd, Client client, Scanner scanner) throws SQLException {
        // Premier menu : choix du mode de recherche
        List<String> menuModeRecherche = new ArrayList<>();
        menuModeRecherche.add("Parmi les livres disponibles en magasin");
        menuModeRecherche.add("Parmi tout les libres enregistré");
        menuModeRecherche.add("Quitter");

        boolean quitterRecherche = false;
        while (!quitterRecherche) {
            System.out.println(AfficherMenu.Menu("Recherche de livre", menuModeRecherche));
            System.out.print("Que veux-tu faire ? : ");
            String choixMode = scanner.nextLine().strip().toLowerCase();
            boolean rechercheDispoMag = false;
            switch (choixMode) {
                case "1":
                    rechercheDispoMag = true;
                    rechercheLivre(bd, client, scanner, rechercheDispoMag);
                    break;
                case "2":
                    rechercheDispoMag = false;
                    rechercheLivre(bd, client, scanner, rechercheDispoMag);
                    break;
                case "3":
                case "q":
                case "quitter":
                    System.out.println("Vous quittez la recherche.");
                    quitterRecherche = true;
                    break;
                default:
                    System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }


    public static void rechercheLivre(ActionBD bd, Client client, Scanner scanner, boolean rechercheDispoMag) throws SQLException
    {
        List<String> menuSousRecherche = new ArrayList<>();
        menuSousRecherche.add("Par auteur");
        menuSousRecherche.add("Par nom de livre");
        menuSousRecherche.add("Voir ce qu'on vous recommande");
        menuSousRecherche.add("Retour");
        boolean sousMenuQuitter = false;
        while (!sousMenuQuitter) {
            System.out.println(AfficherMenu.Menu("Recherche par auteur ou nom", menuSousRecherche));
            System.out.print("Votre choix : ");
            String sousChoix = scanner.nextLine().strip().toLowerCase();
            switch (sousChoix) {
                case "1":
                    System.out.print("Entrez le nom de l'auteur : ");
                    String auteurRecherche = scanner.nextLine().strip();
                    List<Livre> livresAuteur = bd.rechercheLivreAuteurApproximative(auteurRecherche);
                    if (rechercheDispoMag)
                    {
                        List<Livre> livresDispo = new ArrayList<>();
                        for (Livre livre : livresAuteur) {
                            List<Magasin> magasins = bd.getMagasinOuLivreDispo(livre);
                            if (magasins != null && !magasins.isEmpty()) {
                                livresDispo.add(livre);
                            }
                        }
                        livresAuteur = livresDispo;
                    }
                    afficherEtAjouterLivreAuPanier(scanner, client, livresAuteur);
                    break;
                case "2":
                    System.out.print("Entrez le titre du livre : ");
                    String titreRecherche = scanner.nextLine().strip();
                    List<Livre> livresTitre = bd.cherhcherLivreApproximative(titreRecherche);
                    if (rechercheDispoMag)
                    {
                        List<Livre> livresDispo = new ArrayList<>();
                        for (Livre livre : livresTitre) {
                            List<Magasin> magasins = bd.getMagasinOuLivreDispo(livre);
                            if (magasins != null && !magasins.isEmpty()) {
                                livresDispo.add(livre);
                            }
                        }
                        livresTitre = livresDispo;
                    }
                    afficherEtAjouterLivreAuPanier(scanner, client, livresTitre);
                    break;
                case "3":
                    // Voir ce qu'on vous recommande
                    try {
                        List<Livre> recommandations = bd.onVousRecommande(client);
                        if (rechercheDispoMag)
                        {
                            List<Livre> livresDispo = new ArrayList<>();
                            for (Livre livre : recommandations) {
                                List<Magasin> magasins = bd.getMagasinOuLivreDispo(livre);
                                if (magasins != null && !magasins.isEmpty()) {
                                    livresDispo.add(livre);
                                }
                            }
                            recommandations = livresDispo;
                        }
                        afficherEtAjouterLivreAuPanier(scanner, client, recommandations);

                    } catch (PasDHistoriqueException e) {
                        System.out.println("Erreur lors de la récupération des recommandations : vous n'avez pas d'historique d'achat");
                    }
                    break;
                case "4":
                case "q":
                case "retour":
                    sousMenuQuitter = true;
                    break;
                default:
                    System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }

    private static void afficherEtAjouterLivreAuPanier(Scanner scanner, Client client, List<Livre> livres) {
        if (livres == null || livres.isEmpty()) {
            System.out.println("Aucun livre trouvé.");
            return;
        }
        List<String> tabLivreNom = new ArrayList<>();
        for (Livre livre : livres) {
            tabLivreNom.add(livre.getTitre());
        }
        boolean choixFait = false;
        while (!choixFait) {
            System.out.println(AfficherMenu.Menu("Livres trouvés", tabLivreNom));
            System.out.print("Entrez le numéro du livre choisi : ");
            String choix = scanner.nextLine().strip();
            try {
                int indice = Integer.parseInt(choix) - 1;
                if (indice < 0 || indice >= livres.size()) {
                    System.out.println("Numéro invalide.");
                    continue;
                }
                Livre livreChoisi = livres.get(indice);
                System.out.print("Voulez-vous ajouter ce livre au panier ? (oui/non) : ");
                String reponse = scanner.nextLine().strip().toLowerCase();
                if (reponse.equals("oui") || reponse.equals("o")) {
                    System.out.println("Le livre a été ajouté au panier.");
                    client.ajoutePanier(livreChoisi);
                    choixFait = true;
                } else if (reponse.equals("non") || reponse.equals("n")) {
                    choixFait = true;
                } else {
                    System.out.println("Réponse non reconnue, veuillez répondre par oui ou non.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Votre entrée est incorrecte.");
            }
        }
    }

    public static void menuPanier(ActionBD bd, Client client, Scanner scanner) throws SQLException
    { 
        List<String> menuListe = new ArrayList<>();
        menuListe.add("Afficher le panier");
        menuListe.add("Commander le panier");
        menuListe.add("Supprimer un livre du panier");
        menuListe.add("Retour");

        boolean quitter = false;
        while (!quitter) {
            System.out.println(AfficherMenu.Menu("Menu Panier", menuListe));
            System.out.print("Que veux-tu faire ? : ");
            String choix = scanner.nextLine().strip().toLowerCase();

            switch (choix) {
                case "1":
                    HashMap<Livre, Integer> panier = client.getPanier();
                    if (panier.isEmpty()) {
                        System.out.println("Votre panier est vide.");
                    } else {
                        System.out.println("Contenu du panier :");
                        int i = 1;
                        for (Livre livre : panier.keySet()) {
                            System.out.println(i + ". " + livre + " (quantité : " + panier.get(livre) + ")");
                            i++;
                        }
                    }
                    break;
                case "2":
                    menuCommande(bd, client, scanner);
                    break;
                case "3":
                    panier = client.getPanier();
                    if (panier.isEmpty()) {
                        System.out.println("Votre panier est vide.");
                        break;
                    }
                    List<Livre> livresDansPanier = new ArrayList<>(panier.keySet());
                    System.out.println("Livres dans votre panier :");
                    for (int i = 0; i < livresDansPanier.size(); i++) {
                        Livre livre = livresDansPanier.get(i);
                        System.out.println((i + 1) + ". " + livre + " (quantité : " + panier.get(livre) + ")");
                    }
                    System.out.print("Entrez le numéro du livre à supprimer : ");
                    String numLivreStr = scanner.nextLine().strip();
                    try {
                        int numLivre = Integer.parseInt(numLivreStr) - 1;
                        if (numLivre < 0 || numLivre >= livresDansPanier.size()) {
                            System.out.println("Numéro invalide.");
                            break;
                        }
                        Livre livreASupprimer = livresDansPanier.get(numLivre);
                        client.suppPanier(livreASupprimer);
                        System.out.println("Livre supprimé du panier.");
                    } catch (NumberFormatException e) {
                        System.out.println("Entrée invalide.");
                    }
                    break;
                case "4" : 
                case "q":
                case "retour":
                    quitter = true;
                    break;
                default:
                    System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }

    public static void menuCommande(ActionBD bd, Client client, Scanner scanner) throws SQLException 
    {
        HashMap<Livre, Integer> panier = client.getPanier();
        if (panier.isEmpty()) {
            System.out.println("Votre panier est vide, impossible de commander.");
        }
        List<Livre> livresDansPanier = new ArrayList<>(panier.keySet());
        List<Magasin> magasinsDispo = client.getMagasinsAvecTousCesLivres(livresDansPanier, bd);

        if (magasinsDispo.isEmpty()) {
            System.out.println("Aucun magasin ne possède tous les livres de votre panier.");
        }

        List<String> menuMagasins = new ArrayList<>();
        for (Magasin mag : magasinsDispo) {
            menuMagasins.add(mag.getNomMag());
        }
        menuMagasins.add("Retour");

        boolean quitter = false;
        while (!quitter) {
            System.out.println(AfficherMenu.Menu("Magasins disponibles pour votre commande", menuMagasins));
            System.out.print("Dans quel magasin souhaitez-vous commander ? (numéro ou 'retour') : ");
            String choix = scanner.nextLine().strip().toLowerCase();

            if (choix.equals("retour") || choix.equals(String.valueOf(menuMagasins.size()))) {
                quitter = true;
            } else {
                try {
                    int numMag = Integer.parseInt(choix) - 1;
                    if (numMag < 0 || numMag >= magasinsDispo.size()) {
                        System.out.println("Numéro invalide.");
                        continue;
                    }
                    Magasin magasinChoisi = magasinsDispo.get(numMag);
                    Commande comClient = new Commande(numMag, bd);
                    comClient.changerModeReception(bd);
                    double total = 0.0;
                    for (Livre livre : panier.keySet()) {
                        total += livre.getPrix() * panier.get(livre);
                    }
                    System.out.printf("Le montant total de votre commande est : %.2f€%n", total);
                    System.out.print("Voulez-vous payer cette somme ? (oui/non) : ");
                    String reponsePaiement = scanner.nextLine().strip().toLowerCase();
                    if (!(reponsePaiement.equals("oui") || reponsePaiement.equals("o"))) {
                        System.out.println("Commande annulée.");
                    }
                    else{

                        bd.PasserCommande(client, comClient, magasinChoisi);
                        System.out.println("Commande passée dans le magasin : " + magasinChoisi);
                    }
                    
                    client.getPanier().clear();
                    quitter = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide.");
                }
            }
        }

    }

    /**
    * Renvoie la liste des magasins où TOUS les livres de la liste sont disponibles
    * @param livres Liste de livres recherchés
    * @return Liste de magasins où tous les livres sont dispo
    * @throws SQLException
    */
    public List<Magasin> getMagasinsAvecTousCesLivres(List<Livre> livres, ActionBD bd) throws SQLException {
        if (livres == null || livres.isEmpty()) return new ArrayList<>();

        // Initialiser avec les magasins du premier livre
        List<Magasin> magasinsCommuns = new ArrayList<>(bd.getMagasinOuLivreDispo(livres.get(0)));

        for (int i = 1; i < livres.size(); i++) {
            List<Magasin> magasinsPourLivre = bd.getMagasinOuLivreDispo(livres.get(i));
            magasinsCommuns.retainAll(magasinsPourLivre); // intersection
            if (magasinsCommuns.isEmpty()) break; // optimisation
        }
        return magasinsCommuns;
    }
}