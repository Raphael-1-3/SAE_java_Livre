package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurPasserCommande implements EventHandler<ActionEvent> {

    private final TextField champClient;
    private final TextField champISBN;
    private final TextField champQuantite;

    public ControleurPasserCommande(TextField champClient, TextField champISBN, TextField champQuantite) {
        this.champClient = champClient;
        this.champISBN = champISBN;
        this.champQuantite = champQuantite;
    }

    @Override
    public void handle(ActionEvent e) {
        String client = champClient.getText().trim();
        String isbn = champISBN.getText().trim();
        String quantiteStr = champQuantite.getText().trim();

        if (client.isEmpty() || isbn.isEmpty() || quantiteStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            // Logique pour passer la commande

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Commande passée avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La quantité doit être un entier.");
            alert.showAndWait();
        }
    }
}

