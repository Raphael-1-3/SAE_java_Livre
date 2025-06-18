package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurModificationStock implements EventHandler<ActionEvent> {

    private final TextField champMagasin;
    private final TextField champLivre;
    private final TextField champQuantite;

    public ControleurModificationStock(TextField champMagasin, TextField champLivre, TextField champQuantite) {
        this.champMagasin = champMagasin;
        this.champLivre = champLivre;
        this.champQuantite = champQuantite;
    }

    @Override
    public void handle(ActionEvent event) {
        String nomMagasin = champMagasin.getText().trim();
        String nomLivre = champLivre.getText().trim();
        String quantiteStr = champQuantite.getText().trim();

        if (nomMagasin.isEmpty() || nomLivre.isEmpty() || quantiteStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            if (quantite < 0) {
                throw new NumberFormatException("Quantité négative");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                "Stock modifié : magasin '" + nomMagasin + "', livre '" + nomLivre + "', nouvelle quantité : " + quantite);
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La quantité doit être un entier positif.");
            alert.showAndWait();
        }
    }
}
