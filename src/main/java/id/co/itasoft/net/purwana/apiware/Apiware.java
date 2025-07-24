/*
 *  Apiware © 2025 by Angga Purwana AM.d, S.Kom. is licensed under Creative Commons Attribution-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-sa/4.0/
 *
 */
package id.co.itasoft.net.purwana.apiware;

/**
 *
 * @author Angga Purwana
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class Apiware {

    private String token;
    private ApiwareTokenConfig tokenConfig;

    Apiware(ApiwareTokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    public Apiware withTokenConfig(ApiwareTokenConfig config) throws Exception {
        this.tokenConfig = config;
        return this;
    }

    private String fetchToken(ApiwareRequest request, String[] requiredFieldsForGenerateToken) throws Exception {
        //System.out.println("fetchToken");
        ApiwareRequest tokenRequest = new ApiwareRequest(tokenConfig.getTokenUrl())
                .withMethod(tokenConfig.getMethod());

        for (String rqf : requiredFieldsForGenerateToken) {
            tokenRequest.addBodyParams(rqf, request.getBodyParams().get(rqf));
        }

        ApiwareResponse response = sendRequest(tokenRequest, false, requiredFieldsForGenerateToken);

        //System.out.println("response :" + response.getBody());

        JSONObject json = new JSONObject(response.getBody());
        return json.getString(tokenConfig.getRetrievedTokenFieldFromJSON());
    }

    private String contentTypeFrom(String bodyType) {
        switch (bodyType) {
            case "form-data":
                return "multipart/form-data";
            case "x-www-form-urlencoded":
                return "application/x-www-form-urlencoded";
            case "raw":
            default:
                return "application/json";
        }
    }

    public ApiwareResponse send(ApiwareRequest request, String[] requiredFieldsForGenerateToken) throws Exception {
        //System.out.println("send url : " + request.getUrl());
        return sendRequest(request, true, requiredFieldsForGenerateToken);
    }

    private ApiwareResponse sendRequest(ApiwareRequest request, boolean includeToken, String[] requiredFieldsForGenerateToken) throws Exception {
        //System.out.println("sendRequest url : " + request.getUrl());

        if (includeToken) {
            token = fetchToken(request, requiredFieldsForGenerateToken);
        }

        HttpPost post = new HttpPost(request.getUrl());

        // Set Host header
        String host = URI.create(request.getUrl()).getHost();
        post.setHeader("Host", host);

        // Tambah header lainnya dari ApiwareRequest
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
        }

        // Tambah token ke header jika diperlukan
        if (includeToken && token != null && ApiwareTokenConfig.AttachTo.ATTACH_TO_HEADER == tokenConfig.getAttachTo()) {
            post.setHeader("Authorization", "Bearer " + token);
        }

        // Body
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        for (Map.Entry<String, String> entry : request.getBodyParams().entrySet()) {
            builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN);
        }

        // Tambah token ke body form-data jika diperlukan
        if (includeToken && token != null
                && ApiwareTokenConfig.AttachTo.ATTACH_TO_BODY_FORM_DATA == tokenConfig.getAttachTo()) {
            builder.addTextBody(tokenConfig.getTokenFieldName(), token, ContentType.TEXT_PLAIN);
        }

        HttpEntity entity = builder.build();
        post.setEntity(entity);

        // Kirim request
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);

        // Baca response
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent(), StandardCharsets.UTF_8));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }

        response.close();
        client.close();

        return new ApiwareResponse(response.getStatusLine().getStatusCode(), responseBody.toString());
    }

    ///helper:
    private String encodeUrlParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder encoded = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (encoded.length() > 0) {
                encoded.append("&");
            }
            encoded.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            encoded.append("=");
            encoded.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return encoded.toString();
    }

    private String buildFormData(Map<String, String> params, String boundary) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append("--").append(boundary).append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"\r\n\r\n");
            sb.append(entry.getValue()).append("\r\n");
        }
        sb.append("--").append(boundary).append("--\r\n");
        return sb.toString();
    }

    /*
    public String getToken(String [] requiredFieldsForGenerateToken) {
        try {
            fetchToken(requiredFieldsForGenerateToken);
        } catch (Exception ex) {
            Logger.getLogger(Apiware.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }*/
}
