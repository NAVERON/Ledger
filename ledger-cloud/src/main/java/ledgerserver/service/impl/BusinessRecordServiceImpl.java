package ledgerserver.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ledgerserver.service.BusinessRecordService;
import model.BusinessRecord;


@Service 
@Primary 
public class BusinessRecordServiceImpl implements BusinessRecordService {

    private static final Logger log = LoggerFactory.getLogger(BusinessRecordServiceImpl.class);
    

    @Override
    public List<BusinessRecord> getRecentBusinessRecords() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        
        return this.getBusinessRecordsAtTimeRange(start, end);
    }

    @Override
    public List<BusinessRecord> getBusinessRecordsAtTimeRange(LocalDateTime start, LocalDateTime end) {
        
        return null;
    }

    @Override
    public List<BusinessRecord> getRecentBusinessRecordsOfUser(Long userId) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMonths(1);
        
        return this.getBusinessRecordsAtTimeRangeOfUser(userId, start, end);
    }

    @Override
    public List<BusinessRecord> getBusinessRecordsAtTimeRangeOfUser(Long userId, LocalDateTime start, LocalDateTime end) {
        
        return null;
    }

    @Override
    public BusinessRecord addNewBusinessRecord(BusinessRecord businessRecord) {
        
        return null;
    }

    @Override
    public List<BusinessRecord> addNewBusinessRecords(List<BusinessRecord> businessRecords) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}








