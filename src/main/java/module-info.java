module com.danil.labtemp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.danil.labtemp to javafx.fxml;
    exports com.danil.labtemp;
}