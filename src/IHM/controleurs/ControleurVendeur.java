package IHM.controleurs;

import IHM.vues.VueVendeur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

public class ControleurVendeur implements EventHandler<ActionEvent> {

    private BorderPane resultat;
    private VueVendeur vue;

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
                System.out.println("Modifier le stock");
                break;
            case "Regarde dispo":
                System.out.println("Regarde dispo");
                break;
            case "Commande pour client":
                System.out.println("Commande pour client");
                break;
            case "Transferer un Livre":
                System.out.println("Transferer un Livre");
                break;
            case "Obtenir facture":
                System.out.println("Obtenir facture");
                break;
            default:
                System.out.println("Action inconnue : " + choix);
        }
    }
}
