package com.kulturman.webserver;

public class Response {
    public Response(String httpVersion, HttpStatus status, String body) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.body = body;
    }

    public Response(String httpVersion, HttpStatus status) {
        this(httpVersion, status, "");
    }

    private final String httpVersion;
    private final HttpStatus status;
    private final String body;

    public String toString() {
        return String.format("HTTP/%s %s %s\r\n\r\n%s", httpVersion, status.getCode(), status.getMessage(), body);
    }
}
