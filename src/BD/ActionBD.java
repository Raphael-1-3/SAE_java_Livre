package BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.*;
import Exceptions.EmptySetException;
import Exceptions.PasAssezLivreException;
import Exceptions.PasDHistoriqueException;
import Exceptions.PasDeTelUtilisateurException;

import java.util.List;

public class ActionBD{
    private ConnexionMySQL connexion;
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

    /**
     * Permet de passer une commande 
     * @param client L'objet client destinataire la commande 
     * @param commande L'objet commande contenant le panier
     * @param mag L'objet Magasin depuis lequel la commande est passee
     * @throws SQLException
     */
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
    
    /**
     * Recupere tous les livres presents dans la base de donnees
     * @return La liste des Objets livres present dans la base
     * @throws SQLException
     */
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

    /**
     * Ajoute un livre a la base de donnes si il n'existe pas
     * @param l L'objet du livre que l'on desire ajouter
     * @throws SQLException
     */
    public void AddLivre(Livre l) throws SQLException
    {
        PreparedStatement recupLiv = this.connexion.prepareStatement("select * from LIVRE where isbn = ?");
        recupLiv.setLong(1, l.getISBN());
        ResultSet rs = recupLiv.executeQuery();
        if (!rs.next())
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
        rs.close();
        recupLiv.close();
    }

    /**
     * Permet de mettre a jour le stock d'un magasin
     * @param l Le livre dont on modifie le stock 
     * @param mag L'objet magasin a modifier
     * @param nv Le nouveau stock du livre
     * @throws SQLException
     */
    public void UpdateStock(Livre l, Magasin mag, int nv) throws SQLException
    {
        PreparedStatement nbLivre = this.connexion.prepareStatement("select * from POSSEDER where idmag = ? and isbn = ?");
        nbLivre.setInt(1, mag.getIdmag());
        nbLivre.setLong(2, l.getISBN());
        ResultSet rs = nbLivre.executeQuery();
        if (rs.next())
        {
            PreparedStatement ps = this.connexion.prepareStatement("update POSSEDER set qte = ? where idmag = ? and isbn = ?");
            ps.setInt(1, nv);
            ps.setInt(2, mag.getIdmag());
            ps.setLong(3, l.getISBN());
            ps.executeUpdate();
            ps.close();
        }
        else
        {
            PreparedStatement ps = this.connexion.prepareStatement("insert into POSSEDER (idmag, isbn, qte) values (?, ?, ?)");
            ps.setInt(1, mag.getIdmag());
            ps.setLong(2, l.getISBN());
            ps.setInt(3, mag.getIdmag());
            ps.executeUpdate();
            ps.close();
        }
        nbLivre.close();
        rs.close();
    }
    
    /**
     * Permet de consulter le stock entier d'un magasin
     * @param mag L'objet magasin dont on veut consulter le stock
     * @return Le stock du magasin
     * @throws SQLException
     */
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

    /**
     * Permet d'effectuer un transfer d'un livre depuis un magasin a un autre
     * @param isbn L'identifiant du livre a tranferer
     * @param depart L'objet magasin de depart du livre
     * @param arrivee L'objet magasin d'arrivee du livre
     * @param qte La quantitee de livres a transferer
     * @throws SQLException
     * @throws PasAssezLivreException
     */
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

