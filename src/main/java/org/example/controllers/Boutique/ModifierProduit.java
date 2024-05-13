package org.example.controllers.Boutique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Boutique.Produit;
import org.example.services.Boutique.ServiceProduit;
import org.example.utils.Session;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class ModifierProduit {

    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnMatch;

    private Image selectedImage;
    private Produit produit;

    @FXML
    private TextField NomProduit;

    @FXML
    private TextField Prix;

    @FXML
    private TextField Quantite;

    @FXML
    private ComboBox<String> Taille;

    @FXML
    private ComboBox<String> Type;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnImporter;
    @FXML
    private Button btnAnnuler;
    @FXML
    private ImageView imageView;
    private final ServiceProduit pr = new ServiceProduit();
    private int idProduit;
    @FXML
    void Annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Boutique/AfficherProduit.fxml"));
        primaryStage.setTitle("Ajouter Produit");
        primaryStage.setScene(new Scene(root,1920,1080));
        primaryStage.show();
    }


    @FXML
    void modifierProduit(ActionEvent event) throws IOException {
        if (NomProduit.getText().isEmpty() || Prix.getText().isEmpty() || Taille.getValue() == null || Type.getValue() == null || Quantite.getText().isEmpty()) {
            // Afficher une alerte si des champs obligatoires sont vides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return; // Sortir de la méthode
        }
        try{
            String image = produit.getImage();
            if(selectedImage != null){
                image = selectedImage.getUrl();
            }
            pr.modifier(new Produit(idProduit, Type.getValue(),NomProduit.getText(),Integer.parseInt(Prix.getText()),Taille.getValue(),Integer.parseInt(Quantite.getText()),image));
            System.out.println("helloo");
        }
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        Stage stage = (Stage) btnModifier.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Boutique/AfficherProduit.fxml"));
        primaryStage.setTitle("Ajouter Produit");
        primaryStage.setScene(new Scene(root,1920,1080));
        primaryStage.show();





    }
    public void initialize() {
        ServiceProduit serviceProduit = new ServiceProduit();
        ObservableList<String> tailleOptions = FXCollections.observableArrayList("S", "M", "L", "XL", "UNI");
        Taille.setItems(tailleOptions);
        ObservableList<String> typeOptions = FXCollections.observableArrayList("T-SHIRT", "CAPPUCHE", "SURVETTEMENT", "ACCESSOIR");
        Type.setItems(typeOptions);


        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnContrats.setOnAction(e -> {
            naviguezVers("/Employee/Contrat.fxml");
        });
        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/Store.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnArticlles.setOnAction(e -> {
            naviguezVers("/Article/afficherarticles.fxml");
        });
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });

    }
    public void initData(Produit selectedProduit) {
        this.produit = selectedProduit;
        idProduit = produit.getId();
        NomProduit.setText(produit.getNomProduit());
        Prix.setText(String.valueOf(produit.getPrixProduit()));
        Quantite.setText(String.valueOf(produit.getQuantiteProduit()));
        Taille.setValue(produit.getTailleProduit());
        Type.setValue(produit.getType());

    }
    @FXML
    void Importer(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File file = openFile.showOpenDialog(btnImporter.getScene().getWindow());
        if (file != null) {

            selectedImage = new Image(file.toURI().toString());
            imageView.setImage(selectedImage);
            String imagePath = file.toURI().toString(); // Obtenir le chemin de l'image sous forme d'URI

            System.out.println("Chemin de l'image: " + imagePath); // Afficher le chemin de l'image (pour le débogage)
        }

    }
    public void naviguezVers(String URL) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(URL));
            Quantite.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
