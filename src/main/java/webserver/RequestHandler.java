package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.info("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);

            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(dos);

            handle(httpRequest, httpResponse);

            bufferedReader.close();
            dos.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest request, HttpResponse response) {
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getHttpPath();
        response.forward(path);
    }
}
