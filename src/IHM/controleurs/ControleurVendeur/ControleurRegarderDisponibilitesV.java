package IHM.controleurs.ControleurVendeur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.BD.ActionBD;
import main.app.Livre;
import main.app.Magasin;
import main.Exceptions.EmptySetException;
import IHM.vues.VueAdmin;
import java.util.List;

public class ControleurRegarderDisponibilitesV implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurRegarderDisponibilitesV(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            // Récupération des champs de la vue (voir VueAdmin.regarderDisponibilites)
            TextField tfLivre = (TextField) vue.lookup(".text-field");
            TextField tfMagasin = (TextField) vue.lookupAll(".text-field").toArray()[1] instanceof TextField ? (TextField) vue.lookupAll(".text-field").toArray()[1] : null;

            String livreStr = tfLivre != null ? tfLivre.getText().trim() : "";
            String magasinStr = tfMagasin != null ? tfMagasin.getText().trim() : "";

            if (livreStr.isEmpty() || magasinStr.isEmpty()) {
                throw new Exception("Veuillez remplir le titre du livre et le magasin.");
            }

            Magasin mag = modele.magAPartirNom(magasinStr);
            if (mag == null) throw new Exception("Magasin introuvable");

            // Recherche du livre par titre
            Livre livre = null;
            try {
                livre = modele.getLivreParTitre(livreStr);
            } catch (EmptySetException ex) {
                throw new Exception("Livre introuvable");
            }
            if (livre == null) throw new Exception("Livre introuvable");

            // Vérifier si le livre est disponible dans le magasin
            List<Livre> livresDispo = modele.getLivresDispoDansMagasin(mag);
            boolean disponible = false;
            for (Livre l : livresDispo) {
                if (l.getISBN() == livre.getISBN()) {
                    disponible = true;
                    break;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Disponibilité du livre");
            alert.setHeaderText(null);
            if (disponible) {
                alert.setContentText("Le livre '" + livre.getTitre() + "' est disponible dans le magasin '" + mag.getNomMag() + "'.");
            } else {
                alert.setContentText("Le livre '" + livre.getTitre() + "' n'est pas disponible dans le magasin '" + mag.getNomMag() + "'.");
            }
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de vérifier la disponibilité");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
