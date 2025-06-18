package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurModificationStock implements EventHandler<ActionEvent> {

    private final TextField champMagasin;
    private final TextField champISBN;
    private final TextField champQuantite;

    public ControleurModificationStock(TextField champMagasin, TextField champISBN, TextField champQuantite) {
        this.champMagasin = champMagasin;
        this.champISBN = champISBN;
        this.champQuantite = champQuantite;
    }

    @Override
    public void handle(ActionEvent e) {
        String magasin = champMagasin.getText().trim();
        String isbn = champISBN.getText().trim();
        String quantiteStr = champQuantite.getText().trim();

        if (magasin.isEmpty() || isbn.isEmpty() || quantiteStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            // Appel au DAO pour modifier le stock

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Stock modifié avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La quantité doit être un entier.");
            alert.showAndWait();
        }
    }
}
