module com.example.examforge {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires itextpdf;
    requires org.apache.poi.ooxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.examforge to javafx.fxml;
    exports com.example.examforge;
}