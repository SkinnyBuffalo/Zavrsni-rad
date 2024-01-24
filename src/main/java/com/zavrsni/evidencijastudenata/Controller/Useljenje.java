package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Soba.Soba;
import com.zavrsni.evidencijastudenata.Krevet.Krevet;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Useljenje implements Initializable {


    public static class StanarRow {
        private SimpleStringProperty ime;
        private SimpleStringProperty prezime;
        private SimpleLongProperty oib;
        public StanarRow(String ime, String prezime, long oib) {
            this.ime = new SimpleStringProperty(ime);
            this.prezime = new SimpleStringProperty(prezime);
            this.oib = new SimpleLongProperty(oib);
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
        @Override
        public String toString() {
            return "StanarRow{" +
                    "ime=" + ime.getValue() +
                    ", prezime=" + prezime.getValue() +
                    ", oib=" + oib.getValue() +
                    '}';
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
    }
    @FXML
    private ChoiceBox katChoiceBox;
    @FXML
    private ChoiceBox objektChoiceBox;
    @FXML
    private TableColumn<StanarRow, String> imeUseljenjeTablica;
    @FXML
    private TableColumn<StanarRow, String> prezimeUseljenjeTablica;
    @FXML
    private TableColumn<StanarRow, Integer> oibUseljenjeTablica;
    @FXML
    private TableView<StanarRow> tableView;
    @FXML
    private TextField oibUseljenjeTextField;
    @FXML
    private ChoiceBox<String> choiceBoxUseljenjeTextField;
    Stanar stanar = new Stanar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imeUseljenjeTablica.setCellValueFactory(new PropertyValueFactory<StanarRow, String>("ime"));
        prezimeUseljenjeTablica.setCellValueFactory(new PropertyValueFactory<StanarRow, String>("prezime"));
        oibUseljenjeTablica.setCellValueFactory(new PropertyValueFactory<StanarRow, Integer>("oib"));

        for (int i = 1; i <= 5; i++) {
            objektChoiceBox.getItems().add(i);
        }

        objektChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            katChoiceBox.getItems().clear();
            if (newValue != null) {
                int value = (Integer) newValue;
                if (value == 1) {
                    for (int i = 1; i <= 9; i++) {
                        katChoiceBox.getItems().add(i);
                    }
                } else if (value == 2 || value == 3 || value == 4) {
                    for (int i = 1; i <= 3; i++) {
                        katChoiceBox.getItems().add(i);
                    }
                } else if (value == 5) {
                    for (int i = 0; i <= 5; i++) {
                        katChoiceBox.getItems().add(i);
                    }
                }
            }
        });
        katChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                handleGetSobeObjekta(null);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    public void handleGetStanarPoOibu(ActionEvent event) throws SQLException {

        stanar.setOib(Long.parseLong(oibUseljenjeTextField.getText()));
        getStanarPoOibu();
    }
    public void getStanarPoOibu() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        String upit="SELECT DISTINCT `stanar`.`ime`, `stanar`.`prezime`, `stanar`.`oib`\n" +
                "FROM `stanar`\n" +
                "WHERE `stanar`.`oib` = ? AND `stanar`.`oib` NOT IN (\n" +
                "    SELECT `boravak`.`oib` FROM `boravak` WHERE `boravak`.`datum_iseljenja` IS NULL\n" +
                ")";


        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        preparedStatement.setLong(1, stanar.getOib());
        ResultSet resultSet=preparedStatement.executeQuery();

        if(!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Ne postoji stnar s tim Oibom u bazi koji trenutačno ne boravi u sobi");
        }
        else {
            ObservableList<StanarRow> data = FXCollections.observableArrayList();
            do {
                StanarRow stanarRow = new StanarRow(
                        resultSet.getString("ime"),
                        resultSet.getString("prezime"),
                        resultSet.getLong("oib")
                );
                data.add(stanarRow);
            } while (resultSet.next());

            imeUseljenjeTablica.setCellValueFactory(new PropertyValueFactory<>("ime"));
            prezimeUseljenjeTablica.setCellValueFactory(new PropertyValueFactory<>("prezime"));
            oibUseljenjeTablica.setCellValueFactory(new PropertyValueFactory<>("oib"));

            tableView.setItems(data);

            tableView.setFixedCellSize(27);
            tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(27));

        }
    }


    Soba soba=new Soba();
    @FXML
    public void handleGetSobeObjekta(ActionEvent event) throws SQLException {
        Object selectedObjekt = objektChoiceBox.getValue();
        Object selectedKat = katChoiceBox.getValue();
        soba.setBrojObjekta((Integer) selectedObjekt);
        soba.setKatSobe((Integer) selectedKat);
        getSobeObjekta() ;
    }
    public void getSobeObjekta() throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.connect();
        PopupAlert popupAlert = new PopupAlert();

        String upit = "SELECT `id_kreveta`,`broj_kreveta`, `broj_sobe`, `soba`.`kat_sobe` FROM `krevet` RIGHT JOIN `soba` ON `krevet`.`id_sobe` = `soba`.`id_sobe` WHERE `soba`.`broj_objekta`='" + soba.getBrojObjekta() + "'AND `krevet`.`zauzetost` = 0 AND `soba`.`kat_sobe`='" + soba.getKatSobe() + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(upit);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Nema praznih soba u tom objektu i na tome katu");
        } else {
            // Clear the previous items in the ChoiceBox
            choiceBoxUseljenjeTextField.getItems().clear();

            // Add the items to the ChoiceBox
            do {
                String roomInfo = "Soba: " + resultSet.getString("broj_sobe") + ", Kat: " + resultSet.getString("kat_sobe") + ", Broj kreveta: " + resultSet.getString("broj_kreveta")+ ", ID kreveta: " + resultSet.getString("id_kreveta");
                choiceBoxUseljenjeTextField.getItems().add(roomInfo);
            } while (resultSet.next());
        }
    }
    Krevet krevet=new Krevet();
    @FXML
    public void handleSetBoravak(ActionEvent event) throws SQLException {
        if (tableView.getItems().isEmpty()) {
            // Show an error message if the TableView is empty
            PopupAlert popupAlert = new PopupAlert();
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Odaberite stanara.");
            return;
        }
        StanarRow firstStanarRow = tableView.getItems().get(0);
        stanar.setOib(firstStanarRow.getOib());
        String selectedRoomInfo = choiceBoxUseljenjeTextField.getSelectionModel().getSelectedItem();
        if (selectedRoomInfo == null) {
            // Show an error message if no item is selected in the ChoiceBox
            PopupAlert popupAlert = new PopupAlert();
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Odaberite sobu.");
            return;
        }
        krevet.setIdKreveta(Integer.parseInt(selectedRoomInfo.split(",")[3].split(":")[1].trim()));
        setBoravak();
    }

    public void setBoravak() throws SQLException {
        LocalDate datumUseljenja = LocalDate.now();
        Connect connect = new Connect();
        Connection connection = connect.connect();
        PopupAlert popupAlert = new PopupAlert();
        String insertQuery ="INSERT INTO `boravak` (`id_boravka`, `id_kreveta`, `oib`, `datum_useljenja`, `datum_iseljenja`) VALUES (NULL, '"+krevet.getIdKreveta()+"', '"+stanar.getOib()+"', '"+datumUseljenja+"', NULL);";
        String updateQuery ="UPDATE `krevet` SET `zauzetost` = 1 WHERE `krevet`.`id_kreveta` = '"+krevet.getIdKreveta()+"';";
        PreparedStatement preparedStatementInsert = connection.prepareStatement(insertQuery);
        PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateQuery);

        preparedStatementInsert.executeUpdate();
        preparedStatementUpdate.executeUpdate();
        clearFields();
        popupAlert.showAlert(Alert.AlertType.INFORMATION, "Uspješno useljenje", "Stanar je useljen u sobu.");
    }

    private void clearFields() {
        katChoiceBox.setValue(null);
        objektChoiceBox.setValue(null);
        tableView.getItems().clear();
        oibUseljenjeTextField.clear();
        choiceBoxUseljenjeTextField.setValue(null);

        // As TableColumn doesn't have 'clear' method, we can refresh the table to clear columns
        tableView.refresh();
    }

}
