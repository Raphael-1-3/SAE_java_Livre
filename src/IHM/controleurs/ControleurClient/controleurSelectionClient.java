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
        try 
        {
            VueClient vc = this.app.getVueClient(); 
            String filtre = vc.getSelectionRecherche().getValue();
            String mag = vc.getSelectionMagasin().getValue();
            Magasin magasin = this.modele.magAPartirNom(mag);
            String recherche = vc.getScheachbar().getText();

            if (filtre.equals(null) || mag.equals(null)) this.app.getVueConnexion().popUpChampsVides();
            else 
            {
            
                switch (filtre) 
                {
                    case "Rechercher par nom de livre":
                        vc.centerAfficherLivres(this.modele.cherhcherLivreApproximativeParMag(recherche, magasin));
                        this.app.setVueClient(vc);
                        break;
                    case "Rechercher par auteur":
                        List<Auteur> tousAuteurs = this.modele.rechercheAuteurApproximative(recherche);
                        List<Auteur> auteursDansMag = this.modele.getAuteursDansMagasin(magasin);
                        if (tousAuteurs == null) {
                            vc.centerAfficheAuteur(auteursDansMag);
                        } else {
                            List<Auteur> afficheListeAuteurs = new ArrayList<>(tousAuteurs);
                            afficheListeAuteurs.retainAll(auteursDansMag);
                            vc.centerAfficheAuteur(afficheListeAuteurs);
                        }
                        this.app.setVueClient(vc);
                        break;
                    case "Rechercher par classification":
                        List<Classification> toutesClassifications = this.modele.cherhcherClassificationApproximative(recherche);
                        List<Classification> classificationsDansMag = this.modele.getClassificationsDansMagasin(magasin);
                        if (toutesClassifications == null) {
                            vc.centerAfficheClassification(classificationsDansMag);
                        } else {
                            List<Classification> afficheListeClassifications = new ArrayList<>(toutesClassifications);
                            afficheListeClassifications.retainAll(classificationsDansMag);
                            System.out.println(afficheListeClassifications);
                            vc.centerAfficheClassification(afficheListeClassifications);
                        }
                        this.app.setVueClient(vc);
                        break;
                    case "Rechercher par éditeur":
                        List<Editeur> tousEditeurs = this.modele.cherhcherEditeurApproximative(recherche);
                        List<Editeur> editeursDansMag = this.modele.getEditeursDansMagasin(magasin);
                        if (tousEditeurs == null) {
                            vc.centerAfficheEditeur(editeursDansMag);
                        } else {
                            List<Editeur> afficheListeEditeurs = new ArrayList<>(tousEditeurs);
                            afficheListeEditeurs.retainAll(editeursDansMag);
                            System.out.println(afficheListeEditeurs);
                            vc.centerAfficheEditeur(afficheListeEditeurs);
                        }
                        this.app.setVueClient(vc);
                        break;
                    case "Rechecher par magasin":
                        vc.centerAfficherLivres(this.modele.getLivresDispoDansMagasin(magasin));
                        break;
                    default:
                        this.app.getVueConnexion().popUpChampsVides();
                    break;
                }
            }
        }
        catch(SQLException sql) {System.out.println("Probleme modele");}
        

    }
}

