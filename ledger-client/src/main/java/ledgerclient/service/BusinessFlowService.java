package ledgerclient.service;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ledgerclient.utils.HttpClientUtils;

/**
 * 账目流水 相关服务 
 * @author wangy
 *
 */
public class BusinessFlowService {
    
    private static final Logger log = LoggerFactory.getLogger(BusinessFlowService.class);
    private static final BusinessFlowService businessService = new BusinessFlowService();
    
    private String businessFlowBaseURL = "http://localhost:8888/api/v1/business";
    
    public static final BusinessFlowService getInstance() {
        return businessService;
    }
    
    public String getRecentBusinessFlow(Long userID) {
        String requestURL = this.businessFlowBaseURL.concat("/query").concat("?uid=").concat(userID.toString());
        
        try {
            CompletableFuture<HttpResponse<String>> response = HttpClientUtils.asyncHttpGet(requestURL);
            return response.get(10, TimeUnit.SECONDS).body();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    public String getRangeBusinessRecord(Long userID, String start, String end) {
        String requestURL = this.businessFlowBaseURL.concat("/query/").concat(start).concat("_").concat(end)
                .concat("?uid=").concat(userID.toString());
        
        try {
            CompletableFuture<HttpResponse<String>> response = HttpClientUtils.asyncHttpGet(requestURL);
            return response.get(10, TimeUnit.SECONDS).body();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
}






