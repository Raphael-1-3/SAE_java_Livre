package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    List<String> options = Arrays.asList(   "Creer un vendeur",
                                            "Ajouter une librairie",
                                            "Panneau de Bord",
                                            "Ajouter un livre",
                                            "Modifier le stock",
                                            "Regarder les disponibilites",
                                            "Passer une commande pour un Client",
                                            "Tranferer un livre",
                                            "Obtenir les factures",
                                            "Quitter");

    boolean commande_faite = false;
    while (!commande_faite) {
        System.out.println(AfficherMenu.Menu("Application Administrateur", options));
        System.out.print("Que veux-tu faire ? : ");
        String commande_brute = scanner.nextLine();
        String commande = commande_brute.strip().toLowerCase();

        switch (commande) {
            case "1":
                System.out.println("Création d'un vendeur...");
                this.creerVendeur();
                break;
            case "2":
                System.out.println("Ajouter une librairie.");
                this.ajouterLibrairie();
                break;
            case "3":
                System.out.println("Obtenir le panneau de bord");
                this.panneauBord();
                break;
            case "4":
                System.out.println("Obtenir le panneau de bord");
                super.ajouterLivre(bd, scanner);
                break;
            case "5":
                System.out.println("Obtenir le panneau de bord");
                super.updateStock(bd, null, scanner);
                break;
            case "6":
                System.out.println("Obtenir le panneau de bord");
                super.disponibilites(bd, null);
                break;
            case "7":
                System.out.println("Obtenir le panneau de bord");
                super.passerCommande(bd, scanner);
                break;
            case "8":
                System.out.println("Obtenir le panneau de bord");
                super.Transfer(bd, null, scanner);
                break;
            case "9":
                System.out.println("Obtenir le panneau de bord");
                super.FacturesMag(bd,scanner);
                break;
            case "10":
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

    public void panneauBord() {}

}
