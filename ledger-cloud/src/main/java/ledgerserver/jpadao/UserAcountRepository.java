package ledgerserver.jpadao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import model.user.UserAcount;



@Repository 
public interface UserAcountRepository extends JpaRepository<UserAcount, Long> {
    
    @Query(value = "select u from UserAcount u where u.emailAddress = ?1 ", nativeQuery = false)
    public Optional<UserAcount> findByEmailAddress(String emailAddress);
    
    @Query(value = "select u from UserAcount u where u.phoneNumber = ?1 ")
    public Optional<UserAcount> findByPhoneNumber(String phoneNumber);
    
    @Query(value = "select u from UserAcount u where u.id = ?1 ")
    public Optional<UserAcount> findById(Long id);
    
    @Query(value = "select u from UserAcount u where u.password = ?2 and (u.emailAddress = ?1 or u.phoneNumber = ?1) ")
    public Optional<UserAcount> findByIdentifierAndPassword(String identifier, String password);
    
}


