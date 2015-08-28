package org.treeleaf.web.spring.resovler;

/**
 * @Author leaf
 * 2015/8/28 0028 20:50.
 */
public class ErrorInfo {

    private String uri;

    private Exception exception;

    private String ext;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
