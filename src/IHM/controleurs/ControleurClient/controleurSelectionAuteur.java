package IHM.controleurs.ControleurClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import main.Exceptions.EmptySetException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.util.List;

import IHM.vues.*;
import main.app.*;
public class controleurSelectionAuteur implements EventHandler<MouseEvent> 
{
    private LivreExpress app;
    private ActionBD modele;

    public controleurSelectionAuteur(ActionBD modele, LivreExpress app)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(MouseEvent event) 
    {
        
        Object source = event.getSource();
        this.app.getVueClient().resetBox2();

        if (source instanceof Label) 
        {
            try
            { 
                Label labelClique = (Label) source;
                String nomau = labelClique.getText();
                Auteur aut = this.modele.getAuteurAPartirDeNom(nomau);
                String mag = this.app.getVueClient().getSelectionMagasin().getValue();
                Magasin magasin = this.modele.magAPartirNom(mag);
                this.app.getVueClient().centerAfficherLivres(this.modele.rechercheLivreAuteur(aut, magasin));
            }
            catch(SQLException sql) {System.out.println("Erreure sql");}

        }
    }
}

