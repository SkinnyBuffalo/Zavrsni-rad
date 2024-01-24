module com.zavrsni.evidencijastudenata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.zavrsni.evidencijastudenata to javafx.fxml;
    opens com.zavrsni.evidencijastudenata.Controller to javafx.fxml,javafx.base;
    exports com.zavrsni.evidencijastudenata;
}
