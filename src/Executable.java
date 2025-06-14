
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import BD.*;
import Exceptions.*;
import app.*;
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
                app.User user = null;
                System.out.println("Connexion réussie !");
                boolean authentifie = false;
                while (!authentifie) 
                {
                    System.out.println("");
                    System.out.println("╭────────────────────────────╮");
                    System.out.println("│ Connexion a un utilisateur │");
                    System.out.println("├────────────────────────────┤");
                    System.out.println("│ 1 : Se connecter           │");
                    System.out.println("│ 2 : Créer un compte        │");
                    System.out.println("│ 3 : quitter                │");
                    System.out.println("╰────────────────────────────╯");
                    Integer choix = null;
                    try 
                    {
                        choix = Integer.parseInt(scanner.nextLine());
                    }catch (NumberFormatException nfe) {
                        System.out.println("vous n'avez pas rentre un entre correct");
                        continue;
                    }
                    catch (NoSuchElementException e)
                    {
                        System.out.println("Veuillez entrer quelque chose !");
                        continue;
                    }
                    
                    if (choix == 1)
                    {

                        System.out.print("Entrer le email (nom.prenom.codePostal@ex.fr): ");
                        String email = scanner.nextLine();

                        System.out.print("Entrer le mot de passe (mdp+id): ");
                        String mdp = scanner.nextLine();

                        
                        
                            try 
                            {
                                user = bd.connexionRole(email, mdp);
                                // sortie de la boucle si pas d'exception
                            } 
                            catch (PasDeTelUtilisateurException e) 
                            {
                                System.out.println("mot de passe ou email incorrecte. Retour a la page de connexion");
                                continue;
                            }
                        

                        if (user == null) 
                        {
                            System.out.println("Identifiants incorrects. Retour au menu.");
                            continue;
                        }
                        
                        System.out.println(user); // ----- a enlever
                        switch (user.getClass().getSimpleName()) 
                        {
                            case "Client":
                                System.out.println("Bienvenue, client !");
                                Client.application(bd, user, scanner);
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
                        }}   
                    
                    if (choix == 2)
                    {
                        System.out.println("Nous sommes ravis de vous accueillir en tant que nouveau client !");
                        if (!Client.creerCompteClient(bd, scanner)) 
                        {
                            System.out.println("Retour au menu principal.");
                            continue;
                        } else {
                            System.out.println("Veuillez maintenant vous connecter.");
                        }
                    }
                        if (choix == 3) {
                            authentifie = true;
                        } else if (choix != 1 && choix != 2) {
                            System.out.println("vous avez rentrer un truc incorrecte");
                        }
                    } // end while
                } // end if (connexion.isConnecte())
            } catch (ClassNotFoundException e) {
                System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Connexion échouée : " + e.getMessage());
            }  
            finally 
            {
                if (connexion != null && connexion.isConnecte()) 
                { //partie pour fermer le connexion a la lors de la fin de l'éxécution de l'app
                    try 
                    {
                        connexion.close();
                    } 
                    catch (SQLException e) 
                    {
                        System.out.println("Erreur lors de la fermeture : " + e.getMessage());
                    }
                }
                
            }
            scanner.close();
        }
    }


