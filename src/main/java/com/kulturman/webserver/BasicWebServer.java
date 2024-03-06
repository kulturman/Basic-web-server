package com.kulturman.webserver;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class BasicWebServer {
    public static final String DEFAULT_PAGE = "/index.html";
    private final String rootDirectory;

    public String handleRequest(String httpRequest) throws IOException, HttpRequestParserException {
        var requestParser = new RequestParser();
        var request = requestParser.parse(httpRequest);
        var fullPath = getFullPath(request);

        var fileContent = Files.readString(fullPath);

        return "HTTP/1.1 200 OK\r\n\r\n" + fileContent;
    }

    private Path getFullPath(Request request) {
        var path = Path.of(rootDirectory + request.getPath());

        if (Files.isDirectory(path)) {
            return Path.of(rootDirectory + request.getPath() + DEFAULT_PAGE);
        }

        return Path.of(rootDirectory + request.getPath());
    }
}
