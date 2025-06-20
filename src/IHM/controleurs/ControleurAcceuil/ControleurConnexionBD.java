package IHM.controleurs.ControleurAcceuil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import IHM.vues.LivreExpress;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.BD.ActionBD;
import main.BD.ConnexionMySQL;

public class ControleurConnexionBD implements EventHandler<ActionEvent>{
    private LivreExpress app;
    public ControleurConnexionBD(LivreExpress app)
    {
        this.app = app;
    }


    public void handle(ActionEvent event)
    {
        try {
            ConnexionMySQL connexion = new ConnexionMySQL();
            //connexion.connecter("laptop-robin", "LibrairieJava", "root", "robin");
            String serv = this.app.getVueConnexionBD().getTfServ().getText();
            String user = this.app.getVueConnexionBD().getTfUser().getText();
            String passwd = this.app.getVueConnexionBD().getPwUser().getText();
            System.out.println("Serveur: " + serv);
            System.out.println("Utilisateur: " + user);
            System.out.println("Mot de passe: " + passwd);
            connexion.connecter(serv, "LibrairieJava", user, passwd); //"localhost", "LibrairieJava", "root", "raphe"
            if (connexion.isConnecte()) 
            {
                this.app.setCo(connexion);
                System.out.println("Connexion réussie !");

                Properties props = new Properties();
                
                
                props.setProperty("db.nomServ", serv);
                props.setProperty("db.user", user);
                props.setProperty("db.password", passwd);
                props.setProperty("db.base", "LibrairieJava");
                
                try (FileOutputStream fis = new FileOutputStream("dbUser/db.properties")) {
                    props.store(fis, "Configuration BD");
                } catch (IOException e) {
                    System.err.println("Failed to load properties: " + e.getMessage());
                    return;
                }
                ActionBD bd = new ActionBD(connexion);
                bd.CreerBD();

                this.app.init();
                this.app.start(this.app.getStage());
            }
        }
        catch (Exception e)
        {
            System.out.println("ERREUR" + e);
            this.app.getVueConnexionBD().popUpUtilisateurExistePas().showAndWait();
            this.app.quiiter();
        }
    }
}
