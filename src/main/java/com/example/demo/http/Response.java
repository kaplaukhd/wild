package com.example.demo.http;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class Response {
    private int code;
    private HttpEntity body;

    public Response(int code, HttpEntity body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpEntity getBody() {
        return body;
    }

    public void setBody(HttpEntity body) {
        this.body = body;
    }
}
