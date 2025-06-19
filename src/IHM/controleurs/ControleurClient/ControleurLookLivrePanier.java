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
public class ControleurLookLivrePanier implements EventHandler<MouseEvent> {
    private ActionBD modele;
    private LivreExpress app;
    private Livre l;
    public ControleurLookLivrePanier(LivreExpress app, ActionBD modele, Livre l)
    {
        this.modele = modele;
        this.app = app;
        this.l = l;
    }

    public void handle(MouseEvent event)
    {
        this.app.getVueClient().afficherPopUpLivrePanier(this.l);
    }
}
