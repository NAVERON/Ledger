package model.user;

import javax.persistence.Embeddable;

@Embeddable
public enum RoleType { 
    ADMINISTRATOR, REGISTERED, ANONYMITY;
    
}
