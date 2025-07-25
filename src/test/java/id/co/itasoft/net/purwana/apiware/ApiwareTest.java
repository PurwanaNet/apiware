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
        System.out.println("BALANCE RESPONSE:\n" + response.getBody());
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
    
    @Test
    public void testAccountInquiryExternal() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "account_inquiry_external")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("beneficiary_account_no", "1300011854315")
                .withBodyParam("beneficiary_bank_code", "008");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }    
    
    @Test
    public void testBankStatement() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "bank_statement")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("bank_card_token", "6d7963617264746f6b656e")
                .withBodyParam("account_no", "2000200202");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }   
    
    @Test
    public void testTransferIntrabank() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "transfer_intrabank")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("amount", "5000.00")
                .withBodyParam("currency", "USD")
                .withBodyParam("beneficiary_account_no", "2000100101")
                .withBodyParam("beneficiary_email", "test@mail.com")
                .withBodyParam("customer_reference", "10052019")
                .withBodyParam("fee_type", "OUR")
                .withBodyParam("remark", "remark test")
                .withBodyParam("source_account_no", "2000200202")
                .withBodyParam("originator_customer_no", "999901000003300;12345")
                .withBodyParam("originator_customer_name", "Customer1;Customer2")
                .withBodyParam("originator_bank_code", "001;002");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }    
    
    @Test
    public void testTransferInterbank() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "transfer_interbank")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("amount", "5000.00")
                .withBodyParam("currency", "USD")
                .withBodyParam("beneficiary_account_name", "Yories Yolanda")                
                .withBodyParam("beneficiary_account_no", "2000100101")
                .withBodyParam("beneficiary_address", "Jakarta")   
                .withBodyParam("beneficiary_bank_code", "008")    
                .withBodyParam("beneficiary_bank_name", "Bank BRI")                
                .withBodyParam("beneficiary_email", "test@mail.com")
                .withBodyParam("customer_reference", "10052019")
                .withBodyParam("fee_type", "OUR")
                .withBodyParam("remark", "remark test")
                .withBodyParam("source_account_no", "2000200202")
                .withBodyParam("originator_customer_no", "999901000003300;12345")
                .withBodyParam("originator_customer_name", "Customer1;Customer2")
                .withBodyParam("originator_bank_code", "001;002");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }

    @Test
    public void testTransferSKN() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "transfer_skn")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("amount", "5000.00")
                .withBodyParam("currency", "USD")
                .withBodyParam("beneficiary_account_name", "Yories Yolanda")                
                .withBodyParam("beneficiary_account_no", "2000100101")
                .withBodyParam("beneficiary_address", "Jakarta")   
                .withBodyParam("beneficiary_bank_code", "008")    
                .withBodyParam("beneficiary_bank_name", "Bank BRI")    
                .withBodyParam("beneficiary_customer_residence", "1")  
                .withBodyParam("beneficiary_customer_type", "1")                  
                .withBodyParam("beneficiary_email", "test@mail.com")
                .withBodyParam("customer_reference", "10052019")
                .withBodyParam("fee_type", "OUR")
                .withBodyParam("kodepos", "12345")   
                .withBodyParam("receiver_phone", "08123456789")                 
                .withBodyParam("remark", "remark test")
                .withBodyParam("sender_customer_residence", "1")
                .withBodyParam("sender_customer_type", "1")   
                .withBodyParam("sender_phone", "6289656430684")                     
                .withBodyParam("source_account_no", "2000200202")
                .withBodyParam("originator_customer_no", "999901000003300;12345")
                .withBodyParam("originator_customer_name", "Customer1;Customer2")
                .withBodyParam("originator_bank_code", "001;002");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }


    @Test
    public void testTransferRTGS() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "transfer_rtgs")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("amount", "5000.00")
                .withBodyParam("currency", "USD")
                .withBodyParam("beneficiary_account_name", "Yories Yolanda")                
                .withBodyParam("beneficiary_account_no", "2000100101")
                .withBodyParam("beneficiary_address", "Jakarta")   
                .withBodyParam("beneficiary_bank_code", "008")    
                .withBodyParam("beneficiary_bank_name", "Bank BRI")    
                .withBodyParam("beneficiary_customer_residence", "1")  
                .withBodyParam("beneficiary_customer_type", "1")                  
                .withBodyParam("beneficiary_email", "test@mail.com")
                .withBodyParam("customer_reference", "10052019")
                .withBodyParam("fee_type", "OUR")
                .withBodyParam("kodepos", "12345")   
                .withBodyParam("receiver_phone", "08123456789")                 
                .withBodyParam("remark", "remark test")
                .withBodyParam("sender_customer_residence", "1")
                .withBodyParam("sender_customer_type", "1")   
                .withBodyParam("sender_phone", "6289656430684")                     
                .withBodyParam("source_account_no", "2000200202")
                .withBodyParam("originator_customer_no", "999901000003300;12345")
                .withBodyParam("originator_customer_name", "Customer1;Customer2")
                .withBodyParam("originator_bank_code", "001;002");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }

    @Test
    public void testTransactionStatus() throws Exception {
        ApiwareTokenConfig tokenConfig = new ApiwareTokenConfig(BASE_URL + "get_token")
                .withMethod("POST")
                .withTokenField("token")
                .withTokenFieldFromJSON("data");

        Apiware testApiware = new Apiware(tokenConfig);

        String[] requiredFieldsForGenerateToken = {"transaction_id"};

        // === 1. Create request ===
        ApiwareRequest request = new ApiwareRequest(BASE_URL + "transaction_status")
                .withMethod("POST")
                .withBodyType("form-data")
                .withBodyParam("transaction_id", transactionId)
                .withBodyParam("channel_id", "TBA")
                .withBodyParam("partner_reference_no", "TBA")
                .withBodyParam("reference_no", "2020102977770000000009")
                .withBodyParam("external_id", "30443786930722726463280097920912")
                .withBodyParam("service_code", "17")                
                .withBodyParam("amount", "5000.00")
                .withBodyParam("currency", "USD");

        ApiwareResponse response = testApiware.send(request, requiredFieldsForGenerateToken);

        // === 3. Assert ===
        assertEquals(200, response.getStatusCode());
        assertTrue("Should contain 'error_code'", response.getBody().contains("error_code"));
        System.out.println("RESPONSE:\n" + response.getBody());
    }    
}
