package com.kulturman.webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RequestParser {
    public Request parse(String httpRequest) throws HttpRequestParserException {
        var pattern = "^(\\w+)\\s(\\S+)\\sHTTP/(\\d.\\d)";
        var lines = httpRequest.lines().toList();
        var matcher = Pattern.compile(pattern).matcher(lines.get(0).trim());
        if (!matcher.matches()) throw new HttpRequestParserException("Invalid request");

        var path = extractPath(matcher.group(2));
        var parameters = getParameters(matcher.group(2));

        return new Request(matcher.group(1), path, matcher.group(3), parameters);
    }

    private Map<String, String> getParameters(String pathWithParameters) {
        var parametersSubString = pathWithParameters.split("\\?");
        var parametersMap = new HashMap<String, String>();

        if (parametersSubString.length > 1) {
            for (var parameter: parametersSubString[1].split("&")) {
                var keyValue = parameter.split("=");
                parametersMap.put(keyValue[0], keyValue[1]);
            }
        }
        return parametersMap;
    }

    private String extractPath(String pathWithParameters) {
        return pathWithParameters.split("\\?")[0];
    }
}
