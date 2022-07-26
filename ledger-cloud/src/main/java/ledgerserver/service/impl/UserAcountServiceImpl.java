package ledgerserver.service.impl;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ledgerserver.jpadao.RolePermissionRepository;
import ledgerserver.jpadao.UserAcountRepository;
import ledgerserver.service.UserAcountService;
import ledgerserver.utils.MailClient;
import ledgerserver.utils.RedisUtils;
import model.UserIdentifierTypeEnum;
import model.user.RolePermissions;
import model.user.RoleType;
import model.user.UserAcount;
import model.user.UserAndPermissionDTO;
import utils.RandomUtils;

@Service 
@Primary 
public class UserAcountServiceImpl implements UserAcountService {

    private static final Logger log = LoggerFactory.getLogger(UserAcountServiceImpl.class);
    @Resource 
    private UserAcountRepository userAcountRepository;
    @Resource 
    private RolePermissionRepository rolePermissionRepository;
    @Resource 
    private RedisUtils redisUtils;
    @Resource 
    private MailClient mailClient;

    @Override
    public UserAcount getUserByEmail(String email) {
        Optional<UserAcount> user = this.userAcountRepository.findByEmailAddress(email);
        return user.orElse(null);
    }

    @Override
    public UserAcount getUserByPhone(String phone) {
        Optional<UserAcount> user = this.userAcountRepository.findByPhoneNumber(phone);
        return user.orElse(null);
    }

