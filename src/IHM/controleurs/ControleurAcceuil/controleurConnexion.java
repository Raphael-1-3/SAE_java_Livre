package IHM.controleurs.ControleurAcceuil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.fxml.FXML;
import main.BD.*;
import main.Exceptions.PasDeTelUtilisateurException;
import main.app.*;

import java.sql.SQLException;
import java.util.EventListener;
import java.util.List;

import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import IHM.vues.VueClient;
import IHM.vues.VueVendeur;

public class controleurConnexion implements EventHandler<ActionEvent> {
    public LivreExpress app;
    public ActionBD modele;

    public controleurConnexion(LivreExpress app, ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle (ActionEvent event)
    {
        Button b = (Button) event.getSource();
        String s = b.getText();

        switch (s) {
            case "Accueil":
                this.app.getVueConnexion().fenetreAccueil(this.app.getRoot());
                break;
            case "Se connecter":
                try
                {
                    User user = this.modele.connexionRole(this.app.getVueConnexion().getTfCo().getText(), this.app.getVueConnexion().getPwCo().getText());
                    switch (user.getClass().getSimpleName()) 
                        {
                            case "Client":
                                System.out.println("Bienvenue, client !");
                                this.app.setVueClient(new VueClient(this.app, (Client) user, modele));
                                this.app.getScene().setRoot(this.app.getVueClient());
                                break;
                            case "Vendeur":
                                System.out.println("Bienvenue, vendeur !");
                                //Vendeur.application(bd, user, scanner);
                                this.app.setVueVendeur(new VueVendeur(app, modele, (Vendeur) user));
                                this.app.getScene().setRoot(this.app.getVueVendeur());
                                // Actions vendeur
                                break;
                            case "Administrateur":
                                System.out.println("Bienvenue, administrateur !");
                                this.app.setVueAdmin(new VueAdmin(app, modele, (Administrateur) user));
                                this.app.getScene().setRoot(this.app.getVueAdmin());
                                //Administrateur.application(bd, user, scanner);
                                // Actions admin
                                break;
                            default:
                                System.out.println("Rôle inconnu.");
                                break;
                        }}
                catch (SQLException e)
                {
                    System.out.println("Erreur SQL");
                }
                catch (PasDeTelUtilisateurException e)
                {
                    this.app.getVueConnexion().popUpUtilisateurExistePas().showAndWait();
                }
                break;
        }
    }
}
