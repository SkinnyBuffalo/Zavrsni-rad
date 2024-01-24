package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Krevet.Krevet;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SviTrenutniStanari implements Initializable {
    public static class StanarRow {
        private SimpleStringProperty ime;
        private SimpleStringProperty prezime;
        private SimpleLongProperty oib;
        private StringProperty komentar;
        private SimpleLongProperty jmbag;
        private SimpleStringProperty datumRodenja;
        private SimpleStringProperty adresaPrebivalista;
        private SimpleStringProperty uciliste;
        private SimpleBooleanProperty uplataTeretane;
        private SimpleBooleanProperty subvencioniranost;

        public StanarRow(ResultSet resultSet) throws SQLException {
            this.ime = new SimpleStringProperty(resultSet.getString("ime"));
            this.prezime = new SimpleStringProperty(resultSet.getString("prezime"));
            this.oib = new SimpleLongProperty(resultSet.getLong("oib"));
            this.jmbag = new SimpleLongProperty(resultSet.getLong("jmbag"));
            this.datumRodenja = new SimpleStringProperty(resultSet.getString("datum_rodenja"));
            this.adresaPrebivalista = new SimpleStringProperty(resultSet.getString("adresa_prebivalista"));
            this.uciliste = new SimpleStringProperty(resultSet.getString("uciliste"));
            this.komentar = new SimpleStringProperty(resultSet.getString("komentar"));
            this.subvencioniranost = new SimpleBooleanProperty(resultSet.getBoolean("subvencioniranost"));
            this.uplataTeretane = new SimpleBooleanProperty(resultSet.getBoolean("uplata_teretane"));
        }
        public StringProperty imeProperty() {
            return ime;
        }

        public StringProperty prezimeProperty() {
            return prezime;
        }

        public LongProperty oibProperty() {
            return oib;
        }
        public LongProperty jmbagProperty() {
            return jmbag;
        }
        public StringProperty datumRodenjaProperty() {
            return datumRodenja;
        }

        public StringProperty adresaPrebivalistaProperty() {
            return adresaPrebivalista;
        }
        public StringProperty ucilisteProperty() {
            return uciliste;
        }
        public StringProperty komentarProperty() {
            return komentar;
        }

        public BooleanProperty subvencioniranostProperty() {
            return subvencioniranost;
        }

        public BooleanProperty uplataTeretaneProperty() {
            return uplataTeretane;
        }

        public String getIme() {
            return ime.get();
        }

        public void setIme(String ime) {
            this.ime.set(ime);
        }

        public String getPrezime() {
            return prezime.get();
        }

        public void setPrezime(String prezime) {
            this.prezime.set(prezime);
        }

        public long getOib() {
            return oib.get();
        }

        public void setOib(long oib) {
            this.oib.set(oib);
        }

        public String getKomentar() {
            return komentar.get();
        }

        public void setKomentar(String komentar) {
            this.komentar.set(komentar);
        }

        public long getJmbag() {
            return jmbag.get();
        }

        public void setJmbag(long jmbag) {
            this.jmbag.set(jmbag);
        }

        public String getDatumRodenja() {
            return datumRodenja.get();
        }

        public void setDatumRodenja(String datumRodenja) {
            this.datumRodenja.set(datumRodenja);
        }

        public String getAdresaPrebivalista() {
            return adresaPrebivalista.get();
        }

        public void setAdresaPrebivalista(String adresaPrebivalista) {
            this.adresaPrebivalista.set(adresaPrebivalista);
        }

        public String getUciliste() {
            return uciliste.get();
        }

        public void setUciliste(String uciliste) {
            this.uciliste.set(uciliste);
        }

        public boolean isUplataTeretane() {
            return uplataTeretane.get();
        }

        public void setUplataTeretane(boolean uplataTeretane) {
            this.uplataTeretane.set(uplataTeretane);
        }

        public boolean isSubvencioniranost() {
            return subvencioniranost.get();
        }

        public void setSubvencioniranost(boolean subvencioniranost) {
            this.subvencioniranost.set(subvencioniranost);
        }
    }
    Stanar stanar=new Stanar();
    @FXML
    private TextField oibIseljenjeTextField;
    @FXML
    private TableColumn<StanarRow, String> oibColumn;
    @FXML
    private TableColumn<StanarRow, String> jmbagColumn;
    @FXML
    private TableColumn<StanarRow, String> imeColumn;
    @FXML
    private TableColumn<StanarRow, String> prezimeColumn;
    @FXML
    private TableColumn<StanarRow, String> datumRodenjaColumn;
    @FXML
    private TableColumn<StanarRow, String> adresaPrebivalistaColumn;
    @FXML
    private TableColumn<StanarRow, String> ucilisteColumn;
    @FXML
    private TableColumn<StanarRow, String> komentarColumn;
    @FXML
    private TableColumn<StanarRow, String> subvencioniranostColumn;
    @FXML
    private TableColumn<StanarRow, String> teretanaColumn;
    @FXML
    private TableView<StanarRow> tableView;
    Krevet krevet=new Krevet();
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
            getSviStanariKojiBorave();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getSviStanariKojiBorave() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        String upit="SELECT * FROM `stanar` RIGHT JOIN `boravak` ON `boravak`.`oib` = `stanar`.`oib` WHERE `boravak`.`datum_iseljenja` IS NULL";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Greška", "Trenutačno nema stanara u domu");
        }
        else {
            ObservableList<StanarRow> data = FXCollections.observableArrayList();
            do {
                StanarRow stanarRow = new StanarRow(resultSet);
                data.add(stanarRow);
                krevet.setIdKreveta(resultSet.getInt("id_kreveta"));
                stanar.setKomentar(resultSet.getString("komentar"));
            } while (resultSet.next());

            oibColumn.setCellValueFactory(new PropertyValueFactory<>("oib"));
            jmbagColumn.setCellValueFactory(new PropertyValueFactory<>("jmbag"));
            imeColumn.setCellValueFactory(new PropertyValueFactory<>("ime"));
            prezimeColumn.setCellValueFactory(new PropertyValueFactory<>("prezime"));
            datumRodenjaColumn.setCellValueFactory(new PropertyValueFactory<>("datumRodenja"));
            adresaPrebivalistaColumn.setCellValueFactory(new PropertyValueFactory<>("adresaPrebivalista"));
            ucilisteColumn.setCellValueFactory(new PropertyValueFactory<>("uciliste"));

            komentarColumn.setCellValueFactory(new PropertyValueFactory<>("komentar"));
            komentarColumn.setCellFactory(column -> new WrappedTextTableCell());

            subvencioniranostColumn.setCellValueFactory(new PropertyValueFactory<>("subvencioniranost"));
            subvencioniranostColumn.setCellFactory(column -> new BooleanToDaNeTableCell());

            teretanaColumn.setCellValueFactory(new PropertyValueFactory<>("uplataTeretane"));
            teretanaColumn.setCellFactory(column -> new BooleanToDaNeTableCell());

            tableView.setItems(data);

        }
    }
    private TableView<StanarRow> createTableNode() {
        TableView<StanarRow> printTableView = new TableView<>();
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
        TableView<StanarRow> printTableView = createTableNode();

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
