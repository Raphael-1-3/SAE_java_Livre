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
public class ControleurLivraison implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;
    private ComboBox<String> cb;

    public ControleurLivraison(LivreExpress app, ActionBD modele, ComboBox<String> cb)
    {
        this.app = app;
        this.modele = modele;
        this.cb = cb;
    }

    public void handle (ActionEvent event)
    {
        String liv = this.cb.getSelectionModel().getSelectedItem();
        switch (liv) {
            case "A Domicile":
                this.app.getVueClient().setLiv("C");
                break;
            case "En magasin":
                this.app.getVueClient().setLiv("M");
                break;
            default:
                break;
        }
    }
    
}
