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
    void raisesErrorOnIncorrectRequest() {
        assertThrows(HttpRequestParserException.class, () -> requestParser.parse("GET /index.html"));
    }
}
