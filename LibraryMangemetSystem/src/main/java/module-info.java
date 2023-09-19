module LibrarySystem {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    
    opens com.project.librarysystem to javafx.fxml;
    exports com.project.librarysystem;
}