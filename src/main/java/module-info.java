module org.sysfuncionario {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.validation;


    opens org.sysfuncionario to javafx.fxml;
    exports org.sysfuncionario;
    exports org.sysfuncionario.controller;
    opens org.sysfuncionario.controller to javafx.fxml;
}