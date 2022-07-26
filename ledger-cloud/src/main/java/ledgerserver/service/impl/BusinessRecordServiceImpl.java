package ledgerserver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ledgerserver.jpadao.BusinessFlowRepository;
import ledgerserver.jpadao.UserAcountRepository;
import ledgerserver.service.BusinessRecordService;
import model.BusinessRecord;
import model.user.UserAcount;


@Service 
@Primary 
public class BusinessRecordServiceImpl implements BusinessRecordService {

    private static final Logger log = LoggerFactory.getLogger(BusinessRecordServiceImpl.class);
    
    @Resource 
    private BusinessFlowRepository businessFlowRepository;
    @Resource 
    private UserAcountRepository userAcountRepository;
    
    @Override
    public List<BusinessRecord> getRecentBusinessRecords() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        
        return this.getBusinessRecordsAtTimeRange(start, end);
    }

    @Override
    public List<BusinessRecord> getBusinessRecordsAtTimeRange(LocalDateTime start, LocalDateTime end) {
        List<BusinessRecord> queryResult = new ArrayList<>();
        queryResult.addAll(this.businessFlowRepository.findBusinessFlowByTimeRange(start, end));
        
        return queryResult;
    }

    @Override
    public List<BusinessRecord> getRecentBusinessRecordsOfUser(Long userId) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        
        return this.getBusinessRecordsAtTimeRangeOfUser(userId, start, end);
    }

    @Override
    public List<BusinessRecord> getBusinessRecordsAtTimeRangeOfUser(Long userId, LocalDateTime start, LocalDateTime end) {
        List<BusinessRecord> queryResult = new ArrayList<>();
        queryResult.addAll(this.businessFlowRepository.findBusinessFlowByTimeRangeOfUser(userId, start, end));
        
        return queryResult;
    }

    @Override
    public BusinessRecord addNewBusinessRecord(BusinessRecord businessRecord) {
        Long userId = businessRecord.getUserId();
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            return this.businessFlowRepository.save(businessRecord);
        }
        
        log.error("用户不存在, 无法保存非法用户消费记录");
        return null;
    }

    @Override
    public List<BusinessRecord> addNewBusinessRecords(List<BusinessRecord> businessRecords) {
        List<BusinessRecord> savedResult = new LinkedList<>();
        businessRecords.stream().parallel().forEach( businessRecord -> {
                        BusinessRecord savedBusinessRecord = this.addNewBusinessRecord(businessRecord);
                        savedResult.add(savedBusinessRecord);
                    }
                );
        
        return savedResult;
    }
    
    
}








