package IHM.controleurs.ControleurAdmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import main.BD.ActionBD;

public class ControleurChangerPage implements EventHandler<ActionEvent> 
{
    private ActionBD modele;
    private LivreExpress app;
    private ComboBox<String> selectionAction;

    public ControleurChangerPage(ActionBD m, LivreExpress app, ComboBox<String> selectionAction) 
    {
        this.modele = m;
        this.app = app;
        this.selectionAction = selectionAction;
    }

    @Override
    public void handle(ActionEvent event) {
        String action = selectionAction.getValue();

        if (action == null) return;

        try {
            switch (action) {
                case "Créer un vendeur":
                    this.app.getVueAdmin().creerVendeur();
                    break;
                case "Ajouter une librairie":
                    this.app.getVueAdmin().ajouterLibrairie();
                    break;
                case "Panneau de Bord":
                    this.app.getVueAdmin().panneauDeBord();
                    break;
                case "Ajouter un livre":
                    this.app.getVueAdmin().ajouterLivre();
                    break;
                case "Regarder les disponibilités":
                    this.app.getVueAdmin().regarderDisponibilites();
                    break;
                case "Passer une commande pour un Client":
                    this.app.getVueAdmin().passerCommandeClient();
                    break;
                case "Transférer un livre":
                    this.app.getVueAdmin().transfererLivre();
                    break;
                case "Obtenir les factures":
                    this.app.getVueAdmin().choisirFactures();
                    break;
                default:
                    // Rien
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Vous pouvez ajouter une gestion d'erreur utilisateur ici si besoin
        }
    }
}
