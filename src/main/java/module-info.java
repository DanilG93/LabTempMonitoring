module com.labtemp.labtempmonitoring {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Otvaramo glavni paket
    opens com.danil.labtemp to javafx.fxml;
    exports com.danil.labtemp;

    // OBAVEZNO: Otvaramo kontroler paket da bi fxmlLoader mogao da poveže dugmiće
    opens com.danil.labtemp.controller to javafx.fxml;
    exports com.danil.labtemp.controller;
}