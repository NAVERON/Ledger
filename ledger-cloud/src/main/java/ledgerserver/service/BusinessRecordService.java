package ledgerserver.service;

import java.time.LocalDateTime;
import java.util.List;

import model.BusinessRecord;

/**
 * 业务 流水服务 
 * @author eron
 *
 */
public interface BusinessRecordService {
    
    // 获取交易流水记录 
    public List<BusinessRecord> getRecentBusinessRecords();  // 自己设定一段时间 一个月 
    public List<BusinessRecord> getBusinessRecordsAtTimeRange(LocalDateTime start, LocalDateTime end);
    
    public List<BusinessRecord> getRecentBusinessRecordsOfUser(Long userId);
    public List<BusinessRecord> getBusinessRecordsAtTimeRangeOfUser(Long userId, LocalDateTime start, LocalDateTime end);
    // 增加交易流水 
    public BusinessRecord addNewBusinessRecord(BusinessRecord businessRecord);
    public List<BusinessRecord> addNewBusinessRecords(List<BusinessRecord> businessRecords);
    
    
}
