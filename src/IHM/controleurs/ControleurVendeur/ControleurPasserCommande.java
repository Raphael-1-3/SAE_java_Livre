package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurPasserCommande implements EventHandler<ActionEvent> {

    private TextField champClient;
    private TextField champISBN;
    private TextField champQuantite;
    private TextField champNomMag;

    public ControleurPasserCommande(TextField champClient, TextField champISBN, TextField champNomMag,TextField champQuantite) {
        this.champClient = champClient;
        this.champISBN = champISBN;
        this.champQuantite = champQuantite;
        this.champNomMag=champNomMag;
    }

    @Override
    public void handle(ActionEvent e) {
        String client = champClient.getText().trim();
        String isbn = champISBN.getText().trim();
        String quantiteStr = champQuantite.getText().trim();
        String NomMag = champNomMag.getText().trim();

        if (client.isEmpty() || isbn.isEmpty() || quantiteStr.isEmpty()||NomMag.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int quantite = Integer.parseInt(quantiteStr);
            

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Commande passée avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Les infos sont invalides.");
            alert.showAndWait();
        }
    }
}

