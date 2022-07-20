package ledgerserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import model.user.RolePermissions;

public interface RolePermissionRepository extends JpaRepository<RolePermissions, Long>{

}
