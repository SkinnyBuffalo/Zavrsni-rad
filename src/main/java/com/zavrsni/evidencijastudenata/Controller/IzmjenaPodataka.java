package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Krevet.Krevet;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class IzmjenaPodataka {
    Stanar stanar=new Stanar();
    @FXML
    private TextField oibTextField;
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField datumRodenjaTextField;
    @FXML
    private TextField adresaPrebivalistaTextField;
    @FXML
    private TextField ucilisteTextField;
    @FXML
    private TextArea komentarTextArea;
    @FXML
    private CheckBox subvencioniranostBox;
    @FXML
    private CheckBox teretanaBox;

    Krevet krevet=new Krevet();
    @FXML
    public void handleGetStanarPoOibu(ActionEvent event) throws SQLException {
        stanar.setOib(Long.parseLong(oibTextField.getText()));
        getStanarPoOibu();
    }
    public void getStanarPoOibu() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        String upit="SELECT * FROM `stanar` WHERE `oib` = '"+stanar.getOib()+"'";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Ne postoji stanar koji stanuje u domu s tim Oibom u bazi");
        } else {
            do {

                stanar.setOib(resultSet.getLong("oib"));
                stanar.setJmbag(resultSet.getLong("jmbag"));
                stanar.setIme(resultSet.getString("ime"));
                stanar.setPrezime(resultSet.getString("prezime"));
                stanar.setDatumRodenja(resultSet.getDate("datum_rodenja").toLocalDate());
                stanar.setAdresaPrebivalista(resultSet.getString("adresa_prebivalista"));
                stanar.setSubvencioniranost(resultSet.getBoolean("subvencioniranost"));
                stanar.setUciliste(resultSet.getString("uciliste"));
                stanar.setUplataTeretane(resultSet.getBoolean("uplata_teretane"));
                stanar.setKomentar(resultSet.getString("komentar"));
            } while (resultSet.next());

            oibTextField.setText(Long.toString(stanar.getOib()));
            jmbagTextField.setText(Long.toString(stanar.getJmbag()));
            imeTextField.setText(stanar.getIme());
            prezimeTextField.setText(stanar.getPrezime());
            datumRodenjaTextField.setText(stanar.getDatumRodenja().toString());
            adresaPrebivalistaTextField.setText(stanar.getAdresaPrebivalista());
            ucilisteTextField.setText(stanar.getUciliste());
            komentarTextArea.setText(stanar.getKomentar());
            subvencioniranostBox.setSelected(stanar.isSubvencioniranost());
            teretanaBox.setSelected(stanar.isUplataTeretane());
        }
    }
    @FXML
    public void handleUpdateStanar(ActionEvent event) throws SQLException {
        stanar.setOib(Long.parseLong(oibTextField.getText()));
        updateStanar();
    }
    public void updateStanar() throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        stanar.setOib(Long.parseLong(oibTextField.getText()));
        stanar.setJmbag(Long.parseLong(jmbagTextField.getText()));
        stanar.setIme(imeTextField.getText());
        stanar.setPrezime(prezimeTextField.getText());
        stanar.setDatumRodenja(Date.valueOf(datumRodenjaTextField.getText()).toLocalDate());
        stanar.setAdresaPrebivalista(adresaPrebivalistaTextField.getText());
        stanar.setUciliste(ucilisteTextField.getText());
        stanar.setKomentar(komentarTextArea.getText());
        stanar.setSubvencioniranost(subvencioniranostBox.isSelected());
        stanar.setUplataTeretane(teretanaBox.isSelected());

        String upit = "UPDATE `stanar` SET " +
                "`jmbag` = '"+stanar.getJmbag()+"', " +
                "`ime` = '"+stanar.getIme()+"', " +
                "`prezime` =  '"+stanar.getPrezime()+"', " +
                "`datum_rodenja` =  '"+stanar.getDatumRodenja()+"', " +
                "`adresa_prebivalista` =  '"+stanar.getAdresaPrebivalista()+"', " +
                "`uciliste` =  '"+stanar.getUciliste()+"', " +
                "`komentar` =  '"+stanar.getKomentar()+"', " +
                "`subvencioniranost` =  '"+(stanar.isSubvencioniranost() ? 1 : 0)+"', " +
                "`uplata_teretane` =  '"+(stanar.isUplataTeretane() ? 1 : 0) +"'" +
                "WHERE `oib` =  '"+stanar.getOib()+"'";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        int affectedRows = preparedStatement.executeUpdate();
        if(affectedRows == 0) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Ne postoji stanaru domu s tim Oibom u bazi");
        } else {
            popupAlert.showAlert(Alert.AlertType.INFORMATION, "Promijenjeni podaco", "Novo uneseni podaci su pohranjeni");
        }
    }

}
