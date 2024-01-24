package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NoviStanar {
    @FXML
    private TextField oibTextField;
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    @FXML
    private TextField adresaTextField;
    @FXML
    private TextField ucilisteTextField;
    @FXML
    private TextArea komentarTextArea;
    @FXML
    private CheckBox subvencioniranostCheckBox;
    @FXML
    private CheckBox uplataTeretaneCheckBox;

    Stanar stanar = new Stanar();


    @FXML
    public void handleSetStanar(ActionEvent event){
        PopupAlert popupAlert = new PopupAlert();
        try{
            stanar.setOib(Long.parseLong(oibTextField.getText()));
            stanar.setOib(Long.parseLong(oibTextField.getText()));
            stanar.setIme(imeTextField.getText());
            stanar.setPrezime(prezimeTextField.getText());
            stanar.setDatumRodenja(LocalDate.parse(datumRodenjaDatePicker.getValue().toString(), DateTimeFormatter.ISO_DATE));
            stanar.setAdresaPrebivalista(adresaTextField.getText());
            stanar.setSubvencioniranost(subvencioniranostCheckBox.isSelected());
            stanar.setUciliste(ucilisteTextField.getText());
            stanar.setUplataTeretane(uplataTeretaneCheckBox.isSelected());
            stanar.setKomentar(komentarTextArea.getText());

            boolean success = setStanar();
            if (success) {
                oibTextField.clear();
                jmbagTextField.clear();
                imeTextField.clear();
                prezimeTextField.clear();
                datumRodenjaDatePicker.setValue(null);
                adresaTextField.clear();
                ucilisteTextField.clear();
                komentarTextArea.clear();
                subvencioniranostCheckBox.setSelected(false);
                uplataTeretaneCheckBox.setSelected(false);
                popupAlert.showAlert(Alert.AlertType.INFORMATION, "Success", "Stanar je pohranjen");
            } else {
                popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Došlo je doproblema, preovjerijte podatke i pokušajte ponovno");
            }

        } catch (NumberFormatException e){
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Došlo je doproblema, preovjerijte podatke koje ste unijeli, (na primjer slučajna slova u OIB-u ili prazna polja) te pokušajte ponovno");
        } catch (SQLException e) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Došlo je do problema prilikom pohrane podataka u bazu");
        }
    }
    public boolean setStanar() throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.connect();
        int subvencioniranostInt = stanar.isSubvencioniranost() ? 1 : 0;
        int uplatateretaneInt = stanar.isUplataTeretane() ? 1 : 0;
        String upit="INSERT INTO `stanar` (`oib`, `jmbag`, `ime`, `prezime`, `datum_rodenja`, `adresa_prebivalista`, `subvencioniranost`, `uciliste`, `uplata_teretane`, `komentar`) VALUES ('"+ stanar.getOib() +"', '"+stanar.getJmbag()+"', '"+stanar.getIme()+"', '"+stanar.getPrezime()+"', '"+stanar.getDatumRodenja()+"', '"+stanar.getAdresaPrebivalista()+"', '"+subvencioniranostInt+"', '"+stanar.getUciliste()+"', '"+uplatateretaneInt+"', '"+stanar.getKomentar()+"');";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows > 0;
    }
}
