package model;

import java.util.Arrays;
import java.util.Optional;

import utils.RegexMatcher;


public enum UserIdentifierTypeEnum {
    // 用户的id类型 
    NULL("NULL", ""), PHONE("EMAIL", ""), EMAIL("PHONE", "");
    
    private String markName;  // 表示当前类型  < enum name() 可以直接使用 > 
    private String value;  // 绑定的值 
    
    private UserIdentifierTypeEnum(String name, String value) {
        this.markName = name;
        this.value = value;
    }
    
    private UserIdentifierTypeEnum setValue(String value) {
        this.value = value;
        return this;
    }
    
    public String getMarkName() {
        return this.markName;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public Boolean isEmialType() {
        return this.name().equals("EMAIL");
    }
    public Boolean isPhoneType() {
        return this.name().equals("PHONE");
    }
    
    public static UserIdentifierTypeEnum of(String identifierTypeName) {
        final String typeName = identifierTypeName == null ? "" : identifierTypeName;
        Optional<UserIdentifierTypeEnum> userType 
                = Arrays.asList(UserIdentifierTypeEnum.values()).stream()
                .filter(type -> type.name().equals(typeName.toUpperCase()))
                .findAny();
        
        return userType.orElse(UserIdentifierTypeEnum.NULL);
    }
    
    // 判断 id 属于哪种类型并返回 
    public static UserIdentifierTypeEnum matchType(String userIdentifier){
        userIdentifier = userIdentifier == null ? "" : userIdentifier;  // Null 值判断 
        // 可以使用 其他设计模式 这里不复杂化 
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.NULL;
        if(RegexMatcher.isValidPhoneNumber(userIdentifier)) {
            userIdentifierType = UserIdentifierTypeEnum.PHONE.setValue(userIdentifier);
        }else if(RegexMatcher.isValidEmailAddress(userIdentifier)) {
            userIdentifierType = UserIdentifierTypeEnum.EMAIL.setValue(userIdentifier);
        }
        
        return userIdentifierType;
    }
    
}









