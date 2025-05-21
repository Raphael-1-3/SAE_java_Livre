package main;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ActionBD{
    //inserer attribut connexion
    ConnexionMySQL connexion;
    Statement st;
    public  ActionBD(ConnexionMySQL connexion )
    {
        this.connexion = connexion;
    }

    public void PasserCommande(Client client, Commande commande, Magasin mag) throws SQLException{
        PreparedStatement com = this.connexion.prepareStatement("insert into COMMANDE values (?, ?, ?, ?, ?, ?)");
        // insertion des informations de la commande dans la table CLIENT de la base de donnees
        int numcom = this.getMaxNumCom() + 1;
        com.setInt(1, numcom);
        com.setDate(2, commande.getDate());
        com.setString(3, commande.getEnLigne());
        com.setString(4, commande.getLivraison());
        com.setInt(5, client.getId());
        com.setInt(6, mag.getIdmag());
        com.executeUpdate();
        
        //Insertion des informations pour la table DETAILCOMMANDE

        Map<Livre, Integer> panier = commande.getPanier();
        int numligne = 1;
        for (Livre l : panier.keySet())
        {
            PreparedStatement detailCom = this.connexion.prepareStatement("insert into DETAILCOMMANDE VALUES (?, ?, ?, ?, ?)");    
            detailCom.setInt(1, numcom);
            detailCom.setInt(2, numligne);
            detailCom.setInt(3, l.getISBN());
            detailCom.setInt(4, panier.get(l));
            detailCom.setDouble(5, l.getPrix() * panier.get(l)); 
            numligne ++;
            detailCom.executeUpdate();
        }
    }
    
    public static void GetListeLivre(){}
    public static void OnVousRecommande(Client client){}
    public static void AddLivre(){}
    public static void UpdateStock(){}
    public static void VoirStockMag(){}
    public static void Transfer (){}
    public static void AddVendeur(){}
    public static void AddClient(){}
    public static void AddLibrairie(){}
    public static void InfosTableauBord(){}
    public static void ChargerUtilisateur() {}

    public int getMaxNumCom()
    {
        //TODO
        return 0;
    }

    public Livre getLivreATitre(String titreLivre) throws SQLException
    {
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st.executeQuery("Select isbn, titre, nbpages, datepubli, prix from LIVRE where titre="+titreLivre);
        Livre livre = null;
        while (rs.next())   
        {
            livre = new Livre(Long.parseLong(rs.getString("isbn")), 
                                    rs.getString("titre"), 
                                    Integer.parseInt(rs.getString("nbpages")), 
                                    rs.getString("datepubli"), 
                                    Double.parseDouble(rs.getString("prix")));
        }
        return livre;
    }

    /**
     * Renvoie un HashMap avec pour clé les iddewey et en valeur leur description a partir d'une liste de livre
     * @param tabLivre
     * @return
     */
    public static HashMap<Integer, String> getClassificationAPartirHistorique(List<Livre> tabLivre)
    {

        return new HashMap<>();
    }

    /**
     * Permet d'obenir un dictionnaire contenant toute les class d'un livre
     * @param livre
     * @return HashMap
     * @throws SQLException
     */
    public HashMap<Integer, String> getClassification(Livre livre) throws SQLException
    {
        this.st = this.connexion.createStatement();
        int isbnLivre = livre.getISBN();
        HashMap<Integer, String> classLivre = new HashMap<>();
        ResultSet rs = this.st.executeQuery("select iddewey, nomclass from CLASSIFICATION natural join THEMES natural join LIVRE where isbn ="+isbnLivre);
        while (rs.next())
        {
            classLivre.put(Integer.parseInt(rs.getString("iddewey")), rs.getString("nomclass"));
        }
        return classLivre;
    }



}