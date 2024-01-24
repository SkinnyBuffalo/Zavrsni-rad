package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Krevet.Krevet;
import com.zavrsni.evidencijastudenata.Kvar.Kvar;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class TrenutniKvarovi implements Initializable {
    public static class KvarRow {
        private SimpleLongProperty idKvara;

        private SimpleStringProperty datumPrijaveKvara;
        private SimpleStringProperty opis;
        private SimpleStringProperty soba;
        private SimpleStringProperty objekt;

        public KvarRow(ResultSet resultSet) throws SQLException {
            this.idKvara = new SimpleLongProperty(resultSet.getLong("id_kvara"));
            this.datumPrijaveKvara = new SimpleStringProperty(resultSet.getString("datum_prijave_kvara"));
            this.opis = new SimpleStringProperty(resultSet.getString("opis_kvara"));
            this.soba = new SimpleStringProperty(resultSet.getString("broj_sobe"));
            this.objekt = new SimpleStringProperty(resultSet.getString("broj_objekta"));
        }

        public LongProperty idKvaraProperty() {
            return idKvara;
        }

        public StringProperty datumPrijaveKvaraProperty() {
            return datumPrijaveKvara;
        }

        public StringProperty opisProperty() {
            return opis;
        }

        public StringProperty sobaProperty() {
            return soba;
        }

        public StringProperty objektProperty() {
            return objekt;
        }

        public long getIdKvara() {
            return idKvara.get();
        }

        public void setIdKvara(long idKvara) {
            this.idKvara.set(idKvara);
        }

        public String getDatumPrijaveKvara() {
            return datumPrijaveKvara.get();
        }

        public void setDatumPrijaveKvara(String datumPrijaveKvara) {
            this.datumPrijaveKvara.set(datumPrijaveKvara);
        }

        public String getOpis() {
            return opis.get();
        }

        public void setOpis(String opis) {
            this.opis.set(opis);
        }

        public String getSoba() {
            return soba.get();
        }

        public void setSoba(String soba) {
            this.soba.set(soba);
        }

        public String getObjekt() {
            return objekt.get();
        }

        public void setObjekt(String objekt) {
            this.objekt.set(objekt);
        }
    }

    Stanar stanar=new Stanar();
    @FXML
    private TextField oibIseljenjeTextField;
    @FXML
    private TableColumn<KvarRow, String> idColumn;
    @FXML
    private TableColumn<KvarRow, String> datumPrijaveKvaraColumn;
    @FXML
    private TableColumn<KvarRow, String> opisColumn;
    @FXML
    private TableColumn<KvarRow, String> sobaColumn;
    @FXML
    private TableColumn<KvarRow, String> objektColumn;
    @FXML
    private TableView<KvarRow> tableView;
    Krevet krevet=new Krevet();
    Kvar kvar=new Kvar();
    public class BooleanToDaNeTableCell<S> extends TableCell<S, Boolean> {

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                setText(item ? "Da" : "Ne");
            }
        }
    }
    public class WrappedTextTableCell<S> extends TableCell<S, String> {
        private Text text;

        public WrappedTextTableCell() {
            text = new Text();
            text.wrappingWidthProperty().bind(widthProperty());
            setGraphic(text);
            setPadding(new Insets(5, 10, 5, 10));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setStyle("");
                text.setText(null);
            } else {
                setText(item);
                setWrapText(true);
                text.setText(item);
                TableRow currentRow = getTableRow();
                if (currentRow != null) {
                    currentRow.setPrefHeight(Control.USE_COMPUTED_SIZE);
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getTrenutniKvarovi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTrenutniKvarovi() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        String upit="SELECT `id_kvara`,`datum_prijave_kvara`,`opis_kvara`,`soba`.`broj_objekta`,`soba`.`broj_sobe` FROM `kvar` RIGHT JOIN `soba` ON `soba`.`id_sobe`= `kvar`.`id_sobe` WHERE `stanje_kvara`=0;";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Nema aktivnih kvarova");
        }
        else {
            ObservableList<KvarRow> data = FXCollections.observableArrayList();
            do {
                KvarRow kvarRow = new KvarRow(resultSet);
                data.add(kvarRow);
            } while (resultSet.next());


            idColumn.setCellValueFactory(new PropertyValueFactory<>("idKvara"));
            datumPrijaveKvaraColumn.setCellValueFactory(new PropertyValueFactory<>("datumPrijaveKvara"));
            opisColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
            opisColumn.setCellFactory(column -> new WrappedTextTableCell());
            sobaColumn.setCellValueFactory(new PropertyValueFactory<>("soba"));
            objektColumn.setCellValueFactory(new PropertyValueFactory<>("objekt"));


            tableView.setItems(data);

        }
    }
    @FXML
    public void handleSetPopravak(ActionEvent event) throws SQLException {
        KvarRow selectedRow = tableView.getSelectionModel().getSelectedItem();
        kvar.setIdKvara(selectedRow.getIdKvara());
        setPopravak();
    }


    public void setPopravak() {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();
        try{
            String upit = "UPDATE `kvar` SET `stanje_kvara`='1' WHERE `id_kvara`='"+kvar.getIdKvara()+"';";
            PreparedStatement preparedStatement = connection.prepareStatement(upit);
            preparedStatement.executeUpdate();
            popupAlert.showAlert(Alert.AlertType.INFORMATION, "Popravak", "Popravak je zapisan");
            getTrenutniKvarovi();
        }catch (SQLException e){
            popupAlert.showAlert(Alert.AlertType.ERROR, "Greška", "Greška pri spajanju na bazu podataka");
            e.printStackTrace();
        }

    }
    private TableView<KvarRow> createTableNode() {
        TableView<KvarRow> printTableView = new TableView<>();
        printTableView.getColumns().addAll(tableView.getColumns());
        printTableView.getItems().setAll(tableView.getItems());

        printTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        printTableView.layout();
        printTableView.setMinWidth(printTableView.getWidth());
        printTableView.setPrefWidth(printTableView.getWidth());
        printTableView.setMaxWidth(printTableView.getWidth());
        printTableView.setMinHeight(printTableView.getHeight());
        printTableView.setPrefHeight(printTableView.getHeight());
        printTableView.setMaxHeight(printTableView.getHeight());

        return printTableView;
    }

    @FXML
    private void handlePrintTable(ActionEvent event) {
        printTable();
    }
    private void printTable() {
        TableView<KvarRow> printTableView = createTableNode();

        // Create a new stage for the printTableView
        Stage printStage = new Stage();

        // Set the scene for the new stage
        Scene printScene = new Scene(printTableView);
        printStage.setScene(printScene);

        // Show the stage so it gets laid out and printed correctly
        printStage.show();

        // Use PrinterJob to print the contents of the stage
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(printTableView.getScene().getWindow())) {
            boolean success = printerJob.printPage(printTableView);
            if (success) {
                printerJob.endJob();
            }
        }

        // Hide the stage after printing
        printStage.hide();
    }
}
