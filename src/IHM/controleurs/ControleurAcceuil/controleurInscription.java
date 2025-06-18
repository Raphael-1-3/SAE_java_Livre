package IHM.controleurs.ControleurAcceuil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import main.app.Livre;
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

import java.sql.SQLException;
import java.util.List;

import IHM.vues.LivreExpress;
public class controleurInscription implements EventHandler<ActionEvent> {
    private LivreExpress app;
    private ActionBD modele;

    public controleurInscription(LivreExpress app, ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event)
    {
        Button b = (Button) event.getSource();
        String s = b.getText();

        switch (s) {
            case "S'inscrire":
                try
                {
                    Integer codePostal = Integer.parseInt(this.app.getVueConnexion().getTfCodePo().getText());
                    String nom = this.app.getVueConnexion().getTfNom().getText();
                    String prenom = this.app.getVueConnexion().getTfPrenom().getText();
                    String ville = this.app.getVueConnexion().getTfVille().getText();
                    String adresse = this.app.getVueConnexion().getTfAdresse().getText();
                    String email = this.app.getVueConnexion().getTfEmail().getText();
                    String motDePasse = this.app.getVueConnexion().getPwInsc().getText();
                    if (nom.isEmpty() || prenom.isEmpty() || ville.isEmpty() || adresse.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                        this.app.getVueConnexion().popUpChampsVides().showAndWait();
                    }
                    else
                    {
                        this.modele.creerClient(this.app.getVueConnexion().getTfNom().getText(), this.app.getVueConnexion().getTfPrenom().getText(), codePostal, this.app.getVueConnexion().getTfVille().getText(), this.app.getVueConnexion().getTfAdresse().getText(), this.app.getVueConnexion().getTfEmail().getText(), this.app.getVueConnexion().getPwInsc().getText());
                        this.app.getVueConnexion().popUpClientCree().showAndWait();
                    }
                    
                }
                catch (NumberFormatException e)
                {
                    this.app.getVueConnexion().popUpPasUnNbr().showAndWait();
                }
                catch (SQLException e)
                {
                    System.out.println("Erreur SQL");
                }
                
                break;
            case "Accueil":
                this.app.getVueConnexion().fenetreAccueil(this.app.getRoot());
                break;
        }
    }
}
