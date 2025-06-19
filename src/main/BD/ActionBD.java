package main.BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.app.*;
import main.Exceptions.EmptySetException;
import main.Exceptions.PasAssezLivreException;
import main.Exceptions.PasDHistoriqueException;
import main.Exceptions.PasDeTelUtilisateurException;

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

    // ----------------- Methode en lien avec Client -----------------------------------

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

    /**
     * Permet de passer une commande 
     * @param client L'objet client destinataire la commande 
     * @param commande L'objet commande contenant le panier
     * @param mag L'objet Magasin depuis lequel la commande est passee
     * @throws SQLException
     */
    public void PasserCommande(Client client, Commande commande, Magasin mag) throws SQLException{
        PreparedStatement com = this.connexion.prepareStatement("insert into COMMANDE(numcom, datecom, enligne, livraison, idcli, idmag) values (?, ?, ?, ?, ?, ?)");
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
            PreparedStatement detailCom = this.connexion.prepareStatement("insert into DETAILCOMMANDE(numcom, numlig, qte, prixvente, isbn) VALUES (?, ?, ?, ?, ?)");    
            detailCom.setInt(1, numcom);
            detailCom.setInt(2, numligne);
            detailCom.setLong(5, l.getISBN());
            detailCom.setInt(3, panier.get(l));
            detailCom.setDouble(4, l.getPrix() * panier.get(l)); 
            numligne ++;
            detailCom.executeUpdate();
        }
        com.close();
    }

    // --------------------------------------------------------------------------------------------------------

    // ----------------------- Methode utile pour la connexion la l'application -----------------------------------------------

    /**
     * permet a partir d une adresse mail et d un mot de passe de determiner le statut/role d un user
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
                        rsA.getString("nom"),
                        rsA.getString("motDePasse"),
                        role,
                        rsA.getString("prenomad"),
                        null
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
     * Ajoute un vendeur a la BD
     * @param v l'objet Vendeur a ajouter
     * @throws SQLException
     */
    public void AddVendeur(Vendeur v) throws SQLException{
        int id = getIdUserMax() + 1;
        this.createUser(id, v.getNom(), v.getEmail(), v.getMdp(), v.getRole());
        PreparedStatement ps = this.connexion.prepareStatement("insert into VENDEUR (prenomven, magasin, idve) values (?, ?, ?)");
        ps.setString(1, v.getPrenom());
        ps.setInt(2, v.getIdMag());
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Permet a un user de changer de mot de passe (celui par defaut et pas trop secu)
     * @param user
     * @param nouveauMdp
     * @throws SQLException
     */
    public boolean changerMotDePasse(User user, String nouveauMdp) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("update USER set motDePasse = ? where idu = ?");
        ps.setString(1, nouveauMdp);
        ps.setInt(2, user.getId());
        try{ 
            ps.executeUpdate();
            ps.close();
            return true;
        } catch(SQLException e) {ps.close(); return false;}
    }

    /**
     * Permet de changer l'adresse d'un client
     * @param client Le client en question
     * @param nouvelleAdresse La nouvelle adresse
     * @param nouveauCP Le nouveau code postal
     * @param nouvelleVille La nouvelle ville
     * @return Vrai si le changement est valide
     * @throws SQLException
     */
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

    // --------------------------- Methode pour les recherche de livres ---------------------------------------------

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
    public List<Livre> chercherLivreAPartirClassification(Classification classi, Magasin mag) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join POSSEDER natural join MAGASIN natural join THEMES natural join CLASSIFICATION where iddewey = ? and idmag = ? ");
        ps.setInt(1, classi.getIddewey());
        ps.setInt(2, mag.getIdmag());
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
    public List<Livre> chercherLivreAPartiEditeur(Editeur Editeur, Magasin mag) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join POSSEDER natural join MAGASIN natural join EDITER natural join EDITEUR where idedit = ? and idmag = ?");
        ps.setInt(1, Editeur.getIdEditeur());
        ps.setInt(2, mag.getIdmag());
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
     * Permet de retrouver un auteur a partir de son nom 
     * @param nomauteur Le nom de l'auteur
     * @return La liste des auteurs correspondant approximativement au nom
     * @throws SQLException
     */
    public List<Auteur> rechercheAuteurApproximative(String nomauteur) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from AUTEUR where lower(nomauteur) like ? LIMIT 20");
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

    /**
     * Permet de renvoyer une liste de livre a partir d un nom approximatife 
     * @param nomApproximativeLivre
     * @return
     * @throws SQLException
     */
    public List<Livre> cherhcherLivreApproximative(String nomApproximativeLivre) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("SELECT isbn, titre, nbpages, datepubli, prix FROM LIVRE natural join POSSEDER  WHERE LOWER(titre) LIKE ?");
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
     * Permet de renvoyer une liste de livre a partir d un nom approximatife 
     * @param nomApproximativeLivre
     * @return
     * @throws SQLException
     */
    public List<Livre> cherhcherLivreApproximativeParMag(String nomApproximativeLivre, Magasin mag) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("SELECT isbn, titre, nbpages, datepubli, prix FROM LIVRE natural join POSSEDER natural join MAGASIN WHERE LOWER(titre) LIKE ? and idmag = ?");
        ps.setString(1, "%" + nomApproximativeLivre.toLowerCase() + "%");
        ps.setInt(2, mag.getIdmag());
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
     * Permet de rechercher un livre a partir d un nom d auteur approximatife
     * @param auteurRecherche
     * @return
     * @throws SQLException
     */
    public  List<Livre> rechercheLivreAuteur(Auteur auteurRecherche, Magasin mag) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("SELECT isbn, titre, nbpages, datepubli, prix FROM MAGASIN natural join POSSEDER natural join LIVRE natural join ECRIRE natural join AUTEUR WHERE idauteur = ? and idmag = ? ");
        ps.setString(1,  auteurRecherche.getIdAuteur());
        ps.setInt(2, mag.getIdmag());
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

    // ---------------------------- non classe pour l instant -------------------------------

    // ------------------- methode ajouter pendant la semaine IHM ---------------------------

    /**
     * Retourne la liste des vendeurs d'un magasin donné
     * @param mag Le magasin concerné
     * @return Liste des vendeurs présents dans le magasin
     * @throws SQLException
     */
    public List<Vendeur> getVendeurMagasin(Magasin mag) throws SQLException
    {
        List<Vendeur> vendeurs = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT VENDEUR.idve, email, nom, motDePasse, role, prenomven " +
            "FROM VENDEUR " +
            "JOIN USER ON idu = idve " +
            "WHERE magasin = ?"
        );
        ps.setInt(1, mag.getIdmag());
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            Vendeur v = new Vendeur(
                rs.getInt("idve"),
                rs.getString("email"),
                rs.getString("nom"),
                rs.getString("motDePasse"),
                rs.getString("role"),
                rs.getString("prenomven"),
                mag
            );
            vendeurs.add(v);
        }
        rs.close();
        ps.close();
        return vendeurs;
    }


    /**
     * Récupère la liste des auteurs d'un livre à partir de son ISBN.
     * @param isbn L'ISBN du livre
     * @return Liste des auteurs du livre
     * @throws SQLException
     */
    public List<Auteur> getAuteurParIdLivre(long isbn) throws SQLException {
        List<Auteur> auteurs = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT AUTEUR.idauteur, nomauteur, anneenais, anneedeces " +
            "FROM AUTEUR " +
            "NATURAL JOIN ECRIRE " +
            "WHERE isbn = ?"
        );
        ps.setLong(1, isbn);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            auteurs.add(new Auteur(
                rs.getString("idauteur"),
                rs.getString("nomauteur"),
                rs.getInt("anneenais"),
                rs.getInt("anneedeces")
            ));
        }
        rs.close();
        ps.close();
        return auteurs;
    }

    /**
     * Récupère la liste des classifications d'un livre à partir de son ISBN.
     * @param isbn L'ISBN du livre
     * @return Liste des classifications du livre
     * @throws SQLException
     */
    public List<Classification> getClassificationParIdLivre(long isbn) throws SQLException {
        List<Classification> classifications = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT CLASSIFICATION.iddewey, nomclass " +
            "FROM CLASSIFICATION " +
            "NATURAL JOIN THEMES " +
            "WHERE isbn = ?"
        );
        ps.setLong(1, isbn);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            classifications.add(new Classification(
                rs.getInt("iddewey"),
                rs.getString("nomclass")
            ));
        }
        rs.close();
        ps.close();
        return classifications;
    }

    /**
     * Récupère la liste des éditeurs d'un livre à partir de son ISBN.
     * @param isbn L'ISBN du livre
     * @return Liste des éditeurs du livre
     * @throws SQLException
     */
    public List<Editeur> getEditeurParIdLivre(long isbn) throws SQLException {
        List<Editeur> editeurs = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT EDITEUR.idedit, nomedit " +
            "FROM EDITEUR " +
            "NATURAL JOIN EDITER " +
            "WHERE isbn = ?"
        );
        ps.setLong(1, isbn);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            editeurs.add(new Editeur(
                rs.getInt("idedit"),
                rs.getString("nomedit")
            ));
        }
        rs.close();
        ps.close();
        return editeurs;
    }

    public Classification geClassificationParNom(String nomclass) throws SQLException
    {
        Classification classification = null;
        PreparedStatement ps = this.connexion.prepareStatement(
                "SELECT iddewey, nomclass FROM CLASSIFICATION WHERE LOWER(nomclass) LIKE ?"
        );
        ps.setString(1, "%" + nomclass.toLowerCase() + "%");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
                classification =new Classification(
                    rs.getInt("iddewey"),
                rs.getString("nomclass")
            );
    }
        rs.close();
        ps.close();
        
        return classification;
    }

    /**
     * Permet de récupérer un auteur à partir de son nom exact.
     * @param nomAuteur Le nom exact de l'auteur à rechercher.
     * @return L'objet Auteur correspondant, ou null si non trouvé.
     * @throws SQLException
     */
    public Auteur getAuteurAPartirDeNom(String nomAuteur) throws SQLException {
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT idauteur, nomauteur, anneenais, anneedeces FROM AUTEUR WHERE nomauteur = ?"
        );
        ps.setString(1, nomAuteur);
        ResultSet rs = ps.executeQuery();
        Auteur auteur = null;
        if (rs.next()) {
            auteur = new Auteur(
                rs.getString("idauteur"),
                rs.getString("nomauteur"),
                rs.getInt("anneenais"),
                rs.getInt("anneedeces")
            );
        }
        rs.close();
        ps.close();
        return auteur;
    }


    /**
     * Retourne la liste des auteurs ayant au moins un livre dans le magasin donné
     * @param magasin Le magasin concerné
     * @return Liste des auteurs présents dans le magasin
     * @throws SQLException
     */
    public List<Auteur> getAuteursDansMagasin(Magasin magasin) throws SQLException {
        List<Auteur> auteurs = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT DISTINCT AUTEUR.idauteur, nomauteur, anneenais, anneedeces " +
            "FROM MAGASIN " +
            "NATURAL JOIN POSSEDER " +
            "NATURAL JOIN LIVRE " +
            "NATURAL JOIN ECRIRE " +
            "NATURAL JOIN AUTEUR " +
            "WHERE idmag = ?"
        );
        ps.setInt(1, magasin.getIdmag());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            auteurs.add(new Auteur(
                rs.getString("idauteur"),
                rs.getString("nomauteur"),
                rs.getInt("anneenais"),
                rs.getInt("anneedeces")
            ));
        }
        rs.close();
        ps.close();
        return auteurs;
    }

    /**
     * Retourne la liste des classifications présentes dans un magasin donné
     * @param magasin Le magasin concerné
     * @return Liste des classifications présentes dans le magasin
     * @throws SQLException
     */
    public List<Classification> getClassificationsDansMagasin(Magasin magasin) throws SQLException {
        List<Classification> classifications = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT DISTINCT CLASSIFICATION.iddewey, nomclass " +
            "FROM MAGASIN " +
            "NATURAL JOIN POSSEDER " +
            "NATURAL JOIN LIVRE " +
            "NATURAL JOIN THEMES " +
            "NATURAL JOIN CLASSIFICATION " +
            "WHERE idmag = ?"
        );
        ps.setInt(1, magasin.getIdmag());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            classifications.add(new Classification(
                rs.getInt("iddewey"),
                rs.getString("nomclass")
            ));
        }
        rs.close();
        ps.close();
        return classifications;
    }

    /**
     * Retourne la liste des éditeurs présents dans un magasin donné
     * @param magasin Le magasin concerné
     * @return Liste des éditeurs présents dans le magasin
     * @throws SQLException
     */
    public List<Editeur> getEditeursDansMagasin(Magasin magasin) throws SQLException {
        List<Editeur> editeurs = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement("SELECT DISTINCT EDITEUR.idedit, nomedit FROM MAGASIN NATURAL JOIN POSSEDER NATURAL JOIN LIVRE NATURAL JOIN EDITER NATURAL JOIN EDITEUR WHERE idmag = ?");
        ps.setInt(1, magasin.getIdmag());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            editeurs.add(new Editeur(
                rs.getInt("idedit"),
                rs.getString("nomedit")
            ));
        }
        rs.close();
        ps.close();
        return editeurs;
    }

    /**
     * Permet de récupérer un éditeur à partir de son nom exact.
     * @param nomEditeur Le nom exact de l'éditeur à rechercher.
     * @return L'objet Editeur correspondant, ou null si non trouvé.
     * @throws SQLException
     */
    public Editeur getEditeurAPartirDeNom(String nomEditeur) throws SQLException {
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT idedit, nomedit FROM EDITEUR WHERE nomedit = ?"
        );
        ps.setString(1, nomEditeur);
        ResultSet rs = ps.executeQuery();
        Editeur editeur = null;
        if (rs.next()) {
            editeur = new Editeur(
                rs.getInt("idedit"),
                rs.getString("nomedit")
            );
        }
        rs.close();
        ps.close();
        return editeur;
    }


    /**
     * Récupère la liste de tous les auteurs présents dans la base de données.
     * @return Liste de tous les auteurs
     * @throws SQLException
     */
    public List<Auteur> getAllAuteur() throws SQLException {
        List<Auteur> auteurs = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT idauteur, nomauteur, anneenais, anneedeces FROM AUTEUR"
        );
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Auteur auteur = new Auteur(
                rs.getString("idauteur"),
                rs.getString("nomauteur"),
                rs.getInt("anneenais"),
                rs.getInt("anneedeces")
            );
            auteurs.add(auteur);
        }
        rs.close();
        ps.close();
        return auteurs;
    }


    // ====================================== =============================================

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
    public void AddLivre(Livre l, String ida, Integer idc, Integer ide) throws SQLException
    {
        PreparedStatement recupLiv = this.connexion.prepareStatement("select * from LIVRE where isbn = ?");
        recupLiv.setLong(1, l.getISBN());
        ResultSet rs = recupLiv.executeQuery();
        if (!rs.next())
        {
            //table livre
            PreparedStatement ps = this.connexion.prepareStatement("insert into LIVRE values (?, ?, ?, ?, ?)");
            ps.setLong(1, l.getISBN());
            ps.setString(2, l.getTitre());
            ps.setInt(3, l.getNbpages());
            ps.setInt(4, l.getDatepubli());
            ps.setDouble(5, l.getPrix());

            ps.executeUpdate();
            ps.close();

            //table classification
            ps = this.connexion.prepareStatement("insert into THEMES(isbn, iddewey) values (?, ?)");
            ps.setLong(1, l.getISBN());
            ps.setInt(2, idc);

            ps.executeUpdate();
            ps.close();

            //table Auteur
            ps = this.connexion.prepareStatement("insert into ECRIRE(isbn, idauteur) values (?, ?)");
            ps.setLong(1, l.getISBN());
            ps.setInt(2, Integer.parseInt(ida));

            ps.executeUpdate();
            ps.close();

            //table Editeur
            ps = this.connexion.prepareStatement("insert into EDITER(isbn, idedit) values (?, ?)");
            ps.setLong(1, l.getISBN());
            ps.setInt(2, ide);
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
     * Ajoute un magasin a la base de donnees
     * @param m L'objet Magasin a ajouter
     * @throws SQLException
     */
    public void AddLibrairie(Magasin m) throws SQLException{
        PreparedStatement ps = this.connexion.prepareStatement("insert into MAGASIN (idmag, nommag, villemag) values (?, ?, ?)");
        ps.setInt(1, getMaxIdMag() + 1);
        ps.setString(2, m.getNomMag());
        ps.setString(3, m.getVilleMag());
        ps.executeUpdate();
        ps.close();
    }

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
     * Recupere l'identifiant max de magasin
     * @return identifiant le plus grand de la table magasin
     * @throws SQLException
     */
    public int getMaxIdMag() throws SQLException
    {
        ResultSet rs = this.connexion.createStatement().executeQuery("select max(idmag) from MAGASIN");
        rs.next();
        int maxIdMag = rs.getInt("max(idmag)");
        rs.close();

        return maxIdMag;
    }

    /**
     * Recupere l'identifiant d'utilisateur maximal de la base de donnees.
     *
     * @return l'identifiant le plus grand de la BD.
     * @throws SQLException.
     */
    public int getIdUserMax() throws SQLException
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
    public HashMap<Integer, String> getClassificationAPartirHistorique(List<Livre> tabLivre)
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
     * Recupere un objet Magasin a partir de son nom
     * @param nomMagasin le nom du magasin
     * @return le magasin
     * @throws SQLException
     */
    public Magasin magAPartirNom(String nomMagasin) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select * from MAGASIN where nommag = ?");
        ps.setString(1, nomMagasin);
        ResultSet rs = ps.executeQuery();
        Magasin mag = null;
        if (rs.next())
        { 
            mag = new Magasin(rs.getInt("idmag"), rs.getString("nommag"), 
                        rs.getString("villemag"));
        }
        else System.out.println("ce magasin n existe pas");
        rs.close();
        ps.close();
        return mag;
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
     * renvoie la liste de tout les magasin 
     * @return
     * @throws SQLException
     */
    public List<Magasin> getAllMagasins() throws SQLException
    {
        List<Magasin> magasins = new ArrayList<>();
        ResultSet rs = this.connexion.createStatement().executeQuery("select * from MAGASIN");
        while (rs.next()) {
            magasins.add(new Magasin(
                rs.getInt("idmag"),
                rs.getString("nommag"),
                rs.getString("villemag")
            ));
        }
        rs.close();
        return magasins;
    }

    /**
     * Récupère la liste des livres disponibles dans un magasin donné.
     * @param magasin Le magasin pour lequel on veut la liste des livres disponibles
     * @return La liste des livres disponibles dans le magasin
     * @throws SQLException
     */
    public List<Livre> getLivresDispoDansMagasin(Magasin magasin) throws SQLException {
        List<Livre> livres = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement(
            "SELECT LIVRE.isbn, LIVRE.titre, LIVRE.nbpages, LIVRE.datepubli, LIVRE.prix " +
            "FROM POSSEDER " +
            "JOIN LIVRE ON POSSEDER.isbn = LIVRE.isbn " +
            "WHERE POSSEDER.idmag = ? AND POSSEDER.qte > 0"
        );
        ps.setInt(1, magasin.getIdmag());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Livre livre = new Livre(
                rs.getLong("isbn"),
                rs.getString("titre"),
                rs.getInt("nbpages"),
                rs.getInt("datepubli"),
                rs.getDouble("prix")
            );
            livres.add(livre);
        }
        rs.close();
        ps.close();
        return livres;
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

    
    /**
     * Recupere un objet magasin a partir de son ID
     * @param idmag L'id du magasin
     * @return Le magasin 
     * @throws SQLException
     */
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
     * Permet d'edition des factures d'un client
     * @param client Un client
     * @param mois Le mois d'edition des factures
     * @param annee L'annee d'edition des factures
     * @return Chaine de caractere representant les factures
     * @throws SQLException
     */
    public String factureClient(Client client, int mois, int annee) throws SQLException {
        PreparedStatement ps = this.connexion.prepareStatement(
            "select idcli, nomcli, prenomcli, adressecli, codepostal, nommag, numcom, datecom, isbn, titre, qte, prix, prix * qte as totalArticle, sum(prixvente * qte) as total " +
            "from CLIENT " +
            "natural join COMMANDE " +
            "natural join MAGASIN " +
            "natural join DETAILCOMMANDE " +
            "natural join LIVRE " +
            "where month(datecom) = ? " + 
            "and year(datecom) = ? " + 
            "and idcli = ? " + 
            "group by month(datecom), numcom, isbn"
        );

        //insertion des parametres de la requete
        ps.setInt(1, mois);
        ps.setInt(2, annee);
        ps.setInt(3, client.getId());

        //execution de la requete
        ResultSet rs = ps.executeQuery();

        //Creation de la chaine de caractere 
        String res = "Factures du " + mois + "/" + annee + "\n";
        res = res + "Edition des factures du client " + client.getId() + "\n";

        //Instanciation des parametres utiles pour la creation de la chaine de caractere
        Integer idcom = null;
        Integer nbLivres = 0;
        Double CaTotal = 0.0;
        Double CaCommande = 0.0;
        Integer numero = null;

        //parcours de touts les livres commandes
        while(rs.next())
        {

            //recuperation des colonnes de la requete
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

            //incrementation du prix total de tous les articles dans les factures
            CaTotal += totalArticle;
            
            //incrementation du nombre de livres commandes 
            nbLivres += qte;

            //creation du header de chaque facture
            if (!numcom.equals(idcom))
            {
                if (idcom != null)
                {
                    //creation du footer de chaque facture
                    res = res + " ".repeat(70) + "_".repeat(8) + " ".repeat(2) + "\n";
                    res = res + " ".repeat(68) + "Total" + " ".repeat(4) + CaCommande + "\n";
                }
                res = res + "-".repeat(80) + "\n";
                res = res + nom + " " + prenom + "\n";
                res = res + adresse + "\n";
                res = res + codePostal + "\n";
                res = res + " ".repeat(20) + "commande n°" + numcom + " du "  + datecom.toString() +"\n";
                res = res + " ".repeat(5) + "ISBN" + " ".repeat(21) + "Titre" + " ".repeat(20) + "qte" + " ".repeat(3) + "prix" + " ".repeat(3) + "total" + "\n";
                numero = 1;
                CaCommande = 0.0;
            }
            else
            {
                numero++;
            }

            // ajout de la ligne de livre
            CaCommande += totalArticle;
            idcom = numcom;
            res = res + " " + String.format("%-2s", "" + numero) + " " + String.format("%-20s", "" + isbn) + String.format("%-40s", titre) + qte + " " + prix + " ".repeat(2) + totalArticle + "\n";
            

        }

        //lignes de fin de factures
        res = res + " ".repeat(70) + "_".repeat(8) + " ".repeat(2) + "\n";
        res = res + " ".repeat(68) + "Total" + " ".repeat(4) + CaCommande + "\n";
        res = res + "-".repeat(80) + "\n";
        res = res + "Chiffre d'affaire global : " + CaTotal + "\n";
        res = res + "Nombre livres vendus : " + nbLivres + "\n";

        return res;
    }

    /**
     * Genere un chaine de caractere permettant d'afficher des factures pour un magasin
     * @param mag Un magasin
     * @param mois mois d'edition des factures
     * @param annee annee d'edition des factures
     * @return Chaine de caractere formatee pour les factures
     * @throws SQLException
     */
    public String factureMagasin(Magasin mag, int mois, int annee) throws SQLException {

        PreparedStatement ps = this.connexion.prepareStatement(
            "select idcli, nomcli, prenomcli, adressecli, codepostal, nommag, numcom, datecom, isbn, titre, qte, prix, prix * qte as totalArticle, sum(prixvente * qte) as total " +
            "from CLIENT " +
            "natural join COMMANDE " +
            "natural join MAGASIN " +
            "natural join DETAILCOMMANDE " +
            "natural join LIVRE " +
            "where month(datecom) = ? " + 
            "and year(datecom) = ? " + 
            "and idmag = ? " + 
            "group by month(datecom), numcom, isbn"
        );
        
        //insertion des parametres de la requete
        ps.setInt(1, mois);
        ps.setInt(2, annee);
        ps.setInt(3, mag.getIdmag());

        //execution
        ResultSet rs = ps.executeQuery();
        
        //creation de la chaine de caractere
        String res = "Factures du " + mois + "/" + annee + "\n";
        res = res + "Edition des factures du magasin + " + mag.getNomMag() + "\n";
        
        //Instanciation des variables utiles pour les factures
        Integer idcom = null;
        Integer nbLivres = 0;
        Double CaTotal = 0.0;
        Double CaCommande = 0.0;
        Integer numero = null;
        while(rs.next())
        {

            //recuperation des colonnes de la requete
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
            
            //mise a jour du total de prix global 
            CaTotal += totalArticle;
            //mise a jour du nombre de livres total
            nbLivres += qte;

            //header en cas de nouvelle commande
            if (!numcom.equals(idcom))
            {
                if (idcom != null)
                {
                    //a afficher pour la fin de chaque commande
                    res = res + " ".repeat(70) + "_".repeat(8) + " ".repeat(2) + "\n";
                    res = res + " ".repeat(68) + "Total" + " ".repeat(4) + CaCommande + "\n";
                }
                //afficher au debut des commandes
                res = res + "-".repeat(80) + "\n";
                res = res + nom + " " + prenom + "\n";
                res = res + adresse + "\n";
                res = res + codePostal + "\n";
                res = res + " ".repeat(20) + "commande n°" + numcom + " du "  + datecom.toString() +"\n";
                res = res + " ".repeat(5) + "ISBN" + " ".repeat(21) + "Titre" + " ".repeat(20) + "qte" + " ".repeat(3) + "prix" + " ".repeat(3) + "total" + "\n";
                numero = 1;
                CaCommande = 0.0;
            }
            else
            {
                //numero de ligne
                numero++;
            }
            CaCommande += totalArticle;
            idcom = numcom;
            //affichage des informations de la commande 
            res = res + " " + String.format("%-2s", "" + numero) + " " + String.format("%-20s", "" + isbn) + String.format("%-40s", titre) + qte + " " + prix + " ".repeat(2) + totalArticle + "\n";
            

        }
        //affichage de fin
        res = res + " ".repeat(70) + "_".repeat(8) + " ".repeat(2) + "\n";
        res = res + " ".repeat(68) + "Total" + " ".repeat(4) + CaCommande + "\n";
        res = res + "-".repeat(80) + "\n";
        res = res + "Chiffre d'affaire global : " + CaTotal + "\n";
        res = res + "Nombre livres vendus : " + nbLivres + "\n";

        return res;
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
        ResultSet rs = this.connexion.createStatement().executeQuery("select idmag, year(datecom) as annee, count(numcom) as nblivre\r\n" + //
                        "from COMMANDE natural join MAGASIN\r\n" + //
                        "group by year(datecom), nommag ;");
        HashMap<Integer,HashMap<Magasin, Integer>> res = new HashMap<>();
        while (rs.next())
        {
            int annee = rs.getInt("annee");
            Magasin mag = magAPartirId(rs.getInt("idmag"));
            int nblivre = rs.getInt("nblivre");
            if (!res.containsKey(annee))
            {
                res.put(annee, new HashMap<>());
            }
            res.get(annee).put(mag, nblivre);
        }
        rs.close();
        return res;
    }

    /**
     * Renvoie les donnees necessaire pour construire un graphique du chiffre d affaire par ans
     * HashMap<Classification, argent rapporte sur l'annee>
     * @return
     * @throws SQLException
     */
    public HashMap<Classification, Integer> chiffreAffaireParClassificationParAns(int annee) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select iddewey, nomclass, sum(prixvente*qte) as Montant \r\n" + //
                        "        from COMMANDE\r\n" + //
                        "        natural join DETAILCOMMANDE \r\n" + //
                        "        natural join LIVRE \r\n" + //
                        "        natural join THEMES \r\n" + //
                        "        natural join CLASSIFICATION\r\n" + //
                        "        where year(datecom) = ?\r\n" + //
                        "        group by floor(iddewey/100)");
        ps.setInt(1, annee);
        ResultSet rs = ps.executeQuery();
        HashMap<Classification, Integer> res = new HashMap<>();
        while (rs.next())
        {
            int iddewey = rs.getInt("iddewey");
            String nomclassi = rs.getString("nomclass");
            Classification classification = new Classification(iddewey, nomclassi);
            int montant = rs.getInt("Montant");
            if(!res.containsKey(classification))
            {
                res.put(classification, montant);
            }
        }
        rs.close();
        ps.close();
        return res;
    }

    /**
     * renvoie les donnees necessaire pour construire un graphique du chiffre d affaire des magasins par mois par ans 
     * HashMap<mois, HashMap<Magasin, chiffre d affaire>>
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, HashMap<Magasin, Integer>> CAMagasinParMoisParAnnee(int annee) throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select nommag as magasin, month(datecom) as mois,sum(prixvente * qte) as CA\r\n" + //
                        "    from MAGASIN \r\n" + //
                        "    natural join COMMANDE\r\n" + //
                        "    natural join DETAILCOMMANDE\r\n" + //
                        "    natural join LIVRE\r\n" + //
                        "    where year(datecom)=?\r\n" + //
                        "    group by nommag ,mois");
        ps.setInt(1, annee);
        ResultSet rs = ps.executeQuery();
        HashMap<Integer, HashMap<Magasin, Integer>> res = new HashMap<>();
        while(rs.next())
        {
            int mois = rs.getInt("mois");
            Magasin mag = magAPartirNom(rs.getString("magasin"));
            int ca = rs.getInt("CA");
            if (!res.containsKey(mois))
            {
                res.put(mois, new HashMap<>());
            }
            res.get(mois).put(mag, ca);
        }
        rs.close();
        ps.close();
        return res;
    }

    /**
     * renvoie les donnees necessaire pour construire un graphique du chiffre d affaire des vente en ligne ou en magasion par ans
     * HashMap<annee, HashMap<typelivraison, CA>
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, HashMap<String, Integer>> CAVenteEnLigneEnMagasinParAnnee() throws SQLException
    {
        PreparedStatement ps = this.connexion.prepareStatement("select year(datecom) as annee, enligne as typevente, sum(prixvente * qte) as CAL\r\n" + //
                        "from COMMANDE \r\n" + //
                        "natural join DETAILCOMMANDE \r\n" + //
                        "natural join LIVRE\r\n" + //
                        "group by enligne, annee;");
        
        HashMap<Integer, HashMap<String, Integer>> res = new HashMap<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int annee = rs.getInt("annee");
            String typevente = rs.getString("typevente");
            int ca = rs.getInt("CAL");
            if(!res.containsKey(annee)) res.put(annee, new HashMap<>());
            if (typevente.equals("O")) res.get(annee).put("En ligne", ca);
            else res.get(annee).put("En magasin", ca);
        }
        rs.close();
        ps.close();

        return res;
    }

    /**
     * Récupère la liste de tous les livres présents dans la base de données.
     * @return Liste de tous les livres
     * @throws SQLException
     */
    public List<Livre> getAllLivre() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        PreparedStatement ps = this.connexion.prepareStatement("SELECT isbn, titre, nbpages, datepubli, prix FROM LIVRE");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Livre livre = new Livre(
                rs.getLong("isbn"),
                rs.getString("titre"),
                rs.getInt("nbpages"),
                rs.getInt("datepubli"),
                rs.getDouble("prix")
            );
            livres.add(livre);
        }
        rs.close();
        ps.close();
        return livres;
    }

    /**
     * renvoie les donnees necessaire pour construire un graphique du nombre d auteur par editeur
     *  HashMap<Editeur, nombre auteur>
     * @return
     * @throws SQLException
     */
    public HashMap<Editeur, Integer> nombreAuteurParEditeur() throws SQLException
    {
        HashMap<Editeur, Integer> res = new HashMap<>();
        PreparedStatement ps = this.connexion.prepareStatement("select nomedit as Editeur, count(idauteur) as nbauteurs\r\n" + //
                        "from EDITEUR \r\n" + //
                        "natural join EDITER\r\n" + //
                        "natural join LIVRE\r\n" + //
                        "natural join ECRIRE\r\n" + //
                        "natural join AUTEUR\r\n" + //
                        "group by nomedit\r\n" + //
                        "order by count(idauteur) desc \r\n" + //
                        "limit 10;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) 
        {
            Editeur editeur = new Editeur(-1, rs.getString("Editeur"));
            int nbAuteurs = rs.getInt("nbauteurs");
            res.put(editeur, nbAuteurs);
        }
        rs.close();
        ps.close();
        return res;
    }

    /**
     * renvoie les donnees necessaire pour avoir l origine (ville) des clients d un auteur
     * HashMap<Ville, nombre client>
     * @return
     * @throws SQLException
     */
    public HashMap<String, Integer> nombreClientParVilleQuiOntAcheterAuteur(Auteur auteur) throws SQLException
    {
        HashMap<String, Integer> res = new HashMap<>();
        PreparedStatement ps = this.connexion.prepareStatement("select villecli as ville, count(idcli) as qte\r\n" + //
                        "from CLIENT \r\n" + //
                        "natural join COMMANDE \r\n" + //
                        "natural join DETAILCOMMANDE\r\n" + //
                        "natural join LIVRE \r\n" + //
                        "natural join ECRIRE\r\n" + //
                        "natural join AUTEUR\r\n" + //
                        "where nomauteur = ?\n" + //
                        "group by villecli;");
        ps.setString(1, auteur.getNomAuteur());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) 
        {
            String ville = rs.getString("ville");
            int qte = rs.getInt("qte");
            res.put(ville, qte);
        }
        rs.close();
        ps.close();
        return res;
    }

    /**
     * renvoie les donnees necesaire pour construire un graphique de la valeur des stock des magasion
     * HashMap<Magasin, Valeur du stock>
     * @return
     * @throws SQLException
     */
    public HashMap<Magasin, Integer> valeurStockMagasin() throws SQLException
    {
        HashMap<Magasin, Integer> res = new HashMap<>();
        PreparedStatement ps = this.connexion.prepareStatement("select nommag as magasin, sum(qte*prix) as valeurStock\r\n" + //
                        "from MAGASIN\r\n" + //
                        "natural join POSSEDER\r\n" + //
                        "natural join LIVRE\r\n" + //
                        "group by nommag;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) 
        {
            Magasin mag = magAPartirNom(rs.getString("magasin"));
            int valeurStock = rs.getInt("valeurStock");
            res.put(mag, valeurStock);
        }
        rs.close();
        ps.close();
        return res;
    }

    /**
     * revoie les donnees necessair pour construire un graphique ca client par ans
     * HashMap<Integer, HashMap<|etat, Integer>>
     *                          |max
     *                          |min
     *                          |moy
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, HashMap<String, Double>> statsCAParClientParAnnee() throws SQLException {
        HashMap<Integer, HashMap<String, Double>> res = new HashMap<>();
        PreparedStatement ps = this.connexion.prepareStatement("with CAPClient as (\r\n" + //
                        "    select year(datecom) as annee, idcli, sum(prixvente*qte) as CA\r\n" + //
                        "    from COMMANDE\r\n" + //
                        "    natural join CLIENT \r\n" + //
                        "    natural join DETAILCOMMANDE\r\n" + //
                        "    group by idcli, annee \r\n" + //
                        ")\r\n" + //
                        "select annee, min(CA), max(CA), avg(CA) from CAPClient group by annee;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) 
        {
            double max = rs.getDouble("max(CA)");
            double min = rs.getDouble("min(CA)");
            double moy = rs.getDouble("avg(CA)");
            int annee = rs.getInt("annee");
            if (!res.containsKey(annee)) res.put(annee, new HashMap<>());
            res.get(annee).put("max", max);
            res.get(annee).put("min", min);
            res.get(annee).put("avg", moy);
        }
        rs.close();
        ps.close();
        return res;
    }

    /**
     * Renvoie pour chaque année  l'auteur ayant vendu le plus de livres.
     * HashMap<Année,  HashMap<Auteur, Nombre de livres vendus>>
     * @param int anneeExlu
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, HashMap<Auteur, Integer>> auteurLePlusVenduParAnnee(int anneeExclu) throws SQLException {
        HashMap<Integer, HashMap<Auteur, Integer>> res = new HashMap<>();
        PreparedStatement ps = this.connexion.prepareStatement("with venteParAuteur as (\r\n" + //
                        "    select  idauteur,  year(datecom) as annee , sum(qte) as total\r\n" + //
                        "    from AUTEUR\r\n" + //
                        "    natural join ECRIRE\r\n" + //
                        "    natural join LIVRE\r\n" + //
                        "    natural join DETAILCOMMANDE\r\n" + //
                        "    natural join COMMANDE\r\n" + //
                        "    where year(datecom)!=?\r\n" + //
                        "    group by idauteur, annee\r\n" + //
                        "    order by total desc)\r\n" + //
                        "SELECT annee, idauteur,nomauteur , max(total) from AUTEUR\r\n" + //
                        "natural right join venteParAuteur\r\n" + //
                        "group by annee;");
        ps.setInt(1, anneeExclu);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int annee = rs.getInt("annee");
            String idauteur = rs.getString("idauteur");
            String nomauteur = rs.getString("nomauteur");
            int total = rs.getInt("max(total)");
            Auteur auteur = new Auteur(idauteur, nomauteur, 0, 0); 
            if (!res.containsKey(annee)) res.put(annee, new HashMap<>());
            res.get(annee).put(auteur, total);
        }
        rs.close();
        ps.close();
        return res;
    }

    // ----------- Fin Fonction concernant le tableau de bord Admistrateur ---------------------------------------------
}


