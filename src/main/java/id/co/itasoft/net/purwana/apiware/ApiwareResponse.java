/*
 *  Apiware © 2025 by Angga Purwana AM.d, S.Kom. is licensed under Creative Commons Attribution-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-sa/4.0/
 *
 */
package id.co.itasoft.net.purwana.apiware;

import org.json.JSONObject;

/**
 *
 * @author Angga Purwana
 */
public class ApiwareResponse {

    private int statusCode;
    private String body;

    public ApiwareResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public JSONObject getBodyJSON() {
        return new JSONObject(body);
    }
}
