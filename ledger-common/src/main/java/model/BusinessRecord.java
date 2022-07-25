package model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表示谁在什么时间消费了多少 
 * @author wangy
 *
 */
@Entity
@Table(name = "business_record")
public class BusinessRecord { 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;  // 匿名用户使用负数id  -1 
    @Column(name = "amount")
    private Double amount;
    @Column(name = "record_time")
    private LocalDateTime recordTime;  // 记账时间 创建时间 
    
    public BusinessRecord(Long userId, Double amount) {
        this.userId = userId;
        this.amount = amount;
        this.recordTime = LocalDateTime.now();
    }
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public LocalDateTime getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }
    
    @Override
    public String toString() {
        return "BusinessRecord [id=" + id + ", userId=" + userId + ", amount=" + amount + ", recordTime=" + recordTime
                + "]";
    }
    
}





