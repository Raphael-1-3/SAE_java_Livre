package IHM.controleurs.ControleurAdmin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import main.BD.ActionBD;
import main.app.Vendeur;

public class ControleurCreerVendeur implements EventHandler<ActionEvent> {
    private  ActionBD modele;
    private  VueAdmin vue;
    private  LivreExpress app;

    public ControleurCreerVendeur(ActionBD modele, LivreExpress app ) {
        this.modele = modele;
        this.app = app;
    }

    @Override
    public void handle(ActionEvent event) 
    {
        VueAdmin vue = this.app.getVueAdmin();
        try {
            String email = vue.getTfEmail().getText();
            String nom = vue.getTfNom().getText();
            String prenom = vue.getTfPrenom().getText();
            String mdp = vue.getPfMDP().getText();
            int idMagasin = Integer.parseInt(vue.getTfIdMag().getText());

            // Création du vendeur
            int id = this.modele.getIdUserMax();
            Vendeur vendeur = new Vendeur(id,email, nom, mdp, "VENDEUR",prenom,this.modele.magAPartirId(idMagasin));

            // Ajout à la base de données
            modele.AddVendeur(vendeur);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Vendeur créé avec succès !");
            alert.showAndWait();

            // vider les champs après création
            vue.getTfEmail().clear();
            vue.getTfNom().clear();
            vue.getTfPrenom().clear();
            vue.getPfMDP().clear();
            vue.getTfIdMag().clear();

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de créer le vendeur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}