package org.example.controllers.Boutique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.models.Boutique.Commande;
import org.example.models.Boutique.Produit;
import org.example.services.Boutique.ServiceCommande;
import org.example.services.Boutique.ServiceProduit;
import org.example.utils.PDFGenerator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Store {


    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnMatch;

    @FXML
    private ComboBox<String> tri;

    @FXML
    private ListView<Commande> cart;

    @FXML
    private Label prixLabel;

    @FXML
    private GridPane produitContainer;

    @FXML
    private VBox vbox;
    @FXML
    private TextField rechercheField;

//    @FXML
//    private Button accueil;

    private final ServiceProduit serviceProduit = new ServiceProduit();
    private final ServiceCommande serviceCommande = new ServiceCommande();

    private List<Produit> produits;

    @FXML
    public void initialize() {
        ObservableList<String> triOptions = FXCollections.observableArrayList("...", "Prix croissant", "Prix décroissant");
        tri.setItems(triOptions);


        tri.setOnAction(this::trierProduits);

        try {
            produits = serviceProduit.afficherStore();
            afficherProduits();
            refreshCommandeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/AfficherProduit.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });

    }

    private void trierProduits(ActionEvent event) {
        String choixTri = tri.getValue();
        if (choixTri != null) {
            switch (choixTri) {
                case "Prix croissant":
                    Collections.sort(produits, Comparator.comparingInt(Produit::getPrixProduit));
                    break;
                case "Prix décroissant":
                    Collections.sort(produits, Comparator.comparingInt(Produit::getPrixProduit).reversed());
                    break;
                default:
                    break;
            }
            afficherProduits();
        }
    }

    public void naviguezVers(String URL) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(URL));
            btnAcceuil.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void afficherProduits() {
        produitContainer.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Boutique/Carte.fxml"));
                AnchorPane cardPane = fxmlLoader.load();
                Carte card = fxmlLoader.getController();
                card.setStoreController(this); // Définir une référence au contrôleur Store
                card.setData(produit);
                produitContainer.add(cardPane, column++, row);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                produitContainer.setMargin(cardPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Autres méthodes du contrôleur...


    public void refreshCommandeList() {
        try {
            ObservableList<Commande> observableList = FXCollections.observableArrayList(serviceCommande.afficher(1));
            cart.setItems(observableList);
            prixLabel.setText(String.valueOf(serviceCommande.calculSomme(1))+" DT");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void supprimer(ActionEvent event) {
        Commande selectedCommande = cart.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            try {

                int idCommande = selectedCommande.getIdCommande();
                serviceCommande.supprimer(idCommande);
                refreshCommandeList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void annuler(ActionEvent event) throws SQLException {
        serviceCommande.supprimerTout(1);
        this.refreshCommandeList();

    }
    @FXML
    void payer(ActionEvent event) throws SQLException {
        List<Commande> commandes = serviceCommande.afficher(1);

        String filePath = "liste_commandes.pdf";
        PDFGenerator.genererPDFCommandes(commandes, filePath);
        for (Commande com : commandes) {
            Produit produit = com.getProduit();
            int quantiteActuelle = produit.getQuantiteProduit();

            produit.setQuantiteProduit(quantiteActuelle -com.getQuantite());
            System.out.println(com.getQuantite());
            serviceProduit.modifier(produit);
            com.setProduit(produit);
        }
        this.annuler(event);
    }


    @FXML
    void chercher(ActionEvent event) {
        String nomProduitRecherche = rechercheField.getText();
        try {
            // Nettoyer le conteneur produitContainer
            produitContainer.getChildren().clear();

            // Filtrer les produits en fonction du nom donné
            List<Produit> produitsFiltres = serviceProduit.filtre(nomProduitRecherche);

            // Afficher les produits filtrés dans le produitContainer
            int column = 0;
            int row = 1;

            for (Produit produit : produitsFiltres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Carte.fxml"));
                AnchorPane cardPane = fxmlLoader.load();
                Carte card = fxmlLoader.getController();
                card.setStoreController(this);
                card.setData(produit);
                produitContainer.add(cardPane, column++, row);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                produitContainer.setMargin(cardPane, new Insets(10));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void election(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherElection.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1920,1080);
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.close();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    void joueur(ActionEvent event) {
//
//    }
//
//    @FXML
//    void match(ActionEvent event) {
//
//    }
//
//
//
//
//    @FXML
//    void signout(ActionEvent event) {
//
//    }
//    @FXML
@FXML
void accueil(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/afficherarticles.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,1920,1080);
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.close();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    @FXML
    void match(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/affichermatch.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1920,1080);
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.close();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void joueur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Employee/AffichageJoueur.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1920,1080);
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.close();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void signout(ActionEvent event) {

    }



}
