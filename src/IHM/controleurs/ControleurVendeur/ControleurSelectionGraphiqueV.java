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
public class ControleurSelectionGraphiqueV implements EventHandler<ActionEvent> 
{
    private LivreExpress app;
    private ActionBD modele;

    public ControleurSelectionGraphiqueV(ActionBD modele, LivreExpress app)
    {
        this.app = app;
        this.modele = modele;
    }

    public void handle(ActionEvent event) {
        try {
    
            String choixGra = this.app.getVueVendeur().getSelectionStat().getValue();
            String recherche = this.app.getVueVendeur().getbarRecherche().getText();
            this.barActive(choixGra);

            if (choixGra == null || choixGra.isEmpty() || (besoinDeParametre(choixGra) && (recherche == null || recherche.isEmpty()))) {
                this.app.getVueConnexion().popUpChampsVides().showAndWait();
            } else {

                switch (choixGra) {
                    case "NombreDeLivreVendueParMagasinParAns":
                        this.app.getVueVendeur().getbarRecherche().setPromptText("aucune recherche a effectuer");
                        ;
                        System.out.println("Appel de NombreDeLivreVendueParMagasinParAns");
                        var donneesNDELVPMPA = this.modele.NombreDeLivreVendueParMagasinParAns();
                        System.out.println(donneesNDELVPMPA);
                        this.app.getVueVendeur().afficheGraphiqueNombreDeLivreVendueParMagasinParAns(donneesNDELVPMPA);
                        break;
                    case "chiffreAffaireParClassificationParAns":
                        System.out.println("Appel de chiffreAffaireParClassificationParAns");
                        this.app.getVueVendeur().getbarRecherche().setPromptText("entrer une annee");
           
                        var donneesCAPCPA = this.modele.chiffreAffaireParClassificationParAns(Integer.parseInt(recherche));
                        System.out.println(donneesCAPCPA);
                        this.app.getVueVendeur().afficheGraphiqueChiffreAffaireParClassificationParAns(donneesCAPCPA);
                        break;
                    case "CAMagasinParMoisParAnnee":
                        System.out.println("Appel de CAMagasinParMoisParAnnee");
                        this.app.getVueVendeur().getbarRecherche().setPromptText("entrer une annee");
                        
                        var donneesCAMagasinParMoisParAnnee = this.modele.CAMagasinParMoisParAnnee(Integer.parseInt(recherche));
                        System.out.println(donneesCAMagasinParMoisParAnnee);
                        this.app.getVueVendeur().afficheGraphiqueCAMagasinParMoisParAnnee(donneesCAMagasinParMoisParAnnee);
                        break;
                    case "CAVenteEnLigneEnMagasinParAnnee":
                        System.out.println("Appel de CAVenteEnLigneEnMagasinParAnnee");
                        this.app.getVueVendeur().getbarRecherche().setPromptText("aucune recherche a effectuer");
                        
                        var donneesCAVenteEnLigneEnMagasinParAnnee = this.modele.CAVenteEnLigneEnMagasinParAnnee();
                        System.out.println(donneesCAVenteEnLigneEnMagasinParAnnee);
                        this.app.getVueVendeur().afficheGraphiqueCAVenteEnLigneEnMagasinParAnnee(donneesCAVenteEnLigneEnMagasinParAnnee);
                        break;
                    case "nombreAuteurParEditeur":
                    this.app.getVueVendeur().getbarRecherche().setPromptText("aucune recherche a effectuer");
                        
                        this.app.getVueVendeur().afficheGraphiqueNombreAuteurParEditeur(this.modele.nombreAuteurParEditeur());
                        break;
                    case "nombreClientParVilleQuiOntAcheterAuteur":
                        this.app.getVueVendeur().getbarRecherche().setPromptText("entre un nom d auteur");
                        this.app.getVueVendeur().afficheGraphiqueNombreClientParVilleQuiOntAcheterAuteur(this.modele.nombreClientParVilleQuiOntAcheterAuteur(this.modele.getAuteurAPartirDeNom(recherche)));
                        break;
                    case "valeurStockMagasin":
                    this.app.getVueVendeur().getbarRecherche().setPromptText("aucune recherche a effectuer");
                        
                        this.app.getVueVendeur().afficheGraphiqueValeurStockMagasin(this.modele.valeurStockMagasin());
                        break;
                    case "statsCAParClientParAnnee":
                    this.app.getVueVendeur().getbarRecherche().setPromptText("aucune recherche a effectuer");
                        
                        this.app.getVueVendeur().afficheGraphiqueStatsCAParClientParAnnee(this.modele.statsCAParClientParAnnee());
                        break;
                    case "auteurLePlusVenduParAnnee":
                    this.app.getVueVendeur().getbarRecherche().setPromptText("aucune recherche a effectuer");
                        
                        this.app.getVueVendeur().afficheGraphiqueAuteurLePlusVenduParAnnee(this.modele.auteurLePlusVenduParAnnee(Integer.parseInt(recherche)));
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
                 "nombreClientParVilleQuiOntAcheterAuteur",
                 "auteurLePlusVenduParAnnee" -> true;
            default -> false;
        };
    }

    // Méthode utilitaire pour déterminer si un paramètre est requis
    private void barActive(String choix) {
        switch (choix) {
            case "chiffreAffaireParClassificationParAns",
                 "CAMagasinParMoisParAnnee",
                 "nombreClientParVilleQuiOntAcheterAuteur",
                 "auteurLePlusVenduParAnnee" -> this.app.getVueVendeur().activer();
            default -> this.app.getVueVendeur().getbarRecherche().setDisable(true);
        };
    }

}
