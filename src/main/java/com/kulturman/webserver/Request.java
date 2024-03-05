package com.kulturman.webserver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Request {
    private String method;
    private String path;
    private String httpVersion;
}
