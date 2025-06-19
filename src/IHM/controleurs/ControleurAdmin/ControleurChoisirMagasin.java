package IHM.controleurs.ControleurAdmin;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.BD.ActionBD;
import main.app.Magasin;
import IHM.vues.VueAdmin;
import javafx.scene.control.ComboBox;

public class ControleurChoisirMagasin implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurChoisirMagasin(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            // Supposons que la ComboBox des magasins a l'id "cbMagasins"
            ComboBox<String> cbMagasins = (ComboBox<String>) vue.lookup("#cbMagasins");
            String nomMagasin = cbMagasins.getValue();
            if (nomMagasin != null && !nomMagasin.isEmpty()) {
                Magasin magasin = modele.magAPartirNom(nomMagasin);
            }
            
        } catch (Exception e) {
            // Afficher un message d'erreur
        }
    }
}
