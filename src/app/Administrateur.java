package app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    public static void application(ActionBD bd, User u, Scanner scanner) throws SQLException {
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
        Administrateur a = (Administrateur) u;
        boolean commande_faite = false;
        while (!commande_faite) {
            System.out.println(AfficherMenu.Menu("Application Administrateur", options));
            System.out.print("Que veux-tu faire ? : ");
            String commande_brute = scanner.nextLine();
            String commande = commande_brute.strip().toLowerCase();

            switch (commande) {
                case "1":
                    System.out.println("Création d'un vendeur...");
                    creerVendeur();
                    break;
                case "2":
                    System.out.println("Ajouter une librairie.");
                    ajouterLibrairie(bd, scanner);
                    break;
                case "3":
                    System.out.println("Obtenir le panneau de bord");
                    panneauBord(bd);
                    break;
                case "4":
                    System.out.println("Obtenir le panneau de bord");
                    ajouterLivre(bd, scanner);
                    break;
                case "5":
                    System.out.println("Obtenir le panneau de bord");
                    updateStock(bd, null, scanner);
                    break;
                case "6":
                    System.out.println("Obtenir le panneau de bord");
                    disponibilites(bd, null);
                    break;
                case "7":
                    System.out.println("Obtenir le panneau de bord");
                    passerCommande(bd, scanner);
                    break;
                case "8":
                    System.out.println("Obtenir le panneau de bord");
                    Transfer(bd, null, scanner);
                    break;
                case "9":
                    System.out.println("Obtenir le panneau de bord");
                    Factures(bd,a,scanner);
                    break;
                case "10":
                    System.out.println("Fermeture de l'application administrateur.");
                    commande_faite = true;
                    break;
                case "q":
                    System.out.println("Fermeture de l'application administrateur.");
                    commande_faite = true;
                    break;
                case "quitter":
                    System.out.println("Fermeture de l'application administrateur.");
                    commande_faite = true;
                    break;
                default:
                    System.out.println("Commande non reconnue, veuillez entrer un numéro valide.");
            }
        }
    }

    public static void creerVendeur() {
        
    }

    public static void ajouterLibrairie(ActionBD bd, Scanner scan) {
        try{
            System.out.println("Nom de la Librairie ");
            String nom = scan.nextLine();

            System.out.println("Ville de la Librairie ");
            String ville = scan.nextLine();

            Magasin m = new Magasin(null, nom, ville);
            bd.AddLibrairie(m);
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
    }

    public static void panneauBord(ActionBD bd) {
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
    }

}
