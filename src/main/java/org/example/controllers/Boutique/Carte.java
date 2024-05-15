package org.example.controllers.Boutique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Boutique.Commande;
import org.example.models.Boutique.Produit;
import org.example.models.User.User;
import org.example.services.Boutique.ServiceCommande;
import org.example.services.Boutique.ServiceProduit;

import java.io.File;
import java.sql.SQLException;

public class Carte {

    @FXML
    private ImageView Image;

    @FXML
    private Label Prix;

    @FXML
    private Label Produit;

    @FXML
    private Spinner<Integer> Quantite;

    @FXML
    private ComboBox<String> Taille;

    @FXML
    private Button btnCommander;
    private Produit prod;
    private Image image;
    private int qty;
    private String taille = "";
    private Alert alert;

    private Store storeController;
    private User loggedInUser;
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }



    public void setStoreController(Store storeController) {
        this.storeController = storeController;
    }

    public void setData(Produit prod) {
        this.prod = prod;
        Produit.setText(prod.getNomProduit());
        Prix.setText(String.valueOf(prod.getPrixProduit()));
        String absolutePath = "C:/xampp/htdocs/Images/Produits/"+prod.getImage();
        File file = new File(absolutePath);
        image = new Image(file.toURI().toString(), 190, 94, false, true);
        Image.setImage(image);
        ObservableList<String> tailleOptions = FXCollections.observableArrayList("S", "M", "L", "XL", "UNI");
        Taille.setItems(tailleOptions);
        SpinnerValueFactory<Integer> quantite = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        Quantite.setValueFactory(quantite);
    }

    @FXML
    public void ajouterprod() throws SQLException {
        ServiceProduit sr = new ServiceProduit();
        if (Quantite.getValue() != null && Taille.getValue() != null) {
            qty = Quantite.getValue();
            taille = Taille.getValue();
            int somme = 0;
            int check = 0;
            Commande com = new Commande();
            check = sr.existence(taille, prod.getNomProduit());
            System.out.println(check);
            if (check < qty) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid. This product is Out of stock");
                alert.showAndWait();
            } else {
                if (qty == 0 || taille.equals("")) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Donner le nombre et la taille de produit a commender! ");
                    alert.showAndWait();
                } else {
                    ServiceCommande commande = new ServiceCommande();
                    com.setSomme(prod.getPrixProduit());
                    com.setUser(loggedInUser);
                    com.setProduit(prod);
                    com.setQuantite(qty);
                    if (commande.exist(com.getProduit().getId()) != 0) {
                        commande.modifier(com);
                    } else {
                        commande.ajouter(com);
                    }
                }
                if (storeController != null) {
                    storeController.refreshCommandeList();
                }
            }
        } else {
            System.out.println("Valeur du Spinner Quantite ou Taille est null.");
        }
    }
}
