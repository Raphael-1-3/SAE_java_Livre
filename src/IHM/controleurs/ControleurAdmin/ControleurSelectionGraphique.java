package IHM.controleurs.ControleurAdmin;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import IHM.vues.*;
import main.app.*;
public class ControleurSelectionGraphique implements EventHandler<ActionEvent> 
{
    private LivreExpress app;
    private ActionBD modele;

    public ControleurSelectionGraphique(ActionBD modele, LivreExpress app)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event) {
        try {
            
            String choixGra = this.app.getVueAdmin().getSelectionStat().getValue();
            String recherche = this.app.getVueAdmin().getTfAnnee().getText();

            if (choixGra == null || choixGra.isEmpty() || (besoinDeParametre(choixGra) && (recherche == null || recherche.isEmpty()))) {
                this.app.getVueConnexion().popUpChampsVides().showAndWait();
            } else {
                switch (choixGra) {
                    case "NombreDeLivreVendueParMagasinParAns":
                        System.out.println("Appel de NombreDeLivreVendueParMagasinParAns");
                        var donnees = this.modele.NombreDeLivreVendueParMagasinParAns();
                        System.out.println(donnees);
                        this.app.getVueAdmin().afficheGraphiqueNombreDeLivreVendueParMagasinParAns(donnees);
                        break;
                    case "chiffreAffaireParClassificationParAns":
                        this.app.getVueAdmin().afficheGraphiqueChiffreAffaireParClassificationParAns(this.modele.chiffreAffaireParClassificationParAns(Integer.parseInt(recherche)));
                        break;
                    case "CAMagasinParMoisParAnnee":
                        this.app.getVueAdmin().afficheGraphiqueCAMagasinParMoisParAnnee(this.modele.CAMagasinParMoisParAnnee(Integer.parseInt(recherche)));
                        break;
                    case "CAVenteEnLigneEnMagasinParAnnee":
                        this.app.getVueAdmin().afficheGraphiqueCAVenteEnLigneEnMagasinParAnnee(this.modele.CAVenteEnLigneEnMagasinParAnnee(Integer.parseInt(recherche)));
                        break;
                    case "nombreAuteurParEditeur":
                        this.app.getVueAdmin().afficheGraphiqueNombreAuteurParEditeur(this.modele.nombreAuteurParEditeur());
                        break;
                    case "nombreClientParVilleQuiOntAcheterAuteur":
                        this.app.getVueAdmin().afficheGraphiqueNombreClientParVilleQuiOntAcheterAuteur(this.modele.nombreClientParVilleQuiOntAcheterAuteur(this.modele.getAuteurAPartirDeNom(recherche)));
                        break;
                    case "valeurStockMagasin":
                        this.app.getVueAdmin().afficheGraphiqueValeurStockMagasin(this.modele.valeurStockMagasin());
                        break;
                    case "statsCAParClientParAnnee":
                        this.app.getVueAdmin().afficheGraphiqueStatsCAParClientParAnnee(this.modele.statsCAParClientParAnnee());
                        break;
                    case "auteurLePlusVenduParAnnee":
                        this.app.getVueAdmin().afficheGraphiqueAuteurLePlusVenduParAnnee(this.modele.auteurLePlusVenduParAnnee(Integer.parseInt(recherche)));
                        break;
                }
            }
        } catch (NumberFormatException nfe) {
            this.app.getVueConnexion().popUpPasUnNbr().showAndWait();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Méthode utilitaire pour déterminer si un paramètre est requis
    private boolean besoinDeParametre(String choix) {
        return switch (choix) {
            case "chiffreAffaireParClassificationParAns",
                 "CAMagasinParMoisParAnnee",
                 "CAVenteEnLigneEnMagasinParAnnee",
                 "nombreClientParVilleQuiOntAcheterAuteur",
                 "auteurLePlusVenduParAnnee" -> true;
            default -> false;
        };
    }

}
