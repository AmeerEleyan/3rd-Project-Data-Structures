/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 14/5/2021  12:11 AM
 */
package GUI;

import Lists.LinkedList;
import Lists.Node;
import Project.BabyForTraverse;
import Project.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class TraverseSelectedYear {
    private static TableView<BabyForTraverse> babysTableView;
    private static TextField txtTotalFrequencyForTable, txtTotalRecord;
    private static Label lblTotalFrequencyForTable, lblTotalRecord;

    private TraverseSelectedYear() {

    }

    public static void traverseSelectedYear(int year) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Babys in " + year);
        window.setScene(new Scene(centerComponents()));
        updateTable(year);
        window.setMinWidth(475);
        window.setMinHeight(450);
        window.setResizable(false);
        window.show();

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
    public static void updateTable(int year) {

        LinkedList<BabyForTraverse> babyForTraverseLinkedList = new LinkedList<>();
        int totalBabysFrequency = Utilities.traverseSelectedYear(year, babyForTraverseLinkedList);

        if (totalBabysFrequency == -1)
            Message.displayMessage("Warning", " There are no data ");
        else {
            // clear data from table
            babysTableView.getItems().clear();

            // get first node in the queue
            Node<BabyForTraverse> current = babyForTraverseLinkedList.getHead();

            while (current != null) {
                // add tempNode to the table
                babysTableView.getItems().add(current.getData()); // upload data to the table

                current = current.getNext();
            }
            txtTotalFrequencyForTable.setText(totalBabysFrequency + "");
            txtTotalRecord.setText(babyForTraverseLinkedList.length() + "");

        }

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
}
