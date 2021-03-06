/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cyann
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        MainController controler = loader.getController();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/styles/skin.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/styles/visual-source.css").toExternalForm());

        primaryStage.setTitle("Visual Source FX");
        primaryStage.setScene(scene);
        primaryStage.show();

        controler.setData();
    }

}
