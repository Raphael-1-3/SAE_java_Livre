package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurRechercheLivre implements EventHandler<ActionEvent> {

    private final TextField champMagasin;
    private final TextField champLivre;

    public ControleurRechercheLivre(TextField champMagasin, TextField champLivre) {
        this.champMagasin = champMagasin;
        this.champLivre = champLivre;
    }

    @Override
    public void handle(ActionEvent event) {
        String nomMagasin = champMagasin.getText().trim();
        String nomLivre = champLivre.getText().trim();

        if (nomMagasin.isEmpty() || nomLivre.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs (magasin et livre).");
            alert.showAndWait();
            return;
        }

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, 
            "Recherche lancée pour le livre '" + nomLivre + "' dans le magasin '" + nomMagasin + "'.");
        alert.showAndWait();

        
    }
}
