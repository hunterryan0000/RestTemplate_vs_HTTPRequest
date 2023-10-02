package com.tutorial.RestApiTutorial;

import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class API_Methods {
    public static final String API_BASE_URL = "https://api.assemblyai.com/v2/transcript";

    public static void main(String[] args) {
        RestTemplate restTemplate = initializeRestTemplate();
        HttpHeaders headers = initializeHeaders();
        Transcript requestBody = initializeRequestBody();

        Gson gson = new Gson();
        String jsonRequest = toJson(requestBody, gson);

        ResponseEntity<String> postResponse = postTranscript(jsonRequest, headers, restTemplate);

        requestBody = fromJson(postResponse.getBody(), gson);

        System.out.println("Transcription ID: " + requestBody.getId());
        pollTranscriptStatus(restTemplate, requestBody.getId());
    }

    private static RestTemplate initializeRestTemplate() {
        return new RestTemplate();
    }

    private static HttpHeaders initializeHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Confidential.KEY());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static Transcript initializeRequestBody() {
        Transcript requestBody = new Transcript();
        requestBody.setAudio_url("https://www2.cs.uic.edu/~i101/SoundFiles/preamble.wav");
        return requestBody;
    }

    private static String toJson(Transcript requestBody, Gson gson) {
        return gson.toJson(requestBody);
    }

    private static ResponseEntity<String> postTranscript(String jsonRequest, HttpHeaders headers, RestTemplate restTemplate) {
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
        return restTemplate.postForEntity(API_BASE_URL, requestEntity, String.class);
    }

    private static Transcript fromJson(String responseBody, Gson gson) {
        return gson.fromJson(responseBody, Transcript.class);
    }

    private static void pollTranscriptStatus(RestTemplate restTemplate, String id) {
        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.set("Authorization", Confidential.KEY());
        HttpEntity<String> getEntity = new HttpEntity<>(getHeaders);

        String transcriptUrl = API_BASE_URL + "/" + id;

        Transcript requestBody;

        while (true) {
            ResponseEntity<String> getResponse = restTemplate.exchange(transcriptUrl, HttpMethod.GET, getEntity, String.class);
            requestBody = fromJson(getResponse.getBody(), new Gson());

            System.out.println("Transcription Status: " + requestBody.getStatus());

            if ("completed".equals(requestBody.getStatus()) || "error".equals(requestBody.getStatus())) {

                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Transcript Completed!");
        System.out.println();
        System.out.println("Transcript Text: " + requestBody.getText());
    }
}
