package ledgerserver.service.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service 
public class AliPayServiceImpl {
    
    private static final Logger log = LoggerFactory.getLogger(AliPayServiceImpl.class);
    
    private RestTemplate client;
    
    @Autowired 
    public AliPayServiceImpl() {
        RestTemplateBuilder builder = new RestTemplateBuilder(new RestTemplateCustomizer() {
            @Override 
            public void customize(RestTemplate restTemplate) {
                restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
                    
                    @Override
                    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                            ClientHttpRequestExecution execution) throws IOException {
                        log.info("请求日志");
                        log.warn("请求头 -> {}", request.getHeaders().toSingleValueMap());
                        log.warn("请求方法 -> {}", request.getMethod().name());
                        log.warn("请求url -> {}", request.getURI());
                        
                        return execution.execute(request, body);
                    }
                });
            }
        });
        
        client = builder.build();
    }
    
    @PostConstruct 
    public void init() {
        // 初始化 
    }
    
}








