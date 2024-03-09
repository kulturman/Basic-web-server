package com.kulturman.webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RequestParserTest {
    RequestParser requestParser;

    @BeforeEach
    void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    void parsesRequestSuccessfully() throws HttpRequestParserException, IOException {
        var request = requestParser.parse("""
            GET /index.html HTTP/1.1
            Host: localhost:9090
            User-Agent: curl/7.68.0
        """);

        assertEquals("GET", request.getMethod());
        assertEquals("/index.html", request.getPath());
        assertEquals("1.1", request.getHttpVersion());
    }

    @Test
    void extractsParametersFromRequest() throws HttpRequestParserException {
        var request = requestParser.parse("GET /index.html?param1=value1&param2=value2&param3=value3 HTTP/1.1");

        assertEquals("/index.html", request.getPath());
        assertEquals("value1", request.getParameters().get("param1"));
        assertEquals("value2", request.getParameters().get("param2"));
    }

    @Test
    void raisesErrorOnIncorrectRequest() {
        assertThrows(HttpRequestParserException.class, () -> requestParser.parse("GET /index.html"));
    }
}
