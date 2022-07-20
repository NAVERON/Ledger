package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对象的基本描述 在其他模块封装成 VO或者 DO 
 * @author eron
 *
 */
@Entity 
@Table(name = "") 
public class Holder {

    @Id
    private Long id;
    
}



