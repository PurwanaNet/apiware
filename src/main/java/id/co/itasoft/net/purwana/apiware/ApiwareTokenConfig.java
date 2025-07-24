/*
 *  Apiware © 2025 by Angga Purwana AM.d, S.Kom. is licensed under Creative Commons Attribution-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-sa/4.0/
 *
 */

/**
 *
 * @author Angga Purwana
 */
package id.co.itasoft.net.purwana.apiware;

public class ApiwareTokenConfig {
    
    public enum AttachTo {
        ATTACH_TO_HEADER,
        ATTACH_TO_BODY_FORM_DATA,
        ATTACH_TO_BODY_X_WWW_FORM_URLENCODED,
        ATTACH_TO_QUERY_PARAM,
        ATTACH_TO_JSON_BODY
    }    
    private String tokenUrl;
    private String method = "POST";
    private String tokenFieldName = "token";
    private String retrievedTokenFieldFromJSON = "data";
    private AttachTo attachTo = AttachTo.ATTACH_TO_BODY_FORM_DATA;

    public ApiwareTokenConfig(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public ApiwareTokenConfig withMethod(String method) {
        this.method = method;
        return this;
    }

    public ApiwareTokenConfig withTokenField(String fieldName) {
        this.tokenFieldName = fieldName;
        return this;
    }
    
    public ApiwareTokenConfig withTokenFieldFromJSON(String jsonFieldName) {
        this.retrievedTokenFieldFromJSON = jsonFieldName;
        return this;
    }    


    
    public ApiwareTokenConfig withAttachTo(AttachTo attachTo) {
        this.attachTo = attachTo;
        return this;
    }

    public AttachTo getAttachTo() {
        return attachTo;
    }    

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getMethod() {
        return method;
    }

    public String getTokenFieldName() {
        return tokenFieldName;
    }

    public String getRetrievedTokenFieldFromJSON() {
        return retrievedTokenFieldFromJSON;
    }
}
