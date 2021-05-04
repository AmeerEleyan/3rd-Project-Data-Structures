package GUI;

import Lists.LinkedList;
import Lists.Node;
import Project.Babys;
import Project.Frequency;
import Project.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Search {
    private static Button btSearch, btClose;
    private static TextField txtName, txtTotalFrequencyForTable;
    private static Label lblName, lblGender, lblTotalFrequencyForTable;
    private static RadioButton male, female;
    private static TableView<Frequency> frequencyTableView;

    public static void search() {
        // style for labels
        String styleLbl = "-fx-text-fill:#000000; -fx-background-color:#ffffff;-fx-font-weight: BOLd; -fx-font-size:15; ";

        //style for textFields
        String styleTxt = "-fx-background-color: #ffffff; -fx-border-width: 1px1px1px1px;" +
                " -fx-border-color: #000000; -fx-font-size:16; -fx-text-fill: #000000;";

        // Style for buttons
        String styleBt = "-fx-background-color: #ffffff;" + "-fx-font-size:18;-fx-border-width: 1; -fx-border-color: #000000;" +
                "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; ";

        // Style for hover buttons
        String styleHoverBt = "-fx-background-color: #000000; " + "-fx-font-size:18;-fx-border-width: 1; -fx-border-color: #000000;" +
                "-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman'; ";

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Average Frequencies of a Name");

        // gridPane for arrange labels and testFields
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #ffffff;");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(15);
        pane.setHgap(8);

        lblName = new Label("Name  ");
        lblName.setStyle(styleLbl);

        txtName = new TextField();
        txtName.setPromptText("Enter the name");
        txtName.setStyle(styleTxt);
        txtName.setMaxWidth(200);

        lblGender = new Label("Gander   ");
        lblGender.setStyle(styleLbl);


        male = new RadioButton("Male");
        male.setStyle("-fx-background-color: #ffffff;" + "-fx-border-radius: 10px;" +
                " -fx-font-size:14; -fx-font-weight: BOLd;");

        female = new RadioButton("Female");
        female.setStyle("-fx-background-color: #ffffff;" + "-fx-border-radius: 10px;" +
                " -fx-font-size:14; -fx-font-weight: BOLd;");


        // set the radio button as group
        ToggleGroup group = new ToggleGroup();
        male.setToggleGroup(group);
        female.setToggleGroup(group);


        // HBox for radioButton
        HBox paneRadio = new HBox(55);
        paneRadio.getChildren().addAll(male, female);
        paneRadio.setAlignment(Pos.TOP_CENTER);
        paneRadio.setPadding(new Insets(5, 5, 5, 0));
        paneRadio.setStyle("-fx-background-color: #ffffff");

        // button for close the window
        btClose = new Button("Close");
        btClose.setMinWidth(80);
        btClose.setStyle(styleBt);
        btClose.setOnMouseEntered(e -> btClose.setStyle(styleHoverBt));
        btClose.setOnMouseExited(e -> btClose.setStyle(styleBt));
        btClose.setOnAction(e -> window.close());

        btSearch = new Button("Search");
        btSearch.setMinWidth(80);
        btSearch.setStyle(styleBt);
        btSearch.setOnMouseEntered(e -> btSearch.setStyle(styleHoverBt));
        btSearch.setOnMouseExited(e -> btSearch.setStyle(styleBt));

        btSearch.setOnAction(e -> {
            if (!txtName.getText().isEmpty()) {

                if (Utilities.isName(txtName.getText().trim())) {

                    if (male.isSelected() || female.isSelected()) {
                        char gender;
                        if (male.isSelected()) gender = 'M';
                        else gender = 'F';
                        LinkedList<Frequency> frequencyLinkedList = Utilities.searchForBabys(new Babys(txtName.getText().trim(), gender));
                        if (frequencyLinkedList == null) {
                            Message.displayMessage("Warning", txtName.getText() + " Does Not exist ");
                            txtName.clear();
                            male.setSelected(false);
                            female.setSelected(false);
                        } else {
                            updateTable(frequencyLinkedList);
                        }

                    } else {
                        Message.displayMessage("Warning", " Please select the gender ");
                    }
                } else {
                    Message.displayMessage("Warning", " The name is invalid ");
                    txtName.clear();
                }


            } else {
                Message.displayMessage("Warning", " Please enter the name ");
            }
        });

        pane.add(lblName, 0, 0);
        pane.add(txtName, 1, 0);

        pane.add(lblGender, 0, 1);
        pane.add(paneRadio, 1, 1);

        // HBox for button
        HBox hBox = new HBox(60);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(btSearch, btClose);

        // VBox
        VBox vBox = new VBox(40);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setStyle("-fx-background-color: #ffffff;");
        vBox.getChildren().addAll(pane, hBox);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #ffffff;");
        borderPane.setPadding(new Insets(5, 5, 5, 5));
        borderPane.setRight(vBox);
        borderPane.setLeft(table());

        window.setScene(new Scene(borderPane));
        window.setMinWidth(380);
        window.setMinHeight(310);
        window.setResizable(false);
        window.show();
    }

    /**
     * Table view to display frequency record
     */
    public static TableView<Frequency> frequencyTableView() {

        frequencyTableView = new TableView<>();
        frequencyTableView.setEditable(false);

        frequencyTableView.setMaxWidth(300);
        frequencyTableView.setMinHeight(450);
        frequencyTableView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");


        // column for name of the baby
        TableColumn<Frequency, Integer> year = new TableColumn<>("Year");
        year.setMinWidth(150);
        year.setSortable(false);
        year.setResizable(false);
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        frequencyTableView.getColumns().add(year);


        // column for the gender of the baby
        TableColumn<Frequency, Integer> frequency = new TableColumn<>("Frequency");
        frequency.setMinWidth(150);
        frequency.setSortable(false);
        frequency.setResizable(false);
        frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        frequencyTableView.getColumns().add(frequency);


        return frequencyTableView;
    }

    /**
     * to view data in table view
     */
    public static void updateTable(LinkedList<Frequency> frequencyLinkedList) {

        if (!frequencyLinkedList.isEmpty()) {
            frequencyTableView.getItems().clear(); // clear data from table
            // get first node in the queue
            Node<Frequency> current = frequencyLinkedList.getHead();

            // total frequency in the table
            int totalFrequency = 0;

            while (current != null) {
                // add tempNode to the table
                frequencyTableView.getItems().add(current.getData()); // upload data to the table
                // increment totalFrequency
                totalFrequency += current.getData().getFrequency();

                current = current.getNext();
            }
            System.gc();
            txtTotalFrequencyForTable.setText(totalFrequency + "");
        } else {
            txtTotalFrequencyForTable.clear();
            frequencyTableView().getItems().clear(); // clear data from table
        }

    }

    private static VBox table() {

        txtTotalFrequencyForTable = new TextField();
        txtTotalFrequencyForTable.setMaxWidth(125);
        txtTotalFrequencyForTable.setEditable(false);
        txtTotalFrequencyForTable.setStyle("-fx-background-color:#ffffff; -fx-font-size:15;" +
                " -fx-border-width: 0px0px2px0px; -fx-border-color: #000000;" +
                " -fx-text-fill:#000000;  -fx-font-weight: BOLd;");
        lblTotalFrequencyForTable = new Label("Total Frequency");
        lblTotalFrequencyForTable.setStyle("-fx-text-fill:#000000; -fx-background-color:#ffffff;" +
                "-fx-font-weight: BOLd; -fx-font-size:15;");

        HBox hBox1 = new HBox(5);
        hBox1.setStyle("-fx-background-color: #ffffff;");
        hBox1.setAlignment(Pos.CENTER_LEFT);
        hBox1.setPadding(new Insets(5, 5, 5, 5));
        hBox1.setMargin(txtTotalFrequencyForTable, new Insets(0, 0, 10, 0));
        hBox1.getChildren().addAll(lblTotalFrequencyForTable, txtTotalFrequencyForTable);

        VBox layout = new VBox(15);
        layout.setStyle("-fx-background-color: #ffffff;");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.getChildren().addAll(frequencyTableView(), hBox1);

        return layout;
    }
}
