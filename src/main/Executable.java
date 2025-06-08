package main;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Executable{
    public static void main (String [] args)
    {
        //List<String> maListe = new ArrayList<>();
        //maListe.add("");
        //maListe.add("Banane");
        //maListe.add("Cerise");
        //maListe.add("Orange");
        //Commande.changerModeReception();

        //Client.application();

        System.out.println("Connexion base");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrer le nom du serveur : ");
        String nomServeur = scanner.nextLine();

        System.out.print("Entrer le nom de la base : ");
        String nomBase = scanner.nextLine();

        System.out.print("Entrer le login : ");
        String nomLogin = scanner.nextLine();

        System.out.print("Entrer le mot de passe : ");
        String motDePasse = scanner.nextLine();

        ConnexionMySQL connexion = null;

        
        try {
            connexion = new ConnexionMySQL();
            connexion.connecter(nomServeur, nomBase, nomLogin, motDePasse);
            if (connexion.isConnecte()) 
            {
                ActionBD bd = new ActionBD(connexion);
                System.out.println("Connexion réussie !");
                System.out.println("Connexion a un utilisateru");

                System.out.print("Entrer le email (nom.prenom.codePostal@ex.fr): ");
                String email = scanner.nextLine();

                System.out.print("Entrer le mot de passe (mdp+id): ");
                String mdp = scanner.nextLine();

                // (3, 'Martin', 'Martin.Julie.45000@ex.fr', 'mdp3', "CLIENT"),
                User user = bd.connexionRole(email, mdp);
                System.out.println(user);

            }
                
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connexion échouée : " + e.getMessage());
        //}  catch (PasDeTelUtilisateurException e) {
            //System.out.println("utilisateur inexistant");
        }  
        finally {
            if (connexion != null && connexion.isConnecte()) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture : " + e.getMessage());
                }
            }
            scanner.close();
        }
    
    }
}
