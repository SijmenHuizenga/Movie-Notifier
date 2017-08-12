package it.sijmen.jump;

import org.springframework.http.HttpMethod;

import java.util.Map;

public class JumpRequest {

    private HttpMethod method;
    private Map<String, String> headers;
    private String urldata;
    private String body;

    public JumpRequest(HttpMethod method, Map<String, String> headers, String urldata, String body) {
        this.method = method;
        this.headers = headers;
        this.urldata = urldata;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getUrldata() {
        return urldata;
    }

    public String getBody() {
        return body;
    }
}
