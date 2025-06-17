package IHM.controleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class ControleurVendeur implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        ComboBox<?> combo = (ComboBox<?>) event.getSource();
        String choix = (String) combo.getValue();

        switch (choix) {
            case "Ajouter un livre":
                System.out.println("Ajouter un livre");
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
