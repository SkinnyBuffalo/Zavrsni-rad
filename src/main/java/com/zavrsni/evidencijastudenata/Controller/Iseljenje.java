package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Boravak.Boravak;
import com.zavrsni.evidencijastudenata.Krevet.Krevet;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class Iseljenje {

    public static class StanarRow {
        private SimpleStringProperty ime;
        private SimpleStringProperty prezime;
        private SimpleLongProperty oib;
        private SimpleIntegerProperty idSobe;
        private SimpleIntegerProperty brojObjekta;
        private SimpleStringProperty komentar;
        public StanarRow(String ime, String prezime, long oib, int idSobe, int brojObjekta, String komentar) {
            this.ime = new SimpleStringProperty(ime);
            this.prezime = new SimpleStringProperty(prezime);
            this.oib = new SimpleLongProperty(oib);
            this.idSobe = new SimpleIntegerProperty(idSobe);
            this.brojObjekta = new SimpleIntegerProperty(brojObjekta);
            this.komentar = new SimpleStringProperty(komentar);
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
        public IntegerProperty idSobeProperty() {
            return idSobe;
        }

        public IntegerProperty brojObjektaProperty() {
            return brojObjekta;
        }

        public StringProperty komentarProperty() {
            return komentar;
        }
        @Override
        public String toString() {
            return "StanarRow{" +
                    "ime=" + ime.getValue() +
                    ", prezime=" + prezime.getValue() +
                    ", oib=" + oib.getValue() +
                    ", idSobe=" + idSobe.getValue() +
                    ", brojObjekta=" + brojObjekta.getValue() +
                    ", komentar=" + komentar.getValue() +
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

        public int getIdSobe() {
            return idSobe.get();
        }

        public void setIdSobe(int idSobe) {
            this.idSobe.set(idSobe);
        }

        public int getBrojObjekta() {
            return brojObjekta.get();
        }

        public void setBrojObjekta(int brojObjekta) {
            this.brojObjekta.set(brojObjekta);
        }

        public String getKomentar() {
            return komentar.get();
        }

        public void setKomentar(String komentar) {
            this.komentar.set(komentar);
        }
    }
    Stanar stanar=new Stanar();
    @FXML
    private TextField oibIseljenjeTextField;
    @FXML
    private TableColumn<StanarRow, String> imeTablica;
    @FXML
    private TableColumn<StanarRow, String> prezimeTablica;
    @FXML
    private TableColumn<StanarRow, Integer> oibTablica;
    @FXML
    private TableColumn<StanarRow, Integer> sobaTablica;
    @FXML
    private TableColumn<StanarRow, Integer> objektTablica;
    @FXML
    private TableColumn<StanarRow, String> komentarTablica;
    @FXML
    private TableView<StanarRow> tableView;
    Krevet krevet=new Krevet();
    @FXML
    public void handleGetStanarPoOibu(ActionEvent event) throws SQLException {

        stanar.setOib(Long.parseLong(oibIseljenjeTextField.getText()));
        getStanarPoOibu();
    }
    public void getStanarPoOibu() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        String upit="SELECT `ime`, `prezime`, `stanar`.`oib`, `soba`.`broj_sobe`, `soba`.`broj_objekta`, `komentar`,  `krevet`.`id_kreveta` FROM `stanar` LEFT JOIN `boravak` ON `stanar`.`oib` = `boravak`.`oib` LEFT JOIN `krevet` ON `boravak`.`id_kreveta` = `krevet`.`id_kreveta` LEFT JOIN `soba` ON `soba`.`id_sobe` = `krevet`.`id_sobe` WHERE `stanar`.`oib` = '"+stanar.getOib()+"' AND `boravak`.`datum_iseljenja` IS NULL";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Ne postoji stnar koji stanuje u domu s tim Oibom u bazi");
        }
        else {
            ObservableList<StanarRow> data = FXCollections.observableArrayList();
            do {
                StanarRow stanarRow = new StanarRow(
                        resultSet.getString("ime"),
                        resultSet.getString("prezime"),
                        resultSet.getLong("oib"),
                        resultSet.getInt("broj_sobe"),
                        resultSet.getInt("broj_objekta"),
                        resultSet.getString("komentar")
                );
                data.add(stanarRow);
                krevet.setIdKreveta(resultSet.getInt("id_kreveta"));
                stanar.setKomentar(resultSet.getString("komentar"));
            } while (resultSet.next());

            imeTablica.setCellValueFactory(new PropertyValueFactory<>("ime"));
            prezimeTablica.setCellValueFactory(new PropertyValueFactory<>("prezime"));
            oibTablica.setCellValueFactory(new PropertyValueFactory<>("oib"));
            sobaTablica.setCellValueFactory(new PropertyValueFactory<>("idSobe"));
            objektTablica.setCellValueFactory(new PropertyValueFactory<>("brojObjekta"));
            komentarTablica.setCellValueFactory(new PropertyValueFactory<>("komentar"));

            tableView.setItems(data);

            tableView.setFixedCellSize(27);
            tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(27));
        }
    }
    Boravak boravak=new Boravak();
    @FXML
    public void handleSetIseljenje(ActionEvent event) throws SQLException {
        PopupAlert popupAlert=new PopupAlert();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setHeaderText("Jeste li sigurni?");
        alert.setContentText("Pročitajte postoji li ikakvih napomena u komentaru: \n "+stanar.getKomentar());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            StanarRow firstStanarRow = tableView.getItems().get(0);
            stanar.setOib(firstStanarRow.getOib());
            setIseljenje();
            tableView.getItems().clear();
            oibIseljenjeTextField.clear();
            popupAlert.showAlert(Alert.AlertType.INFORMATION, "Iseljenje", "Stanar je iseljenj iz sobe");
        }
    }


    public void setIseljenje() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();

        String boravakUpdate = "UPDATE `boravak` SET `datum_iseljenja` = ? WHERE `id_kreveta` = ? AND `oib` = ? AND `datum_iseljenja` IS NULL";
        String krevetUpdate = "UPDATE `krevet` SET `zauzetost` = 0 WHERE `id_kreveta` = ?";


        // Prepare and execute the first update statement
        PreparedStatement boravakStmt = connection.prepareStatement(boravakUpdate);
        boravakStmt.setDate(1, Date.valueOf(LocalDate.now()));
        boravakStmt.setInt(2, krevet.getIdKreveta());
        boravakStmt.setLong(3, stanar.getOib());
        boravakStmt.executeUpdate();

// Prepare and execute the second update statement
        PreparedStatement krevetStmt = connection.prepareStatement(krevetUpdate);
        krevetStmt.setInt(1, krevet.getIdKreveta());
        krevetStmt.executeUpdate();
        krevetStmt.executeUpdate();
    }
}
