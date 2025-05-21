package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;

public class ActionBD{
    //inserer attribut connexion
    private ConnexionMySQL connexion;
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
            detailCom.setLong(3, l.getISBN());
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
            Livre l = new Livre(rs.getInt("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getInt("datepubli"), rs.getDouble("prix"));
            res.add(l);
        }
        rs.close();
        return res;
    }
    public static void OnVousRecommande(Client client){}

    public void AddLivre(Livre l) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("insert into LIVRE values (?, ?, ?, ?, ?)");
        ps.setLong(1, l.getISBN());
        ps.setString(2, l.getTitre());
        ps.setInt(3, l.getNbpages());
        ps.setInt(4, l.getDatepubli());
        ps.setDouble(5, l.getPrix());

        ps.executeUpdate();
        ps.close();

    }

    public void UpdateStock(Livre l, Magasin mag, int nv) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("update POSSEDER set qte = ? where idmag = ? and isbn = ?");
        ps.setInt(1, nv);
        ps.setInt(2, mag.getIdmag());
        ps.setLong(3, l.getISBN());
        ps.executeUpdate();
        ps.close();
    }
    
    public HashMap<Livre, Integer> VoirStockMag(Magasin mag) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select isbn, titre, nbpages, datepubli, prix, qte from POSSEDER natural join MAGASIN natural join LIVRE where idmag = ?");
        ps.setInt(1, mag.getIdmag());
        ResultSet rs = ps.executeQuery();
        HashMap<Livre, Integer> map = new HashMap<>();
        while (rs.next())
        {
            Livre l = new Livre(rs.getLong("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getInt("datepubli"), rs.getDouble("prix"));
            map.put(l, rs.getInt("qte"));
        }
        rs.close();
        ps.close();

        return map;
    }
    public static void Transfer (){}
    public static void AddVendeur(){}
    public static void AddClient(){}
    public static void AddLibrairie(){}
    public static void InfosTableauBord(){}
    public static void ChargerUtilisateur() {}

    /**
     * renvoie le numéro de commande le plus élever
     * @return
     * @throws SQLException
     */
    public int getMaxNumCom() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select max(numcom) from COMMANDE");
        rs.next();
        int maxNumCommande = rs.getInt("max(numcom)");
        rs.close();

        return maxNumCommande;
    }

    /**
     * Permet de creer un object Livre a partir d'un titre de livre présent dans la base (Raphaël)
     * @param titreLivre
     * @return
     * @throws SQLException
     */
    public Livre getLivreATitre(String titreLivre) throws SQLException, EmptySetException
    {
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT isbn, titre, nbpages, datepubli, prix FROM LIVRE WHERE titre = ?"
        );
        ps.setString(1, titreLivre);
        ResultSet rs = ps.executeQuery();
        Livre livre = null;
        if (rs.next()) {
            livre = new Livre(
                rs.getLong("isbn"),
                rs.getString("titre"),
                rs.getInt("nbpages"),
                rs.getInt("datepubli"),
                rs.getDouble("prix")
            );
        } else {
            throw new EmptySetException();
        }
        rs.close();
        ps.close();
        return livre;
    }

    /**
     * Renvoie un HashMap avec pour clé les iddewey et en valeur leur description a partir d'une liste de livre (Raphaël)
     * @param tabLivre
     * @return
     */
    public  HashMap<Integer, String> getClassificationAPartirHistorique(List<Livre> tabLivre)
    {
        HashMap<Integer, String> res = new HashMap<>();
        HashMap<Integer, String> tmp = new HashMap<>();

        for (Livre lvr : tabLivre)
        {
            try
            {
                tmp = getClassification(lvr);
                res.putAll(tmp);
            }
            catch(EmptySetException ese) 
            {
                System.err.println("Aucun résultat trouvé (empty set).");
            }
            catch(SQLException sql) 
            {
                System.err.println(sql.getErrorCode());
            }
        }
        return res;
    }

    /**
     * Permet d'obenir un dictionnaire contenant toute les class d'un livre (Raphaël)
     * @param livre
     * @return HashMap
     * @throws SQLException
     */
    public HashMap<Integer, String> getClassification(Livre livre) throws SQLException, EmptySetException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select iddewey, nomclass from CLASSIFICATION natural join THEMES natural join LIVRE where isbn =?");
        ps.setLong(1, livre.getISBN());
        this.st = this.connexion.createStatement();
        ResultSet rs = ps.executeQuery();
        HashMap<Integer, String> classLivre = new HashMap<>();
        if (rs.next())
        {
            classLivre.put(Integer.parseInt(rs.getString("iddewey")), rs.getString("nomclass"));
        }
        else throw new EmptySetException();
        ps.close();
        rs.close();
        return classLivre;
    }



}