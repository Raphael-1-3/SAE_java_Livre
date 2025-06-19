package IHM.controleurs.ControleurAdmin;

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


public class ControleurAjouterLibrairie implements EventHandler<ActionEvent>{
    private LivreExpress app;
    private ActionBD modele;

    public ControleurAjouterLibrairie(LivreExpress app, ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event)
    {
        Magasin m = new Magasin(null, this.app.getVueAdmin().getTfNom().getText(), this.app.getVueAdmin().getTfVille().getText());
        try {
            this.modele.AddLibrairie(m);
            this.app.getVueAdmin().popUpActionEffectuee();
        }
        catch (SQLException e )
        {
            System.out.println("Erreur SQL");
        }
}
}
