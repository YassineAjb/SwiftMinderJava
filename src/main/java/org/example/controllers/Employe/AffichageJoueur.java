package org.example.controllers.Employe;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.example.models.Employe.Joueur;
import org.example.services.Employe.ServiceJoueur;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageJoueur implements Initializable {

    private final ServiceJoueur serviceJoueur = new ServiceJoueur();
    @FXML
    private ScrollPane JoueurScroll;
    @FXML
    private GridPane gridJoueurs;
    @FXML
    private Button btnArticlles;
    @FXML
    private Button JoueurDetails;

    @FXML
    private Button btnAcceuil;
    @FXML
    private Button btncalendrier;

    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;


    @FXML
    private TextField searchAfficher;

    private List<Joueur> joueurs = new ArrayList<>();
    public static String getProjectPath() {
        Path currentPath = Paths.get("").toAbsolutePath();
        return currentPath.toString();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            joueurs = serviceJoueur.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        searchAfficher.setOnKeyTyped(e -> {
            try {
                filterItems(searchAfficher.getText(),joueurs);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        int column = 0;
        int row = 1;
        try {
            //joueurs.addAll(serviceJoueur.afficher());
            for(int i = 0; i < joueurs.size(); i++){
                BitMatrix matrix = new MultiFormatWriter().encode(joueurs.get(i).getLink(), BarcodeFormat.QR_CODE,500,500);
                MatrixToImageWriter.writeToPath(matrix,"png", Paths.get(getProjectPath() + "\\src\\main\\resources\\Employee\\QR\\"+joueurs.get(i).getId()+".png"));
                System.out.println("QR CODE GENERATED");
            }
            for (int i = 0; i < joueurs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Employee/JoueurRow.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setPrefHeight(1160);

                JoueurRowController joueurRowController = fxmlLoader.getController();
                joueurRowController.setData(joueurs.get(i));
                gridJoueurs.addRow(row++,anchorPane);

                gridJoueurs.setMinWidth(1140);
                gridJoueurs.setPrefWidth(1140);
                gridJoueurs.setMaxWidth(1140);

                /*gridJoueurs.setMinHeight(300);
                gridJoueurs.setPrefHeight(300);
                gridJoueurs.setMaxHeight(300);*/

                gridJoueurs.setMargin(anchorPane,new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

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


    }

    private void filterItems(String searchText, List<Joueur> list) throws SQLException {
        // If the search text is empty, display all original items
        if (searchText == null || searchText.isEmpty()) {
            list.addAll(serviceJoueur.afficher());
            return;
        }

        ObservableList<Joueur> filteredList = FXCollections.observableArrayList();
        for (Joueur item : serviceJoueur.afficher()) {
            if (item.getPosition().toLowerCase().equals(searchText.toLowerCase())) {
                filteredList.add(item);
            }
            if (item.getPiedfort().toLowerCase().equals(searchText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        list.addAll(filteredList);
    }


    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            gridJoueurs.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}

