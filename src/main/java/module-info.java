module com.example.mponye {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.mponye to javafx.fxml;
    exports com.example.mponye;
}