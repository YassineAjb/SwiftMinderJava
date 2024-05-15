package org.example.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.example.models.User.User;
import org.example.services.User.Crud_user;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Crud implements Initializable {

    @FXML
    private Button btnBoutique;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;

    @javafx.fxml.FXML
    private Pane pnlOrders;
    Crud_user us = new Crud_user();
    @javafx.fxml.FXML
    private Pane pnlCustomer;
    @javafx.fxml.FXML
    private Button btnSignout1;
    @javafx.fxml.FXML
    private Pane pnlMenus;
    @javafx.fxml.FXML
    private Button AddButton;
    private Scene previousScene;
    @javafx.fxml.FXML
    private TableColumn mot_de_passe_col;
    @javafx.fxml.FXML
    private TableView tableuser;
    @javafx.fxml.FXML
    private TableColumn date_creationcol;
    @javafx.fxml.FXML
    private TableColumn emailcol;
    @javafx.fxml.FXML
    private TableColumn roleCol;
    @javafx.fxml.FXML
    private Button btnreturnSign;
    @javafx.fxml.FXML
    private ImageView downloadPdf;
    @javafx.fxml.FXML
    private TextField Search;
    private FilteredList<User> filteredList;
    private ObservableList<User> userList = FXCollections.observableArrayList();
    @javafx.fxml.FXML
    private TableColumn NumTel;
    @javafx.fxml.FXML
    private Button reclmationButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        mot_de_passe_col.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        date_creationcol.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        NumTel.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        refresh();
        filteredList = new FilteredList<>(userList, p -> true);

        // Attacher un écouteur d'événements au champ de recherche pour détecter les changements de texte
        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUsers(newValue);
        });

        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/Employe.fxml");
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
        btnReclamations.setOnAction(e -> {
            naviguezVers("/User/tablereclamation.fxml");
        });
        btnUsers.setOnAction(e -> {
            naviguezVers("/User/Crud.fxml");
        });
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });
    }
    @javafx.fxml.FXML
    public void AddButton(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User/afficherListeUser.fxml"));
            AddButton.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnElection.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
    public void refresh() {
        this.tableuser.setItems(us.afficher());
        this.tableuser.refresh();
    }

    @javafx.fxml.FXML
    public void exportToPdf(MouseEvent mouseEvent) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            if (printerJob.showPrintDialog(tableuser.getScene().getWindow())) {
                PageLayout pageLayout = printerJob.getPrinter().getDefaultPageLayout();
                double scaleX = pageLayout.getPrintableWidth() / tableuser.getBoundsInParent().getWidth();
                double scaleY = pageLayout.getPrintableHeight() / tableuser.getBoundsInParent().getHeight();
                double scale = Math.min(scaleX, scaleY);
                Scale printScale = new Scale(scale, scale);
                tableuser.getTransforms().add(printScale);
                boolean success = printerJob.printPage(tableuser);
                if (success) {
                    showSuccessMessage("Le PDF a été téléchargé avec succès !");
                    printerJob.endJob();
                }else{  showErrorMessage("Une erreur s'est produite lors du téléchargement du PDF.");}

                tableuser.getTransforms().remove(printScale); // Réinitialiser la transformation après l'impression
            }
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void returnSign(ActionEvent actionEvent) {
        try{

            Session.getSession().clearSession();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent root = loader.load();

            Login controller = loader.getController();
            controller.setPreviousScene(((Node)actionEvent.getSource()).getScene());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (
                IOException e) {
            System.err.println(e.getMessage());
        }

}
    private void filterUsers(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            refresh();
        } else {
            ObservableList<User> filteredList = FXCollections.observableArrayList();
            for (Object item : tableuser.getItems()) {
                User user = (User) item;
                if (user.getEmail().toLowerCase().contains(keyword.toLowerCase()) || user.getRole().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(user);
                }
            }
            tableuser.setItems(filteredList);
        }
    }

    @javafx.fxml.FXML
    public void Gotoreclamation(ActionEvent actionEvent) {
        try{


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/tablereclamation.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (
                IOException e) {
            System.err.println(e.getMessage());
        }



    }
}
