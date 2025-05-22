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
            if (connexion.isConnecte()) {
                System.out.println("Connexion réussie !");
                ActionBD bd = new ActionBD(connexion);

                // Afficher les recommandations pour chaque client
                //List<Client> clients = bd.getAllClients();
                //for (Client client : clients) {
                //    try {
                //        List<Livre> recommandations = bd.onVousRecommande(client);
                //        System.out.println("Recommandations pour " + client.getPrenom() + " " + client.getNom() + " :");
                //        if (recommandations.isEmpty()) {
                //            System.out.println("  Aucune recommandation.");
                //        } else {
                //            for (Livre livre : recommandations) {
                //                System.out.println("  - " + livre.getTitre());
                //            }
                //        }
                //    } catch (PasDHistoriqueException e) {
                //        System.out.println("Pas d'historique pour " + client.getPrenom() + " " + client.getNom() + " " + client.getCodePostal());
                //    }
                //}

                //List<Livre> recommandations = bd.onVousRecommande(bd.getClientAPartirNomPrenomCodePostal("","",0);
                HashMap<Client, List<Livre>> historiqueAllClient = bd.getHistoriqueAllClient();
                double sommeRessemblance = 0.0;
                int nbComparaisons = 0;
                for (Client client1 : historiqueAllClient.keySet()) {
                    for (Client client2 : historiqueAllClient.keySet()) {
                        if (!client1.equals(client2)) {
                            double ressemblance = bd.ressemblanceHistorique(historiqueAllClient.get(client1), historiqueAllClient.get(client2));
                            sommeRessemblance += ressemblance;
                            nbComparaisons++;
                            if (true)
                            System.out.println("Ressemblance entre " + client1.getPrenom()+client1.getNom()+client1.getCodePostal() + " et " + client2.getPrenom()+client2.getNom()+client2.getCodePostal() + " : " + ressemblance);
                            
                        }
                    }
                    
                }
                System.out.println("Moyenne de ressemblance : "+(sommeRessemblance/nbComparaisons));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connexion échouée : " + e.getMessage());
        //}  catch (PasDeTelUtilisateurException e) {
            //System.out.println("utilisateur inexistant");
        }   catch (PasDHistoriqueException e) {
            System.out.println("utilisateur sans historique");
        }finally {
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
