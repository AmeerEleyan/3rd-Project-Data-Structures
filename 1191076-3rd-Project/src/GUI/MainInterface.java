package GUI;

import Lists.Node;
import Project.BabyForTraverse;
import Project.Babys;
import Project.Frequency;
import Project.Utilities;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;


public class MainInterface extends Application {
    private static Stage window;
    private static TableView<BabyForTraverse> babysTableView;
    private static TextField txtTotalFrequencyForTable, txtTotalFrequencyForFunctions, txtTotalRecord;
    private static Button btAverage, btNameOfMaxFreq, btExport, btBrowse, btSearch;
    private static Label lblTotalFrequencyForTable, lblTotalFrequencyForFunctions, lblTotalRecord;
    private static ComboBox<Integer> cbxYears;

    // Style for buttons
    private static String styleBt = "-fx-background-color:  #1aff1a; -fx-background-radius:35;" + "-fx-font-size:18;-fx-border-width: 1.5; -fx-border-color: #000000;" +
            "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; -fx-border-radius: 35; ";

    // Style for hover buttons
    private static String styleHoverBt = "-fx-background-color: #ffffff;-fx-background-radius:35; " + "-fx-font-size:18;-fx-border-width: 1.5; -fx-border-color: #000000;" +
            "-fx-text-fill:  #000000; -fx-font-family: 'Times New Roman';-fx-border-radius: 35; ";

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setScene(new Scene(allComponents()));
        Actions();
        window.setResizable(false);
        window.setTitle("Babys");
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
            updateTable();
            cbxYears.getItems().clear();
            Node<Integer> current = Utilities.years.getHead();
            while (current != null) {
                cbxYears.getItems().add(current.getData());
                current = current.getNext();
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
                Message.displayMessage("Warning", "  There are No records in the file " + fileName + " ");
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
    public static TableView<BabyForTraverse> babysTableView() {

        babysTableView = new TableView<>();
        babysTableView.setEditable(false);

        babysTableView.setMaxWidth(450);
        babysTableView.setMinHeight(450);
        babysTableView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");


        // column for name of the baby
        TableColumn<BabyForTraverse, String> name = new TableColumn<>("Name");
        name.setMinWidth(150);
        name.setSortable(false);
        name.setResizable(false);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        babysTableView.getColumns().add(name);


        // column for the gender of the baby
        TableColumn<BabyForTraverse, String> gender = new TableColumn<>("Gender");
        gender.setMinWidth(150);
        gender.setSortable(false);
        gender.setResizable(false);
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        babysTableView.getColumns().add(gender);


        // column for the frequency
        TableColumn<BabyForTraverse, Integer> frequency = new TableColumn<>("Frequency");
        frequency.setMinWidth(150);
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
            Node<BabyForTraverse> current = Utilities.BABYS_AVL_TREE.traverseLevelOrder().getHead();

            // total frequency in the table
            int totalFrequency = 0;

            while (current != null) {
                // add tempNode to the table
                babysTableView.getItems().add(current.getData()); // upload data to the table
                // increment totalFrequency
                totalFrequency += current.getData().getFrequency();

                current = current.getNext();
            }
            txtTotalFrequencyForTable.setText(totalFrequency + "");
            txtTotalRecord.setText(Utilities.BABYS_AVL_TREE.size() + "");
        } else {
            txtTotalRecord.clear();
            txtTotalFrequencyForTable.clear();
            babysTableView().getItems().clear(); // clear data from table
        }

    }

