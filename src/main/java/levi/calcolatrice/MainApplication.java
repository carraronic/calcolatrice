package levi.calcolatrice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/main-view.fxml"));

        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        double w = screenBound.getWidth();
        double h = screenBound.getHeight();

        Scene scene = new Scene(fxmlLoader.load(), w*0.9, h*0.9);
        stage.getIcons().add(new Image(MainApplication.class.getResource("imgs/calc.png").toExternalForm()));

        stage.setTitle("Calcolatrice");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}