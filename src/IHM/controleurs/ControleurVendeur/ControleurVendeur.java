package IHM.controleurs.ControleurVendeur;

import IHM.vues.VueVendeur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        ComboBox<?> combo = (ComboBox<?>) event.getSource();
        String choix = (String) combo.getValue();

        switch (choix) {
            case "Ajouter un livre":
                vue.afficherFormulaireAjoutLivre(resultat);
                break;
            case "Modifier le stock":
                vue.afficherRechercheParMagasin(resultat);
                break;
            case "Regarde dispo":
                vue.afficherRechercheNomMagasinSeul(resultat);
                break;
            case "Commande pour client":
                vue.afficherPasserCommande(resultat);
                break;
            case "Transferer un Livre":
                System.out.println("Transferer un Livre (à implémenter)");
                break;
            case "Obtenir facture":
                System.out.println("Obtenir facture (à implémenter)");
                break;
            default:
                System.out.println("Action inconnue : " + choix);
        }
    }
}

