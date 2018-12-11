/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author nikos
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ADResponse {

    private String status;
    private String id;
    private ErrorWrapper error;
    private Details details;
    

    public ADResponse() {
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ErrorWrapper getError() {
        return error;
    }

    public void setError(ErrorWrapper error) {
        this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Details {
        private String id;
        @JsonProperty("userPrincipalName")
        private String principal;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }
        
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class ErrorWrapper {

        private Error error;

        public ErrorWrapper() {
        }

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }

        /*
     *{"code":"Request_ResourceNotFound","message":"Resource 'nikos@i4mlabUAegean.onmicrosoft.com' does not exist or one of its queried reference-property objects are not present.","innerError":{"request-id":"e27f5f73-5a75-4bf3-96db-7182e6709ecd","date":"2018-05-09T14:55:17"} 
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Error {

            private String code;
            private String message;
            private InnerError innerError;

            public Error() {
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public InnerError getInnerError() {
                return innerError;
            }

            public void setInnerError(InnerError innerError) {
                this.innerError = innerError;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public class InnerError {

                @JsonProperty("request-id")
                private String requestId;
                private String date;

                public InnerError() {
                }

                public String getRequestId() {
                    return requestId;
                }

                public void setRequestId(String requestId) {
                    this.requestId = requestId;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

            }

        }

    }

}
