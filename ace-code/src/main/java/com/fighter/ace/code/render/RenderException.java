package com.fighter.ace.code.render;

/**
 * Created by hanebert on 17/10/31.
 */
public class RenderException extends RuntimeException{

    private String code;
    private String message;

    public String getMessage() {
        return this.message == null?this.code:this.message;
    }

    public RenderException(Throwable throwable) {
        super(throwable);
    }

    public RenderException() {
    }

    public RenderException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(String message) {
        super(message);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
