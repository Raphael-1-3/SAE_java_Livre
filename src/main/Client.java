package main;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {
    private char modeReception;
    private int codePostal;
    private String villeCli;
    private String adresseCli;
    private List<Commande> commandes;
    private ActionBD bd;

    public Client(int id, String prenom, String nom, Magasin magasin, int codePostal, String villeCli, String adresseCli)
    {
        super(id, prenom, nom, magasin);
        this.modeReception = 'M';
        this.codePostal = codePostal;
        this.villeCli = villeCli;
        this.adresseCli = adresseCli;
        this.commandes = new ArrayList<>();

    }

    public Client(int id, String prenom, String nom, Magasin magasin, int codePostal, String villeCli, String adresseCli, List<Commande> commandes)
    {
        super(id, prenom, nom, magasin);
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
}
