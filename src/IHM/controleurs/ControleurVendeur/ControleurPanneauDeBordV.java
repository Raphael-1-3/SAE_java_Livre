package IHM.controleurs.ControleurVendeur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.BD.ActionBD;
import IHM.vues.VueAdmin;

public class ControleurPanneauDeBordV implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurPanneauDeBordV(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            
        } catch (Exception e) {
           
        }
    }
}
