package project3;

import Lists.AVL_Tree;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class Main extends Application {
    Pane pane;
    Stage window;

    @Override

    public void start(Stage stage) throws Exception {
        window = stage;
        Button btUpload = new Button("Upload");
        pane = new Pane(btUpload);
        btUpload.setAlignment(Pos.CENTER);
        AVL_Tree<Babys> babysAVL_tree = new AVL_Tree<>();
        btUpload.setOnAction(e -> {
            upload(babysAVL_tree);
        });
        window.setScene(new Scene(pane, 200, 200));
        window.show();
    }

    public void upload(AVL_Tree<Babys> babysAVL_tree) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> list = fileChooser.showOpenMultipleDialog(window.getScene().getWindow());


        if (list != null) {

            System.out.println("Upload");
            for (File file : list) {
                // System.out.println(file.getName());
                int year = Utilities.getYearFromFileName(file.getName());
                //  if(year != 0) ;
                readPurchaseDataFromAFile(file, babysAVL_tree, year);
            }
            System.out.println("uploading");
        }
        babysAVL_tree.traverseLevelOrder();
        System.out.println(babysAVL_tree.size());

    }


    /**
     * Methode to read data from file iteratively
     */
    public void readPurchaseDataFromAFile(File fileName, AVL_Tree<Babys> babysAVL_tree, int year) {

        try {
            Scanner input = new Scanner(fileName); // instance of scanner for read data from file
            if (fileName.length() == 0) {
                // no data in file
                //   GUI.Message.displayMassage("Warning", "  There are No records in file " + fileName + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        babysAVL_tree.insert(new Babys(temp, year));
                        line++; // increment the line by one

                    } catch (Exception ex) {
                        System.out.println("Error " + line);
                        // the record in the file has a problem
                        // e.g. he does not have a company or The data arrangement is not in the right place
                        //    GUI.Message.displayMassage("Warning", " Invalid format in line # " + line + " in file " + fileName + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            //   GUI.Message.displayMassage("Error", " The system can NOT find the file " + fileName + "  ");
        }
    }
}
