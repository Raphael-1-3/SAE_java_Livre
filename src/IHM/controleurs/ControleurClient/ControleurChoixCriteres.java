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

public class ControleurChoixCriteres implements EventHandler<ActionEvent>{
    private ActionBD modele;
    private LivreExpress app;
    private ComboBox<String> cb;

    public ControleurChoixCriteres(LivreExpress app, ActionBD modele, ComboBox<String> cb)
    {
        this.app = app;
        this.modele = modele;
        this.cb = cb;
    }

    public void handle(ActionEvent event)
    {
        String s = this.cb.getSelectionModel().getSelectedItem();
        if (s != null)
        {
            try {
                switch (s) {
                    case "Rechecher par magasin":
                        this.app.getVueClient().panelAfficherLivres(this.modele.getLivresDispoDansMagasin(this.app.getVueClient().getMag()), "");
                        break;
                    case "Recommandations":
                        this.app.getVueClient().setupRecommandations();
                        break;
                    default:
                        break;
                }
            }
            catch (SQLException e )
            {
                System.out.println("Erreur");
            }
        }
    }
}
