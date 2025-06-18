package IHM.controleurs.ControleurVendeur;

import IHM.vues.VueVendeur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

public class ControleurVendeur implements EventHandler<ActionEvent> {

    private final BorderPane resultat;
    private final VueVendeur vue;

    public ControleurVendeur(BorderPane resultat, VueVendeur vue) {
        this.resultat = resultat;
        this.vue = vue;
    }

    @Override
    public void handle(ActionEvent event) {
        if (!(event.getSource() instanceof ComboBox<?> combo)) {
            System.err.println("Source de l'événement inattendue");
            return;
        }

        String choix = (String) combo.getValue();
        if (choix == null) return;

        switch (choix) {
            case "Ajouter un livre":
                vue.afficherFormulaireAjoutLivre(resultat);
                break;
            case "Modifier le stock":
                vue.afficherRechercheParMagasin(resultat);
                break;
            case "Regarde dispo":
                vue.afficherRechercheParMagasin(resultat);
                break;
            case "Commande pour client":
                vue.afficherPasserCommande(resultat);
                break;
            case "Transferer un Livre":
                vue.afficherTransfererUnLivre(resultat);
                break;
            case "Obtenir facture":
                vue.afficherFacture(resultat);
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Action inconnue : " + choix).showAndWait();
                break;
        }
    }
}
