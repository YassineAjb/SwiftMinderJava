package org.example.controllers.Article;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Article.Article;
import org.example.models.Article.Sendsms;
import org.example.services.Article.ArticleService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;



public class AjouterArticle {
    ArticleService artic = new ArticleService() ;

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

    @FXML
    private Button btnafficher;

    @FXML
    private Button btnajouter;

    @FXML
    private Button btncalendrier;
    @FXML
    private Button importer ;


    @FXML
    private TextArea description;

    @FXML
    private TextField idJourNaliste;

    @FXML
    private Pane img;

    @FXML
    private TextField titre;
    @FXML
    private ImageView photoImageView;

    @FXML
    void afficherarticle(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Article/afficherarticles.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnAcceuil.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    String fileUpload = "" ;
    @FXML
    private void handleUploadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File initialDirectory = new File("D:\\swiftMinder2.0\\swiftMinder2.0\\src\\main\\resources\\images");
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            photoImageView.setImage(image);
            String photoFilePath = selectedFile.getAbsolutePath();
            fileUpload = photoFilePath ;
        }
    }
    private static final String ACCOUNT_SID = "ACd411a1b330f9b8d6c148c415ee1a4f3d";
    private static final String AUTH_TOKEN = "95e3ae77cc9c4539bc828161c3504f45";

    private static final String TWILIO_PHONE_NUMBER = "+19292961575";

    static {
        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void initialize(){
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
    }

    @FXML
    void ajouterArticle(ActionEvent event) {

        Sendsms sendsms = new Sendsms();
        Message.creator(new PhoneNumber("+21625506906"),
                new PhoneNumber(sendsms.getMynumber()), "Félicitation Article Ajouté Avec sucées ").create();
        try {
            String desc = description.getText();
            String tit = titre.getText();
            String idJournalisteText = idJourNaliste.getText();
//            path = imageView.getImage();



            // Vérifier si les champs sont vides
            if (desc.isEmpty() || tit.isEmpty() || idJournalisteText.isEmpty()) {
                // Afficher un message d'erreur
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;  // Sortir de la fonction si les champs sont vides
            }

            int idJournaliste = Integer.parseInt(idJournalisteText);
            Article article = new Article(tit, desc, fileUpload, idJournaliste);
            artic.add(article);

            Parent root = FXMLLoader.load(getClass().getResource("/afficherarticles.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);

        } catch (SQLException e) {
            e.getErrorCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en entier échoue
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un numéro valide pour le journaliste.");
            alert.showAndWait();
        }



    }
    // Method to send SMS using Twilio
    @FXML
    private void sendSms(String message) {
        // Replace TWILIO_PHONE_NUMBER with your Twilio phone number
        String TWILIO_PHONE_NUMBER = "+19292961575";

        // Replace recipientPhoneNumber with the actual recipient's phone number
        String recipientPhoneNumber = "+21625506906";

        com.twilio.rest.api.v2010.account.Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                message
        ).create();
    }

}
//    @FXML
//    void importer(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
//        File selectedFile = fileChooser.showOpenDialog(new Stage());
//        if (selectedFile != null) {
//            Image image = new Image(selectedFile.toURI().toString());
//            image.getClass();
//        }
//    }

