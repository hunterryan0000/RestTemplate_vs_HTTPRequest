package com.tutorial.RestApiTutorial;

import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**This Java code is a complete example of interacting with a RESTful API to transcribe audio.
 * It demonstrates the usage of various key components and libraries, such as RestTemplate,
 * HttpHeaders, and Google's Gson library */

public class RestApiTutorial {
    public static final String API_BASE_URL = "https://api.assemblyai.com/v2/transcript";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        //Initializes a RestTemplate object to make HTTP requests.
        HttpHeaders headers = new HttpHeaders();
        //Initializes the headers that will be included in the HTTP request.
        headers.set("Authorization", Confidential.KEY());
        //Sets the authorization header
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Sets the content type to JSON.

        Transcript requestBody = new Transcript();
        // Initializes a Transcript object to be sent in the request body.
        requestBody.setAudio_url("https://www2.cs.uic.edu/~i101/SoundFiles/preamble.wav");
        //Sets the URL of the audio file to be transcribed.

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(requestBody);
        // Converts the Transcript object into a JSON string using Gson.

        System.out.println(jsonRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
        //Encapsulates the request body and headers.

        ResponseEntity<String> postResponse = restTemplate.postForEntity(
                API_BASE_URL,
                requestEntity,
                String.class
        );
        // Sends a POST request and receives the response.

        System.out.println(postResponse.getBody());

        requestBody = gson.fromJson(postResponse.getBody(), Transcript.class);

        System.out.println("Transcription ID: " + requestBody.getId());

        String transcriptUrl = API_BASE_URL + "/" + requestBody.getId();
        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.set("Authorization", Confidential.KEY());
        HttpEntity<String> getEntity = new HttpEntity<>(getHeaders);

        //The code then extracts the transcription ID and constructs the URL to periodically check the status of the transcription.

        while (true) {
            ResponseEntity<String> getResponse = restTemplate.exchange(
                    transcriptUrl,
                    HttpMethod.GET,
                    getEntity,
                    String.class
            );

            requestBody = gson.fromJson(getResponse.getBody(), Transcript.class);
            System.out.println("Transcription Status: " + requestBody.getStatus());

            if ("completed".equals(requestBody.getStatus()) || "error".equals(requestBody.getStatus())) {
                break;
            }
            // loop continuously sends GET requests to check the status,
            // breaking when the status is "completed" or "error".

            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Transcript Completed!");
        System.out.println();
        System.out.println("Transcript Text: " + requestBody.getText());
    }
}

