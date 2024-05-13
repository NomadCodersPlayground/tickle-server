package com.techblogfinder.api.common.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

public class HttpRequestBuilder {

    public static HttpClient create() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public static HttpRequest build(String uri) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "text/html")
                .GET()
                .build();
    }
}