    public static VBox functions() {
        btSearch = new Button("Search");
        btSearch.setMinWidth(220);
        btSearch.setMinHeight(40);
        btSearch.setStyle(styleBt);
        btSearch.setOnMouseEntered(e -> {
            btSearch.setStyle(styleHoverBt);
        });
        btSearch.setOnMouseExited(e -> {
            btSearch.setStyle(styleBt);
        });

        btBrowse = new Button("Browse...");
        btBrowse.setMinWidth(220);
        btBrowse.setMinHeight(40);
        btBrowse.setStyle(styleBt);
        btBrowse.setOnMouseEntered(e -> {
            btBrowse.setStyle(styleHoverBt);
        });
        btBrowse.setOnMouseExited(e -> {
            btBrowse.setStyle(styleBt);
        });


        btAverage = new Button("Average");
        btAverage.setMinWidth(220);
        btAverage.setMinHeight(40);
        btAverage.setStyle(styleBt);
        btAverage.setOnMouseEntered(e -> {
            btAverage.setStyle(styleHoverBt);
        });
        btAverage.setOnMouseExited(e -> {
            btAverage.setStyle(styleBt);
        });

        btNameOfMaxFreq = new Button("Max Frequency");
        btNameOfMaxFreq.setMinWidth(220);
        btNameOfMaxFreq.setMinHeight(40);
        btNameOfMaxFreq.setStyle(styleBt);
        btNameOfMaxFreq.setOnMouseEntered(e -> {
            btNameOfMaxFreq.setStyle(styleHoverBt);
        });
        btNameOfMaxFreq.setOnMouseExited(e -> {
            btNameOfMaxFreq.setStyle(styleBt);
        });

        btExport = new Button("Export");
        btExport.setMinWidth(220);
        btExport.setMinHeight(40);
        btExport.setStyle(styleBt);
        btExport.setOnMouseEntered(e -> {
            btExport.setStyle(styleHoverBt);
        });
        btExport.setOnMouseExited(e -> {
            btExport.setStyle(styleBt);
        });


        lblTotalFrequencyForFunctions = new Label("Frequencies");
        lblTotalFrequencyForFunctions.setStyle("-fx-text-fill:#000000; -fx-background-color: #1aff1a;" +
                "-fx-font-weight: BOLd; -fx-font-size:15;");

        txtTotalFrequencyForFunctions = new TextField();
        txtTotalFrequencyForFunctions.setEditable(false);
        txtTotalFrequencyForFunctions.setVisible(false);
        txtTotalFrequencyForFunctions.setMaxWidth(220);
        txtTotalFrequencyForFunctions.setStyle("-fx-background-color: #1aff1a; -fx-font-size:15;" +
                "-fx-background-radius:35;-fx-border-radius: 35;" +
                " -fx-text-fill:#000000;  -fx-font-weight: BOLd;");
        cbxYears = new ComboBox<>();
        cbxYears.setStyle("-fx-background-color:  #1aff1a;" + "-fx-font-weight: BOLd;-fx-font-size:15;" +
                "-fx-background-radius:35;-fx-border-radius: 35;");
        cbxYears.setMinWidth(220);
        cbxYears.setMinHeight(0);
        cbxYears.setPromptText("Select the year:");


        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(cbxYears);

        VBox layout = new VBox(28.5);
        layout.setStyle("-fx-background-color: #ffffff;");
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setMargin(btBrowse, new Insets(4, 0, 0, 0));
        layout.getChildren().addAll(btBrowse, btSearch, btAverage, btNameOfMaxFreq, btExport, hBox, txtTotalFrequencyForFunctions);

        return layout;

    }

