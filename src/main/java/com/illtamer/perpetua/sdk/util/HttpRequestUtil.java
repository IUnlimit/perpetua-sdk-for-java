package com.illtamer.perpetua.sdk.util;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.Pair;
import com.illtamer.perpetua.sdk.handler.APIHandler;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

@UtilityClass
public class HttpRequestUtil {

    /**
     * 根据有无 payload 自动选择 post / get
     * */
    public static String request(String url, Object payload, @Nullable Map<String, String> headers) {
        final String payloadJson = parsePayload(payload);
        if (payloadJson.length() <= 2) {
            Pair<Integer, String> resp = getJson(url, null);
            Assert.isTrue(resp.getKey() == HttpStatus.SC_OK, "Unexpected status: " + resp.getKey());
            return resp.getValue();
        } else {
            return postJson(url, payload, headers);
        }
    }

    @NotNull
    @SneakyThrows(IOException.class)
    public static String postJson(String url, Object payload, @Nullable Map<String, String> headers) {
        final String payloadJson = parsePayload(payload);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectTimeout(3000)
                    .setSocketTimeout(3000)
                    .build());
            if (headers != null && headers.size() != 0)
                headers.entrySet().stream()
                        .filter(entry -> entry.getValue() != null)
                        .forEach(entry -> httpPost.setHeader(entry.getKey(), entry.getValue()));
            httpPost.setHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(payloadJson, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    return EntityUtils.toString(responseEntity);
                }
            } catch (Exception e) {
                System.out.println("Url: " + url);
                System.out.println("Payload: " + payload);
                e.printStackTrace();
            }
        }
        throw new RuntimeException("接口连接失败！");
    }

    @NotNull
    @SneakyThrows(IOException.class)
    public static Pair<Integer, String> getJson(String url, @Nullable Map<String, String> params) {
        int status;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(concatUrl(url, params));
            httpGet.setConfig(RequestConfig.custom()
                    .setConnectTimeout(3000)
                    .setSocketTimeout(3000)
                    .build());
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    return new Pair<>(status, EntityUtils.toString(responseEntity));
                }
            }
        }
        return new Pair<>(status, "");
    }

    @NotNull
    public static String parsePayload(@Nullable Object payload) {
        String content = "";
        if (payload == null) return content;
        try {
            content = new Gson().toJson(payload);
        } catch (Exception e) {
            System.err.printf("Some exception occurred when parse payload with endpoint: %s, %s",
                    ((APIHandler<?>) payload).getAction(), e);
        }
        return content;
    }

    @NotNull
    private static String concatUrl(@NotNull String url, @Nullable Map<String, String> params) {
        if (params == null || params.size() == 0) return url;
        StringBuilder builder = new StringBuilder(url).append('?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
        }
        return builder.deleteCharAt(builder.length()-1).toString();
    }

}
