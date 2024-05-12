package org.example.controllers.Boutique;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.models.Boutique.Produit;
import org.example.services.Boutique.ServiceProduit;
import java.io.IOException;
import java.sql.SQLException;

public class CarteDashBoard {

    @FXML
    private ImageView Image;

    @FXML
    private Label Prix;

    @FXML
    private Label Produit;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    private Produit prod;

    private AfficherProduit afficherProduitController;

    public void setData(Produit prod) {
        this.prod = prod;
        Produit.setText(prod.getNomProduit());
        Prix.setText(String.valueOf(prod.getPrixProduit()));
        Image.setImage(new Image("file:/C:/xampp/htdocs/Images/Produits/"+prod.getImage()));
    }

    @FXML
    void modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boutique/ModifierProduit.fxml"));
            Parent root = loader.load();

            ModifierProduit modifierProduitController = loader.getController();
            modifierProduitController.initData(prod);

            // Afficher la nouvelle scène
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Produit");
            stage.show();

            // Fermer la scène actuelle
            ((Stage) btnModifier.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        try {
            ServiceProduit serviceProduit = new ServiceProduit();
            serviceProduit.supprimer(prod.getId());
            // Rafraîchir l'affichage des produits après la suppression
            afficherProduitController.refreshProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour définir le contrôleur AfficherProduit
    public void setAfficherProduitController(AfficherProduit afficherProduitController) {
        this.afficherProduitController = afficherProduitController;
    }
}
