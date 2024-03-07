package com.kulturman.webserver;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class BasicWebServer {
    public static final String DEFAULT_PAGE = "/index.html";
    private final String rootDirectory;

    public Response handleRequest(String httpRequest) throws IOException, HttpRequestParserException {
        var requestParser = new RequestParser();
        var request = requestParser.parse(httpRequest);
        var fullPath = getFullPath(request);

        if (pathNotInRootDirectory(fullPath)) {
            return new Response("1.1", HttpStatus.FORBIDDEN);
        }

        if (!Files.exists(fullPath)) {
            return new Response("1.1", HttpStatus.NOT_FOUND);
        }

        return new Response("1.1", HttpStatus.OK, Files.readString(fullPath));
    }

    private boolean pathNotInRootDirectory(Path fullPath) {
        return !fullPath.startsWith(FileSystems.getDefault().getPath(rootDirectory).normalize());
    }

    private Path getFullPath(Request request) {
        var path = rootDirectory + request.getPath();

        if (Files.isDirectory(Path.of(path))) {
            path += DEFAULT_PAGE;
        }

        return FileSystems.getDefault().getPath(path).normalize();
    }
}
