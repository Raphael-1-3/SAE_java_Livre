package test;
import org.junit.Test;

import app.*;
import BD.ActionBD;
import BD.ConnexionMySQL;
import Exceptions.EmptySetException;
import Exceptions.PasDHistoriqueException;
import Exceptions.PasDeTelUtilisateurException;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import app.*;

public class testBD
{
    private ActionBD bd;
    private ConnexionMySQL connexion;

    public testBD()
    {
        ConnexionMySQL connexion = null;
        try 
        {

            connexion = new ConnexionMySQL(); // a changer en fonction de la machine 
            connexion.connecter("localhost", "LibrairieJava", "root", "raphe");
            if (connexion.isConnecte()) {
                System.out.println("Connexion réussie !");
            }
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non trouvé : " + e.getMessage());
        } 
        catch (SQLException e) {
            System.out.println("Connexion échouée : " + e.getMessage());
        } 
        this.connexion = connexion; 
        this.bd = new ActionBD(this.connexion);  
    }

    @Test
    public void testgetLivreParTitre() throws SQLException
    {
        try
        {
            Livre livre = bd.getLivreParTitre("La torpille");
            assertTrue(livre != null);
            Long isbn = 9782871295914L;
            assertTrue(livre.getISBN()==isbn && livre.getTitre().equals("La torpille") 
                    && livre.getNbpages()==48 && livre.getDatepubli()==2004);
            
        }
        catch(EmptySetException ese) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
        assertThrows(EmptySetException.class, () -> bd.getLivreParTitre("ce livre n'existe pas")); // appris sur cette sae
    }

    @Test
    public void testGetClassification() throws SQLException
    {
        try
        {
            Livre livre = bd.getLivreParTitre("Final cut");
            HashMap<Integer, String> iddewey = bd.getClassification(livre);
            Map<Integer, String> test = Map.of(740, "Arts décoratifs");
            assertEquals(iddewey.entrySet(), test.entrySet());
            assertEquals("Arts décoratifs", iddewey.get(740));
            
            
        }
        catch(EmptySetException ese) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
        assertThrows(EmptySetException.class, () -> bd.getClassification(bd.getLivreParTitre("livre qui n'existe pas")));
        
    }

    @Test
    public void testGetClassificationAPartirHistorique() throws SQLException
    {
        try
        {
            List<Livre> tabLivre = Arrays.asList(bd.getLivreParTitre("Final cut"), 
                                    bd.getLivreParTitre("Du monde et de l'étranger"), 
                                    bd.getLivreParTitre("Trois républiques pour une France"), 
                                    bd.getLivreParTitre("Tahiti, Polynésie française"), 
                                    bd.getLivreParTitre("Ecritures dramatiques") ,
                                    bd.getLivreParTitre("Un livre"));
        
        HashMap<Integer, String> dicoIddewey = bd.getClassificationAPartirHistorique(tabLivre);
        Map<Integer, String> test = Map.of(740, "Arts décoratifs", 
                                        950, "Histoire de l'Asie",
                                        940, "Histoire de l'Europe",
                                        910, "Géographie et voyages",
                                        800, "Littérature",
                                        840, "Littérature française");

        assertEquals(test, dicoIddewey);
        } 
        catch (EmptySetException e) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
    }

    @Test
    public void testgetLivreParIddewey() throws SQLException
    {
        try
        {
            // liste de livre ayant un iddewey 100 selon : select titre, iddewey FROM CLASSIFICATION NATURAL JOIN THEMES NATURAL JOIN LIVRE WHERE iddewey = 100;
            List<Livre> expected = Arrays.asList(bd.getLivreParTitre("Dictionnaire des auteurs et des the  mes de la philosophie"), 
                                    bd.getLivreParTitre("Créer le réel"), 
                                    bd.getLivreParTitre("Le champ mimétique"), 
                                    bd.getLivreParTitre("Pour un catastrophisme éclairé"), 
                                    bd.getLivreParTitre("Je m'explique") ,
                                    bd.getLivreParTitre("La condition anarchique"),
                                    bd.getLivreParTitre("Fragments"),
                                    bd.getLivreParTitre("Traités"),
                                    bd.getLivreParTitre("Alter ego"),
                                    bd.getLivreParTitre("L'ironie"),
                                    bd.getLivreParTitre("Le Sujet de la philosophie"));
        List<Livre> test = bd.getLivreParIddewey(100);
        assertEquals(expected, test);
        }
        catch (EmptySetException e) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
        assertThrows(EmptySetException.class, () -> bd.getLivreParIddewey(9999)); 
    }

