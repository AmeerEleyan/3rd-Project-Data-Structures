package GUI;

import Lists.AVL_Tree;
import Lists.Node;
import Project.BabyForTable;
import Project.Babys;
import Project.Utilities;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class MainInterface extends Application {
    private static Stage window;
    private static TableView<BabyForTable> babysTableView;
    private static TextField txtTotalFrequencyForTable, txtTotalFrequencyForFunctions;
    private static Button btAverage, btNameOfMaxFreq, btExport;
    private static Label lblTotalFrequency;
    private static ComboBox<Integer> years;

    // Style for buttons
    static String styleBt = "-fx-background-color: #ffffff;" + "-fx-font-size:18;-fx-border-width: 1.5; -fx-border-color: #000000;" +
            "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; ";

    // Style for hover buttons
    static String styleHoverBt = "-fx-background-color: #000000; " + "-fx-font-size:18;-fx-border-width: 1.5; -fx-border-color: #000000;" +
            "-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman'; ";

    @Override
    public void start(Stage stage) throws Exception {
        AVL_Tree<Babys> babysAVL_tree = new AVL_Tree<>();
        window = stage;
        Button btUpload = new Button("Upload");
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        btUpload.setAlignment(Pos.CENTER);
        Button btSerach = new Button("Search");
        btSerach.setOnAction(e -> {
            // System.out.println(Utilities.totalNumberOfBabysInSelectedYear(babysAVL_tree, 2001));
        });
        btUpload.setOnAction(e -> {
            //  uploadFiles(babysAVL_tree);
        });
        vBox.getChildren().addAll(btUpload, btSerach);
        window.setScene(new Scene(vBox, 200, 200));
        window.show();
    }

    public static void uploadFiles() {

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(window.getScene().getWindow());


        if (fileList != null) {
            for (File file : fileList) {
                int year = Utilities.getYearFromFileName(file.getName());
                Utilities.years.addBySort(year);
                if (year != 0)
                    readBabyRecordFromFile(file, year);
                else
                    Message.displayMessage("Warning", "The year is not defined in file " + file.getName());
            }
        }
    }


    /**
     * Methode to read data from file iteratively
     */
    public static void readBabyRecordFromFile(File fileName, int year) {
        try {
            Scanner input = new Scanner(fileName); // instance of scanner for read data from file
            if (fileName.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in file " + fileName + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        Utilities.BABYS_AVL_TREE.insert(new Babys(temp, year));
                        line++; // increment the line by one

                    } catch (Exception ex) {
                        System.out.println("Error " + line);
                        // the record in the file has a problem
                        Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + fileName + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + fileName + "  ");
        }
    }


    /**
     * Table view to display baby record
     */
    public static TableView<BabyForTable> babysTableView() {

        babysTableView = new TableView<>();
        babysTableView.setEditable(false);
        babysTableView.setMinWidth(350);
        babysTableView.setMinHeight(450);
        babysTableView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");


        // column for name of the baby
        TableColumn<BabyForTable, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setSortable(false);
        name.setResizable(false);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        babysTableView.getColumns().add(name);


        // column for the gender of the baby
        TableColumn<BabyForTable, Character> gender = new TableColumn<>("Gender");
        gender.setMinWidth(100);
        gender.setSortable(false);
        gender.setResizable(false);
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        babysTableView.getColumns().add(gender);


        // column for the frequency
        TableColumn<BabyForTable, Integer> frequency = new TableColumn<>("Frequency");
        frequency.setMinWidth(100);
        frequency.setSortable(false);
        frequency.setResizable(false);
        frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        babysTableView.getColumns().add(frequency);

        return babysTableView;
    }

    /**
     * to view data in table view
     */
    public static void updateTable() {

        if (!Utilities.BABYS_AVL_TREE.isEmpty()) {
            babysTableView.getItems().clear(); // clear data from table
            // get first node in the queue
            Node<Babys> curr = Utilities.BABYS_AVL_TREE.LevelOrder().getFirst();

            // temp node to display data to the table
            Node<BabyForTable> tempBabyForTable;

            // total frequency in the table
            int totalFrequency = 0;

            while (curr != null) {
                // create new node from current node to display it
                tempBabyForTable = new Node<>(new BabyForTable(curr.getData().getName(),
                        curr.getData().getGender(), curr.getData().getFrequency().getFrequency()));

                // add tempNode to the table
                babysTableView().getItems().add(tempBabyForTable.getData()); // upload data to the table
                // increment totalFrequency
                totalFrequency += curr.getData().getFrequency().getFrequency();

                curr = curr.getNext();
            }
            MainInterface.txtTotalFrequencyForTable.setText(totalFrequency + "");
        } else {
            txtTotalFrequencyForTable.clear();
            babysTableView().getItems().clear(); // clear data from table
        }

    }

    public static VBox functions() {
        btAverage = new Button("Average");
        btAverage.setStyle(styleBt);
        btAverage.setOnMouseEntered(e -> {
            btAverage.setStyle(styleHoverBt);
        });
        btAverage.setOnMouseExited(e -> {
            btAverage.setStyle(styleBt);
        });

        btNameOfMaxFreq = new Button("Max Frequency");
        btNameOfMaxFreq.setStyle(styleBt);
        btNameOfMaxFreq.setOnMouseEntered(e -> {
            btNameOfMaxFreq.setStyle(styleHoverBt);
        });
        btNameOfMaxFreq.setOnMouseExited(e -> {
            btNameOfMaxFreq.setStyle(styleBt);
        });

        btExport = new Button("Export");
        btExport.setStyle(styleBt);
        btExport.setOnMouseEntered(e -> {
            btExport.setStyle(styleHoverBt);
        });
        btExport.setOnMouseExited(e -> {
            btExport.setStyle(styleBt);
        });

        txtTotalFrequencyForTable = new TextField();

        lblTotalFrequency = new Label("Total Frequency");
        txtTotalFrequencyForFunctions = new TextField();

        years = new ComboBox<>();
        years.setPromptText("Select the year:");

        Node<Integer> current = Utilities.years.getHead();
        while (current != null) {
            years.getItems().add(current.getData());
            current = current.getNext();
        }

        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(lblTotalFrequency, years);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #ffffff;");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.getChildren().addAll(btAverage, btNameOfMaxFreq, btExport, hBox, txtTotalFrequencyForFunctions);

        return layout;

    }


}
