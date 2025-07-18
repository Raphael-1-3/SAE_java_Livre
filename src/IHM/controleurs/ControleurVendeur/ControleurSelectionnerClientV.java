package IHM.controleurs.ControleurVendeur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.BD.ActionBD;
import main.Exceptions.PasDeTelUtilisateurException;
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

import java.lang.classfile.attribute.LocalVariableTypeTableAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import IHM.vues.*;
import main.app.*;
public class ControleurSelectionnerClientV implements EventHandler<ActionEvent> {
    private LivreExpress app;
    private ActionBD modele;

    public ControleurSelectionnerClientV(LivreExpress app, ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
    }
    public void handle (ActionEvent event)
    {
        String nom = this.app.getVueVendeur().getTfNom().getText();
        String prenom = this.app.getVueVendeur().getTfPrenom().getText();
        String codePosRaw = this.app.getVueVendeur().getTfCodePostal().getText();
        if (nom.isEmpty() || prenom.isEmpty() || codePosRaw.isEmpty())
        {
            this.app.getVueVendeur().popUpChampsVides().show();
        }
        else
        {
            Integer codePo = null;
            try {
                codePo = Integer.parseInt(codePosRaw);
                Client c = this.modele.getClientAPartirNomPrenomCodePostal(nom, prenom, codePo);
                this.app.getVueVendeur().setClient(c);
                this.app.getVueVendeur().pannelChoisirMoisAnnee();
            }
            catch (NumberFormatException e)
            {
                this.app.getVueVendeur().popUpPasUnNbr().show();
            }
            catch (SQLException e)
            {
                System.out.println("Erreur SQL" + e);
            }
            catch (PasDeTelUtilisateurException e)
            {
                this.app.getVueVendeur().popUpClientInexistant().show();
            }
        }
    }
}
