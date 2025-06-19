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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import IHM.vues.*;
import main.app.*;
import main.BD.*;

public class ControleurChosirMagasin implements EventHandler<MouseEvent>{

    private ActionBD modele;
    private LivreExpress app;

    public ControleurChosirMagasin(ActionBD modele, LivreExpress app)
    {
        this.modele = modele;
        this.app = app;
    }

    public void handle(MouseEvent event)
    {
        try {
            VBox vb = (VBox) event.getSource();
            Label l = (Label) vb.getChildren().get(0);
            Magasin mag = this.modele.magAPartirNom(l.getText());
            this.app.getVueClient().setMag(mag);
            this.app.getVueClient().activer();
            this.app.getVueClient().setupRecommandations();
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL");
        }
        
    }
    
}
