package ledgerclient.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConstantConfig;

/**
 * ref : https://openjdk.java.net/groups/net/httpclient/intro.html
 *
 * @author eron
 */
public class HttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
    private static HttpClient client = null;
    private static String token = "";

    // 单例模式
    private static HttpClient getDefaultHttpClient() {
        if (client == null) {
            synchronized (HttpClientUtils.class) {
                if (client == null) {
                    client = HttpClient.newBuilder()
                        .followRedirects(Redirect.NORMAL)  // Redirect.SAME_PROTOCOL
                        .connectTimeout(Duration.ofMinutes(1))
                        //.proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
                        .version(Version.HTTP_2)
                        .build();
                }
            }
        }

        return client;
    }


    public static CompletableFuture<HttpResponse<String>> asyncHttpGet(String url) {
        log.info("http GET URL ==> " + url);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .header(ConstantConfig.AUTHORIZATION_HEADER, ConstantConfig.AUTH_HEADER_PREFIX + token)
            .GET()
            .build();

        CompletableFuture<HttpResponse<String>> getAsyncResponse
            = getDefaultHttpClient().sendAsync(request, BodyHandlers.ofString());
        getAsyncResponse.whenComplete((response, error) -> {
            if (response != null) {
                log.info("get response => " + response.statusCode());
                log.info("get response body => " + response.body());
            }
            if (error != null) {
                log.info("get error => " + error.getMessage());
            }
        });

        return getAsyncResponse;
    }


    public static CompletableFuture<HttpResponse<String>> asyncHttpPost(String url, String requestBody) {
        log.info("http POST URL ==> " + url);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .header(ConstantConfig.AUTHORIZATION_HEADER, ConstantConfig.AUTH_HEADER_PREFIX + token)
            .POST(BodyPublishers.ofString(requestBody))
            .build();

        CompletableFuture<HttpResponse<String>> postAsyncResponse
            = getDefaultHttpClient().sendAsync(request, BodyHandlers.ofString());
        postAsyncResponse.whenComplete((response, error) -> {
            if (response != null) {
                log.info("post response => " + response.statusCode());
                log.info("post response body => " + response.body());
            }
            if (error != null) {
                log.info("post error => " + error.getMessage());
            }
        });

        return postAsyncResponse;
    }

    public static void setToken(String token) {
        HttpClientUtils.token = token;
    }
}




