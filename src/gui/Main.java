package gui;

import info.Info;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //load .fxml to init GUI
        URL url = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        //get controller
        final Controller controller = fxmlLoader.getController();
        primaryStage.setTitle("Graphic Draw");
        primaryStage.setScene(new Scene(root, Info.SceneWidth, Info.SceneHeight));
        //init controller
        controller.initController(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
