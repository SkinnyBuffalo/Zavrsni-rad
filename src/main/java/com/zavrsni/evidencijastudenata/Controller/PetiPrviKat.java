package com.zavrsni.evidencijastudenata.Controller;

import com.zavrsni.evidencijastudenata.Soba.Soba;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PetiPrviKat implements Initializable {
    Soba soba = new Soba();
    @FXML
    private Button gumb1, gumb2, gumb3, gumb4, gumb5, gumb6, gumb7, gumb8, gumb9, gumb10, gumb11;

    private List<Button> getButtons() {
        return Arrays.asList(gumb1, gumb2, gumb3, gumb4, gumb5, gumb6, gumb7, gumb8, gumb9, gumb10, gumb11);
    }
    private int getKrevetCount(int brojObjekta, int brojSobe) throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.connect();

        // Calculate id_sobe from broj_objekta and broj_sobe
        int idSobe = brojObjekta * 1000 + brojSobe;

        String query = "SELECT COUNT(`id_kreveta`) AS count FROM `krevet` WHERE `zauzetost` = 1 AND `id_sobe` = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idSobe);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("count");
        } else {
            throw new SQLException("No count returned for broj_objekta " + brojObjekta + " and broj_sobe " + brojSobe);
        }
    }

    private void executeQuery(int objekt, int kat) throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.connect();

        String query = "SELECT `broj_sobe` FROM `soba` WHERE `broj_objekta` = ? AND `kat_sobe` = ? ORDER BY `broj_sobe` ASC";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, objekt);
        preparedStatement.setInt(2, kat);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Button> buttons = getButtons();
        int index = 0;

        while (resultSet.next() && index < buttons.size()) {
            // Process the result set
            int brojSobe = resultSet.getInt("broj_sobe");

            // Get the count of krevet entities for this brojSobe
            int krevetCount = getKrevetCount(objekt, brojSobe);

            // Set the button text
            Button button = buttons.get(index);
            button.setText(String.valueOf(brojSobe));

            // Set the button color based on the krevetCount
            if (krevetCount == 2) {
                button.setStyle("-fx-background-color: red;");
            } else if (krevetCount == 1) {
                button.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
            } else { // krevetCount is 0
                button.setStyle("-fx-background-color: green;");
            }

            index++;
        }
    }
    public void setSoba(Soba soba) {
        this.soba = soba;
        try {
            executeQuery(soba.getBrojObjekta(), soba.getKatSobe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void openDialog(String buttonText, String buttonColor) {
        try {
            PopupAlert popupAlert=new PopupAlert();
            // Parse parameters from buttonText
            int brojObjekta = soba.getBrojObjekta();
            int katSobe = soba.getKatSobe();
            int brojSobe = Integer.parseInt(buttonText);

            // Create a new Stage (this will be our new window)
            Stage stage = new Stage();

            // Load the FXML file for the dialog
            FXMLLoader loader;
            boolean isSingleStudent;
            if (buttonColor.equals("red")){
                loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/podaciDvaStudenta.fxml"));
                isSingleStudent = false;
            }
            else if(buttonColor.equals("yellow")){
                loader = new FXMLLoader(getClass().getResource("/com/zavrsni/evidencijastudenata/fxml/planDoma/podaciJednogStudenta.fxml"));
                isSingleStudent = true;
            }
            else{
                popupAlert.showAlert(Alert.AlertType.ERROR, "Error", "Nema stanara u toj sobi");
                return;
            }
            Parent root = loader.load();

            if (isSingleStudent) {
                PodaciJednogStudenta controller = (PodaciJednogStudenta) loader.getController();
                // Pass the parameters to the controller
                controller.setParameters(brojObjekta, katSobe, brojSobe);
            } else {
                PodaciDvaStudenta controller = (PodaciDvaStudenta) loader.getController();
                // Pass the parameters to the controller
                controller.setParameters(brojObjekta, katSobe, brojSobe);
            }


            Scene scene = new Scene(root);
            stage.setScene(scene);


            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            executeQuery(soba.getBrojObjekta(), soba.getKatSobe());
            for (Button button : getButtons()) {
                button.setOnAction(event -> {
                    Button btn = (Button) event.getSource(); // This is the button that was clicked
                    String style = btn.getStyle();
                    String color = style.split(";")[0].replaceAll(".*-fx-background-color: ", "");
                    openDialog(btn.getText(), color);
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}