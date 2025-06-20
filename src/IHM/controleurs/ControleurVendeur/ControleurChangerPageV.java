package IHM.controleurs.ControleurVendeur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

import IHM.vues.LivreExpress;
import IHM.vues.VueVendeur;
import main.BD.ActionBD;

public class ControleurChangerPageV implements EventHandler<ActionEvent> 
{
    private ActionBD modele;
    private LivreExpress app;
    private ComboBox<String> selectionAction;

    public ControleurChangerPageV(ActionBD m, LivreExpress app, ComboBox<String> selectionAction) 
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
                    this.app.getVueVendeur().creerVendeur();
                    break;
                case "Ajouter une librairie":
                    this.app.getVueVendeur().ajouterLibrairie();
                    break;
                case "Panneau de Bord":
                    this.app.getVueVendeur().panneauDeBord();
                    break;
                case "Ajouter un livre":
                    this.app.getVueVendeur().ajouterLivre();
                    break;
                case "Regarder les disponibilités":
                    this.app.getVueVendeur().regarderDisponibilites();
                    break;
                case "Passer une commande pour un Client":
                    this.app.getVueVendeur().passerCommandeClient();
                    break;
                case "Transférer un livre":
                    this.app.getVueVendeur().transfererLivre();
                    break;
                case "Obtenir les factures":
                    this.app.getVueVendeur().choisirFactures();
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
