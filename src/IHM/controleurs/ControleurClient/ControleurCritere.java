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

public class ControleurCritere {
    private VueClient vue;
    private ActionBD modele;

    public ControleurCritere(VueClient vue, ActionBD modele)
    {
        this.vue = vue;
        this.modele = modele;
        this.vue.getRecherche().textProperty().addListener((obs, oldVal, newVal) -> {
            try{
                String typeRecherche = this.vue.getCriteres().getSelectionModel().getSelectedItem();
                if (typeRecherche != null)
                {
                    switch (typeRecherche) {
                    case "Rechercher par nom de livre":
                        this.vue.panelAfficherLivres(this.modele.cherhcherLivreApproximativeParMag(newVal, this.vue.getMag()), "Catalogue");
                        break;
                    case "Rechercher par auteur":
                        List<Auteur> auteurs = this.modele.rechercheAuteurApproximative(newVal);
                        List<Livre> livresA = new ArrayList<>();
                        for (Auteur a : auteurs)
                        {
                            for (Livre l : this.modele.rechercheLivreAuteur(a, this.vue.getMag()))
                            {
                                livresA.add(l);
                            }
                        }
                        this.vue.panelAfficherLivres(livresA, "Catalogue");
                        break;
                    case "Rechercher par classification":
                        List<Classification> classif = this.modele.cherhcherClassificationApproximative(newVal);
                        List<Livre> livresC = new ArrayList<>();
                        for (Classification c : classif)
                        {
                            for (Livre l : this.modele.chercherLivreAPartirClassification(c, this.vue.getMag()))
                            {
                                livresC.add(l);
                            }
                        }
                        this.vue.panelAfficherLivres(livresC, "Catalogue");
                        break;
                    case "Rechercher par éditeur":
                        List<Editeur> editeurs = this.modele.cherhcherEditeurApproximative(newVal);
                        List<Livre> livresE = new ArrayList<>();
                        for (Editeur e : editeurs)
                        {
                            for (Livre l : this.modele.chercherLivreAPartiEditeur(e, this.vue.getMag()))
                            {
                                livresE.add(l);
                            }
                        }
                        this.vue.panelAfficherLivres(livresE, "Catalogue");
                        break;
                    case "Rechecher par magasin":
                        List<Livre> livresM = this.modele.getLivresDispoDansMagasin(this.vue.getMag());
                        this.vue.panelAfficherLivres(livresM, "Catalogue");
                        break;
                    case "Recommandations":
                        this.vue.setupRecommandations();
                        break;
                    default:
                        break;
                }
            }
                }
                
            catch (SQLException e)
            {
                System.out.println("Erreur ");
            }
            
        });
    }
}