    /**
     * Ajoute un vendeur a la BD
     * @param v l'objet Vendeur a ajouter
     * @throws SQLException
     */
    public void AddVendeur(Vendeur v) throws SQLException{
        this.createUser(v.getId(), v.getNom(), v.getEmail(), v.getMdp(), v.getRole());
        PreparedStatement ps = this.connexion.prepareStatement("insert into VENDEUR (prenomven, magasin, idve) values (?, ?, ?)");
        ps.setString(1, v.getPrenom());
        ps.setInt(2, v.getIdMag());
        ps.setInt(3, v.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    /**
     * Ajoute un magasin a la base de donnees
     * @param m L'objet Magasin a ajouter
     * @throws SQLException
     */
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

    /**
     * Recupere l'identifiant d'utilisateur maximal de la base de donnees.
     *
     * @return l'identifiant le plus grand de la BD.
     * @throws SQLException.
     */
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
                    magAPartirId(rsV.getInt("magasin"))
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
            "select * from MAGASIN natural join POSSEDER natural join LIVRE where isbn = ?"
        );
        ps.setLong(1, livre.getISBN());
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

    /**
     * Permet de recuperer une liste de clients a partir d'un nom et d'un prenom
     * @param nom Nom du client
     * @param prenom Prenom du client
     * @return Liste de clients correspondant a ces caracteristiques
     * @throws SQLException
     */
    public List<Client> getClientNonPrenom(String nom, String prenom) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from CLIENT join USER on idu = idcli where LOWER(nomcli) LIKE ? and LOWER(prenomcli) LIKE ?");
        ps.setString(1, nom);
        ps.setString(2, prenom);
        ResultSet rs = ps.executeQuery();
        List<Client> liste = new ArrayList<>();
        while(rs.next())
        {
            int id = rs.getInt("idcli");
            String email = rs.getString("email");
            String nomC = rs.getString("nomcli");
            String prenomC = rs.getString("prenomcli");
            String mdp = rs.getString("motDePasse");
            String role = rs.getString("role");
            int codePostal = rs.getInt("codepostal");
            String ville = rs.getString("villecli");
            String adresse = rs.getString("adressecli");
            Client c = new Client(id, email, nomC, prenomC, mdp, role, codePostal, ville, adresse);
            liste.add(c);
        }
        return liste;

    }

