package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Executable{
    public static void main (String [] args)
    {
        List<String> maListe = new ArrayList<>();
        maListe.add("");
        maListe.add("Banane");
        maListe.add("Cerise");
        maListe.add("Orange");
        Commande.changerModeReception();

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
                // Ici tu peux exécuter des requêtes SQL
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connexion échouée : " + e.getMessage());
        } finally {
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
