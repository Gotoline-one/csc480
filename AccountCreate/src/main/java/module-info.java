module org.csc480.accountcreate {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.csc480.accountcreate to javafx.fxml;
    exports org.csc480.accountcreate;
}