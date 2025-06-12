package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    private char modeReception;
    private int codePostal;
    private String villeCli;
    private String adresseCli;
    private String prenom;
    private List<Commande> commandes;
    private List<Livre> panier;

    // Ajout des paramètres manquants pour User : email, motDePasse, role
    public Client(int id, String email, String nom, String prenom, String motDePasse, String role, int codePostal, String villeCli, String adresseCli) {
        super(id, email, nom, motDePasse, role);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = new ArrayList<>();
        this.panier = new ArrayList<>();
    }

    public Client(int id, String email, String nom, String prenom, String motDePasse, String role, int codePostal, String villeCli, String adresseCli, List<Commande> commandes) {
        super(id, email, nom, motDePasse, role);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = commandes;
        this.panier = new ArrayList<>();
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

    public void ajoutePanier(Livre livre)
    {
        this.panier.add(livre);
    }

    public List<Livre> getPanier()
    {
        return this.panier;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return this.modeReception == client.modeReception &&
                this.codePostal == client.codePostal &&
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

    public static void application(ActionBD bd, User client) throws SQLException
    {
        Client clientC = (Client) client;
        List<String> menuListe = new ArrayList<>();
        menuListe.add("Commander un panier");
        menuListe.add("rechercher un Livre");
        menuListe.add("consulter Panier");
        menuListe.add("Paramètres");
        menuListe.add("Quitter");

        boolean commande_faite = false;
        Scanner scanner_test = new Scanner(System.in);
        while (!commande_faite) {
            System.out.println(AfficherMenu.Menu("Application", menuListe));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = scanner_test.nextLine();
            String commande = commande_brute.strip().toLowerCase();

            // On suppose que l'utilisateur entre un numéro de menu (1, 2, 3, ...)
            switch (commande) {
            case "1":
                System.out.println("emmene vers le menu des commade");
                // TODO: appeler la méthode pour commander un panier
                break;
            case "2":
                System.out.println("emmene vers le catalogue");
                rechercheLivre(bd, clientC);
                break;
            case "3":
                System.out.println("emmene vers consulter Panier");
                // TODO: afficher le panier du client
                menuPanier(bd, clientC);
                break;
            case "4":
                System.out.println("emmene vers parametre pour changer mot de passe");
                // TODO: afficher les paramètres
                break;
            case "5":
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

    public static void rechercheLivre(ActionBD bd, Client client) throws SQLException {
        List<String> menuListe = new ArrayList<>();
        menuListe.add("Auteur");
        menuListe.add("Nom de livre");
        menuListe.add("Quitter");

        boolean commande_faite = false;
        Scanner scanner = new Scanner(System.in);
        while (!commande_faite) {
            System.out.println(AfficherMenu.Menu("Recherche de livre", menuListe));
            System.out.print("Que veux-tu faire ? : ");
            String commande_brute = scanner.nextLine();
            String commande = commande_brute.strip().toLowerCase();

            switch (commande) {
                case "1":
                    System.out.println("Recherche par auteur non implémentée.");
                    break;
                case "2":
                    System.out.print("Entrez le titre du livre : ");
                    String titreRecherche = scanner.nextLine().strip();
                    List<Livre> tabLiv = bd.cherhcherLivreApproximative(titreRecherche);
                    if (tabLiv == null || tabLiv.isEmpty()) {
                        System.out.println("Aucun livre trouvé.");
                        break;
                    }
                    List<String> tabLivreNom = new ArrayList<>();
                    for (Livre livre : tabLiv) {
                        tabLivreNom.add(livre.getTitre());
                    }
                    boolean choixFait = false;
                    while (!choixFait) {
                        System.out.println(AfficherMenu.Menu("Livres trouvés", tabLivreNom));
                        System.out.print("Entrez le numéro du livre choisi : ");
                        String choix = scanner.nextLine().strip();
                        try {
                            int indice = Integer.parseInt(choix) - 1 ;
                            if (indice < 0 || indice >= tabLiv.size()) {
                                System.out.println("Numéro invalide.");
                                continue;
                            }
                            Livre livreChoisi = tabLiv.get(indice);
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
                    break;
                case "3":
                case "q":
                case "quitter":
                    System.out.println("Vous quittez la recherche.");
                    commande_faite = true;
                    break;
                default:
                    System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }

    public static void menuPanier(ActionBD bd, Client client) 
    { 
        List<String> menuListe = new ArrayList<>();
        menuListe.add("Afficher le panier");
        menuListe.add("Commander le panier");
        menuListe.add("Retour");

        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;
        while (!quitter) {
            System.out.println(AfficherMenu.Menu("Menu Panier", menuListe));
            System.out.print("Que veux-tu faire ? : ");
            String choix = scanner.nextLine().strip().toLowerCase();

            switch (choix) {
                case "1":
                    List<Livre> panier = client.getPanier();
                    if (panier.isEmpty()) {
                        System.out.println("Votre panier est vide.");
                    } else {
                        System.out.println("Contenu du panier :");
                        int i = 1;
                        for (Livre livre : panier) {
                            System.out.println(i + ". " + livre);
                            i++;
                        }
                    }
                    break;
                case "2":
                    if (client.getPanier().isEmpty()) {
                        System.out.println("Votre panier est vide, impossible de commander.");
                    } else {
                        // Ici, il faudrait appeler la méthode de commande réelle
                        System.out.println("Commande du panier en cours...");
                        // Exemple : bd.commanderPanier(client);
                        System.out.println("Votre commande a été prise en compte !");
                        client.getPanier().clear();
                    }
                    break;
                case "3":
                case "q":
                case "retour":
                    quitter = true;
                    break;
                default:
                    System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }
    
    public static void choisirMagasin(ActionBD bd){
        List<String> menuListe = new ArrayList<>();
        List<Magasin> tabMag = null; //bd.listeMagasin()
        //parcour + ajout menu
        menuListe.add("");
        menuListe.add("Retour");
        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Choix Magasins",menuListe));
            System.out.println("Dans quel magasin ? : ");
            String commande_brute=System.console().readLine();
            String commande = commande_brute.strip().toLowerCase();
            if (commande.equals("5")){
                commande_faite=true;

            }
        }
    }

    public void avoirRecommandations() {}
}
