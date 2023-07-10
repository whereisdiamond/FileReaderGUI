module com.example.filereadergui {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.filereadergui to javafx.fxml;
    exports com.example.filereadergui;
}