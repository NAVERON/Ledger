package ledger.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ledger.dao.HolderRespository;


@Service(value = "holder")
@Primary
public class ServiceHolderImpl {

    private static final Logger log = LoggerFactory.getLogger(ServiceHolderImpl.class);

    @Resource 
    private HolderRespository holderDAO;
    
    
}
