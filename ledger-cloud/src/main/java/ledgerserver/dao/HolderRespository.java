package ledgerserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Holder;


@Repository 
public interface HolderRespository extends JpaRepository<Holder, Long>{

}
