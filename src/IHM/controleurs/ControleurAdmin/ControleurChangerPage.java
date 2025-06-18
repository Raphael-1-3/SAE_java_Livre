package IHM.controleurs.ControleurAdmin;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import IHM.vues.VueAdmin;
import main.BD.ActionBD;

public class ControleurChangerPage implements EventHandler<MouseEvent> 
{
    private ActionBD modele;
    private VueAdmin vue; // On utilise vueAdmin ici

    public ControleurChangerPage(ActionBD m, VueAdmin vue){
        this.modele = m;
        this.vue = vue;
    }

    @Override
    public void handle(MouseEvent event){
        Object source = event.getSource();
        if (source instanceof Button) {
            Button bouton = (Button) source;
            String texte = bouton.getText();

            switch (texte) {
                case "Creer un vendeur":
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
                case "Tranferer un livre":
                    vue.transfererLivre();
                    break;
                case "Obtenir les factures":
                    vue.obtenirFactures();
                    break;
                case "Choisir Un magasin":
                    vue.choisirMagasin();
                    break;
                default:
                    // Rien
            }
        }
    }
}