package com.zavrsni.evidencijastudenata.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import com.zavrsni.evidencijastudenata.Soba.Soba;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PlanDoma implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ChoiceBox<Integer> objektChoiceBox;

    @FXML
    private ChoiceBox<Integer> katChoiceBox;

    Soba soba=new Soba();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i <= 5; i++) {
            objektChoiceBox.getItems().add(i);
        }

        objektChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            katChoiceBox.getItems().clear();
            if (newValue == 1) {
                for (int i = 1; i <= 9; i++) {
                    katChoiceBox.getItems().add(i);
                }
            } else if (newValue == 2 || newValue == 3 || newValue == 4) {
                for (int i = 1; i <= 3; i++) {
                    katChoiceBox.getItems().add(i);
                }
            } else if (newValue == 5) {
                for (int i = 0; i <= 5; i++) {
                    katChoiceBox.getItems().add(i);
                }
            }
        });
        katChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && objektChoiceBox.getValue() != null) {
                int selectedObjekt = objektChoiceBox.getValue();
                int selectedKat = newValue;
                soba.setKatSobe(selectedKat);
                soba.setBrojObjekta(selectedObjekt);
                    // If the selected object is 1, call the switchPrviObjekt method
                    if (selectedObjekt == 1) {
                        try {

                            switchPrviObjekt(null);  // Passing null because the method expects an ActionEvent
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ((selectedObjekt == 2||selectedObjekt == 3||selectedObjekt == 4)&&selectedKat==1) {
                        try {
                            switchDrugiDoCetvrtogPrviKat(null);  // Passing null because the method expects an ActionEvent
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if ((selectedObjekt == 2||selectedObjekt == 3||selectedObjekt == 4)&&selectedKat!=1) {
                        try {
                            switchDrugiDoCetvrtog(null);  // Passing null because the method expects an ActionEvent
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (selectedObjekt == 5&&selectedKat==0) {
                        try {
                            switchPetiPrviKat(null);  // Passing null because the method expects an ActionEvent
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (selectedObjekt == 5&&selectedKat!=0) {
                        try {
                            switchPeti(null);  // Passing null because the method expects an ActionEvent
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        });
    }






    @FXML
    public void switchPrviObjekt(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/prviObjekt.fxml"));

        // Load the new AnchorPane from the FXMLLoader
        BorderPane newCenter = loader.load();

        // Get the controller and set the soba object
        PrviObjekt controller = loader.getController();
        controller.setSoba(soba);

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);

    }
    @FXML
    public void switchDrugiDoCetvrtogPrviKat(ActionEvent event) throws IOException {
        // Load the new AnchorPane from the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/drugiDoCetvrtogPrviKat.fxml"));

        // Load the new AnchorPane from the FXMLLoader
        BorderPane newCenter = loader.load();

        // Get the controller and set the soba object
        DrugiDoCetvrtogPrviKat controller = loader.getController();
        controller.setSoba(soba);

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchDrugiDoCetvrtog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/drugiDoCetvrtog.fxml"));

        // Load the new AnchorPane from the FXMLLoader
        BorderPane newCenter = loader.load();

        // Get the controller and set the soba object
        DrugiDoCetvrtog controller = loader.getController();
        controller.setSoba(soba);

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchPetiPrviKat(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/petiPrviKat.fxml"));

        // Load the new AnchorPane from the FXMLLoader
        BorderPane newCenter = loader.load();

        // Get the controller and set the soba object
        PetiPrviKat controller = loader.getController();
        controller.setSoba(soba);

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }
    @FXML
    public void switchPeti(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/peti.fxml"));

        // Load the new AnchorPane from the FXMLLoader
        BorderPane newCenter = loader.load();

        // Get the controller and set the soba object
        Peti controller = loader.getController();
        controller.setSoba(soba);

        // Replace the center of the BorderPane with the new AnchorPane
        borderPane.setCenter(newCenter);
    }

}