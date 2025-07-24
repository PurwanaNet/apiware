/*
 *  Apiware © 2025 by Angga Purwana AM.d, S.Kom. is licensed under Creative Commons Attribution-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-sa/4.0/
 *
 */
package id.co.itasoft.net.purwana.apiware;

/**
 *
 * @author Angga Purwana
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiwareRequest {

    private String method = "GET";
    private String url;
    private String bodyType = "form-data";
    private String body;
    private Map<String, String> headers = new HashMap<>();

    public ApiwareRequest(String url) {
        this.url = url;
    }

    public ApiwareRequest withMethod(String method) {
        this.method = method;
        return this;
    }

    public ApiwareRequest withBody(String body, String type) {
        this.body = body;
        this.bodyType = type;
        return this;
    }

    public ApiwareRequest withBodyType(String type) {
        this.bodyType = type;
        return this;
    }

    public ApiwareRequest withHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    private Map<String, String> bodyParams = new HashMap<>();

    public ApiwareRequest withBodyParam(String key, String value) {
        this.bodyParams.put(key, value);
        return this;
    }

    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public String getBodyType() {
        return bodyType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addBodyParams(String key, String value) {
        this.bodyParams.put(key, value);
    }

    ///helpers:
    public byte[] buildMultipartFormData(String boundary) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String LINE_FEED = "\r\n";

        for (Map.Entry<String, String> entry : getBodyParams().entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            outputStream.write(("--" + boundary + LINE_FEED).getBytes(StandardCharsets.UTF_8));
            outputStream.write(("Content-Disposition: form-data; name=\"" + name + "\"" + LINE_FEED).getBytes(StandardCharsets.UTF_8));
            outputStream.write(("Content-Type: text/plain; charset=UTF-8" + LINE_FEED).getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_FEED.getBytes(StandardCharsets.UTF_8));
            outputStream.write(value.getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_FEED.getBytes(StandardCharsets.UTF_8));
        }

        // Tambahkan newline sebelum boundary penutup
        outputStream.write(("--" + boundary + "--" + LINE_FEED).getBytes(StandardCharsets.UTF_8));

        return outputStream.toByteArray();
    }
}