    /**
     * Recupere le client grace a son Id
     * @param id l'identifiant unique du client
     * @return L'object correpondant a l'identifiant
     * @throws SQLException
     */
    public Client getClientParId(int id) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from CLIENT join USER on idu = idcli where idcli = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int idc = rs.getInt("idcli");
        String email = rs.getString("email");
        String nomC = rs.getString("nomcli");
        String prenomC = rs.getString("prenomcli");
        String mdp = rs.getString("motDePasse");
        String role = rs.getString("role");
        int codePostal = rs.getInt("codepostal");
        String ville = rs.getString("villecli");
        String adresse = rs.getString("adressecli");
        Client c = new Client(idc, email, nomC, prenomC, mdp, role, codePostal, ville, adresse);
        return c;
    }

    /**
     * Permet de rechercher un livre a partir d un nom d auteur approximatife
     * @param auteurRecherche
     * @return
     * @throws SQLException
     */
    public  List<Livre> rechercheLivreAuteur(Auteur auteurRecherche) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("SELECT isbn, titre, nbpages, datepubli, prix FROM LIVRE natural join ECRIRE natural join AUTEUR WHERE idauteur = ?");
        ps.setString(1,  auteurRecherche.getIdAuteur());
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
     * Permet de recuperer la date actuelle 
     * @return Date
     * @throws SQLException
     */
    public Date getCurrentDate() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select CURDATE()");
        rs.next();
        return rs.getDate(1);
    }

    /**
     * Permet de recuperer un objet Livre a partir d'un isbn
     * @param isbn l'isbn du livre a recuperer 
     * @return 
     * @throws SQLException
     */
    public Livre getLivreParId(long isbn) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from LIVRE where isbn = ?");
        ps.setLong(1, isbn);
        ResultSet rs = ps.executeQuery();
        Livre livre = null;
        if (rs.next())
        {   livre = new Livre(
                rs.getLong("isbn"),
                rs.getString("titre"),
                rs.getInt("nbpages"),
                rs.getInt("datepubli"),
                rs.getDouble("prix")
            );
        }
            return livre;
    }

    /**
     * Recupere le plus grand isbn de la BD
     * @return un isbn
     * @throws SQLException
     */
    public Long getMaxISBN() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select max(isbn) from LIVRE");
        rs.next();
        return rs.getLong(1);
    }

    public Magasin getMagasinParId(Integer idmag) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from MAGASIN where idmag = ?");
        ps.setInt(1, idmag);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String nom = rs.getString("nommag");
        String ville = rs.getString("villemag");
        Magasin m = new Magasin(idmag, nom, ville);
        return m;
    }

    /**
     * Permet a un client de changer de mot de passe (celui par defaut et pas trop secu)
     * @param client
     * @param nouveauMdp
     * @throws SQLException
     */
    public boolean changerMotDePasse(Client client, String nouveauMdp) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("update USER set motDePasse = ? where idu = ?");
        ps.setString(1, nouveauMdp);
        ps.setInt(2, client.getId());
        try{ 
            ps.executeUpdate();
            ps.close();
            return true;
        } catch(SQLException e) {ps.close(); return false;}
    }

    public boolean changerAdresse(Client client, String nouvelleAdresse, int nouveauCP, String nouvelleVille) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("update CLIENT set adressecli = ?, codepostal = ?, villecli = ? where idcli = ?");
        ps.setString(1, nouvelleAdresse);
        ps.setInt(2, nouveauCP);
        ps.setString(3, nouvelleVille);
        ps.setInt(4, client.getId());
        try {
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            ps.close();
            return false;
        }
    }

    public Magasin magAPartirId(Integer idmag) throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select * from MAGASIN where idmag = "+idmag);
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
     * Génère la facture détaillée d'un client pour un mois et une année donnés.
     */
    public String factureClient(Client client, int mois, int annee) throws SQLException {
        StringBuilder res = new StringBuilder();
        res.append("Facture de ").append(client.getPrenom()).append(" ").append(client.getNom()).append(" - ").append(client.getAdresseCli()).append(", ").append(client.getCodePostal()).append(" ").append(client.getVilleCli()).append("\n");
        res.append("Période : ").append(mois).append("/").append(annee).append("\n");
        res.append("--------------------------------------------------------------------------------\n");

        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT c.numcom, c.datecom, l.isbn, l.titre, dc.qte, l.prix, dc.total " +
            "FROM COMMANDE c " +
            "JOIN DETAILCOMMANDE dc ON c.numcom = dc.numcom " +
            "JOIN LIVRE l ON dc.isbn = l.isbn " +
            "WHERE c.idcli = ? AND MONTH(c.datecom) = ? AND YEAR(c.datecom) = ? " +
            "ORDER BY c.numcom, l.titre"
        );
        ps.setInt(1, client.getId());
        ps.setInt(2, mois);
        ps.setInt(3, annee);
        ResultSet rs = ps.executeQuery();

        int lastNumCom = -1;
        double totalCommande = 0;
        double totalGlobal = 0;
        while (rs.next()) {
            int numcom = rs.getInt("numcom");
            if (numcom != lastNumCom) {
                if (lastNumCom != -1) {
                    res.append("Total commande : ").append(String.format("%.2f", totalCommande)).append(" €\n");
                    res.append("--------------------------------------------------------------------------------\n");
                }
                totalCommande = 0;
                res.append("Commande n°").append(numcom).append(" du ").append(rs.getDate("datecom")).append("\n");
                res.append("ISBN         Titre                               Qte   Prix   Total\n");
            }
            double totalArticle = rs.getDouble("total");
            res.append(String.format("%-12s%-35s%-6d%-7.2f%-7.2f\n",
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getInt("qte"),
                rs.getDouble("prix"),
                totalArticle
            ));
            totalCommande += totalArticle;
            totalGlobal += totalArticle;
            lastNumCom = numcom;
        }
        if (lastNumCom != -1) {
            res.append("Total commande : ").append(String.format("%.2f", totalCommande)).append(" €\n");
            res.append("--------------------------------------------------------------------------------\n");
        }
        res.append("Total à payer : ").append(String.format("%.2f", totalGlobal)).append(" €\n");
        rs.close();
        ps.close();
        return res.toString();
    }

    /**
     * Génère la facture globale d'un magasin pour un mois et une année donnés.
     */
    public String factureMagasin(Magasin mag, int mois, int annee) throws SQLException {

        PreparedStatement ps = this.connexion.prepareStatement(
            "select idcli, nomcli, prenomcli, adressecli, codepostal, nommag, numcom, datecom, isbn, titre, qte, prix, prix * qte as totalArticle, sum(prixvente * qte) as total " +
            "from CLIENT " +
            "natural join COMMANDE " +
            "natural join MAGASIN " +
            "natural join DETAILCOMMANDE " +
            "natural join LIVRE " +
            "where month(datecom) = ?" + 
            "and year(datecom) = ?" + 
            "and idmag = ?" + 
            "group by month(datecom), numcom, isbn"
        );
        
        ps.setInt(1, mois);
        ps.setInt(2, annee);
        ps.setInt(3, mag.getIdmag());
        ResultSet rs = ps.executeQuery();

        String res = "Factures du " + mois + "/" + annee + "\n";
        res = res + "Edition des factures du magasin + " + mag.getNomMag() + "\n";
        Integer idcom = null;
        Integer nbLivres = 0;
        Double CaTotal = 0.0;
        Integer numero = null;
        while(rs.next())
        {
            String nom = rs.getString("nomcli");
            String prenom = rs.getString("prenomcli");
            String adresse = rs.getString("adressecli");
            Integer codePostal = rs.getInt("codepostal");
            String nommag = rs.getString("nommag");
            Integer numcom = rs.getInt("numcom");
            Date datecom = rs.getDate("datecom");
            Long isbn = rs.getLong("isbn");
            String titre = rs.getString("titre");
            Integer qte = rs.getInt("qte");
            Double prix = rs.getDouble("prix");
            Double totalArticle = rs.getDouble("totalArticle");
            Double totalCom = rs.getDouble("total");
            CaTotal += totalArticle;
            nbLivres += qte;
            if (idcom != numcom)
            {
                if (idcom != null)
                {
                    res = res + " ".repeat(70) + "_".repeat(8) + " ".repeat(2) + "\n";
                    res = res + " ".repeat(68) + "Total" + " ".repeat(4) + totalCom + "\n";
                }
                res = res + "-".repeat(80) + "\n";
                res = res + nom + " " + prenom + "\n";
                res = res + adresse + "\n";
                res = res + codePostal + "\n";
                res = res + " ".repeat(20) + "commande n°" + numcom + " du "  + datecom.toString() +"\n";
                res = res + " ".repeat(5) + "ISBN" + " ".repeat(21) + "Titre" + " ".repeat(20) + "qte" + " ".repeat(3) + "prix" + " ".repeat(3) + "total" + "\n";
                numero = 1;
            }
            else
            {
                numero++;
            }
            res = res + " " + String.format("%-2s", "" + numero) + " " + String.format("%-40s", titre) + qte + " " + prix + " ".repeat(2) + totalArticle + "\n";
            if (idcom != numcom)
            {
               

            }

        }
        res = res + "-".repeat(80) + "\n";
        res = res + "Chiffre d'affaire global : " + CaTotal + "\n";
        res = res + "Nombre livres vendus : " + nbLivres + "\n";

        return res;
    }

    /**
     * Recherche les classifications dont le nom est approché du paramètre donné.
     * @param nomClass Le nom (ou partie du nom) de la classification à rechercher.
     * @return Une liste de chaînes représentant les classifications trouvées.
     * @throws SQLException
     */
    public List<Classification> cherhcherClassificationApproximative(String nomClass) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from CLASSIFICATION where lower(nomclass) like ?");
        ps.setString(1,"%" +nomClass+"%");
        ResultSet rs = ps.executeQuery();
        List<Classification> tabclass = new ArrayList<>();
        while (rs.next())
        {
            tabclass.add(new Classification(rs.getInt("iddewey"), rs.getString("nomclass")));
        }
        return tabclass;
    }

    /**
     * Recherche les livres associés à une classification donnée par son nom.
     * @param nomClass Le nom de la classification.
     * @return Une liste de livres correspondant à la classification.
     * @throws SQLException
     */
    public List<Livre> chercherLivreAPartirClassification(Classification classi) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join THEMES natural join CLASSIFICATION where iddewey = ?");
        ps.setInt(1, classi.getIddewey());
        ResultSet rs = ps.executeQuery();
        List<Livre> tabLivre = new ArrayList<>();
        while (rs.next())
        {
            tabLivre.add(new Livre(
            rs.getLong("isbn"),
            rs.getString("titre"),
            rs.getInt("nbpages"),
            rs.getInt("datepubli"),
            rs.getDouble("prix")
            ));
        }
        return tabLivre;
    }

    /**
     * Recherche les éditeurs dont le nom est approché du paramètre donné.
     * @param nomEditeur Le nom (ou partie du nom) de l'éditeur à rechercher.
     * @return Une liste de chaînes représentant les éditeurs trouvés.
     * @throws SQLException
     */
    public List<Editeur> cherhcherEditeurApproximative(String nomEditeur) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from EDITEUR where lower(nomedit) like ?");
        ps.setString(1, "%"+nomEditeur+"%");
        ResultSet rs = ps.executeQuery();
        List<Editeur> tabediteur = new ArrayList<>();
        while (rs.next())
        {
            tabediteur.add(new Editeur(rs.getInt("idedit"), rs.getString("nomedit")));
        }
        return tabediteur;
    }

    /**
     * Recherche les livres associés à un éditeur donné par son nom.
     * @param nomEditeur Le nom de l'éditeur.
     * @return Une liste de livres correspondant à l'éditeur.
     * @throws SQLException
     */
    public List<Livre> chercherLivreAPartiEditeur(Editeur Editeur) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join EDITER natural join EDITEUR where idedit = ?");
        ps.setInt(1, Editeur.getIdEditeur());
        ResultSet rs = ps.executeQuery();
        List<Livre> tabLivre = new ArrayList<>();
        while (rs.next())
        {
            tabLivre.add(new Livre(
            rs.getLong("isbn"),
            rs.getString("titre"),
            rs.getInt("nbpages"),
            rs.getInt("datepubli"),
            rs.getDouble("prix")
            ));
        }
        return tabLivre;
    }


    public List<Auteur> rechercheAuteurApproximative(String nomauteur) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from AUTEUR where lower(nomauteur) like ?");
        ps.setString(1, "%" +nomauteur+ "%");
        ResultSet rs = ps.executeQuery();
        List<Auteur> tabauteur = new ArrayList<>();
        while (rs.next())
        {
            tabauteur.add(new Auteur(
                rs.getString("idauteur"),
                rs.getString("nomauteur"),
                rs.getInt("anneenais"),
                rs.getInt("anneedeces")
            ));
        }
        return tabauteur;
    }

    // ----------- Fonction concernant le tableau de bord Admistrateur ---------------------------------------------

    /**
     * Renvoie les donnees necessaire pour construire un graphique pour le nombre de livre par magasion par ans
     *  HashMap<annee,HashMap<Magasin, nombre de livre vendu sur l'annee>
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, HashMap<Magasin, Integer>> NombreDeLivreVendueParMagasinParAns() throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select nommag, year(datecom) as annee, count(numcom) as nblivre\r\n" + //
                        "from COMMANDE natural join MAGASIN\r\n" + //
                        "group by nommag, year(datecom);");
        
        return new HashMap<Integer,HashMap<Magasin, Integer>>();
    }

    /**
     * Renvoie les donnees necessaire pour construire un graphique du chiffre d affaire par ans
     * HashMap<Classification, argent rapporte sur l'annee>
     * @return
     * @throws SQLException
     */
    public HashMap<Classification, Integer> chiffreAffaireParClassificationParAns(int annee) throws SQLException
    {
        return new HashMap<Classification, Integer>();
    }

    /**
     * renvoie les donnees necessaire pour construire un graphique du chiffre d affaire des magasins par mois par ans 
     * HashMap<mois, HashMap<Magasin, chiffre d affaire>>
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, HashMap<Magasin, Integer>> CAMagasinParMoisParAnnee(int annee) throws SQLException
    {
        return new HashMap<Integer, HashMap<Magasin, Integer>>();
    }

    /**
     * renvoie les donnees necessaire pour construire un graphique du chiffre d affaire des vente en ligne ou en magasion par ans
     * HashMap<typelivraison, CA>
     * @return
     * @throws SQLException
     */
    public HashMap<String, Integer> CAVenteEnLigneEnMagasinParAnnee(int annee) throws SQLException
    {
        return new HashMap<String, Integer>();
    }

    /**
     * renvoie les donnees necessaire pour construire un graphique du nombre d auteur par editeur
     *  HashMap<Editeur, nombre auteur>
     * @return
     * @throws SQLException
     */
    public HashMap<Editeur, Integer> nombreAuteurParEditeur() throws SQLException
    {
        return new HashMap<Editeur, Integer>();
    }

    /**
     * renvoie les donnees necessaire pour avoir l origine (ville) des clients d un auteur
     * HashMap<Ville, nombre client>
     * @return
     * @throws SQLException
     */
    public HashMap<String, Integer> nombreClientParVilleQuiOntAcheterAuteur(Auteur auteur) throws SQLException
    {
        return new HashMap<String, Integer>();
    }

    /**
     * renvoie les donnees necesaire pour construire un graphique de la valeur des stock des magasion
     * HashMap<Magasin, Valeur du stock>
     * @return
     * @throws SQLException
     */
    public HashMap<Magasin, Integer> valeurStockMagasin() throws SQLException
    {
        return new HashMap<Magasin, Integer>();
    }

    /**
     * revoie les donnees necessair pour construire un graphique ca client par ans
     * HashMap<Client, CA>
     * @param annee
     * @return
     * @throws SQLException
     */
    public HashMap<Client, Integer> CAParClientParAns(int annee) throws SQLException
    {
        return new HashMap<Client, Integer>();
    }

    // ----------- Fin Fonction concernant le tableau de bord Admistrateur ---------------------------------------------
}


