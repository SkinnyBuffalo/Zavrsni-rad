package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Krevet.Krevet;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class PodaciDvaStudenta {
    Stanar stanar=new Stanar();
    Stanar stanar2 = new Stanar();
    @FXML
    private Label oibTextLabel;
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private DatePicker datumRodenjaTextField;
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
    @FXML
    private Label oibTextLabel2;
    @FXML
    private TextField jmbagTextField2;
    @FXML
    private TextField imeTextField2;
    @FXML
    private TextField prezimeTextField2;
    @FXML
    private DatePicker datumRodenjaTextField2;
    @FXML
    private TextField adresaPrebivalistaTextField2;
    @FXML
    private TextField ucilisteTextField2;
    @FXML
    private TextArea komentarTextArea2;
    @FXML
    private CheckBox subvencioniranostBox2;
    @FXML
    private CheckBox teretanaBox2;


    Krevet krevet=new Krevet();

    public void setParameters(int brojObjekta, int katSobe, int brojSobe)  throws SQLException {
        Connect connect=new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        String upit="SELECT * FROM `stanar` RIGHT JOIN `boravak` ON `stanar`.`oib`=`boravak`.`oib` RIGHT JOIN `krevet` ON `krevet`.`id_kreveta`=`boravak`.`id_kreveta` RIGHT JOIN `soba` ON `soba`.`id_sobe`=`krevet`.`id_sobe` WHERE `soba`.`broj_objekta`='"+brojObjekta+"' AND `soba`.`kat_sobe`='"+katSobe+"' AND `soba`.`broj_sobe`='"+brojSobe+"'";
        PreparedStatement preparedStatement=connection.prepareStatement(upit);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(!resultSet.next()) {
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Ne postoji stanar koji stanuje u domu s tim Oibom u bazi");
        } else {
            int count = 0;
            do {
                if (count == 0) {
                    oibTextLabel.setText(String.valueOf(resultSet.getLong("oib")));
                    jmbagTextField.setText(String.valueOf(resultSet.getLong("jmbag")));
                    imeTextField.setText(resultSet.getString("ime"));
                    prezimeTextField.setText(resultSet.getString("prezime"));
                    datumRodenjaTextField.setValue(LocalDate.parse(resultSet.getString("datum_rodenja")));
                    adresaPrebivalistaTextField.setText(resultSet.getString("adresa_prebivalista"));
                    ucilisteTextField.setText(resultSet.getString("uciliste"));
                    komentarTextArea.setText(resultSet.getString("komentar"));
                    subvencioniranostBox.setSelected(resultSet.getBoolean("subvencioniranost"));
                    teretanaBox.setSelected(resultSet.getBoolean("uplata_teretane"));
                } else {
                    oibTextLabel2.setText(String.valueOf(resultSet.getLong("oib")));
                    jmbagTextField2.setText(String.valueOf(resultSet.getLong("jmbag")));
                    imeTextField2.setText(resultSet.getString("ime"));
                    prezimeTextField2.setText(resultSet.getString("prezime"));
                    datumRodenjaTextField2.setValue(LocalDate.parse(resultSet.getString("datum_rodenja")));
                    adresaPrebivalistaTextField2.setText(resultSet.getString("adresa_prebivalista"));
                    ucilisteTextField2.setText(resultSet.getString("uciliste"));
                    komentarTextArea2.setText(resultSet.getString("komentar"));
                    subvencioniranostBox2.setSelected(resultSet.getBoolean("subvencioniranost"));
                    teretanaBox2.setSelected(resultSet.getBoolean("uplata_teretane"));
                }
                count++;
            } while (resultSet.next());


        }
    }
    @FXML
    public void handleUpdateStanar(ActionEvent event){
        PopupAlert popupAlert=new PopupAlert();
        try{
            // Update first Stanar
            stanar.setOib(Long.parseLong(oibTextLabel.getText()));
            updateStanar(stanar, oibTextLabel, jmbagTextField, imeTextField, prezimeTextField, datumRodenjaTextField, adresaPrebivalistaTextField, ucilisteTextField, komentarTextArea, subvencioniranostBox, teretanaBox);

            // Update second Stanar
            stanar2.setOib(Long.parseLong(oibTextLabel2.getText()));
            updateStanar(stanar2, oibTextLabel2, jmbagTextField2, imeTextField2, prezimeTextField2, datumRodenjaTextField2, adresaPrebivalistaTextField2, ucilisteTextField2, komentarTextArea2, subvencioniranostBox2, teretanaBox2);

            popupAlert.showAlert(Alert.AlertType.INFORMATION, "Promijenjeni podaci", "Novo uneseni podaci su pohranjeni");
        }catch(SQLException e){
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Greska pri pohrani podataka");
        }


    }
    public void updateStanar(Stanar stanar, Label oibTextField, TextField jmbagTextField, TextField imeTextField, TextField prezimeTextField, DatePicker datumRodenjaTextField, TextField adresaPrebivalistaTextField, TextField ucilisteTextField, TextArea komentarTextArea, CheckBox subvencioniranostBox, CheckBox teretanaBox) throws SQLException {
        Connect connect = new Connect();
        Connection connection=connect.connect();
        PopupAlert popupAlert=new PopupAlert();

        stanar.setOib(Long.parseLong(oibTextField.getText()));
        stanar.setJmbag(Long.parseLong(jmbagTextField.getText()));
        stanar.setIme(imeTextField.getText());
        stanar.setPrezime(prezimeTextField.getText());
        stanar.setDatumRodenja(datumRodenjaTextField.getValue());
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
            popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Ne postoji stanaru domu s Oibom: "+stanar.getOib()+" bazi podataka");
        }
    }

}
