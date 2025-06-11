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

    // Ajout des paramètres manquants pour User : email, motDePasse, role
    public Client(int id, String email, String nom, String prenom, String motDePasse, String role, int codePostal, String villeCli, String adresseCli) {
        super(id, email, nom, motDePasse, role);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = new ArrayList<>();
    }

    public Client(int id, String email, String nom, String prenom, String motDePasse, String role, int codePostal, String villeCli, String adresseCli, List<Commande> commandes) {
        super(id, email, nom, motDePasse, role);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = commandes;
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

    public static void application(ActionBD bd, User client) {
        List<String> maListe = new ArrayList<>();
        maListe.add("Commander");
        maListe.add("Catalogue");
        maListe.add("Choisir un magasin");
        maListe.add("Paramètres");
        maListe.add("Quitter");

        boolean commande_faite = false;
        Scanner scanner_test = new Scanner(System.in);
        while (!commande_faite) {
            System.out.println(AfficherMenu.Menu("Application", maListe));
            System.out.println("Que veut tu faire ? : ");
            String commande_brute = scanner_test.nextLine();
            String commande = commande_brute.strip().toLowerCase();

            if (commande.equals("4")) {
                commande_faite = true;
            }
            if (commande.equals("1")) {
                commande_faite = true;
            }
            if (commande.equals("2")) {
                rechercheLivre(bd);
            }
            if (commande.equals("3")) {
                Commande.menu_rechercher();
            }
        }
    }

    public static void choisirMagasin(ActionBD bd){
        List<String> maListe = new ArrayList<>();
        List<Magasin> tabMag = null; //bd.listeMagasin()
        //parcour + ajout menu
        maListe.add("");
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

    public static  void rechercheLivre(ActionBD bd) 
    {
        //TODO   fair en sorte que cette fonction appel cherhcherLivreApproximative(String nomApproximativeLivre)
    }

    public void avoirRecommandations() {}
}
