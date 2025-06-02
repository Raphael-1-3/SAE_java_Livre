package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends User {
    private char modeReception;
    private int codePostal;
    private String villeCli;
    private String adresseCli;
    private List<Commande> commandes;
    private ActionBD bd;

    public Client(int id, String nom, String prenom, int codePostal, String villeCli, String adresseCli)
    {
        super(id, prenom, nom);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = new ArrayList<>();

    }

    public Client(int id, String nom, String prenom, int codePostal, String villeCli, String adresseCli, List<Commande> commandes)
    {
        super(id, prenom, nom);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = commandes;
    }

    public void CommanderCommande(){
        System.out.println("╭────────────────────────────╮");
        System.out.println("│       Menu                 │");
        System.out.println("├────────────────────────────┤");
        System.out.println("│ 1 : exemple                │");
        System.out.println("│ 2 : exemple                │");
        System.out.println("│ Q : quitter                │");
        System.out.println("╰────────────────────────────╯");
    }


    public void choisirMagasin() {}

    public void consulterCatalogue() {}

    public void avoirRecommandations() {}
    
    public int getCodePostal() {
        return codePostal;
    }

    public String getVilleCli() {
        return villeCli;
    }

    public String getAdresseCli() {
        return adresseCli;
    }


    public static void application(){
        List<String> maListe = new ArrayList<>();
        maListe.add("Creer un compte");
        maListe.add("Se connecter");
        maListe.add("Commander");
        maListe.add("Paramètres");
        maListe.add("Quitter");

        boolean commande_faite = false;
        while(!commande_faite){
            System.out.println(AfficherMenu.Menu("Application",maListe));
            System.out.println("Que veut tu faire ? : ");
            Scanner scanner_test = new Scanner(System.in);
            String commande_brute = scanner_test.nextLine();

            String commande = commande_brute.strip().toLowerCase();

            if (commande.equals("5")){
                commande_faite=true;

            }

            if (commande.equals("1")){
                commande_faite=true;
            }

            if (commande.equals("2")){
                commande_faite=true;}

            if (commande.equals("3")){
                Commande.menu_rechercher();}
            scanner_test.close();
            }

        }
            

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return this.modeReception == client.modeReception &&
                this.codePostal == client.getCodePostal() &&
                this.villeCli.equals(client.getVilleCli()) &&
                this.adresseCli.equals(client.getAdresseCli());
    }

    

    @Override
    public int hashCode() {
        return this.id * 3131  + this.nom.hashCode() + this.prenom.hashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + this.id +
                ", nom='" + this.nom + '\'' +
                ", prenom='" + this.prenom + '\'' +
                ", codePostal=" + this.codePostal +
                ", villeCli='" + this.villeCli + '\'' +
                ", adresseCli='" + this.adresseCli + '\'' +
                '}';
    }
}