    @Test
    public void testGetClientAPartirNomPrenomcodePostal() throws SQLException
    {
        try{
            Client expected = new Client(
                9, 
                "Bouzid.Raul.38000@ex.fr", 
                "Bouzid", 
                "Raul", 
                "mdp9", 
                "CLIENT", 
                38000, 
                "Grenoble", 
                "23 chemin de la Forêt" 
            );
            Client Atester = bd.getClientAPartirNomPrenomCodePostal("Bouzid", "Raul", 38000);
            assertEquals(expected, Atester);
        }
        catch (PasDeTelUtilisateurException pdtue)
        {}
        assertThrows(PasDeTelUtilisateurException.class, () -> bd.getClientAPartirNomPrenomCodePostal("null", "null", 0));

    }

    @Test
    public void testgetHistoriqueClient() throws SQLException, PasDeTelUtilisateurException
    {
        try
        {
            Client client = bd.getClientAPartirNomPrenomCodePostal("Rodriguez", "Fatima", 45000);
            HashMap<Client, List<Livre>> aTester = bd.getHistoriqueClient(client);
            Map<Client, List<Livre>> expected = Map.of(client, Arrays.asList(bd.getLivreParTitre("L'aéroport"),
                                                                        bd.getLivreParTitre("Les journalistes en France (1880-1950)"),
                                                                        bd.getLivreParTitre("Terres lointaines"),
                                                                        bd.getLivreParTitre("Quatorze jours sur un banc de glace"),
                                                                        bd.getLivreParTitre("Histoire des parachutistes français"),
                                                                        bd.getLivreParTitre("L' homme dans le rétroviseur"),
                                                                        bd.getLivreParTitre("Chambres d'hôtes au château dans les vignes"),
                                                                        bd.getLivreParTitre("Le tableau diabolique"),
                                                                        bd.getLivreParTitre("Les Romains à petits pas"),
                                                                        bd.getLivreParTitre("Les machos expliqués à mon frère"),
                                                                        bd.getLivreParTitre("Aufklärung"),
                                                                        bd.getLivreParTitre("Un arche ologue au pays de la Bible"),
                                                                        bd.getLivreParTitre("Jugendstil et art nouveau"),
                                                                        bd.getLivreParTitre("égypte"),
                                                                        bd.getLivreParTitre("C'est oblige  de dire merci?")));

            assertEquals(expected, aTester);
        }
        catch (EmptySetException e) 
        {}
        catch (PasDHistoriqueException e) 
        {}
        assertThrows(PasDHistoriqueException.class, () -> bd.getHistoriqueClient(bd.getClientAPartirNomPrenomCodePostal("Pereira", "Tiago", 69001)));
    }

    @Test
    public void testressemblanceHistorique() throws SQLException, EmptySetException, PasDeTelUtilisateurException {
        try
        {
        // cas 0.0 de ressemblance
        Client emma = bd.getClientAPartirNomPrenomCodePostal("Fournier", "Emma", 44000);
        Client jean = bd.getClientAPartirNomPrenomCodePostal("Lefebvre", "Jean", 38000);
        List<Livre> histEmma = bd.getHistoriqueClient(emma).get(emma);
        List<Livre> histJean = bd.getHistoriqueClient(jean).get(jean);
        double res1 = bd.ressemblanceHistorique(histEmma, histJean);
        assertEquals(12.5, res1, 0.01);

        // cas 12.5 de ressemblance
        Client jeanB = bd.getClientAPartirNomPrenomCodePostal("Bernard", "Jean", 33000);
        Client elodie = bd.getClientAPartirNomPrenomCodePostal("Thomas", "Elodie", 31000);
        List<Livre> histJeanB = bd.getHistoriqueClient(jeanB).get(jeanB);
        List<Livre> histElodie = bd.getHistoriqueClient(elodie).get(elodie);
        double res2 = bd.ressemblanceHistorique(histJeanB, histElodie);
        assertEquals(0.0, res2, 0.01);

        // cas 25.0 % (le poucentage maximum)
        Client paolo = bd.getClientAPartirNomPrenomCodePostal("Bouzid", "Paolo", 13001);
        Client laura = bd.getClientAPartirNomPrenomCodePostal("Martin", "Laura", 6000);
        List<Livre> histPaolo = bd.getHistoriqueClient(paolo).get(paolo);
        List<Livre> histLaura = bd.getHistoriqueClient(laura).get(laura);
        double res3 = bd.ressemblanceHistorique(histPaolo, histLaura);
        assertEquals(25.0, res3, 0.01);
        }
        catch (PasDHistoriqueException e) 
        {}
    } 

