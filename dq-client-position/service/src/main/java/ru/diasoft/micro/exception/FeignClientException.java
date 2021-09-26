package ru.diasoft.micro.exception;

import java.util.Collection;
import java.util.Map;

public class FeignClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Integer status;
    private final String errorMessage;

    private final Map<String, Collection<String>> headers;

    public FeignClientException(
            Integer status, String errorMessage, Map<String, Collection<String>> headers) {
        super(String.format("%d %s", status, errorMessage));
        this.status = status;
        this.errorMessage = errorMessage;
        this.headers = headers;
    }

    /**
     * Http Status Code
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * FeignResponse Headers
     *
     * @return
     */
    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }
}

