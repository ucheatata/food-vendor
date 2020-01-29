package com.foodvendor.payload;

/**
 * Class handles response messages and states
 */
public class ApiResponse {
    private Boolean success;
    private String message;

    /**
     * Constructor
     *
     * @param success
     * @param message
     */
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Method returns state of response
     *
     * @return Response state
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * Method sets response state
     *
     * @param success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Method returns response message
     *
     * @return Response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method sets response message
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
