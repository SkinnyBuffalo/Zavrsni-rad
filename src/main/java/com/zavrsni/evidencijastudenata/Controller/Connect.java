package com.zavrsni.evidencijastudenata.Controller;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    public Connection connect() {
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://student.veleri.hr/mfeltrin?serverTimezone=UTC", "mfeltrin", "11");
        }
        catch(Exception e){
            PopupAlert popupAlert=new PopupAlert();
            popupAlert.showAlert(Alert.AlertType.ERROR, "Greška pri spajanju na bazu", e.getMessage());
            System.out.println(e);
        }
        return con;
    }
}
