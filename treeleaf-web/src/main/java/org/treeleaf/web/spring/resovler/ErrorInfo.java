package org.treeleaf.web.spring.resovler;

/**
 * @Author leaf
 * 2015/8/28 0028 20:50.
 */
public class ErrorInfo {

    private String uri;

    private Throwable exception;

    private String ext;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
