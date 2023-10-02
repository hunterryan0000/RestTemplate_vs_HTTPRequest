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
    /**
     * Initializes a RestTemplate object to handle HTTP requests.
     * @return RestTemplate object
     */
    private static RestTemplate initializeRestTemplate() {
        return new RestTemplate();
    }
    /**
     * Initializes HttpHeaders with Authorization and Content-Type.
     * @return HttpHeaders object
     */
    private static HttpHeaders initializeHeaders() {
        // Specify the credentials that the server needs to validate before granting access to its resources.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", Confidential.KEY());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    /**
     * Initializes the request body for the transcript API.
     * @return Transcript object with set properties
     */
    private static Transcript initializeRequestBody() {
        Transcript requestBody = new Transcript();
        requestBody.setAudio_url("https://www2.cs.uic.edu/~i101/SoundFiles/preamble.wav");
        return requestBody;
    }
    /**
     * Converts a Transcript object to JSON string.
     * @param requestBody Transcript object
     * @param gson Gson object for serialization
     * @return JSON string
     */
    private static String toJson(Transcript requestBody, Gson gson) {
        return gson.toJson(requestBody);
    }
    /**
     * Sends a POST request to the API to create a new transcript.
     * @param jsonRequest JSON string as request body
     * @param headers HttpHeaders object
     * @param restTemplate RestTemplate object
     * @return ResponseEntity object containing API response
     */
    private static ResponseEntity<String> postTranscript(String jsonRequest, HttpHeaders headers, RestTemplate restTemplate) {
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
        //used to encapsulate the request information you send to the server.
        return restTemplate.postForEntity(API_BASE_URL, requestEntity, String.class);
        //encapsulates the response received from the server.
        //ResponseEntity extends HttpEntity, so it is an HttpEntity with additional features like status codes.
    }
    /**
     * Converts a JSON string to a Transcript object.
     * @param responseBody JSON string received from API
     * @param gson Gson object for deserialization
     * @return Transcript object
     */
    private static Transcript fromJson(String responseBody, Gson gson) {
        return gson.fromJson(responseBody, Transcript.class);
    }
    /**
     * Polls the transcript status until it is completed or errors out.
     * @param restTemplate RestTemplate object
     * @param id Transcript ID
     */
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
