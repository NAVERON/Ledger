
open module ledgercommon {
    
    requires java.persistence;
//    requires jakarta.persistence;
//    requires org.hibernate.orm.core;
    requires jjwt.api;
    // requires jjwt.impl;
    //requires jjwt.gson;
    requires com.google.gson;
    
    requires org.slf4j;
    requires org.slf4j.simple;
    
    exports model;
    exports model.user;
    exports utils;
    exports configuration;
    
}

