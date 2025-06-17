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
import java.util.List;

import IHM.vues.LivreExpress;

public class controleurAccueil implements EventHandler<ActionEvent> {
    public LivreExpress app;
    
    public controleurAccueil(LivreExpress app)
    {
        this.app = app;
    }

    public void handle(ActionEvent event)
    {
        Button b = (Button) event.getSource();
        String s = b.getText();

        switch (s) {
            case "Connexion":
                this.app.getVueConnexion().fenetreConnexion(this.app.getRoot());
                break;
            case "Inscription":
                this.app.getVueConnexion().fenetreInscription(this.app.getRoot());
                break;
            case "Quitter":
                this.app.quiiter();
                break;
        }
    }
}
