package http;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final String DELIMITER = ": ";
    private static final int SPLIT_LIMIT = 2;
    private static final int INDEX_OF_KEY = 0;
    private static final int INDEX_OF_VALUE = 1;
    public static final String COOKIE = "Cookie";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(BufferedReader reader) {
        String line;
        try {
            while ((line = reader.readLine()).isBlank()) {
                System.out.println("line = " + line);
                String[] keyValue = line.split(DELIMITER, SPLIT_LIMIT);
                headers.put(keyValue[INDEX_OF_KEY], keyValue[INDEX_OF_VALUE]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public String getCookie() {
        // TODO: Cookie 객체 생성
        return headers.getOrDefault(COOKIE, "");
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
    }
}