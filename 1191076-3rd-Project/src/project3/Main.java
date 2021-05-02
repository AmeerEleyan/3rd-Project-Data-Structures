package project3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;


public class Main extends Application {
    Pane pane;
    Stage window;

    @Override

    public void start(Stage stage) throws Exception {
        window = stage;
        Button btUpload = new Button("Upload");
        pane = new Pane(btUpload);
        btUpload.setAlignment(Pos.CENTER);
        btUpload.setOnAction(e -> {
            upload();
        });
        window.setScene(new Scene(pane, 200, 200));
        window.show();
    }

    public void upload() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> list = fileChooser.showOpenMultipleDialog(window.getScene().getWindow());
        System.out.println("Upload");
        if (list != null) {
            for (File file : list) {
                System.out.println(file.getName());
            }
        }
        System.out.println("uploading");
    }
}