    private static VBox centerComponents() {
        VBox vBox = new VBox(10);
        vBox.setStyle("-fx-background-color: #ffffff;");
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));

        txtTotalFrequencyForTable = new TextField();
        txtTotalFrequencyForTable.setMaxWidth(125);
        txtTotalFrequencyForTable.setEditable(false);
        txtTotalFrequencyForTable.setStyle("-fx-background-color: #1aff1a; -fx-background-radius:35; -fx-font-size:15;" +
                " -fx-border-radius: 35;" + " -fx-text-fill:#000000;  -fx-font-weight: BOLd;");
        lblTotalFrequencyForTable = new Label("Total Frequency");
        lblTotalFrequencyForTable.setStyle("-fx-text-fill:#000000; -fx-background-color: #ffffff;" +
                "-fx-font-weight: BOLd; -fx-font-size:15;");


        txtTotalRecord = new TextField();
        txtTotalRecord.setMaxWidth(125);
        txtTotalRecord.setEditable(false);
        txtTotalRecord.setStyle("-fx-background-color: #1aff1a; -fx-background-radius:35; -fx-font-size:15;" +
                " -fx-border-radius: 35;" + " -fx-text-fill:#000000;  -fx-font-weight: BOLd;");
        lblTotalRecord = new Label("Total Recorde");
        lblTotalRecord.setStyle("-fx-text-fill:#000000; -fx-background-color:#ffffff;" +
                "-fx-font-weight: BOLd; -fx-font-size:15;");

        VBox vBox1 = new VBox(5);
        vBox1.setStyle("-fx-background-color: #ffffff;");
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPadding(new Insets(5, 5, 5, 5));
        vBox1.setMargin(txtTotalFrequencyForTable, new Insets(0, 0, 15, 0));
        vBox1.getChildren().addAll(lblTotalFrequencyForTable, txtTotalFrequencyForTable);

        VBox vBox2 = new VBox(5);
        vBox2.setStyle("-fx-background-color: #ffffff;");
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setPadding(new Insets(5, 5, 5, 5));
        vBox2.setMargin(txtTotalRecord, new Insets(0, 0, 15, 0));
        vBox2.getChildren().addAll(lblTotalRecord, txtTotalRecord);

        HBox hBox = new HBox(180);
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.getChildren().addAll(vBox1, vBox2);

        vBox.getChildren().addAll(babysTableView(), hBox);
        return vBox;
    }

    private static BorderPane allComponents() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #ffffff;");
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setRight(functions());
        pane.setLeft(centerComponents());
        return pane;

    }

    private static void Actions() {

        // Actions for browse button
        btBrowse.setOnAction(e -> {
            txtTotalFrequencyForFunctions.setVisible(false);
            txtTotalFrequencyForFunctions.clear();
            uploadFiles();
        });

        // Actions for Average button
        btAverage.setOnAction(e -> {
            txtTotalFrequencyForFunctions.setVisible(false);
            txtTotalFrequencyForFunctions.clear();
            Average.displayAverage();
        });

        // Actions for Search button
        btSearch.setOnAction(e -> {
            txtTotalFrequencyForFunctions.setVisible(false);
            txtTotalFrequencyForFunctions.clear();
            Search.search();
        });

        // Actions for Export button
        btExport.setOnAction(e -> {
            txtTotalFrequencyForFunctions.setVisible(false);
            txtTotalFrequencyForFunctions.clear();
            traverseToFile();
        });

        // Actions for NameOfMaxFreq button
        btNameOfMaxFreq.setOnAction(e -> {
            txtTotalFrequencyForFunctions.setVisible(false);
            txtTotalFrequencyForFunctions.clear();
            Frequency frequency = new Frequency();
            Babys babys = Utilities.nameOfMaxFrequency(frequency);
            if (babys == null) {
                Message.displayMessage("Warning", " There are no data ");
            } else {
                Message.displayMessage("Result", "Information for maximum frequency all over the years is:"
                        + "\nName: " + babys.getName() + "\nGender: " + babys.getGender() + "\nFrequency: " + frequency.getFrequency());
            }
        });

        // Actions for years comboBox
        cbxYears.setOnAction(e -> {
            int year = Utilities.totalNumberOfBabysInSelectedYear(cbxYears.getValue());
            if (year == -1)
                Message.displayMessage("Warning", " There are no data ");
            else {
                txtTotalFrequencyForFunctions.setVisible(true);
                txtTotalFrequencyForFunctions.setText("Total frequency: " + year);
            }
        });
    }

    /**
     * To print queue to the file(update)
     */
    public static void traverseToFile() {
        try {
            File file = new File("Babys.csv");
            PrintWriter writer = new PrintWriter(file);
            writer.println("Baby, Gender, TotalFrequency");
            writer.println(Utilities.BABYS_AVL_TREE.traverseLevelOrder().toString());
            writer.close();
            Message.displayMessage("Successfully", " Successfully exported to the file ");
        } catch (IOException exception) {
            Message.displayMessage("Warning", exception.getMessage());
        }
    }


}
