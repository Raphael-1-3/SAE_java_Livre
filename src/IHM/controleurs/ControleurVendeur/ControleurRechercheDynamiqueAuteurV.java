package IHM.controleurs.ControleurVendeur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.BD.ActionBD;
import main.app.Auteur;
import main.app.Livre;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import IHM.vues.LivreExpress;
import IHM.vues.VueAdmin;
import IHM.vues.VueVendeur;

public class ControleurRechercheDynamiqueAuteurV {
    private ObservableList<Auteur> toutesLesValeurs;
    private  ActionBD modele;
    private  VueVendeur vue;
    private  LivreExpress app;
    private ListView<Auteur> listeSuggestions;
    

    public ControleurRechercheDynamiqueAuteurV(LivreExpress app, ActionBD modele) throws SQLException
    {
        this.app = app;
        this.modele = modele;
        this.vue = app.getVueVendeur();
        this.listeSuggestions = new ListView<>();
        this.toutesLesValeurs = FXCollections.observableArrayList(this.modele.getAllAuteur());

        initialiserRecherche();
    }

    private void initialiserRecherche() {
        this.app.getVueVendeur().getbarRecherche().textProperty().addListener((obs, ancienTexte, nouveauTexte) -> {
            if (nouveauTexte == null || nouveauTexte.isEmpty()) {
                listeSuggestions.getItems().clear();
            } else {
                String filtre = nouveauTexte.toLowerCase();
                List<Auteur> resultatFiltre = toutesLesValeurs.stream()
                                        .filter(aut -> aut.getNomAuteur().toLowerCase().contains(filtre))
                                        .collect(Collectors.toList());
                listeSuggestions.setItems(FXCollections.observableArrayList(resultatFiltre));

            }
        });

        listeSuggestions.setOnMouseClicked(e -> {
            Auteur selection = listeSuggestions.getSelectionModel().getSelectedItem();
            if (selection != null) {
                this.app.getVueVendeur().getbarRecherche().setText(selection.getNomAuteur());
                listeSuggestions.getItems().clear();
            }
        });
    


    listeSuggestions.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
        @Override
        protected void updateItem(Auteur item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.getNomAuteur()); 
            }
        }
    });

}

public ListView<Auteur> getListeSuggestions() {
    return this.listeSuggestions;
}
}