    @Test
    public void testonVousRecommande() throws SQLException, EmptySetException, PasDeTelUtilisateurException 
    {
        try
        {
            // Test pour Camille Martin
            Client camille = bd.getClientAPartirNomPrenomCodePostal("Martin", "Camille", 33000);
            List<Livre> expectedCamille = Arrays.asList(
                bd.getLivreParTitre("Ce que les hommes disent aux dieux"),
                bd.getLivreParTitre("Le passager de la maison du temps"),
                bd.getLivreParTitre("L' opérette en France"),
                bd.getLivreParTitre("Petit ours brun et les chaussures"),
                bd.getLivreParTitre("Pagaille au chenil"),
                bd.getLivreParTitre("Paris en poésie"),
                bd.getLivreParTitre("Passion passions"),
                bd.getLivreParTitre("Cookies délices"),
                bd.getLivreParTitre("Swan"),
                bd.getLivreParTitre("Barye"),
                bd.getLivreParTitre("Rendez l'argent!")
            );
            List<Livre> recommandationsCamille = bd.onVousRecommande(camille);
            assertEquals(expectedCamille, recommandationsCamille);

            // Test pour Hugo David
            Client hugo = bd.getClientAPartirNomPrenomCodePostal("David", "Hugo", 44000);
            List<Livre> expectedHugo = Arrays.asList(
                bd.getLivreParTitre("Les trois fileuses"),
                bd.getLivreParTitre("Attirances"),
                bd.getLivreParTitre("Les signes du temps et l'art moderne"),
                bd.getLivreParTitre("Rose activité mortelle"),
                bd.getLivreParTitre("Master histoire"),
                bd.getLivreParTitre("Les sept pièces"),
                bd.getLivreParTitre("La légende du sang")
            );
            List<Livre> recommandationsHugo = bd.onVousRecommande(hugo);
            assertEquals(expectedHugo, recommandationsHugo);

            // Test pour Louis Garcia
            Client louis = bd.getClientAPartirNomPrenomCodePostal("Garcia", "Louis", 37000);
            List<Livre> expectedLouis = Arrays.asList(
                bd.getLivreParTitre("La vie quotidienne en France au temps du Front Populaire, 1935-1938."),
                bd.getLivreParTitre("Le guide Hachette des vins 2006"),
                bd.getLivreParTitre("La mort opportune"),
                bd.getLivreParTitre("La femme dans la Grèce antique"),
                bd.getLivreParTitre("Le dico de l'argot fin de siècle"),
                bd.getLivreParTitre("Choisir ses poissons d'eau de mer"),
                bd.getLivreParTitre("Une Grève de la faim"),
                bd.getLivreParTitre("Le plaisir des yeux"),
                bd.getLivreParTitre("Le rêve pluie"),
                bd.getLivreParTitre("Vous n'aurez pas le dernier mot!"),
                bd.getLivreParTitre("Bestiaire imaginaire"),
                bd.getLivreParTitre("Les miserables"),
                bd.getLivreParTitre("Qui a fondé la christianisme"),
                bd.getLivreParTitre("La promeneuse d'oiseaux")
            );
            List<Livre> recommandationsLouis = bd.onVousRecommande(louis);
            assertEquals(expectedLouis, recommandationsLouis);
        }
        catch (PasDHistoriqueException e) 
        {}
    }

    /**
     * Pour test cette méthode on prend la valeur actuel,
     *  on ajoute une commande fictif, on verifie que la valeur est augmenté puis en la suppime
     * @throws SQLException
     */@Test
    public void testGetMaxNumCom() throws SQLException 
    {
        int maxAvant = bd.getMaxNumCom();

        int numTest = maxAvant + 1;
        PreparedStatement ps = this.connexion.prepareStatement(
            "INSERT INTO COMMANDE (numcom, datecom, enligne, livraison, idcli, idmag) VALUES (?, CURRENT_DATE, 'O', 'N', 1, 1)"
        );
        ps.setInt(1, numTest);
        ps.executeQuery();

        int maxApres = bd.getMaxNumCom();
        assertEquals(numTest, maxApres);

        ps.executeUpdate("DELETE FROM COMMANDE WHERE numcom = " + numTest + ";");
        ps.close();
    }

