//package org.example.controllers.User;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import model.User;
//import services.Crud_user;
//import utils.DB;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//
//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
//        Parent root=fxmlLoader.load();
//        Scene scene = new Scene(root);
//        stage.setTitle("Affichage!");
//        stage.setScene(scene);
//        stage.show();
//    }
//    public static void main(String[] args) {
//        DB mydatabase= DB.getInstance();
//        User u = new User("yasmine.attia@esprit.tn","bizerte00" ,new Date(), "superviseur", "11169480");
//        User u2 = new User("salma.benhmida@esprit.tn","sfax1111" ,new Date(), "superviseur", "11169480");
//        User u3 = new User("maram.hlali@esprit.tn","0000" ,new Date(), "journaliste", "11178948");
//        Crud_user userCrud = new Crud_user();
//        //userCrud.createUser(u);
//        //userCrud.createUser(u3);
//       //userCrud.createUser(u2);//userCrud.createUser(u3);
//        userCrud.deleteUser(4);userCrud.deleteUser(5);userCrud.deleteUser(6);
//        List<User> users = userCrud.afficher();
//        // Afficher les utilisateurs dans la console
//        for (User user : users) {
//            System.out.println(user);
//        }
//        launch();
//    }
//
//}