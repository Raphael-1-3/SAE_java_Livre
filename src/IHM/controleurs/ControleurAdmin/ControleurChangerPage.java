package IHM.controleurs.ControleurAdmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import main.BD.ActionBD;

public class ControleurChangerPage implements EventHandler<ActionEvent> 
{
    private ActionBD modele;
    private LivreExpress app;
    private ComboBox<String> selectionAction;

    public ControleurChangerPage(ActionBD m, LivreExpress app, ComboBox<String> selectionAction){
        this.modele = m;
        this.app = app;
        this.selectionAction = selectionAction;
    }

    @Override
    public void handle(ActionEvent event){
        String action = selectionAction.getValue();
        VueAdmin vue = this.app.getVueAdmin();

        if (action == null) return;

        switch (action) {
            case "Créer un vendeur":
                vue.creerVendeur();
                break;
            case "Ajouter une librairie":
                vue.ajouterLibrairie();
                break;
            case "Panneau de Bord":
                vue.panneauDeBord();
                break;
            case "Ajouter un livre":
                vue.ajouterLivre();
                break;
            case "Regarder les disponibilités":
                vue.regarderDisponibilites();
                break;
            case "Passer une commande pour un Client":
                vue.passerCommandeClient();
                break;
            case "Transférer un livre":
                vue.transfererLivre();
                break;
            case "Obtenir les factures":
                vue.obtenirFactures();
                break;
            case "Choisir un magasin":
                vue.choisirMagasin();
                break;
            default:
                // Rien
        }
    }
}
