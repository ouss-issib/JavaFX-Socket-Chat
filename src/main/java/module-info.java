module ma.enset.chatapplicationsocketthreds {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens ma.enset.chatapplicationsocketthreds to javafx.fxml;
    exports ma.enset.chatapplicationsocketthreds;
}