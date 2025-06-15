package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Affichage.AfficherMenu;
import BD.ActionBD;

public class Administrateur extends Vendeur {
    public Administrateur(int id, String email, String nom, String motDePasse, String role, String prenom, Magasin mag) {
        super(id, email, nom, motDePasse, role, prenom, mag);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    
public void application(ActionBD bd, Scanner scanner) throws SQLException {
    List<String> menuListe = new ArrayList<>();
    menuListe.add("Creer Vendeur");
    menuListe.add("Choisir Magasin");
    menuListe.add("Faire Facture");
    menuListe.add("Quitter");

    boolean commande_faite = false;
    while (!commande_faite) {
        System.out.println(AfficherMenu.Menu("Application Administrateur", menuListe));
        System.out.print("Que veux-tu faire ? : ");
        String commande_brute = scanner.nextLine();
        String commande = commande_brute.strip().toLowerCase();

        switch (commande) {
            case "1":
                System.out.println("Création d'un vendeur...");
                this.creerVendeur();
                break;
            case "2":
                System.out.println("Choix du magasin...");
                this.choisirMagasin();
                break;
            case "3":
                System.out.println("Création d'une facture...");
                this.faireFacture();
                break;
            case "4":
            case "q":
            case "quitter":
                System.out.println("Fermeture de l'application administrateur.");
                commande_faite = true;
                break;
            default:
                System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
        }
    }
}


    public void creerVendeur() {}

    public void ajouterLibrairie() {}

    public void gererStock() {}

    public void panneauBord() {}
    
    public void faireFacture() {}

    public void choisirMagasin() {}

    public void StatsVente() {}
}
