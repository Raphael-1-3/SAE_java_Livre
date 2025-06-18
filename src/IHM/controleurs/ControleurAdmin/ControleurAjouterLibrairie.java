package IHM.controleurs.ControleurAdmin;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.BD.ActionBD;
import main.app.Magasin;
import IHM.vues.VueAdmin;

public class ControleurAjouterLibrairie implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurAjouterLibrairie(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            // À adapter selon les champs de ta vue
            String nom = vue.getTfNom().getText();
            String ville = vue.getTfPrenom().getText(); // Remplace par le bon champ si besoin
            Magasin magasin = new Magasin(null, nom, ville);
            modele.AddLibrairie(magasin);
            // Afficher un message de succès
        } catch (Exception e) {
            // Afficher un message d'erreur
        }
    }
}
