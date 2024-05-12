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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.models.User.Reclamation;
import org.example.services.User.CrudReclamation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Tablereclamation implements Initializable {
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

    @javafx.fxml.FXML
    TableColumn<Reclamation, String> Coltitre;
    @javafx.fxml.FXML
    private TableColumn<Reclamation, String> colreclamation;
    @javafx.fxml.FXML
    private TableColumn<Reclamation, Boolean> coletat;
    @javafx.fxml.FXML
    private TableView<Reclamation> tablereclamation;
    private CrudReclamation us=new CrudReclamation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();

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
        try {
            Coltitre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
            colreclamation.setCellValueFactory(new PropertyValueFactory<>("Description"));
            coletat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
            coletat.setCellFactory(column -> {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {

                            if (!item) {

                                Button button = new Button("Traiter");
                                button.setTextFill(Color.WHITE);
                                button.setStyle("-fx-background-color: green;");
                                setGraphic(button);
                                button.setOnAction(event -> {
                                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                                    reclamation.setEtat(true);
                                    CrudReclamation cr=new CrudReclamation();
                                    cr.updateReclamation(reclamation);
                                    refresh();
                                    // Now you have access to the Reclamation object
                                });

                            }else{
                                Button button = new Button("Supprimer");
                                button.setTextFill(Color.WHITE);
                                button.setStyle("-fx-background-color: red;");
                                setGraphic(button);
                                button.setOnAction(event -> {
                                    Reclamation reclamation = getTableView().getItems().get(getIndex());

                                    CrudReclamation cr=new CrudReclamation();
                                    cr.deleteReclamation(reclamation.getIdUser());
                                    refresh();
                                    // Now you have access to the Reclamation object
                                });
                            }
                        }
                    }
                };
            });

            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(us.getAllReclamation());
            tablereclamation.setItems(reclamations);
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging purposes
        }
    }



    @javafx.fxml.FXML
    public void goTostat(ActionEvent actionEvent) { try{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/statReclamation.fxml"));
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


