package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Kvar.Kvar;
import com.zavrsni.evidencijastudenata.Soba.Soba;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class NoviKvar implements Initializable {
    @FXML
    private ChoiceBox<Integer> objektChoiceBox;

    @FXML
    private ChoiceBox<Integer> sobaChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();
        for (int i = 1; i <= 5; i++) {
            objektChoiceBox.getItems().add(i);
        }
        objektChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sobaChoiceBox.getItems().clear();

            String upit = "SELECT `broj_sobe` FROM `soba` WHERE `broj_objekta`=" + newValue + ";";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(upit);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int brojSobe = resultSet.getInt("broj_sobe");
                    sobaChoiceBox.getItems().add(brojSobe);
                }
            } catch (SQLException e) {
                popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Problem pri spajanju na bazu");
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    private TextArea opisKvaraTextArea;
    Kvar kvar=new Kvar();
    Soba soba=new Soba();
    @FXML
    public void handleSetKvar(ActionEvent event){
        Connect connect = new Connect();
        Connection connection = connect.connect();
        PopupAlert popupAlert = new PopupAlert();
        kvar.setOpisKvara(opisKvaraTextArea.getText());
        kvar.setDatumPrijaveKvara(LocalDate.now());
        soba.setBrojSobe(sobaChoiceBox.getValue());
        soba.setBrojObjekta(objektChoiceBox.getValue());
        try{
            String upit="SELECT `id_sobe` FROM `soba` WHERE `broj_sobe`= '"+soba.getBrojSobe()+"' AND `broj_objekta` ='"+soba.getBrojObjekta()+"';";
            PreparedStatement preparedStatement=connection.prepareStatement(upit);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()) {
                kvar.setIdSobe(resultSet.getLong("id_sobe"));
                setKvar();
            }

        }
        catch (SQLException e){
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Problem pri spajanju na bazu");
            throw new RuntimeException(e);
        }


    }
    public void setKvar() throws SQLException {
        PopupAlert popupAlert = new PopupAlert();
        Connect connect = new Connect();
        Connection connection = connect.connect();
        int StanjeKvaraInt = 0;
        String upit="INSERT INTO `kvar` (`id_kvara`, `datum_prijave_kvara`, `opis_kvara`, `stanje_kvara`, `id_sobe`) VALUES (NULL, '"+kvar.getDatumPrijaveKvara().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"', '"+kvar.getOpisKvara()+"', '"+StanjeKvaraInt+"', '"+kvar.getIdSobe()+"');";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        int affectedRows = preparedStatement.executeUpdate();
        if(affectedRows > 0) popupAlert.showAlert(Alert.AlertType.INFORMATION, "Evidentirano", "Kvar je evidentiran u bazu");
    }
}
