package ledgerserver.jpadao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import model.BusinessRecord;

@Repository 
public interface BusinessFlowRepository extends JpaRepository<BusinessRecord, Long> {

    @Query(value = "select b from BusinessRecord b where b.recordTime between ?1 and ?2 ") 
    public List<BusinessRecord> findBusinessFlowByTimeRange(LocalDateTime start, LocalDateTime end);
    
    @Query(value = "select b from BusinessRecord b where b.userId = ?1 and b.recordTime between ?2 and ?3 ") 
    public List<BusinessRecord> findBusinessFlowByTimeRangeOfUser(Long userId, LocalDateTime start, LocalDateTime end);
    
}
