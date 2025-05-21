package test;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;

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
            connexion.connecter("localhost", "Librairie", "raphael", "raphe");
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
        this.bd = new ActionBD(null);
    }

    public void testGetClassification() throws SQLException
    {
        Livre livre = bd.getLivreATitre("La torpille");
        assertTrue(livre != null);
        Long isbn = 9782871295914L;
        assertTrue(livre.getISBN()==isbn && livre.getTitre()=="La torpille" 
                    && livre.getNbpages()==48 && livre.getDatepubli()==2004);
        
    }

    public void testgetLivreATitre() throws SQLException
    {
        String titre = "La torpille";
        bd.getLivreATitre(titre);
    }



}