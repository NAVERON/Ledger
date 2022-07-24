package model.user;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 合并用户和权限 数据 DTO 
 * @author eron
 * 
 */
public class UserAndPermissionDTO {

    private static final Logger log = LoggerFactory.getLogger(UserAndPermissionDTO.class);
    private static final UserAndPermissionDTO buildOuter = new UserAndPermissionDTO();
    
    private UserAndPermissionDTO() {}
    public static UserAndPermissionBuilder createBuilder() {
        return buildOuter.new UserAndPermissionBuilder();
    }

    private Long id = -1L;  // user_id 
    private String userName = "eron";
    private String emailAddress = "default@gmail.com";
    private String phoneNumber = "1234567890";
    private RoleType roleType = RoleType.ANONYMITY;
    private List<String> permissions = new LinkedList<String>();
    
    public class UserAndPermissionBuilder {
        UserAndPermissionDTO generateObj = new UserAndPermissionDTO();
        private UserAndPermissionBuilder() {}  // 禁止外部初始化 
        
        public UserAndPermissionBuilder id(Long id) {
            this.generateObj.id = id;
            return this;
        }
        public UserAndPermissionBuilder userName(String userName) {
            this.generateObj.userName = userName.trim();  // 保证对象数据整齐 
            return this;
        }
        public UserAndPermissionBuilder emailAddress(String emailAddress) {
            this.generateObj.emailAddress = emailAddress.trim();
            return this;
        }
        public UserAndPermissionBuilder phoneNumber(String phoneNumber) {
            this.generateObj.phoneNumber = phoneNumber.trim();
            return this;
        }
        public UserAndPermissionBuilder roleType(RoleType roleType) {
            this.generateObj.roleType = roleType;
            return this;
        }
        public UserAndPermissionBuilder permissionString(String permissions) {
            this.generateObj.permissions = new LinkedList<>(Arrays.asList(permissions.split(",")));
            return this;
        }
        public UserAndPermissionBuilder permissions(List<String> permissions) {
            this.generateObj.permissions = permissions;
            return this;
        }
        
        public UserAndPermissionDTO build() {
            return this.generateObj;
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName.trim();
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress.trim();
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }
    public RoleType getRoleType() {
        return roleType;
    }
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    public List<String> getPermissions() {
        return permissions;
    }
    public String getPermissionsString() {
        StringJoiner sj = new StringJoiner(",");
        this.permissions.forEach(perm -> sj.add(perm));
        return sj.toString();
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
    @Override
    public String toString() {
        return "UserAndPermissionDTO [id=" + id + ", userName=" + userName + ", emailAddress=" + emailAddress
                + ", phoneNumber=" + phoneNumber + ", permissions=" + permissions + "]";
    }
    
}









