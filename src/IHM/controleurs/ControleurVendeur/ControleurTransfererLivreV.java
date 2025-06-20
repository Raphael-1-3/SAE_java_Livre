package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import main.BD.ActionBD;
import main.app.Magasin;
import main.app.Livre;
import main.Exceptions.PasAssezLivreException;

import javax.swing.*;
import java.sql.SQLException;

public class ControleurTransfererLivreV implements EventHandler<ActionEvent> {
    private final ActionBD modele;
    private final LivreExpress app;

    public ControleurTransfererLivreV(ActionBD modele, LivreExpress app) {
        this.modele = modele;
        this.app = app;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            // Récupération des champs depuis la app.getVueVendeur()
            String isbnStr = app.getVueVendeur().getTfISBN().getText();
            String magDepStr = app.getVueVendeur().getTfMagDep().getText();
            String magArrStr = app.getVueVendeur().getTfMagArr().getText();
            String qteStr = app.getVueVendeur().getTfQte().getText();

            if (isbnStr.isEmpty() || magDepStr.isEmpty() || magArrStr.isEmpty() || qteStr.isEmpty()) {
                app.getVueVendeur().popUpChampsVides().showAndWait();
                return;
            }

            long isbn = Long.parseLong(isbnStr);
            int idMagDep = Integer.parseInt(magDepStr);
            int idMagArr = Integer.parseInt(magArrStr);
            int qte = Integer.parseInt(qteStr);

            Magasin depart = modele.magAPartirId(idMagDep);
            Magasin arrivee = modele.magAPartirId(idMagArr);

            modele.Transfer(isbn, depart, arrivee, qte);

            app.getVueVendeur().popUpActionEffectuee().showAndWait();
        } catch (NumberFormatException e) {
            app.getVueVendeur().popUpPasUnNbr().showAndWait();
        } catch (PasAssezLivreException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de stock");
            alert.setHeaderText(null);
            alert.setContentText("Pas assez de livres en stock pour effectuer le transfert.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors du transfert : " + e.getMessage());
            alert.showAndWait();
        }
    }
}