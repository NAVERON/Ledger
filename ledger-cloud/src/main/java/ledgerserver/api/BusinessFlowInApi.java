package ledgerserver.api;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import ledgerserver.annotation.RequiredRolePermission;
import ledgerserver.service.AliPayService;
import ledgerserver.service.BusinessRecordService;
import model.BusinessRecord;
import model.user.RoleType;
import utils.DateAndTimeTransferUtils;

@RestController 
@RequestMapping(value = "api/v1/business", name = "business flow options") 
@RequiredRolePermission(role = RoleType.ADMINISTRATOR)  // 交易需要admin role 
public class BusinessFlowInApi {
    
    private static final Logger log = LoggerFactory.getLogger(BusinessFlowInApi.class);
    
    @Resource 
    private BusinessRecordService businessRecordService;
    @Resource 
    private AliPayService aliPayService;
    
    // 进行了一笔交易 
    /**
     * 1 检查user 是否合法 
     * 2 检查支付宝是否收到款项 
     * 3 记录 
     * @return
     */
    @PostMapping(value = "deal")
    public ResponseEntity<String> makeDeal(@RequestParam(value = "uid") Long userId, 
            @RequestParam(value = "amount") Double amount) {
        BusinessRecord record = this.businessRecordService.addNewBusinessRecord(new BusinessRecord(userId, amount));
        
        return ResponseEntity.ok("交易记录成功\nRecord : " + record.toString());
    }
    
    @GetMapping(value = "query")  // 查询最近一个月的 
    public ResponseEntity<String> queryRecentDealsOfUser(@RequestParam(value = "uid") Long userId) {
        List<BusinessRecord> recentDeals = this.businessRecordService.getRecentBusinessRecordsOfUser(userId);
        
        return ResponseEntity.ok(recentDeals.toString());
    }
    
    // start end format = yyyy-MM-ddTHH:mm:ss 
    @GetMapping(value = "query/{start}_{end}")
    public ResponseEntity<String> queryDealsOfUserAtRange(@RequestParam(value = "uid") Long userId, 
            @PathVariable(value = "start") String start, @PathVariable(value = "end") String end) {
        LocalDateTime startTime = DateAndTimeTransferUtils.parseStringToLocalDateTime(start, null);
        LocalDateTime endTime = DateAndTimeTransferUtils.parseStringToLocalDateTime(end, null);
        List<BusinessRecord> records = this.businessRecordService.getBusinessRecordsAtTimeRangeOfUser(userId, startTime, endTime);
        
        return ResponseEntity.ok(records.toString());
    }
    
}










