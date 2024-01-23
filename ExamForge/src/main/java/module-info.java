module com.example.examforge {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.examforge to javafx.fxml;
    exports com.example.examforge;
}