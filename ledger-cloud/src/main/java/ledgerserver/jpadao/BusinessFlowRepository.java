package ledgerserver.jpadao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.BusinessRecord;

@Repository 
public interface BusinessFlowRepository extends JpaRepository<BusinessRecord, Long> {

}
