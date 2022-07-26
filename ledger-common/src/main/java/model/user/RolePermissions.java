package model.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "role_permission")
public class RolePermissions {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_type")
    @Enumerated(value = EnumType.STRING) 
    private RoleType roleType = RoleType.ANONYMITY;
    @Column(name = "description")
    private String description = "";  // 特殊描述 
    @Column(name = "permissions")
    private String permissions = "";  // 权限list 
    
    public RolePermissions() {}
    public RolePermissions(RoleType roleType, String permissions) {
        this.roleType = roleType;
        this.permissions = permissions;
    }
    public RolePermissions(RoleType roleType, List<String> permissions) {
        StringJoiner sj = new StringJoiner(",");
        permissions.stream().forEach(perm -> sj.add(perm));
        
        this.roleType = roleType;
        this.permissions = sj.toString();
    }
    public RolePermissions(RoleType roleType, List<String> permissions, String description) {
        this(roleType, permissions);
        this.description = description;
    }
    
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<String> getPermissions() {
        return Arrays.asList(this.permissions.split(","));  // 获取权限不需要修改 
    }

    public void setPermissions(List<String> permissions) {
        StringJoiner sj = new StringJoiner(",");
        permissions.forEach(perm -> sj.add(perm));
        this.permissions = sj.toString();
    }

    @Override
    public String toString() {
        return "Role [roleType=" + roleType + ", permissions=" + permissions + "]";
    }
}



