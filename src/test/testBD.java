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
    }

    @Before
    public void init()
    {
        this.bd = new ActionBD(this.connexion);
    }

    @Test
    public void testgetLivreATitre() throws SQLException
    {
        try
        {
            Livre livre = bd.getLivreATitre("La torpille");
            assertTrue(livre != null);
            Long isbn = 9782871295914L;
            assertTrue(livre.getISBN()==isbn && livre.getTitre().equals("La torpille") 
                    && livre.getNbpages()==48 && livre.getDatepubli()==2004);
            
        }
        catch(EmptySetException ese) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
    }

    @Test
    public void testGetClassification() throws SQLException
    {
        try
        {
            Livre livre = bd.getLivreATitre("Final cut");
            HashMap<Integer, String> iddewey = bd.getClassification(livre);
            Map<Integer, String> test = Map.of(740, "Arts décoratifs");
            assertEquals(iddewey.entrySet(), test.entrySet());
            assertEquals("Arts décoratifs", iddewey.get(740));
            
        }
        catch(EmptySetException ese) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
        
    }

    @Test
    public void testGetClassificationAPartirHistorique() throws SQLException
    {
        try
        {
            List<Livre> tabLivre = Arrays.asList(bd.getLivreATitre("Final cut"), 
                                    bd.getLivreATitre("Du monde et de l'étranger"), 
                                    bd.getLivreATitre("Trois républiques pour une France"), 
                                    bd.getLivreATitre("Tahiti, Polynésie française"), 
                                    bd.getLivreATitre("Ecritures dramatiques") ,
                                    bd.getLivreATitre("Un livre"));
        
        HashMap<Integer, String> dicoIddewey = bd.getClassificationAPartirHistorique(tabLivre);
        Map<Integer, String> test = Map.of(740, "Arts décoratifs", 
                                        950, "Histoire de l'Asie",
                                        940, "Histoire de l'Europe",
                                        910, "Géographie et voyages",
                                        800, "Littérature",
                                        840, "Littérature française");

        assertEquals(test, dicoIddewey);

        } catch (EmptySetException e) 
        {
            System.err.println("Aucun résultat trouvé (empty set).");
        }
    }

    

    



}