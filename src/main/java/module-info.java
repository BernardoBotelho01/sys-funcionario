module org.sysfuncionario {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.sysfuncionario to javafx.fxml;
    exports org.sysfuncionario;
}