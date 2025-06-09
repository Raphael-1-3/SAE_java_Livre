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
                boolean authentifie = false;
                while (!authentifie) {
                    System.out.println("Connexion a un utilisateur");
                    System.out.println("Menu :");
                    System.out.println("1. Se connecter");
                    System.out.println("2. Créer un compte");
                    System.out.print("Votre choix : ");
                    int choix = Integer.parseInt(scanner.nextLine());
                    
                    if (choix == 2)
                    {
                        System.out.println("Nous sommes ravis de vous accueillir en tant que nouveau client !");
                        if (!creerCompteClient(bd)) {
                            System.out.println("Retour au menu principal.");
                            continue;
                        } else {
                            System.out.println("Veuillez maintenant vous connecter.");
                        }
                    }

                    System.out.print("Entrer le email (nom.prenom.codePostal@ex.fr): ");
                    String email = scanner.nextLine();

                    System.out.print("Entrer le mot de passe (mdp+id): ");
                    String mdp = scanner.nextLine();

                    User user = bd.connexionRole(email, mdp);
                    if (user == null) {
                        System.out.println("Identifiants incorrects. Retour au menu.");
                        continue;
                    }
                    System.out.println(user);
                    switch (user.getClass().getSimpleName()) 
                    {
                        case "Client":
                            System.out.println("Bienvenue, client !");
                            // Actions client
                            break;
                        case "Vendeur":
                            System.out.println("Bienvenue, vendeur !");
                            // Actions vendeur
                            break;
                        case "Admin":
                            System.out.println("Bienvenue, administrateur !");
                            // Actions admin
                            break;
                        default:
                            System.out.println("Rôle inconnu.");
                            break;
                    }
                    authentifie = true;
                }
            }
                
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connexion échouée : " + e.getMessage());
        }  
        finally {
            if (connexion != null && connexion.isConnecte()) { //partie pour fermer le connexion a la lors de la fin de l'éxécution de l'app
                try {
                    connexion.close();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture : " + e.getMessage());
                }
            }
            scanner.close();
        }
    }

    public static boolean creerCompteClient(ActionBD bd) throws SQLException
    {
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
        if (!creationCompte) 
        { 
            System.out.println("Un probleme rencontré lors de la création de compte");
            return false;
        }
        else
        {
            System.out.println("Compte créé avec succès !");
            return true;
        }
    }
}
