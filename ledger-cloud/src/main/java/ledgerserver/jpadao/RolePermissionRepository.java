package ledgerserver.jpadao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import model.user.RolePermissions;
import model.user.RoleType;



@Repository 
public interface RolePermissionRepository extends JpaRepository<RolePermissions, Long> {

    @Query(value = "select r from RolePermissions r where r.roleType = ?1 ")
    public Optional<RolePermissions> findByRoleType(RoleType roleType);
    
    @Query(value = "select r from RolePermissions r where r.id = ?1 ")
    public Optional<RolePermissions> findByRoleId(Long id);
    
}
