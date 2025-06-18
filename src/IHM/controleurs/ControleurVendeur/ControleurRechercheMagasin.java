package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurRechercheMagasin implements EventHandler<ActionEvent> {

    private final TextField champMagasin;

    public ControleurRechercheMagasin(TextField champMagasin) {
        this.champMagasin = champMagasin;
    }

    @Override
    public void handle(ActionEvent e) {
        String magasin = champMagasin.getText().trim();

        if (magasin.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez saisir un magasin.");
            alert.showAndWait();
            return;
        }

        // Recherche des livres disponibles dans ce magasin
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Recherche des livres pour le magasin : " + magasin);
        alert.showAndWait();
    }
}

