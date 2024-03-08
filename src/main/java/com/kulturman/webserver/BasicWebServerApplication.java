package com.kulturman.webserver;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;

@SpringBootApplication
public class BasicWebServerApplication {

    public static void main(String[] args) throws IOException, HttpRequestParserException, ArgumentParserException {
        SpringApplication.run(BasicWebServerApplication.class, args);

        var parser = ArgumentParsers.newFor("BasicWebServer").build()
            .defaultHelp(true)
            .description("BasicWebServer to serve static files.");

        parser.addArgument("-rootDir")
            .required(true);

        parser.addArgument("-p", "--port")
            .setDefault(9090)
            .help("Application port number. Default is 9090.");

        var ns = parser.parseArgs(args);

        try (var serverSocket = new ServerSocket(ns.getInt("port"))) {
            var webServer = new BasicWebServer(ns.getString("rootDir"));

            while (true) {
                var clientSocket = serverSocket.accept();
                var output = new PrintStream(clientSocket.getOutputStream());
                var input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                var request = new StringBuilder();

                while (input.ready()) {
                    request.append(input.readLine()).append("\r\n");
                }

                output.println(webServer.handleRequest(request.toString()));
                clientSocket.close();
            }
        }
    }
}
