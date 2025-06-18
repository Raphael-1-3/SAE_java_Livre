package IHM.controleurs.ControleurAdmin;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.BD.ActionBD;
import main.app.Livre;
import IHM.vues.VueAdmin;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

public class ControleurAjouterLivre implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurAjouterLivre(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            TextField tfISBN = (TextField) vue.lookup("#tfISBN");
            TextField tfTitre = (TextField) vue.lookup("#tfTitre");
            TextField tfNbPages = (TextField) vue.lookup("#tfNbPages");
            TextField tfDatePubli = (TextField) vue.lookup("#tfDatePubli");
            TextField tfPrix = (TextField) vue.lookup("#tfPrix");
            long isbn = Long.parseLong(tfISBN.getText());
            String titre = tfTitre.getText();
            int nbpages = Integer.parseInt(tfNbPages.getText());
            int datepubli = Integer.parseInt(tfDatePubli.getText());
            double prix = Double.parseDouble(tfPrix.getText());
            Livre livre = new Livre(isbn, titre, nbpages, datepubli, prix);
            modele.AddLivre(livre);
            
        } catch (Exception e) {
            
        }
    }
}
