package com.kulturman.webserver;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
public class Request {
    private String method;
    private String path;
    private String httpVersion;
    private Map<String, String> parameters;

    public Request(String method, String path, String httpVersion) {
        this(method, path, httpVersion, new HashMap<>());
    }

    public Request(String method, String path, String httpVersion, Map<String, String> parameters) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.parameters = parameters;
    }
}
