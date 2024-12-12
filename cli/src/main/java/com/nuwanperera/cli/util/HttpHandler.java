package com.nuwanperera.cli.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class HttpHandler {
  private final HttpClient client;
  private final String URL;
  private final Gson gson;

  public HttpHandler(String URL) {
    this.URL = URL;
    this.client = HttpClient.newHttpClient();
    this.gson = new Gson();
  }

  public <T> T post(String endpoint, Object requestBody, Class<T> responseType) throws Exception {
    String requestBodyJson = gson.toJson(requestBody);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(URL + endpoint))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson)).build();

    return executeRequest(request, responseType);
  }

  public <T> T get(String endpoint, Class<T> responseType) throws Exception {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL + endpoint)).GET().build();

    return executeRequest(request, responseType);
  }

  public <T> T patch(String endpoint, Object requestBody, Class<T> responseType) throws Exception {
    String requestBodyJson = gson.toJson(requestBody);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(URL + endpoint))
        .header("Content-Type", "application/json")
        .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBodyJson)).build();

    return executeRequest(request, responseType);
  }

  public <T> T delete(String endpoint, Class<T> responseType) throws Exception {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL + endpoint)).DELETE().build();

    return executeRequest(request, responseType);
  }

  private <T> T executeRequest(HttpRequest request, Class<T> responseType) throws Exception {
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    int statusCode = response.statusCode();
    if (statusCode < 200 && statusCode > 300) {
      throw new RuntimeException("Request failed with status code: " + statusCode);
    }

    String responseBody = response.body();
    return gson.fromJson(responseBody, responseType);
  }
}
