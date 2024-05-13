package org.example.controllers.Election.Election;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Election.Election;
import org.example.services.Election.ElectionService;
import org.example.utils.Session;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ModifierElectionController {



        @FXML
        private TextField posteTF;

        @FXML
        private TextField nomTF;
    @FXML
    private Button btnArticlles;

        @FXML
        private TextField periodeTF;

        @FXML
        private DatePicker dateTF;

        @FXML
        private Button chooseEimgModif;

        @FXML
        private ImageView imageModif;

    @FXML
    private Button btnAcceuil;

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

        private final ElectionService ps = new ElectionService();
        private String selectedImagePathModif;
        private String elpaaaathModif;
        Election election = new Election();



    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

   /*      @FXML
        public void modifierElection(ActionEvent actionEvent) {
            try {
                int id = Integer.parseInt(idTFM.getText());
                String nom = nomTFM.getText();
                LocalDate date = dateTFM.getValue();
                String description = descriptionTFM.getText();

                // Vérifiez si tous les champs nécessaires sont remplis
                if (nom.isEmpty() || date == null || description.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait();
                    return; // Arrêtez l'exécution si des champs sont vides
                }

                // Créez un objet Election avec les nouvelles données
                Election election = new Election(id, nom, date, description);

                // Utilisez le service ElectionService pour modifier l'élection
                ps.modifier(election);

                // Affichez une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("L'élection a été modifiée avec succès.");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                // Gestion de l'exception si la conversion de l'ID échoue
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("L'ID doit être un nombre entier.");
                alert.showAndWait();
            } catch (SQLException e) {
                // Gestion des erreurs SQL
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
*/
        int idElection;
        public void initializeValues(String nomE, Date dateE, String posteE, String periodeE, String imgEpath, int id) {


            nomTF.setText(nomE);
            dateTF.setValue(dateE.toLocalDate()); // Convert Date to LocalDate
            posteTF.setText(posteE);
            periodeTF.setText(periodeE);
            //imgEpath.setText(imgEpath);

           // String imagePath = election.getImgEpath();
            InputStream stream = getClass().getResourceAsStream("C:/Users/tun/Desktop/projet/Oussamaassal2.0/src/main/resources/Election/images/"+imgEpath);
            Image image = new Image(stream);
            imageModif.setImage(image);

            idElection=id;
        }

    public void initialize(URL location, ResourceBundle resources) {
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

    @FXML
    void chosirImageEModif(ActionEvent event) {
        Stage stage = (Stage) imageModif.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:/Users/yassi/IdeaProjects/ProjetAjbouni/ProjetPidev3A8/src/main/resources/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("All images", "*.str")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImagePathModif = selectedFile.getAbsolutePath();
            Path selectedPath = Paths.get(selectedImagePathModif);
            Path resourcePath = Paths.get("C:/Users/yassi/IdeaProjects/ProjetAjbouni/ProjetPidev3A8/src/main/resources/images");
            Path relativePath = resourcePath.relativize(selectedPath);
            String elpaaaathModif = "/images/"+relativePath;

            System.out.println("*****************************");
            System.out.println(selectedImagePathModif);
            System.out.println(selectedPath);
            System.out.println(resourcePath);
            System.out.println(relativePath);
            System.out.println(elpaaaathModif);
            System.out.println("******************************");

            election.setImgEpath(elpaaaathModif);

            // Update the ImageView with the selected image
            Image image = new Image("file:" + selectedImagePathModif);
            imageModif.setImage(image);            //System.out.println(image);


        }
    }

 /*   @FXML
    void modifierElection(ActionEvent event) {
        LocalDate date = dateTF.getValue();
        elpaaaathModif = election.getImgEpath();
        if(nomTF.getText().isEmpty()||posteTF.getText().isEmpty()||periodeTF.getText().isEmpty()|| date == null )
        {   Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("At least one field is empty ");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields !");
            alert.showAndWait();
        }
        else if(!nomTF.getText().matches("^[a-zA-Z]+$")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Election name must be all alphabetic characters");
            alert.setHeaderText(null);
            alert.setContentText("please fill this field only with alphabetic characters !");
            alert.showAndWait();

        }
        else if (!posteTF.getText().matches("^[a-zA-Z]+$")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Poste must be all alphabetic characters");
            alert.setHeaderText(null);
            alert.setContentText("please fill this field only with alphabetic characters !");
            alert.showAndWait();
        }
        else if(!periodeTF.getText().matches("^[a-zA-Z]+$")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Periode must be all alphabetic characters");
            alert.setHeaderText(null);
            alert.setContentText("please fill this field only with alphabetic characters !");
            alert.showAndWait();

        }
        else{
            try {
                System.out.println("111111111111111");
                System.out.println(elpaaaathModif);
                ps.modifier( nomTF.getText(),date,posteTF.getText(),
                        periodeTF.getText(),elpaaaathModif,idElection);
                System.out.println("2222222222222");


                switchToDisplayAllElections();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error while modifying a election");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                //throw new RuntimeException(e);
            }
        }



    }*/


    @FXML
    void modifierElection(ActionEvent event) {
        LocalDate date = dateTF.getValue();
        elpaaaathModif = election.getImgEpath();

        if (nomTF.getText().isEmpty() || posteTF.getText().isEmpty() || periodeTF.getText().isEmpty() || date == null || elpaaaathModif.isEmpty()) {
            showAlert("Please fill all the fields!");
        } else if (!nomTF.getText().matches("^[a-zA-Z]+$")) {
            showAlert("Election name must be all alphabetic characters");
        } else if (!posteTF.getText().matches("^[a-zA-Z]+$")) {
            showAlert("Poste must be all alphabetic characters");
        } else if (!periodeTF.getText().matches("\\d+ans")) {
            showAlert("Invalid Periode Format. Please enter a valid period in the format 'Xans', where X is a number.");
        } else {
            try {
                System.out.println("111111111111111");
                System.out.println(elpaaaathModif);
                ps.modifier(nomTF.getText(), date, posteTF.getText(),
                        periodeTF.getText(), elpaaaathModif, idElection);
                System.out.println("2222222222222");

                switchToDisplayAllElections();
                showSuccessMessage("Election modified successfully!");
            } catch (SQLException e) {
                handleSQLExceptionError(e, "Error while modifying an election");
            }
        }
    }


    // Helper method to show a standard alert
    private void showAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Helper method to show a success message
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to handle SQLException errors
    private void handleSQLExceptionError(SQLException e, String title) {
        String errorMessage;

        if (e.getErrorCode() == 1452 && e.getMessage().contains("foreign key")) {
            errorMessage = "Please check the Name of the Election. This name does not exist!";
        } else {
            errorMessage = e.getMessage();
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @FXML
        void naviguezMVersAffichage(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
                nomTF.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    public void switchToDisplayAllElections() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherElection.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) nomTF.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    }


