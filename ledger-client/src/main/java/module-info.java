open module ledgerclient {
    
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    
    requires java.net.http;
    
    requires ledgercommon;
    
    requires org.slf4j;
    requires org.slf4j.simple;

    exports ledgerclient;
}