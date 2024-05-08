module com.example.examforge {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires itextpdf;
    requires org.apache.poi.ooxml;


    opens com.example.examforge to javafx.fxml;
    exports com.example.examforge;
}