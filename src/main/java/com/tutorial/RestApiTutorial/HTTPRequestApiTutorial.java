package com.tutorial.RestApiTutorial;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPRequestApiTutorial {
    // Define the base URL for the API endpoint
    public static final String API_BASE_URL = "https://api.assemblyai.com/v2/transcript";

    // The main method of your Java application
    public static void main(String[] args) throws Exception {

        // Create a new Transcript object
        Transcript transcript = new Transcript();

        // Set the audio URL in the Transcript object
        transcript.setAudio_url("https://www2.cs.uic.edu/~i101/SoundFiles/preamble.wav");

        // Create a Gson object for JSON serialization
        Gson gson = new Gson();

        // Serialize the Transcript object to JSON format
        String jsonRequest = gson.toJson(transcript);

        // Print the JSON request for debugging purposes
        System.out.println(jsonRequest);

        // Create an HTTP POST request using HttpRequest.Builder
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(API_BASE_URL))
                .header("Authorization", Confidential.KEY()) // Add an authorization header with a confidential key
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest)) // Set the request body with the JSON data
                .build();

        // Create an HTTP client
        HttpClient httpClient = HttpClient.newHttpClient();

        // Send the POST request and store the response
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        // Print the response body from the POST request
        System.out.println(postResponse.body());

        // Deserialize the response JSON into a Transcript object
        transcript = gson.fromJson(postResponse.body(), Transcript.class);

        // Print the ID of the transcript
        System.out.println(transcript.getId());

        // Create an HTTP GET request to check the status of the transcript
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(API_BASE_URL + "/" + transcript.getId()))
                .header("Authorization", Confidential.KEY()) // Add an authorization header with a confidential key
                .build();

        // Continuously check the status of the transcript until it's completed or an error occurs
        while(true) {
            // Send the GET request and store the response
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            transcript = gson.fromJson(getResponse.body(), Transcript.class);

            // Print the status of the transcript
            System.out.println(transcript.getStatus());

            // Check if the transcript status is "completed" or "error" to exit the loop
            if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())){
                break;
            }
            Thread.sleep(1000); // Wait for 1 second before checking again
        }

        // Print a message when the transcript is completed
        System.out.println("Transcript Completed!");
        System.out.println();
        // Print the text of the transcript
        System.out.println(transcript.getText());
    }

}
