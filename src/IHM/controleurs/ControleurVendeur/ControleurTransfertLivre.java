package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurTransfertLivre implements EventHandler<ActionEvent> {

    private final TextField champISBN;
    private final TextField champMagasinSource;
    private final TextField champMagasinDest;
    private final TextField champQuantite;

    public ControleurTransfertLivre(TextField champISBN, TextField champMagasinSource, TextField champMagasinDest, TextField champQuantite) {
        this.champISBN = champISBN;
        this.champMagasinSource = champMagasinSource;
        this.champMagasinDest = champMagasinDest;
        this.champQuantite = champQuantite;
    }

    @Override
    public void handle(ActionEvent e) {
        String isbn = champISBN.getText().trim();
        String magasinSource = champMagasinSource.getText().trim();
        String magasinDest = champMagasinDest.getText().trim();
        String quantiteStr = champQuantite.getText().trim();

        if (isbn.isEmpty() || magasinSource.isEmpty() || magasinDest.isEmpty() || quantiteStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            // Logique pour transférer les livres entre magasins

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Transfert effectué avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La quantité doit être un entier.");
            alert.showAndWait();
        }
    }
}

