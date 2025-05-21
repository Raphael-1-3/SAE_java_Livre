package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionBD{
    //inserer attribut connexion
    private ConnexionMySQL connexion;
    public ActionBD( ConnexionMySQL connexion)
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
        com.close();
    }
    
    public List<Livre> GetListeLivre() throws SQLException{
        List<Livre> res = new ArrayList<>();
        ResultSet rs = this.connexion.createStatement().executeQuery("select * from LIVRE");

        while (rs.next())
        {
            Livre l = new Livre(rs.getInt("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getDate("datepubli"), rs.getDouble("prix"));
            res.add(l);
        }
        rs.close();
        return res;
    }
    public static void OnVousRecommande(){}
    public static void AddLivre(){}
    public static void UpdateStock(){}
    public static void VoirStockMag(){}
    public static void Transfer (){}
    public static void AddVendeur(){}
    public static void AddClient(){}
    public static void AddLibrairie(){}
    public static void InfosTableauBord(){}
    public static void ChargerUtilisateur() {}

    public int getMaxNumCom() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select max(numcom) from COMMANDE");
        rs.next();

        return rs.getInt("max(numcom)");
    }



}