package IHM.controleurs.ControleurAdmin;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.BD.ActionBD;
import main.app.Client;
import main.app.Commande;
import main.app.Livre;
import main.app.Magasin;
import main.Exceptions.EmptySetException;
import main.Exceptions.PasDeTelUtilisateurException;
import IHM.vues.VueAdmin;

public class ControleurPasserCommandeClient implements EventHandler<MouseEvent> {
    private final ActionBD modele;
    private final VueAdmin vue;

    public ControleurPasserCommandeClient(ActionBD modele, VueAdmin vue) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            // Récupération des champs de la vue (voir VueAdmin.passerCommandeClient)
            TextField tfClient = (TextField) vue.lookup(".text-field");
            TextField tfLivre = (TextField) vue.lookupAll(".text-field").toArray()[1] instanceof TextField ? (TextField) vue.lookupAll(".text-field").toArray()[1] : null;
            TextField tfMagasin = (TextField) vue.lookupAll(".text-field").toArray()[2] instanceof TextField ? (TextField) vue.lookupAll(".text-field").toArray()[2] : null;

            String clientStr = tfClient != null ? tfClient.getText().trim() : "";
            String livreStr = tfLivre != null ? tfLivre.getText().trim() : "";
            String magasinStr = tfMagasin != null ? tfMagasin.getText().trim() : "";

            if (clientStr.isEmpty() || livreStr.isEmpty() || magasinStr.isEmpty()) {
                throw new Exception("Tous les champs doivent être remplis.");
            }

            // Client : format "Nom Prenom CodePostal"
            String[] parts = clientStr.split(" ");
            if (parts.length < 3) throw new Exception("Format du champ client incorrect (Nom Prenom CodePostal)");
            String nom = parts[0];
            String prenom = parts[1];
            int codePostal = Integer.parseInt(parts[2]);
            Client client = modele.getClientAPartirNomPrenomCodePostal(nom, prenom, codePostal);
            if (client == null) throw new Exception("Client introuvable");

            // Magasin
            Magasin mag = modele.magAPartirNom(magasinStr);
            if (mag == null) throw new Exception("Magasin introuvable");

            // Livre : on tente ISBN puis titre
            Livre livre = null;
            try {
                long isbn = Long.parseLong(livreStr);
                livre = modele.getLivreParId(isbn);
            } catch (NumberFormatException e) {
                // Pas un ISBN, on tente par titre
                try {
                    livre = modele.getLivreParTitre(livreStr);
                } catch (EmptySetException ex) {
                    throw new Exception("Livre introuvable");
                }
            }
            if (livre == null) throw new Exception("Livre introuvable");

            // Création de la commande (quantité 1 par défaut)
            Commande commande = new Commande(0, modele); // idcommande fictif, géré par la BD
            commande.ajouterArticle(livre, 1);

            modele.PasserCommande(client, commande, mag);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commande passée");
            alert.setHeaderText(null);
            alert.setContentText("La commande a bien été enregistrée.");
            alert.showAndWait();
        } catch (PasDeTelUtilisateurException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur client");
            alert.setHeaderText("Client non trouvé");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de passer la commande");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
