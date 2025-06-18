package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurFacture implements EventHandler<ActionEvent> {

    private final TextField champClient;
    private final TextField champMois;
    private final TextField champAnnee;

    public ControleurFacture(TextField champClient, TextField champMois, TextField champAnnee) {
        this.champClient = champClient;
        this.champMois = champMois;
        this.champAnnee = champAnnee;
    }

    @Override
    public void handle(ActionEvent e) {
        String client = champClient.getText().trim();
        String moisStr = champMois.getText().trim();
        String anneeStr = champAnnee.getText().trim();

        if (client.isEmpty() || moisStr.isEmpty() || anneeStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int mois = Integer.parseInt(moisStr);
            int annee = Integer.parseInt(anneeStr);

            if (mois < 1 || mois > 12) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Le mois doit être entre 1 et 12.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Afficher la facture pour le client : " + client +
                    "\nMois : " + mois + "\nAnnée : " + annee);
            alert.showAndWait();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Mois et Année doivent être des nombres entiers.");
            alert.showAndWait();
        }
    }
}

