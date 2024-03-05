package com.kulturman.webserver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RequestParserTest {
    RequestParser requestParser;

    @BeforeEach
    void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    void parsesRequestSuccessfully() throws HttpRequestParserException {
        var request = requestParser.parse("GET /index.html HTTP/1.1");

        assertEquals("GET", request.getMethod());
        assertEquals("/index.html", request.getPath());
        assertEquals("1.1", request.getHttpVersion());
    }

    @Test
    void raisesErrorOnIncorrectRequest() {
        assertThrows(HttpRequestParserException.class, () -> requestParser.parse("GET /index.html"));
    }
}
