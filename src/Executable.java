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
            connexion.connecter(nomServeur, nomBase, nomLogin,  motDePasse); //"localhost", "LibrairieJava", "root", "raphe"
            if (connexion.isConnecte()) 
            {
                ActionBD bd = new ActionBD(connexion);
                User user = null;
                System.out.println("Connexion réussie !");
                boolean authentifie = false;

                // "test" du tableau de bord
                
                try {
                    // 1. Nombre de livres vendus par magasin par an
                    HashMap<Integer, HashMap<Magasin, Integer>> statsLivres = bd.NombreDeLivreVendueParMagasinParAns();
                    System.out.println("=== Nombre de livres vendus par magasin par an ===");
                    for (Integer annee : statsLivres.keySet()) {
                        System.out.println("Année : " + annee);
                        for (Magasin mag : statsLivres.get(annee).keySet()) {
                            System.out.println("  Magasin : " + mag.getNomMag() + " - Livres vendus : " + statsLivres.get(annee).get(mag));
                        }
                    }
                
                    // 2. Chiffre d'affaire par classification pour une année (exemple : 2024)
                    HashMap<Classification, Integer> caClass = bd.chiffreAffaireParClassificationParAns(2024);
                    System.out.println("\n=== Chiffre d'affaire par classification pour 2024 ===");
                    for (Classification c : caClass.keySet()) {
                        System.out.println("Classification : " + c.getNomClass() + " - CA : " + caClass.get(c));
                    }
                
                    // 3. CA magasin par mois pour une année (exemple : 2024)
                    HashMap<Integer, HashMap<Magasin, Integer>> caMagMois = bd.CAMagasinParMoisParAnnee(2024);
                    System.out.println("\n=== Chiffre d'affaire des magasins par mois pour 2024 ===");
                    for (Integer mois : caMagMois.keySet()) {
                        System.out.println("Mois : " + mois);
                        for (Magasin mag : caMagMois.get(mois).keySet()) {
                            System.out.println("  Magasin : " + mag.getNomMag() + " - CA : " + caMagMois.get(mois).get(mag));
                        }
                    }
                
                    // 4. CA vente en ligne/en magasin par an (hors année 2024)
                    HashMap<Integer, HashMap<String, Integer>> caVente = bd.CAVenteEnLigneEnMagasinParAnnee(2025);
                    System.out.println("\n=== CA vente en ligne/en magasin par an (hors 2025) ===");
                    for (Integer annee : caVente.keySet()) {
                        System.out.println("Année : " + annee);
                        for (String type : caVente.get(annee).keySet()) {
                            System.out.println("  Type : " + type + " - CA : " + caVente.get(annee).get(type));
                        }
                    }
                    
                    // 5. Nombre d'auteurs par éditeur (top 10)
                    HashMap<Editeur, Integer> auteursParEditeur = bd.nombreAuteurParEditeur();
                    System.out.println("\n=== Nombre d'auteurs par éditeur (top 10) ===");
                    for (Editeur ed : auteursParEditeur.keySet()) {
                        System.out.println("Editeur : " + ed.getNomEdit() + " - Nombre d'auteurs : " + auteursParEditeur.get(ed));
                    }
                
                    // 6. Nombre de clients par ville ayant acheté un auteur (exemple : "René Goscinny")
                    Auteur auteurExemple = new Auteur("1", "René Goscinny", 0, 0);
                    HashMap<String, Integer> clientsParVille = bd.nombreClientParVilleQuiOntAcheterAuteur(auteurExemple);
                    System.out.println("\n=== Nombre de clients par ville pour l'auteur René Goscinny ===");
                    for (String ville : clientsParVille.keySet()) {
                        System.out.println("Ville : " + ville + " - Nombre de clients : " + clientsParVille.get(ville));
                    }
                
                    // 7. Valeur du stock par magasin
                    HashMap<Magasin, Integer> valeurStock = bd.valeurStockMagasin();
                    System.out.println("\n=== Valeur du stock par magasin ===");
                    for (Magasin mag : valeurStock.keySet()) {
                        System.out.println("Magasin : " + mag.getNomMag() + " - Valeur du stock : " + valeurStock.get(mag));
                    }
                
                    HashMap<Integer, HashMap<String, Double>> statsCA = bd.statsCAParClientParAnnee();
                    System.out.println("\n=== Statistiques CA par client et par année ===");
                    for (Integer annee : statsCA.keySet()) {
                        HashMap<String, Double> stats = statsCA.get(annee);
                        System.out.println("Année : " + annee);
                        System.out.println("  CA max  : " + stats.get("max"));
                        System.out.println("  CA min  : " + stats.get("min"));
                        System.out.println("  CA moyen: " + stats.get("avg"));
                    }
                    // 9. Auteur le plus vendu par année (hors 2025)
                    HashMap<Integer, HashMap<Auteur, Integer>> auteurPlusVendu = bd.auteurLePlusVenduParAnnee(2025);
                    System.out.println("\n=== Auteur le plus vendu par année (hors 2025) ===");
                    for (Integer annee : auteurPlusVendu.keySet()) {
                        System.out.println("Année : " + annee);
                        for (Auteur aut : auteurPlusVendu.get(annee).keySet()) {
                            System.out.println("  Auteur : " + aut.getNomAuteur() + " - Livres vendus : " + auteurPlusVendu.get(annee).get(aut));
                        }
                    }
                
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la récupération des statistiques : " + e.getMessage());
                }
                
                
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
                                Vendeur.application(bd, user, scanner);
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


