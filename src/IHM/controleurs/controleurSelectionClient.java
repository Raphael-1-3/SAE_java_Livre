package IHM.controleurs;
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
public class controleurSelectionClient implements EventHandler<ActionEvent> 
{
    private LivreExpress app;
    private ActionBD modele;

    public controleurSelectionClient(ActionBD modele, LivreExpress app)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event)
    {
        String filtre = this.app.getvu.getValue();
        String mag = selectionMagasin.getValue();

        if (filtre.equals(null) || mag.equals(null)) this.app.getVueConnexion().popUpChampsVides();
        else 
        {

    }
}
