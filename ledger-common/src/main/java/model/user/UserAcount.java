package model.user;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.UserIdentifierTypeEnum;

/**
 * 用户账户信息 包含基本的登录信息 
 * @author eron 
 * 
 */

@Entity 
@Table(name = "user_acount") 
public class UserAcount {
    
    private static final Logger log = LoggerFactory.getLogger(UserAcount.class);
    private static final UserAcount EMPTY_USER = new UserAcount(null, null);  // 创建一个空对象 避免空指针判断 
    
    // 账户 id 用户名 加密密码 登陆时间 登录日志关联的id 等 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Column(name = "user_name")
    private String userName = "DEFAULT";
    @Column(name = "password")
    private String password = "";
    @Column(name = "email_address")
    private String emailAddress = "";
    @Column(name = "phone_number")
    private String phoneNumber = "";
    @Column(name = "role_type")
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType = RoleType.ANONYMITY;
    @Column(name = "role_id")
    private Long roleId;  // 这里不需要roleId 因为需要从权限表中查询 后期再解决  
    
    public UserAcount(String identifier, String password) {
        // 这种判断很多 如何消除此种类似的校验 ?  只设置一个identifier 另外设置一个标记表示是电话还是邮箱 ? 
        UserIdentifierTypeEnum identifierType = UserIdentifierTypeEnum.of(identifier);
        // 先判断是否违法 
        if(identifierType.getMarkName().equals(UserIdentifierTypeEnum.NULL.getMarkName())) {
            log.error("Constructure user --> {}, {}", identifier, password);
            throw new IllegalArgumentException("创建用户的identifier 是无效的, 请检查identifier");
        }else if(identifierType.getMarkName().equals(UserIdentifierTypeEnum.EMAIL.getMarkName())) {
            this.emailAddress = identifierType.getValue();
        }else if(identifierType.getMarkName().equals(UserIdentifierTypeEnum.PHONE.getMarkName())) {
            this.phoneNumber = identifierType.getValue();
        }
        
        this.password = password;
    }
    
    public UserAcount(String identifier, String password, RoleType roleType) {
        this(identifier, password);
        this.roleType = roleType;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override 
    public String toString() {
        return "UserAcount [id=" + id + ", userName=" + userName + ", password=" + password + ", emailAddress="
                + emailAddress + ", phoneNumber=" + phoneNumber + "]";
    }
    
}





