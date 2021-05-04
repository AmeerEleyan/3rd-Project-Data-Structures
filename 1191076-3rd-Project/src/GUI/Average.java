package GUI;

import Project.Babys;
import Project.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Average {

    private static Button btCalculate, btClose;
    private static TextField txtName, txtAverage;
    private static Label lblName, lblGender, lblAverage;
    private static RadioButton male, female;

    public static void displayAverage() {

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

        lblGender = new Label("Gander  ");
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

        lblAverage = new Label("Average  ");
        lblAverage.setStyle(styleLbl);

        txtAverage = new TextField();
        txtAverage.setEditable(false);
        txtAverage.setStyle(styleTxt);
        txtAverage.setMaxWidth(200);
        txtAverage.setPrefHeight(0);

        // button for close the window
        btClose = new Button("Close");
        btClose.setMinWidth(80);
        btClose.setStyle(styleBt);
        btClose.setOnMouseEntered(e -> btClose.setStyle(styleHoverBt));
        btClose.setOnMouseExited(e -> btClose.setStyle(styleBt));
        btClose.setOnAction(e -> window.close());

        btCalculate = new Button("Calculate");
        btCalculate.setMinWidth(80);
        btCalculate.setStyle(styleBt);
        btCalculate.setOnMouseEntered(e -> btCalculate.setStyle(styleHoverBt));
        btCalculate.setOnMouseExited(e -> btCalculate.setStyle(styleBt));

        btCalculate.setOnAction(e -> {
            if (!txtName.getText().isEmpty()) {

                if (Utilities.isName(txtName.getText().trim())) {

                    if (male.isSelected() || female.isSelected()) {
                        char gender;
                        if (male.isSelected()) gender = 'M';
                        else gender = 'F';
                        float avg = Utilities.averageFrequency(new Babys(txtName.getText().trim(), gender));
                        if (avg != -1) {
                            txtAverage.setText(String.format("%.2f", avg));
                        } else {
                            Message.displayMessage("Warning", txtName.getText() + " Does Not exist ");
                            txtName.clear();
                            txtAverage.clear();
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

        pane.add(lblAverage, 0, 2);
        pane.add(txtAverage, 1, 2);


        // HBox for button
        HBox hBox = new HBox(60);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(btCalculate, btClose);

        // VBox
        VBox vBox = new VBox(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setStyle("-fx-background-color: #ffffff;");

        vBox.getChildren().addAll(pane, hBox);

        window.setScene(new Scene(vBox));
        window.setMinWidth(380);
        window.setMinHeight(310);
        window.setResizable(false);
        window.show();
    }
}
