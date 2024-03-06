package com.kulturman.webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicWebServerTest {
    BasicWebServer webServer;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        webServer = new BasicWebServer(ResourceUtils.getFile("classpath:www").toPath().toString());
    }

    @Test
    void getsDefaultPage() throws IOException, HttpRequestParserException {
        var fileContent = Files.readString(ResourceUtils.getFile("classpath:www/index.html").toPath());

        var response = webServer.handleRequest("GET / HTTP/1.1");

        assertEquals("HTTP/1.1 200 OK\r\n\r\n" + fileContent, response);
    }

    @Test
    void getsSpecificFile() throws IOException, HttpRequestParserException {
        var fileContent = Files.readString(ResourceUtils.getFile("classpath:www/home.html").toPath());

        var response = webServer.handleRequest("GET /home.html HTTP/1.1");

        assertEquals("HTTP/1.1 200 OK\r\n\r\n" + fileContent, response);
    }

    @Test
    void getsInnerDirectoryFile() throws IOException, HttpRequestParserException {
        var fileContent = Files.readString(ResourceUtils.getFile("classpath:www/e-commerce/amazon.html").toPath());

        var response = webServer.handleRequest("GET /e-commerce/amazon.html HTTP/1.1");

        assertEquals("HTTP/1.1 200 OK\r\n\r\n" + fileContent, response);
    }
}
