package model;



import utils.RegexMatcher;


public enum UserIdentifierTypeEnum {
    // 用户的id类型 
    NULL("NULL", ""), PHONE("EMAIL", ""), EMAIL("PHONE", "");
    
    private String name;  // 表示当前类型 
    private String value;  // 绑定的值 
    
    private UserIdentifierTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    private UserIdentifierTypeEnum setValue(String value) {
        this.value = value;
        return this;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public static UserIdentifierTypeEnum byName(String name, String value) {
        for(UserIdentifierTypeEnum type : UserIdentifierTypeEnum.values()) {
            if(type.name.equals(name)) {
                type.setValue(value);
                return type;
            }
        }
        
        return UserIdentifierTypeEnum.NULL.setValue(value);
    }
    
    // 判断 id 属于哪种类型并返回 
    public static UserIdentifierTypeEnum of(String userIdentifier){
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.NULL.setValue("");
        if(RegexMatcher.isValidPhoneNumber(userIdentifier)) {
            userIdentifierType = UserIdentifierTypeEnum.PHONE.setValue(userIdentifier);
        }else if(RegexMatcher.isValidEmailAddress(userIdentifier)) {
            userIdentifierType = UserIdentifierTypeEnum.EMAIL.setValue(userIdentifier);
        }
        
        return userIdentifierType;
    }
}
