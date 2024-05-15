package org.example.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.models.User.User;
import org.example.services.User.Crud_user;
import org.example.utils.Encryptor;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AfficherListeUser implements Initializable {

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
    private Button btnSignout;
    @FXML
    private TableView tableuser;
    @FXML
    private TableColumn roleCol;
    Crud_user us = new Crud_user();
    @FXML
    private TableColumn mot_de_passe_col;
    @FXML
    private TableColumn date_creationcol;
    @FXML
    private Button btnArticlles;
    @FXML
    private TableColumn emailcol;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;
    @FXML
    private TextField userrole;
    @FXML
    private TextField UserEmail;
    @FXML
    private DatePicker Datecreation;
    @FXML
    private TextField Password;
    @FXML
    private Label novalid2;
    @FXML
    private Label novalid;
    @FXML
    private Button modiferbutton;
    private boolean isAddingMode = true;
    private User selectedUserToUpdate;

    @FXML
    private ImageView Redirect;
    private Scene previousScene;
    @FXML
    private TableColumn NumTelCol;
    @FXML
    private TextField userNumTel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        mot_de_passe_col.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        date_creationcol.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        NumTelCol.setCellValueFactory(new PropertyValueFactory<>("NumTel"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //  tableuser.getColumns().addAll(roleCol,mot_de_passe_col,date_creationcol,cincol,emailcol);
        UserEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                novalid.setText("Invalid email format");
            } else {
                novalid.setText("");
            }
        });
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().matches("\\d{0,8}")) {
                    return change;
                }
            }
            return null;
        });
        userNumTel.setTextFormatter(formatter);
        refresh();
        // Créer une FilteredList à partir de la liste observable userList

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

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnElection.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void refresh() {
        this.tableuser.setItems(us.afficher());
        this.tableuser.refresh();
    }

    @FXML
    public void DeleteUseronClick(ActionEvent actionEvent) {

        Object selectedItem = tableuser.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof User) {
            User selectedUser = (User) selectedItem;
            int userId = selectedUser.getId();
            System.out.println("User ID to delete: " + userId);
            us.deleteUser(userId);
            refresh();
        } else {
            System.out.println("rien n'est selectionne");
        }
    }

    @FXML
    public void SaveUser(ActionEvent actionEvent) {
        String email = UserEmail.getText();
        String password = Password.getText();
        String role = userrole.getText();
        Date dateCreation = new Date();
        String NumTel=userNumTel.getText();
        String encryptedPassword = Encryptor.encryptPassword(password);

        if (role == null || role.isEmpty() || email.isEmpty() || password.isEmpty()) {
            novalid2.setText("All fields are required");
            return;
        }
        User user = new User(email, encryptedPassword, dateCreation, role, NumTel);
        if (isAddingMode) {
            // Mode ajout
            us.createUser(user);
            novalid2.setText("Nouvel utilisateur ajouté avec succès !");
        } else {
            user.setId(selectedUserToUpdate.getId());
            // Mode modification
            if (us.modifier(user)) {
                novalid2.setText("Utilisateur modifié avec succès !");
                selectedUserToUpdate = null; // Réinitialiser l'utilisateur sélectionné après modification


            }

        }
        refresh();


    }

    @FXML
    public void modiferUser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifyUser.fxml"));
            Parent root = loader.load();
            ModifyUser controller=loader.getController();
            User selectedItem = (User)tableuser.getSelectionModel().getSelectedItem();
            controller.setUser(selectedItem);
            modiferbutton.getScene().setRoot(root);



        } catch (IOException ex) {
            throw new RuntimeException(ex);
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


    @FXML
    public void returnToPreviousScene(MouseEvent event) {
      try{
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Crud.fxml"));
          Parent root = loader.load();

          Crud controller = loader.getController();
          controller.setPreviousScene(((Node) event.getSource()).getScene());

          Scene scene = new Scene(root);
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.setScene(scene);

    } catch (
    IOException e) {
        System.err.println(e.getMessage());
    }
    }
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

}















