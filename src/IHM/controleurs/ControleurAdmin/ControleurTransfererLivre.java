package IHM.controleurs.ControleurAdmin;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.BD.ActionBD;
import main.app.Livre;
import main.app.Magasin;
import main.Exceptions.PasAssezLivreException;
import IHM.vues.VueAdmin;

public class ControleurTransfererLivre implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurTransfererLivre(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            // Récupération des champs de la vue (voir VueAdmin.transfererLivre)
            TextField tfISBN = (TextField) vue.lookup(".text-field");
            TextField tfMagDep = (TextField) vue.lookupAll(".text-field").toArray()[1] instanceof TextField ? (TextField) vue.lookupAll(".text-field").toArray()[1] : null;
            TextField tfMagArr = (TextField) vue.lookupAll(".text-field").toArray()[2] instanceof TextField ? (TextField) vue.lookupAll(".text-field").toArray()[2] : null;
            TextField tfQte = (TextField) vue.lookupAll(".text-field").toArray()[3] instanceof TextField ? (TextField) vue.lookupAll(".text-field").toArray()[3] : null;

            String isbnStr = tfISBN != null ? tfISBN.getText().trim() : "";
            String magDepStr = tfMagDep != null ? tfMagDep.getText().trim() : "";
            String magArrStr = tfMagArr != null ? tfMagArr.getText().trim() : "";
            String qteStr = tfQte != null ? tfQte.getText().trim() : "";

            if (isbnStr.isEmpty() || magDepStr.isEmpty() || magArrStr.isEmpty() || qteStr.isEmpty()) {
                throw new Exception("Tous les champs doivent être remplis.");
            }

            long isbn = Long.parseLong(isbnStr);
            int qte = Integer.parseInt(qteStr);
            if (qte <= 0) throw new Exception("La quantité doit être positive.");

            Magasin depart = modele.magAPartirNom(magDepStr);
            if (depart == null) throw new Exception("Magasin de départ introuvable");
            Magasin arrivee = modele.magAPartirNom(magArrStr);
            if (arrivee == null) throw new Exception("Magasin d'arrivée introuvable");

            modele.Transfer(isbn, depart, arrivee, qte);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transfert effectué");
            alert.setHeaderText(null);
            alert.setContentText("Le transfert a bien été effectué.");
            alert.showAndWait();
        } catch (PasAssezLivreException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de stock");
            alert.setHeaderText("Stock insuffisant");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de transférer le livre");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
