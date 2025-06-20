package IHM.controleurs.ControleurVendeur;

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
import IHM.vues.*;


public class ControleurAjouterLivreV implements EventHandler<ActionEvent> {
    public LivreExpress app;
    public ActionBD modele;

    public ControleurAjouterLivreV(LivreExpress app, ActionBD modele)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event)
    {
        VueVendeur vue = this.app.getVueVendeur();
        String nom = vue.getTfNom().getText();
        String nbPages = vue.getTfNbPages().getText();
        String annee = vue.getTfAnnee().getText();
        String prix = vue.getTfPrix().getText();
        String classification = vue.getTfClassification().getText();
        String auteur = vue.getTfAuteur().getText();
        String editeur = vue.getTfEditeur().getText();

        if (nom.isEmpty() || nbPages.isEmpty() || annee.isEmpty() || prix.isEmpty() ||
            classification.isEmpty() || auteur.isEmpty() || editeur.isEmpty()) {
            vue.popUpChampsVides().show();
        }

        try {
            int nbPagesInt = Integer.parseInt(nbPages);
            int anneeInt = Integer.parseInt(annee);
            Double prixDouble = Double.parseDouble(prix);
            int classificationInt = Integer.parseInt(classification);
            int editeurInt = Integer.parseInt(editeur);

            Livre l = new Livre(0, nom, nbPagesInt, anneeInt, prixDouble);
            this.modele.AddLivre(l, auteur, classificationInt, editeurInt);
            vue.popUpActionEffectuee().show();
        }
        catch (NumberFormatException e)
        {
            vue.popUpPasUnNbr().show();
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQL" + e);
        }
    }
}
