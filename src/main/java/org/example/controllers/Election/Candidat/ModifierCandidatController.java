package org.example.controllers.Election.Candidat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Election.Candidat;
import org.example.services.Election.CandidatService;
import org.example.utils.Session;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ModifierCandidatController {
    @FXML
    private ImageView imageCModif;

    @FXML
    private Button btnAcceuil;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnBoutique;
    @FXML
    private Button btnTerrain;
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
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

    @FXML
    private Button btnSignout;
    @FXML
    private ImageView imageEModif;

    @FXML
    private TextField prenomTFCM;

    @FXML
    private Button btncalendrier;

    @FXML
    private TextField ageTFCM;

    @FXML
    private TextField nomTFCM;

    @FXML
    private TextField nomElectionTFCM;

    @FXML
    private Button btnAcceuil1;


    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnBoutique.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private final CandidatService ps = new CandidatService();
    int idELection;

    private String selectedImagePathModifC;
    private String elpaaaathModifC;
    Candidat candidat = new Candidat();
    int idCandidat;

    public void initializeValues(String nomC, String prenomC, int ageC, String imgCpath, int idC, int id) {


        nomTFCM.setText(nomC);
        prenomTFCM.setText(prenomC);
        ageTFCM.setText(String.valueOf(ageC));
        //nomElectionTFCM.setText(String.valueOf(ageC));
        //imgEpath.setText(imgEpath);
        idELection = id ;
        // String imagePath = election.getImgEpath();
        //InputStream stream = getClass().getResourceAsStream(imgCpath);
        //Image image = new Image(stream);
        //imageCModif.setImage(image);
        String imagePath ="C:/xampp/htdocs/Images/Candidats/" +imgCpath ;
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imageCModif.setImage(image);
        System.out.println("helllllllooo "+imagePath);

        idCandidat = idC;
    }


    @FXML
    void chosirImageCModif(ActionEvent event) {
        Stage stage = (Stage) imageCModif.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:/Users/yassi/OneDrive/Documents/JavaFx/src/main/resources/Election/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("All images", "*.str")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Define the target directory and create it if it doesn't exist
                Path targetDir = Paths.get("C:/xampp/htdocs/Images/Candidats");
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                // Define the target file path
                Path targetFilePath = targetDir.resolve(selectedFile.getName());

                // Move the file to the target directory
                Files.move(selectedFile.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Get the relative path for the candidate object
                Path relativePath = targetDir.relativize(targetFilePath);
                String elpaaaathC =relativePath.toString().replace("\\", "/");

                // Update the candidate object with the new image path
                candidat.setImgCpath(elpaaaathC);

                // Update the ImageView with the moved image
                Image image = new Image(targetFilePath.toUri().toString());
                imageCModif.setImage(image);

                System.out.println("*****************************");
                System.out.println("Selected file path: " + selectedFile.getAbsolutePath());
                System.out.println("Target file path: " + targetFilePath);
                System.out.println("Relative path: " + relativePath);
                System.out.println("Image path in candidate object: " + elpaaaathC);
                System.out.println("******************************");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    private boolean isAlphabetic(String input) {
        return input.matches("[a-zA-Z]+");
    }

    // Helper method to check if the age is within the specified range (25 to 99)
    private boolean isAgeValid(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            return age >= 25 && age <= 99;
        } catch (NumberFormatException e) {
            // If parsing fails, it's not a valid integer
            return false;
        }
    }
    @FXML
    void modifierCandidat(ActionEvent event) {
        elpaaaathModifC = candidat.getImgCpath();
        //int x;

        try {
            //x = ps.getIdEbynom(nomElectionTFCM.getText());
            //System.out.println("teeeeeeeeeeeeeest "+x);

            // Validate name (nomTFCM) and surname (prenomTFCM)
            if (isAlphabetic(nomTFCM.getText()) && isAlphabetic(prenomTFCM.getText())) {

                // Validate age (ageTFCM)
                if (isAgeValid(ageTFCM.getText())) {
                   // System.out.println("Value of x: " + x);

                    ps.modifierCC(nomTFCM.getText(), prenomTFCM.getText(), Integer.valueOf(ageTFCM.getText()), elpaaaathModifC,idELection, idCandidat
                    );
                    // Display a success message
                    showSuccessMessage("Candidat modified successfully!");
                    //switchToDisplayAllCandidats();
                    gobackListCandidat();
                } else {
                    // Display an alert if the age is not a valid integer or outside the range
                    showValidationError("Invalid age. Please enter a valid integer between 25 and 99.");
                }
            } else {
                // Display an alert if the name or surname is not alphabetic
                showValidationError("Invalid name or surname. Please enter alphabetical characters only.");
            }
        } catch (SQLException e) {
            String errorMessage;

            // Check if the SQL exception is related to a foreign key constraint
            if (e.getErrorCode() == 1452 && e.getMessage().contains("foreign key")) {
                errorMessage = "Please check the Name of the Election. This name does not exist!";
            } else {
                errorMessage = e.getMessage();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while modifying an election");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }
    void gobackListCandidat() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();

            AfficherCandidatController afficherCandidatController= loader.getController();
            afficherCandidatController.recupererIdE(idELection);
            System.out.println("+999999"+ idELection);


            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) nomTFCM.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void switchToDisplayAllCandidats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) nomTFCM.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Helper method to show validation error in an Alert
    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void goBack(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();

            AfficherCandidatController afficherCandidatController= loader.getController();
            afficherCandidatController.recupererIdE(idELection);
            System.out.println("+999999"+ idELection);


            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) nomTFCM.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/listeReservation.fxml");
        });
        btnTerrain.setOnAction(e -> {
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
}