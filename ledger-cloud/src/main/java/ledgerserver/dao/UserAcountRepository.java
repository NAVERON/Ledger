package ledgerserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.user.UserAcount;

@Repository 
public interface UserAcountRepository extends JpaRepository<UserAcount, Long>{

}
