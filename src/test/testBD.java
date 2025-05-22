package test;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import main.*;

public class testBD
{
    private ActionBD bd;
    private ConnexionMySQL connexion;

    public testBD()
    {
        ConnexionMySQL connexion = null;
        try 
        {

            connexion = new ConnexionMySQL();
            connexion.connecter("localhost", "Librairie", "root", "raphe");
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
        Client expected = new Client(9, "Bouzid",  "Raul", 38000, "Grenoble", "23 chemin de la Forêt");
        Client Atester = bd.getClientAPartirNomPrenomCodePostal("Bouzid", "Raul", 38000);
        assertEquals(expected, Atester);
        }
        catch (PasDeTelUtilisateurException pdtue)
        {
            System.err.println("Cette utilisateur n'existe pas (empty set)");
        }
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
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
        catch (PasDHistoriqueException e) 
        {
            System.err.println("Aucun résultat trouvé (null).");
        }
        try{
        System.out.println(bd.getHistoriqueClient(bd.getClientAPartirNomPrenomCodePostal("Pereira", "Tiago", 69001)));
        }
        catch (PasDHistoriqueException e) 
        {
            System.err.println("Aucun résultat trouvé (null).");
        }
        assertThrows(PasDHistoriqueException.class, () -> bd.getHistoriqueClient(bd.getClientAPartirNomPrenomCodePostal("Pereira", "Tiago", 69001)));
    }


}