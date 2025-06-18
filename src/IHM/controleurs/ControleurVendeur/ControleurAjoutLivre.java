package IHM.controleurs.ControleurVendeur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurAjoutLivre implements EventHandler<ActionEvent> {

    private final TextField champISBN;
    private final TextField champTitre;
    private final TextField champAuteur;
    private final TextField champPrix;

    public ControleurAjoutLivre(TextField champISBN, TextField champTitre, TextField champAuteur, TextField champPrix) {
        this.champISBN = champISBN;
        this.champTitre = champTitre;
        this.champAuteur = champAuteur;
        this.champPrix = champPrix;
    }

    @Override
    public void handle(ActionEvent e) {
        String isbn = champISBN.getText().trim();
        String titre = champTitre.getText().trim();
        String auteur = champAuteur.getText().trim();
        String prixStr = champPrix.getText().trim();

        if (isbn.isEmpty() || titre.isEmpty() || auteur.isEmpty() || prixStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            double prix = Double.parseDouble(prixStr);
            // Ici tu peux ajouter l'appel au DAO pour ajouter le livre en base

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Livre ajouté avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le prix doit être un nombre valide.");
            alert.showAndWait();
        }
    }
}
