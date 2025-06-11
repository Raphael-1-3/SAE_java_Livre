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

    /**
     * renvoir la connexion, utile pour les tests dans test.testBD
     * @return
     */
    public ConnexionMySQL getConnexion() {
        return this.connexion;
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
    public void Transfer (long isbn, Magasin depart, Magasin arrivee, int qte) throws SQLException, PasAssezLivreException{
        PreparedStatement nbLivreDep = this.connexion.prepareStatement("select qte from POSSEDER where idmag = ? and isbn = ?");
        nbLivreDep.setInt(1, depart.getIdmag());
        nbLivreDep.setLong(2, isbn);
        ResultSet nbLivre = nbLivreDep.executeQuery();
        if (!nbLivre.next())
        {
            throw new PasAssezLivreException();
        }
        int qteDep = nbLivre.getInt("qte");
        if (qteDep < qte)
        {
            throw new PasAssezLivreException();
        }
        else
        {
            nbLivreDep.close();
            nbLivre.close();
            qteDep -= qte;
            PreparedStatement RetirerLivreDep = this.connexion.prepareStatement("update POSSEDER set qte = ? where idmag = ? and isbn = ?");
            RetirerLivreDep.setInt(1, qteDep);
            RetirerLivreDep.setInt(2, depart.getIdmag());
            RetirerLivreDep.setLong(3, isbn);
            RetirerLivreDep.executeUpdate();
            RetirerLivreDep.close();

            PreparedStatement recupQteArrivee = this.connexion.prepareStatement("select qte from POSSEDER where isbn = ? and idmag = ?");
            recupQteArrivee.setLong(1, isbn);
            recupQteArrivee.setInt(2, arrivee.getIdmag());
            ResultSet rs = recupQteArrivee.executeQuery();
            rs.next();
            int qteArriv = rs.getInt("qte");
            recupQteArrivee.close();
            rs.close();
            
            PreparedStatement ajouterArrivee = this.connexion.prepareStatement("update POSSEDER set qte = ? where idmag = ? and isbn = ?");
            ajouterArrivee.setInt(1, qteArriv);
            ajouterArrivee.setInt(2, arrivee.getIdmag());
            ajouterArrivee.setLong(3, isbn);
            ajouterArrivee.executeUpdate();
            ajouterArrivee.close();
        }

    }
    public void AddVendeur(Vendeur v) throws SQLException{
        this.createUser(v.getId(), v.getNom(), v.getEmail(), v.getMdp(), v.getRole());
        PreparedStatement ps = this.connexion.prepareStatement("insert into VENDEUR (prenomven, magasin, idve) values (?, ?, ?)");
        ps.setString(1, v.getPrenom());
        ps.setInt(2, v.getIdMag());
        ps.setInt(3, v.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void AddLibrairie(Magasin m) throws SQLException{
        PreparedStatement ps = this.connexion.prepareStatement("insert into MAGASIN (idmag, nommag, villemag) values (?, ?, ?)");
        ps.setInt(1, m.getIdmag());
        ps.setString(2, m.getNomMag());
        ps.setString(3, m.getVilleMag());
        ps.executeUpdate();
        ps.close();
    }
    public static void InfosTableauBord(){}

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

    public  int getIdUserMax() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select max(idu) from USER");
        rs.next();
        int idmax = rs.getInt("max(idu)");
        return idmax;
    }

    /**
     * Permet de creer un object Livre a partir d'un titre de livre présent dans la base (Raphaël)
     * @param titreLivre
     * @return
     * @throws SQLException
     */
    public Livre getLivreParTitre(String titreLivre) throws SQLException, EmptySetException
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

    /**
     * Permet de créer une Liste de livre a partir d'un iddewey (une classification)
     * @param iddewey
     * @return Liste de livre
     * @throws SQLException
     * @throws EmptySetException
     */
    public List<Livre> getLivreParIddewey(int iddewey) throws SQLException, EmptySetException
    {
        PreparedStatement ps = this.connexion.prepareStatement(
            "select titre, iddewey FROM CLASSIFICATION NATURAL JOIN THEMES NATURAL JOIN LIVRE WHERE iddewey = ?");
        ps.setInt(1, iddewey);
        ResultSet rs = ps.executeQuery();
        List<Livre> res = new ArrayList<>();
        while (rs.next())
        {
            res.add(getLivreParTitre(rs.getString("titre")));
        }
        if (res.isEmpty()) throw new EmptySetException();
        ps.close();
        rs.close();
        return res;
    }

    /**
     * Renvoie une liste de recommendation pour un client en fonction de ses achats passé
     * @return
     */
    public List<Livre> onVousRecommande(Client client) throws SQLException, PasDHistoriqueException
    {
        List<Livre> recommandationTmp = new ArrayList<>();
        List<Livre> historiqueClient = getHistoriqueClient(client).get(client);
        HashMap<Client, List<Livre>> historiqueAllClient = getHistoriqueAllClient();
        historiqueAllClient.remove(client);
        Double maxPourcentage = null;
        Double pourcentageCurrent = null;
        for (Map.Entry<Client, List<Livre>> entry : historiqueAllClient.entrySet()) 
        {
            List<Livre> current = entry.getValue();
            pourcentageCurrent = ressemblanceHistorique(historiqueClient, current);
            if (maxPourcentage == null || pourcentageCurrent > maxPourcentage)
            {
                maxPourcentage = pourcentageCurrent;
                recommandationTmp = current;
            }
        }
        
        List<Livre> recommandation = new ArrayList<>(); // retire tout les livre que le client a déjà acheté
        for (Livre livre : recommandationTmp)
        {
            if (!historiqueClient.contains(livre)) recommandation.add(livre);
        }
        return recommandation;
    }

    /**
     * renvoie un pourcentage de ressemblance entre deux liste de livre, dans notre cas des historique d'achat de client
     * @param historiqueClient1
     * @param historiqueClient2
     * @return
     */
    public double ressemblanceHistorique(List<Livre> historiqueClient1, List<Livre> historiqueClient2)
    {
        double tailleMoyenneListe = (historiqueClient1.size() + historiqueClient2.size()) / 2;
        List<Livre> tabLivreIdentique = new ArrayList<>();
        for (Livre livre : historiqueClient2)
        {
            if (historiqueClient1.contains(livre)) tabLivreIdentique.add(livre);
        }
        double pourcentageRessemblance = (tabLivreIdentique.size() / tailleMoyenneListe) * 100;
        return pourcentageRessemblance;
    }

    /**
     * Permet d'obtenir l'historique d'achat d'un client a partir d'un client
     * @param client
     * @return
     * @throws SQLException
     * @throws PasDHistoriqueException
     */
    public HashMap<Client, List<Livre>> getHistoriqueClient(Client client) throws SQLException, PasDHistoriqueException
    {
        PreparedStatement ps = this.connexion.prepareStatement(
            "select idcli,  numcom, titre from CLIENT natural left join COMMANDE natural left join DETAILCOMMANDE natural left join LIVRE where idcli = ? group by titre order by numcom;");
        ps.setInt(1, client.getId());
        ResultSet rs = ps.executeQuery();
        HashMap<Client,List<Livre>> historique = new HashMap<>();
        historique.put(client, new ArrayList<>());
        while (rs.next())
        {
            try
            {
            historique.get(client).add(getLivreParTitre(rs.getString("titre")));
            }
            catch (EmptySetException ese)
            {}
        }
        if (historique.get(client).isEmpty()) throw new PasDHistoriqueException();
        ps.close();
        rs.close();
        return historique;
    }

    /**
     * renvoie un dictionnaire contenant en clé tout les client et en valeur une liste de livre représentant leur achat passé
     * @param client
     * @return
     * @throws SQLException
     * @throws PasDHistoriqueException
     */
    public HashMap<Client, List<Livre>> getHistoriqueAllClient() throws SQLException, PasDHistoriqueException
    {
        HashMap<Client, List<Livre>> AllHistorique = new HashMap<>();
        List<Client> AllClient = getAllClients();
        
        for (Client client : AllClient)
        {
            try 
            {
                AllHistorique.putAll(getHistoriqueClient(client));
            }    
            catch (PasDHistoriqueException e)  
            {}
            // On ignore ce client et on continue
        }
        return AllHistorique;
    }

    /**
     * Permet de charger un client depuis le base de donnée a partir des son nom prénom et codepostal
     * @param nom
     * @param prenom
     * @param codepaostal
     * @return
     * @throws SQLException
     * @throws PasDeTelUtilisateurException
     */
    public Client getClientAPartirNomPrenomCodePostal(String nom, String prenom, int codepaostal) throws SQLException, PasDeTelUtilisateurException
    {
        PreparedStatement ps = this.connexion.prepareStatement(
            "select * FROM CLIENT join USER ON idu = idcli WHERE nomcli = ? and prenomcli = ? and codepostal = ?");
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ps.setInt(3, codepaostal);
        ResultSet rs = ps.executeQuery();
        Client client = null;
        if (rs.next())
        {
            client = new Client(
                rs.getInt("idcli"),
                rs.getString("email"),
                rs.getString("nomcli"),
                rs.getString("prenomcli"),
                rs.getString("motDePasse"),
                rs.getString("role"),
                rs.getInt("codepostal"),
                rs.getString("villecli"),
                rs.getString("adressecli")
            );
        }
        else throw new PasDeTelUtilisateurException();
        return client;
    }

    /**
     * Charge la liste de tout les clients
     * @return
     * @throws SQLException
     */
    public List<Client> getAllClients() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select * from CLIENT join USER on idu = idcli");
        List<Client> tabClient = new ArrayList<>();
        while (rs.next())
        {
            tabClient.add(new Client(
                rs.getInt("idcli"),
                rs.getString("email"),
                rs.getString("nomcli"),
                rs.getString("prenomcli"),
                rs.getString("motDePasse"),
                rs.getString("role"),
                rs.getInt("codepostal"),
                rs.getString("villecli"),
                rs.getString("adressecli")
            ));
        }
        rs.close();
        return tabClient;
    }

    public Magasin magAPartirNom(String nomMagasin) throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select * from MAGASIN where nommag = "+nomMagasin);
        Magasin mag = null;
        if (rs.next())
        { 
            mag = new Magasin(rs.getInt("idmag"), rs.getString("nommag"), 
                                    rs.getString("villemag"));
        }
        else System.out.println("ce magasin n existe pas");
        rs.close();
        return mag;
    }

    /**
     * permet a partir d une adresse mail est d un mot de passe le statut/role d un user
     * @return
     */
    public User connexionRole(String email, String mdp) throws SQLException, PasDeTelUtilisateurException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select idu, role from USER where email = ? and motDePasse = ?");
        ps.setString(1, email);
        ps.setString(2, mdp);
        ResultSet rs = ps.executeQuery();
        Integer id = null;
        String role = null;
        User user =  null;
        if (rs.next())
        { 
            id = rs.getInt(1);
            role = rs.getString(2);
        }
        else throw new PasDeTelUtilisateurException();

        switch (role) 
        {
            case "CLIENT":
                ResultSet rsC = this.connexion.createStatement().executeQuery(
                    "select * from CLIENT join USER on idu = idcli where idcli =" + id);
                if (rsC.next())
                {
                    user = new Client(
                        rsC.getInt("idcli"),
                        rsC.getString("email"),
                        rsC.getString("nomcli"),
                        rsC.getString("prenomcli"),
                        rsC.getString("motDePasse"),
                        rsC.getString("role"),
                        rsC.getInt("codepostal"),
                        rsC.getString("villecli"),
                        rsC.getString("adressecli")
                    );
                }
                rsC.close();
                break;
        
            case "ADMIN":
                ResultSet rsA = this.connexion.createStatement().executeQuery(
                    "select * from USER join ADMIN on idu = idad where idad =" + id);
                if (rsA.next())
                {
                    user = new Administrateur(
                        rsA.getInt("idad"),
                        rsA.getString("email"),
                        rsA.getString("nomad"),
                        rsA.getString("motDePasse"),
                        role,
                        rsA.getString("prenomad"),
                        magAPartirNom(rsA.getString("magasin"))
                    );
                }
                rsA.close();
                break;
            case "VENDEUR":
                ResultSet rsV = this.connexion.createStatement().executeQuery("select * from USER join VENDEUR on idu = idve  where idve ="+id);
                if (rsV.next())
                {
                user = new Vendeur(
                    rsV.getInt("idve"),
                    rsV.getString("email"),
                    rsV.getString("nom"),
                    rsV.getString("motDePasse"),
                    role,
                    rsV.getString("prenomven"),
                    magAPartirNom(rsV.getString("magasin"))
                );
                }
                break;
            default:
                System.out.println("role inconnu.");
                break;
        }
        return user;
    }


    /**
     * Crée un nouvel utilisateur dans la table USER.
     * @param nom le nom de l'utilisateur
     * @param email l'email de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     * @param role le rôle de l'utilisateur (ex: "CLIENT", "ADMIN", "VENDEUR")
     * @return true si l'utilisateur a été créé avec succès, false sinon
     * @throws SQLException
     */
    private boolean createUser(int idu, String nom, String email, String motDePasse, String role) throws SQLException {
        PreparedStatement ps = this.connexion.prepareStatement(
            "INSERT INTO USER (idu, nom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?)"
        );
        ps.setInt(1, idu);
        ps.setString(2, nom);
        ps.setString(3, email);
        ps.setString(4, motDePasse);
        ps.setString(5, role);
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    /**
     * Creer un nouveau client et l ajoute a la base de donnee
     * @param nom
     * @param prenom
     * @param codePostal
     * @param villeCli
     * @param adresseCli
     * @param email
     * @param mdp
     * @return
     * @throws SQLException
     */
    public boolean creerClient(String nom, String prenom, int codePostal, String villeCli, String adresseCli, String email, String mdp) throws SQLException
    {
        int idu = getIdUserMax() + 1;
        boolean userCreated = createUser(idu, nom, email, mdp, "CLIENT");
        if (!userCreated) {
            return false;
        }
        PreparedStatement ps = this.connexion.prepareStatement(
            "INSERT INTO CLIENT (idcli, nomcli, prenomcli, adressecli, codepostal, villecli) VALUES (?, ?, ?, ?, ?, ?)"
            
        );
        ps.setInt(1, idu);
        ps.setString(2, nom);
        ps.setString(3, prenom);
        ps.setString(4, adresseCli);
        ps.setInt(5, codePostal);
        ps.setString(6, villeCli);
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    /**
     * Permet de renvoyer une liste de livre a partir d un nom approximatife 
     * @param nomApproximativeLivre
     * @return
     * @throws SQLException
     */
    public List<Livre> cherhcherLivreApproximative(String nomApproximativeLivre) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("SELECT * FROM LIVRE WHERE LOWER(titre) LIKE ?");
        ps.setString(1, "%" + nomApproximativeLivre.toLowerCase() + "%");
        ResultSet rs = ps.executeQuery();
        List<Livre> livres = new ArrayList<>();
        while (rs.next()) {
            Livre l = new Livre(
            rs.getLong("isbn"),
            rs.getString("titre"),
            rs.getInt("nbpages"),
            rs.getInt("datepubli"),
            rs.getDouble("prix")
            );
            livres.add(l);
        }
        rs.close();
        ps.close();
        return livres;
    }

    /**
     * Permet d obtenir la liste de tout les magasin
     * @return magasin une liste de magasin
     * @throws SQLException
     */
    public List<Magasin> getListMagasin() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select * from MAGASIN");
        List<Magasin> magasin = new ArrayList<>();
        while (rs.next())
        {
            magasin.add(new Magasin(
                rs.getInt("idmag"),
                rs.getString("nommag"),
                rs.getString("villemag")
            ));
        }
        rs.close();
        return magasin;
    }

    /**
     * Permert d obtenir la liste des magasins ou un livre un particulier est disponible
     * @param livre
     * @return
     * @throws SQLException
     */
    public List<Magasin> getMagasinOuLivreDispo(Livre livre) throws SQLException
    {
        List<Magasin> tabMag = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "select * from MAGASIN natural join POSSEDER natural join LIVRE where titre = ?"
        );
        ps.setString(1, livre.getTitre());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tabMag.add(new Magasin(
                rs.getInt("idmag"),
                rs.getString("nommag"),
                rs.getString("villemag")
            ));
        }
        rs.close();
        ps.close();
        return tabMag;
    }


}