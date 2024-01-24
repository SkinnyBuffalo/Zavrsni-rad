package com.zavrsni.evidencijastudenata;

import com.zavrsni.evidencijastudenata.Boravak.Boravak;
import com.zavrsni.evidencijastudenata.Controller.Connect;
import com.zavrsni.evidencijastudenata.Soba.Soba;
import com.zavrsni.evidencijastudenata.Stanar.Stanar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class EvidencijaStudenataController {
    @FXML
    private BorderPane borderPane;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void switchToNoviStanar(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/stanari/noviStanar.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToSviTrenutacniStanari(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/stanari/sviTrenutniStanari.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToIseljenje(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/stanari/iseljenje.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToUseljenje(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/stanari/useljenje.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToPromijenaPodataka(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/stanari/promijenaPodataka.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToStanariGodine(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/stanari/stanariGodine.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToDomPlan(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/planDoma/domPlan.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToNoviKvar(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/kvar/noviKvar.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchToTrenutniKvarovi(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        BorderPane newCenter = FXMLLoader.load(getClass().getResource("fxml/kvar/trenutniKvarovi.fxml"));

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }


}