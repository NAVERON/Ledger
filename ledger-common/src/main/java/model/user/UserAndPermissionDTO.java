package model.user;

import java.util.List;

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

    private Long id;  // user_id 
    private String userName;
    private String emailAddress;
    private String phoneNumber;
    private RoleType roleType;
    private List<String> permissions;
    
    public class UserAndPermissionBuilder {
        UserAndPermissionDTO generateObj = new UserAndPermissionDTO();
        
        public UserAndPermissionBuilder id(Long id) {
            this.generateObj.id = id;
            return this;
        }
        public UserAndPermissionBuilder userName(String userName) {
            this.generateObj.userName = userName;
            return this;
        }
        public UserAndPermissionBuilder emailAddress(String emailAddress) {
            this.generateObj.emailAddress = emailAddress;
            return this;
        }
        public UserAndPermissionBuilder phoneNumber(String phoneNumber) {
            this.generateObj.phoneNumber = phoneNumber;
            return this;
        }
        public UserAndPermissionBuilder roleType(RoleType roleType) {
            this.generateObj.roleType = roleType;
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

    @Override
    public String toString() {
        return "UserAndPermissionDTO [id=" + id + ", userName=" + userName + ", emailAddress=" + emailAddress
                + ", phoneNumber=" + phoneNumber + ", permissions=" + permissions + "]";
    }
    
}









