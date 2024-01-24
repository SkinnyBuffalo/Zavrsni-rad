package com.zavrsni.evidencijastudenata.Controller;

import javafx.scene.control.Alert;

public class PopupAlert {
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
