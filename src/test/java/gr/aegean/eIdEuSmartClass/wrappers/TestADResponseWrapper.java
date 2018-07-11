/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.wrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.aegean.eIdEuSmartClass.utils.pojo.ADResponse;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestADResponseWrapper {

    @Test
    public void TestWrappErrorResponse() throws IOException {
        String error = "{\"status\":\"NOK\",\"error\":{\"error\":{\"code\":\"Request_ResourceNotFound\",\"message\":\"Resource 'nikos@i4mlabUAegean.onmicrosoft.com' does not exist or one of its queried reference-property objects are not present.\",\"innerError\":{\"request-id\":\"e27f5f73-5a75-4bf3-96db-7182e6709ecd\",\"date\":\"2018-05-09T14:55:17\"}}}}";
        ObjectMapper mapper = new ObjectMapper();
        ADResponse resp = mapper.readValue(error, ADResponse.class);
        assertEquals(resp.getStatus(), "NOK");
        assertEquals(resp.getError().getError().getCode(), "Request_ResourceNotFound");
    }

    @Test
    public void TestWrapOKResponse() throws IOException {
        String resp = "{\"status\":\"OK\",\"details\":{\"@odata.context\":\"https://graph.microsoft.com/v1.0/$metadata#users/$entity\",\"id\":\"77fb1362-3c03-4769-8ff8-8925a83a073e\",\"businessPhones\":[],\"displayName\":\"nikostriantafylloutest\",\"givenName\":\"nikos\",\"jobTitle\":null,\"mail\":\"NIKOS.Test1@i4mlabUAegean.onmicrosoft.com\",\"mobilePhone\":null,\"officeLocation\":\"myEid\",\"preferredLanguage\":null,\"surname\":\"triant\",\"userPrincipalName\":\"NIKOS.Test1@i4mlabUAegean.onmicrosoft.com\"}}";
        ObjectMapper mapper = new ObjectMapper();
        ADResponse result = mapper.readValue(resp, ADResponse.class);
        assertEquals(result.getStatus(), "OK");
        assertEquals(result.getError(), null);
        assertEquals(result.getDetails().getId(), "77fb1362-3c03-4769-8ff8-8925a83a073e");
        assertEquals(result.getDetails().getPrincipal(), "NIKOS.Test1@i4mlabUAegean.onmicrosoft.com");
    }

    /*
    '{
  "error": {
    "code": "Request_ResourceNotFound",
    "message": "Resource '0db9d518-4f59-4c7c-9fd3-8f7e835e93ac' does not exist or one of its queried reference-property objects are not present.",
    "innerError": {
      "request-id": "27893c80-7603-451f-82df-2ec2eeb10b75",
      "date": "2018-05-30T17:20:02"
    }
  }
}'
     */
    @Test
    public void TestWrapErrorResponse() throws IOException {
        String resp = "{\n"
                + "  \"error\": {\n"
                + "    \"code\": \"Request_ResourceNotFound\",\n"
                + "    \"message\": \"Resource '0db9d518-4f59-4c7c-9fd3-8f7e835e93ac' does not exist or one of its queried reference-property objects are not present.\",\n"
                + "    \"innerError\": {\n"
                + "      \"request-id\": \"27893c80-7603-451f-82df-2ec2eeb10b75\",\n"
                + "      \"date\": \"2018-05-30T17:20:02\"\n"
                + "    }\n"
                + "  }\n"
                + "}";
        ObjectMapper mapper = new ObjectMapper();
        ADResponse result = mapper.readValue(resp, ADResponse.class);
//        assertEquals(result.getStatus(), "OK");
//        assertEquals(result.getError(), null);
//        assertEquals(result.getDetails().getId(), "77fb1362-3c03-4769-8ff8-8925a83a073e");
//        assertEquals(result.getDetails().getPrincipal(), "NIKOS.Test1@i4mlabUAegean.onmicrosoft.com");
    }
}
