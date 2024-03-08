package com.kulturman.webserver;

import java.util.regex.Pattern;

public class RequestParser {
    public Request parse(String httpRequest) throws HttpRequestParserException {
        var pattern = "^(\\w+)\\s(\\S+)\\sHTTP/(\\d.\\d)";
        var lines = httpRequest.lines().toList();
        var matcher = Pattern.compile(pattern).matcher(lines.get(0).trim());
        if (!matcher.matches()) throw new HttpRequestParserException("Invalid request");

        var path = extractPath(matcher.group(2));

        return new Request(matcher.group(1), path, matcher.group(3));
    }

    private String extractPath(String pathWithParameters) {
        return pathWithParameters.split("\\?")[0];
    }
}