    /**
     * Pour test cette méthode on prend la valeur actuel,
     *  on ajoute un user fictif, on verifie que la valeur est augmenté puis en le suppime
     * @throws SQLException
     */
    @Test
    public void testGetIdUserMax() throws SQLException 
    {
        int maxAvant = bd.getIdUserMax(); // 
        int idTest = maxAvant + 1;
        PreparedStatement ps = this.connexion.prepareStatement(
            "INSERT INTO USER (idu, nom, email, motDePasse, role) VALUES (" 
            + idTest + ", 'Test', 'test@example.com', 'testpassword', 'client')");
        ps.executeQuery();

        int maxApres = bd.getIdUserMax();
        assertEquals(idTest, maxApres);

        ps.executeUpdate("DELETE FROM USER WHERE idu = " + idTest + ";");
        ps.close();
    }

    @Test
    public void testPasserCommande() throws SQLException, PasDeTelUtilisateurException, EmptySetException {
        // On vérifie que le nombre de commandes augmente après l'insertion
        int maxAvant = bd.getMaxNumCom();
        int numTest = maxAvant + 1;
        Client client = bd.getClientAPartirNomPrenomCodePostal("Bouzid", "Raul", 38000);
        Magasin magasin = bd.magAPartirNom("La librairie parisienne");
        Livre livre = bd.getLivreParTitre("La torpille");
        Commande commande = new Commande(0, bd);
        commande.ajouterArticle(livre, 1);
        bd.PasserCommande(client, commande, magasin);
        int maxApres = bd.getMaxNumCom();
        assertEquals(numTest, maxApres);

        PreparedStatement ps = this.connexion.prepareStatement(
            "DELETE FROM COMMANDE WHERE numcom = ?");
        ps.setInt(1, numTest);
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testGetListeLivre() throws SQLException {
        // Récupération de la liste des livres
        List<Livre> listeLivres = bd.GetListeLivre();
        // Vérifie que la liste n'est pas nulle
        assertNotNull(listeLivres);
        // Vérifie que la liste contient au moins un livre
        assertTrue(listeLivres.size() > 0);
    }

    @Test
    public void testAddLivre() throws SQLException {
         // Création d'un nouveau livre à ajouter
        Livre nouveauLivre = new Livre(1, "TitreTestAjout", 200, 2024, 19.99);

        // Ajout du livre via la méthode à tester
        bd.AddLivre(nouveauLivre);

        // Récupération de la liste des livres pour vérifier l'ajout
        List<Livre> listeLivres = bd.GetListeLivre();

        boolean found = false;
        int i = 0;
        while (i < listeLivres.size() && !found) {
            Livre livre = listeLivres.get(i);
            if (livre.getISBN() == 1 && "TitreTestAjout".equals(livre.getTitre())) {
            found = true;
            }
            i++;
        }
        assertTrue(found);
    }


    @Test
    public void testUpdateStock() throws SQLException, EmptySetException {
        // On suppose qu'il existe un magasin et un livre dans la base
    Magasin magasin = bd.magAPartirNom("La librairie parisienne");
    Livre livre = bd.getLivreParTitre("La torpille");

    // On récupère le stock actuel
    int stockAvant = bd.VoirStockMag(magasin).get(livre);

    // On met à jour le stock (+5)
    bd.UpdateStock(livre, magasin, stockAvant + 5);

    // On vérifie que le stock a bien été mis à jour
    int stockApres = bd.VoirStockMag(magasin).get(livre);
    assertEquals(stockAvant + 5, stockApres);

    // On remet le stock à sa valeur initiale pour ne pas impacter les autres tests
    bd.UpdateStock(livre, magasin, stockAvant);
    assertEquals(stockAvant, stockApres);
    }

    @Test
    public void testVoirStockMag() throws SQLException, EmptySetException {
        // On suppose qu'un magasin et un livre existent déjà
        Magasin magasin = bd.magAPartirNom("La librairie parisienne");
        Livre livre = bd.getLivreParTitre("La torpille");
        // On récupère le stock du magasin
        HashMap<Livre, Integer> stock = bd.VoirStockMag(magasin);
        // Vérifie que la map n'est pas nulle
        assertNotNull(stock);
        // Vérifie que le livre testé est bien dans le stock (ou au moins la map contient des livres)
        assertTrue(stock.size() > 0);
        // Vérifie que la quantité pour ce livre est cohérente (>=0)
        if (stock.containsKey(livre)) {
            assertTrue(stock.get(livre) >= 0);
        }
    }

    @Test
    public void testTransfer() throws SQLException, Exceptions.PasAssezLivreException, EmptySetException {
        // On suppose que deux magasins et un livre existent déjà
        Magasin depart = bd.magAPartirNom("Cap au Sud");
        Magasin arrivee = bd.magAPartirNom("La librairie parisienne");
        Livre livre = bd.getLivreParTitre("La torpille");
        // On récupère le stock initial dans chaque magasin
        HashMap<Livre, Integer> stockDepart = bd.VoirStockMag(depart);
        HashMap<Livre, Integer> stockArrivee = bd.VoirStockMag(arrivee);
        int qteDepartAvant = stockDepart.getOrDefault(livre, 0);
        int qteArriveeAvant = stockArrivee.getOrDefault(livre, 0);
        // On tente de transférer 1 exemplaire si possible
        if (qteDepartAvant > 0) {
            bd.Transfer(livre.getISBN(), depart, arrivee, 1);
            int qteDepartApres = bd.VoirStockMag(depart).getOrDefault(livre, 0);
            int qteArriveeApres = bd.VoirStockMag(arrivee).getOrDefault(livre, 0);
            assertEquals(qteDepartAvant - 1, qteDepartApres);
            assertEquals(qteArriveeAvant + 1, qteArriveeApres);
            // Remise en état (on transfère dans l'autre sens)
            bd.Transfer(livre.getISBN(), arrivee, depart, 1);
        } else {
            // Si pas assez de stock, on vérifie que l'exception est bien levée
            assertThrows(Exceptions.PasAssezLivreException.class, () -> {
                bd.Transfer(livre.getISBN(), depart, arrivee, 1);
            });
        }
    }

    @Test
    public void testAddVendeur() throws SQLException, PasDeTelUtilisateurException {
        Magasin magasin = bd.magAPartirNom("La librairie parisienne");
        // Création d'un vendeur fictif avec le bon constructeur
        Vendeur vendeur = new Vendeur(null, "paul.dupont@exemple.com", "Dupont", "mdpTest", "VENDEUR", "Paul", magasin);
        // Ajout du vendeur
        bd.AddVendeur(vendeur);
        // Vérification : le vendeur doit maintenant exister dans la base (table USER et VENDEUR)
        User user = bd.connexionRole("paul.dupont@exemple.com", "mdpTest");
        assertNotNull(user);
        assertEquals("VENDEUR", user.getRole());
        // Nettoyage : suppression du vendeur ajouté
        PreparedStatement ps = this.connexion.prepareStatement("DELETE FROM USER WHERE email = ?");
        ps.setString(1, "paul.dupont@exemple.com");
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testAddLibrairie() throws SQLException {
        // Création d'un magasin fictif
        Magasin mag = new Magasin(null, "LibrairieTest", "Paris");
        bd.AddLibrairie(mag);
        // Vérification : le magasin doit maintenant exister
        Magasin magRecup = bd.magAPartirNom("LibrairieTest");
        assertNotNull(magRecup);
        assertEquals("LibrairieTest", magRecup.getNomMag());
        // Nettoyage : suppression du magasin ajouté
        PreparedStatement ps = this.connexion.prepareStatement("DELETE FROM MAGASIN WHERE nommag = ?");
        ps.setString(1, "LibrairieTest");
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testMagAPartirNom() throws SQLException {
        // On suppose qu'un magasin existe déjà
        Magasin mag = bd.magAPartirNom("La librairie parisienne");
        assertNotNull(mag);
        assertEquals("La librairie parisienne", mag.getNomMag());
    }

    @Test
    public void testConnexionRole() throws SQLException, PasDeTelUtilisateurException {
        // On suppose qu'un client existe déjà
        User user = bd.connexionRole("Bouzid.Raul.38000@ex.fr", "mdp9");
        assertNotNull(user);
        assertEquals("CLIENT", user.getRole());
    }

    @Test
    public void testCreerClient() throws SQLException {
        // Création d'un client fictif
        boolean res = bd.creerClient("TestNom", "TestPrenom", 12345, "TestVille", "TestAdresse", "testclient@ex.fr", "mdpTest");
        assertTrue(res);
        // Vérification : le client doit maintenant exister
        Client client = null;
        try {
            client = bd.getClientAPartirNomPrenomCodePostal("TestNom", "TestPrenom", 12345);
        } catch (PasDeTelUtilisateurException e) {
            fail("Client non trouvé après création");
        }
        assertNotNull(client);
        // Nettoyage : suppression du client ajouté
        PreparedStatement ps = this.connexion.prepareStatement("DELETE FROM USER WHERE email = ?");
        ps.setString(1, "testclient@ex.fr");
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testChercherLivreApproximative() throws SQLException {
        // On suppose qu'un livre "La torpille" existe
        List<Livre> livres = bd.cherhcherLivreApproximative("torpille");
        assertNotNull(livres);
        boolean found = false;
        for (Livre l : livres) {
            if ("La torpille".equals(l.getTitre())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetListMagasin() throws SQLException {
        List<Magasin> magasins = bd.getListMagasin();
        assertNotNull(magasins);
        assertTrue(magasins.size() > 0);
        for (Magasin m : magasins) {
            assertNotNull(m);
            assertNotNull(m.getNomMag());
        }
    }

    @Test
    public void testGetMagasinOuLivreDispo() throws SQLException, Exceptions.EmptySetException {
        Livre livre = bd.getLivreParTitre("La torpille");
        List<Magasin> magasins = bd.getMagasinOuLivreDispo(livre);
        assertNotNull(magasins);
        assertTrue(magasins.size() > 0);
        for (Magasin m : magasins) {
            assertNotNull(m);
        }
    }

    @Test
    public void testGetClientNonPrenom() throws SQLException {
        List<Client> clients = bd.getClientNonPrenom("Bouzid", "Raul");
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
        for (Client c : clients) {
            assertNotNull(c);
        }
    }

    @Test
    public void testGetClientParId() throws SQLException {
        //TODO: Ajouter un test pour getClientParId;
    }

    @Test
    public void testRechercheLivreAuteurApproximative() throws SQLException {
        // On suppose qu'un auteur "Dumas" existe
        List<Auteur> auteurs = bd.rechercheAuteurApproximative("Dumas");
        assertNotNull(auteurs);
        assertTrue(auteurs.size() > 0);
        Auteur auteur = auteurs.get(0);
        List<Livre> livres = bd.rechercheLivreAuteur(auteur);
        assertNotNull(livres);
    }

    @Test
    public void testGetCurrentDate() throws SQLException {
        java.sql.Date date = bd.getCurrentDate();
        assertNotNull(date);
    }

    // TODO: Ajouter ces tests pour couvrir toutes les méthodes publiques de ActionBD

    @Test
    public void testGetHistoriqueAllClient() throws SQLException, PasDHistoriqueException {
        // TODO: Implémenter un test pour getHistoriqueAllClient
    }

    @Test
    public void testChangerMotDePasse() throws SQLException {
        // TODO: Implémenter un test pour changerMotDePasse
    }

    @Test
    public void testChangerAdresse() throws SQLException {
        // TODO: Implémenter un test pour changerAdresse
    }

    @Test
    public void testFactureClient() throws SQLException {
        // TODO: Implémenter un test pour factureClient
    }

    @Test
    public void testFactureMagasin() throws SQLException {
        // TODO: Implémenter un test pour factureMagasin
    }

    @Test
    public void testCherhcherClassificationApproximative() throws SQLException {
        // TODO: Implémenter un test pour cherhcherClassificationApproximative
    }

    @Test
    public void testChercherLivreAPartirClassification() throws SQLException {
        // TODO: Implémenter un test pour chercherLivreAPartirClassification
    }

    @Test
    public void testCherhcherEditeurApproximative() throws SQLException {
        // TODO: Implémenter un test pour cherhcherEditeurApproximative
    }

    @Test
    public void testChercherLivreAPartiEditeur() throws SQLException {
        // TODO: Implémenter un test pour chercherLivreAPartiEditeur
    }

    @Test
    public void testRechercheAuteurApproximative() throws SQLException {
        // TODO: Implémenter un test pour rechercheAuteurApproximative
    }
}