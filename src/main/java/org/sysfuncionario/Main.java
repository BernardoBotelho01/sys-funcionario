package org.sysfuncionario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sysfuncionario.utils.PathFxml;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // carrega todo arquivo xml
        FXMLLoader fxmlLoader = new FXMLLoader();
        // criação do palco que e a janela
        Parent root = fxmlLoader.load(new FileInputStream(PathFxml.pathBase() + "\\main-view.fxml"));
        // criação da cena, e detro dela passo a janela, que e root
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("SysFuncionario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}