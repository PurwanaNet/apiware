/*
 *  Apiware © 2025 by Angga Purwana AM.d, S.Kom. is licensed under Creative Commons Attribution-ShareAlike 4.0 International. To view a copy of this license, visit https://creativecommons.org/licenses/by-sa/4.0/
 *
 */
/**
 *
 * @author Angga Purwana
 */
package id.co.itasoft.net.purwana.apiware;

import org.junit.Test;
import static org.junit.Assert.*;

public class ApiwareTest {

    private String BASE_URL = "http://172.16.253.64/mcm/";
    private String transactionId = "2020102900000000000001";

    @Test
    public void testBalanceInquiry() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        ///
        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};
        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "balance_inquiry")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("bank_card_token", "TBA")
                .withBodyParam("account_no", "2000100101");

        // === 2. Send ===
        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        //System.out.println("statusCode: " + response.getStatusCode() + ", response : " + response.getBody());
        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        //System.out.println("BALANCE RESPONSE:\n" + response.getBody());
    }

    @Test
    public void testAccountInquiryInternal() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "account_inquiry_internal")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("beneficiary_account_no", "1300011854315");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("INQUIRY RESPONSE:\n" + response.getBody());
    }
}
