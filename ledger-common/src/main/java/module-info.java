
open module ledgercommon {
    
    requires java.persistence;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    
    requires org.slf4j;
    requires org.slf4j.simple;
    
    exports model;
    exports utils;
    
}

