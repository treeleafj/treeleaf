package org.treeleaf.common.safe;

/**
 * @Author leaf
 * 2015/6/26 0026 0:36.
 */
public class AssertException extends RuntimeException {

    private String message;

    private String retCode;

    public AssertException(String message) {
        super(message);
        this.message = message;
    }

    public AssertException(String retCode, String message){
        super(message);
        this.retCode = retCode;
        this.message = message;
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
