/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.pojo;

/**
 *
 * @author nikos
 */
public class BaseResponse {

    public static final String SUCCESS = "OK";
    public static final String FAILED = "NOK";

    private String status;

    public BaseResponse() {
    }

    ;
    
    public BaseResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