    @Override
    public UserAcount getUserByIdentifier(String identifier) {
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.matchType(identifier);
        
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.EMAIL.getMarkName())) {
            return this.getUserByEmail(identifier);
        }
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.PHONE.getMarkName())) {
            return this.getUserByPhone(identifier);
        }
        
        return null;
    }
    
    @Override 
    public UserAcount getUserAcountByIdentifierAndPassword(String identifier, String password) {
        Optional<UserAcount> user = this.userAcountRepository.findByIdentifierAndPassword(identifier, password);
        return user.orElse(null);
    }

    @Override 
    public UserAcount getUserById(Long id) {
        Optional<UserAcount> user = this.userAcountRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public RolePermissions getRolePermissionByRoleType(RoleType roleType) {
        Optional<RolePermissions> rolePermissions = this.rolePermissionRepository.findByRoleType(roleType);
        return rolePermissions.orElse(null);
    }

    @Override
    public RolePermissions getRolePermissionByRoleId(Long roleId) {
        Optional<RolePermissions> rolePermissions = this.rolePermissionRepository.findByRoleId(roleId);
        return rolePermissions.orElse(null);
    }

    @Override
    public UserAndPermissionDTO getUserAndPermissions(String identifier, String password) {
        
        UserAcount user = this.getUserAcountByIdentifierAndPassword(identifier, password);
        if(user == null) {
            return null;
        }
        RolePermissions rolePermissions = this.getRolePermissionByRoleId(user.getRoleId());
        
        UserAndPermissionDTO userAndPermissionDTO = UserAndPermissionDTO.createBuilder()
                .id(user.getId())
                .userName(user.getUserName())
                .emailAddress(user.getEmailAddress())
                .phoneNumber(user.getPhoneNumber())
                .roleType(user.getRoleType())
                .permissions(rolePermissions.getPermissions())
                .build();
        
        return userAndPermissionDTO;
    }
    
    // 生成激活码 并发送激活邮箱链接 
    public String sendActiveEmail(String identifier) {
        String code = RandomUtils.generateRandomString(10);
        log.info("当前生成的激活码是 = {}", code);
        // 这个激活链路设计没有考虑周全 暂时先完成基本功能, 以后再详细设计 
        String activeUrl = "localhost:8888/api/v1/user/active?id=" + identifier + "&code=" + code;
        log.info("激活链接 --> {}", activeUrl);
        
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userName", identifier);
        mapParams.put("registDate", LocalDateTime.now().toString());
        mapParams.put("identifier", identifier);
        mapParams.put("activeCode", code);
        mapParams.put("url", activeUrl);
        this.mailClient.sendActiveAcountEmail("naveron@163.com", mapParams);
        
        return code;
    }
    
    @Override 
    public UserAcount registUser(UserAcount user) {
        // 构造一个不完全对象 设置关键属性 
        UserAcount userExist = null;
        if(!user.getEmailAddress().isBlank()) {
            userExist = this.getUserAcountByIdentifierAndPassword(user.getEmailAddress(), user.getPassword());
        }else if(!user.getPhoneNumber().isBlank()) {
            userExist = this.getUserAcountByIdentifierAndPassword(user.getPhoneNumber(), user.getPassword());
        }else {
            log.error("user 对象构造多雾 --> {}", user.toString());
            return null;
        }
        
        if(userExist != null) {
            log.warn("用户已经存在 重复创建/请重新使用id 或使用此id登录 !");
            return userExist;
        }
        
        // UserAcount createdUser = this.userAcountRepository.save(user);
        String userJSON = redisUtils.cacheRegistUser(user, this.sendActiveEmail(user.getEmailAddress() + user.getPhoneNumber()));
        log.info("缓存redis user对象 JSON --> {}", userJSON);
        
        return user;
    }
    
    @Override 
    public UserAcount registUser(UserIdentifierTypeEnum userIdentifier, String password) {
        UserAcount userExist = this.getUserAcountByIdentifierAndPassword(userIdentifier.getValue(), password);;
        if(userExist != null) {
            log.warn("用户已经存在 重复创建/请重新使用id 或使用此id登录 !");
            return userExist;  // 这里如果重复创建可以返回标志表示已经有user账户存在 
        }
        // 不应该在user构造方法上判断 应当做成2个不同的入口 
        // UserAcount createdUser = this.userAcountRepository.save(new UserAcount(userIdentifier.getValue(), password));
        UserAcount cacheUser = new UserAcount(userIdentifier.getValue(), password);
        String userJSON = redisUtils.cacheRegistUser(cacheUser, this.sendActiveEmail(userIdentifier.getValue()));
        log.info("缓存redis user对象 JSON --> {}", userJSON);
        
        return cacheUser;
    }

    /**
     * 1 检查是否已经存在用户 
     * 2 存在直接返回用户 不存在进入注册流程 
     * 3 创建用户并保存 返回 
     */
    @Override
    public UserAcount registUser(String identifier, String password) {
        UserAcount userExist = this.getUserAcountByIdentifierAndPassword(identifier, password);
        if(userExist != null) {
            log.warn("用户已经存在 重复创建/请重新使用id 或使用此id登录 !");
            return userExist;
        }
        // UserAcount createdUser = this.userAcountRepository.save(new UserAcount(identifier, password));
        UserAcount cacheUser = new UserAcount(identifier, password);
        String userJSON = redisUtils.cacheRegistUser(cacheUser, this.sendActiveEmail(identifier));
        log.info("缓存redis user对象 JSON --> {}", userJSON);
        
        return cacheUser;
    }

    @Override
    public UserAcount registUser(String identifier, String password, String roleType) {
        UserAcount userExist = this.getUserAcountByIdentifierAndPassword(identifier, password);
        if(userExist != null) {
            log.warn("用户已经存在 重复创建/请重新使用id 或使用此id登录 !");
            return userExist;
        }
        RoleType roleTypeEnum = RoleType.Of(roleType);
        // UserAcount createdUser = this.userAcountRepository.save(new UserAcount(identifier, password, roleTypeEnum));
        UserAcount cacheUser = new UserAcount(identifier, password, roleTypeEnum);
        String userJSON = redisUtils.cacheRegistUser(cacheUser, this.sendActiveEmail(identifier));
        log.info("缓存redis user对象 JSON --> {}", userJSON);
        
        return cacheUser;
    }
    
    @Override 
    public UserAcount activeUser(UserIdentifierTypeEnum userIdentifier, String code) {
        UserAcount user = redisUtils.getRegistUserCache(userIdentifier.getValue(), code);
        if(user == null) {
            log.error("当前用户没有注册, 或者注册码等id认证错误");
            return null;
        }
        // 持久化user 
        user = this.userAcountRepository.save(user);
        
        return user;
    }

    /**
     * 删除用户 
     * 1 先判断是否存在这个用户 
     * 2 存在 执行删除 
     * 3 不存在 直接返回空对象 
     */
    @Override
    public UserAcount deleteUser(Long id) {
        Optional<UserAcount> user = this.userAcountRepository.findById(id);
        if(user.isPresent()) {
            log.warn("user exists ! deleting ...");
            this.userAcountRepository.deleteById(id);
            return user.get();
        }
        
        log.info("用户不存在, 直接返回");
        return null;
    }

    @Override
    public UserAcount deleteUser(String identifier) {
        UserAcount user = this.getUserByIdentifier(identifier);
        if(user != null) {
            log.warn("用户存在,执行删除操作");
            this.userAcountRepository.deleteById(user.getId());
            return user;
        }
        
        log.info("用户不存在");
        return null;
    }

    @Override
    public UserAcount updateUserEmail(Long userId, String email) {
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            UserAcount updatedUser = user.get();
            updatedUser.setEmailAddress(email);
            updatedUser = this.userAcountRepository.save(updatedUser);
            
            return updatedUser;
        }
        
        log.warn("用户不存在, 更改无效");
        return null;
    }

    @Override
    public UserAcount updateUserPhone(Long userId, String phone) {
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            UserAcount updatedUser = user.get();
            updatedUser.setPhoneNumber(phone);
            updatedUser = this.userAcountRepository.save(updatedUser);
            
            return updatedUser;
        }
        
        log.warn("用户不存在, 更改无效");
        return null;
    }

    @Override
    public UserAcount updateUserName(Long userId, String userName) {
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            UserAcount updatedUser = user.get();
            updatedUser.setUserName(userName);
            updatedUser = this.userAcountRepository.save(updatedUser);
            
            return updatedUser;
        }
        
        log.warn("用户不存在, 更改无效");
        return null;
    }

    @Override
    public RolePermissions addRolePermissions(RoleType roleType, List<String> permissions) {
        RolePermissions rolePermissions = this.rolePermissionRepository.save(
                    new RolePermissions(roleType, permissions)
                );
        return rolePermissions;
    }

    @Override
    public RolePermissions addRolePermissions(RoleType roleType, String permissions) {
        RolePermissions rolePermissions = this.rolePermissionRepository.save(
                    new RolePermissions(roleType, permissions)
                );
        return rolePermissions;
    }

    @Override
    public RolePermissions addRolePermissions(RoleType roleType, List<String> permissions, String description) {
        RolePermissions rolePermissions = this.rolePermissionRepository.save(
                    new RolePermissions(roleType, permissions, description)
                );
        return rolePermissions;
    }
    
    
}



