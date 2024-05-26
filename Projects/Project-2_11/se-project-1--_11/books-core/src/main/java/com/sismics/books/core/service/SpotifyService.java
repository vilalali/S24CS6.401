package com.sismics.books.core.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class SpotifyService implements AudioServiceProvider {
    
    private static final String SPOTIFY_API_URL = "https://api.spotify.com/v1/audiobooks/";
    private static final String SPOTIFY_API_URL_PODCAST = "https://api.spotify.com/v1/episodes/";

    private static final String clientId = "aeb6c920f0b342329aa3fa43bcae00fc"; // Replace with your actual client ID
    private static final String clientSecret = "7e64878d39604fe1bce9d8521d70ee5c"; // Replace with your actual client secret

    @Override
    public String playAudiobook(String audiobookId) {
        String apiUrl = SPOTIFY_API_URL + audiobookId + "?market=US";

        try {
        // Step 1: Get Access Token
            String accessToken = getAccessToken(clientId, clientSecret);
            
            if (accessToken != null) {
                // Step 2: Make GET Request with Access Token
               return getAudioData(apiUrl, accessToken);

            } else {
                System.out.println("Failed to obtain access token.");
            }
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    public String playPodcast(String podcast) {
        
        // Step 1: Get Access Token
        String accessToken = getAccessToken(clientId, clientSecret);
        
        if (accessToken != null) {
            // Step 2: Make GET Request with Access Token
            //"https://api.spotify.com/v1/episodes/${podcast}?market=US"; 
            String apiUrl = SPOTIFY_API_URL_PODCAST+podcast+"?market=US";
            System.out.println(apiUrl + "apiUrl value is here");
            String Data = getData(apiUrl, accessToken);
            return Data;
        } else {
            System.out.println("Failed to obtain access token.");
        }
        return "false";
        // Implement method for playing podcasts from Spotify
    }

    private static String getAccessToken(String clientId, String clientSecret) {
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method
            connection.setRequestMethod("POST");
            
            // Set request headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            // Encode client ID and client secret
            String auth = clientId + ":" + clientSecret;
            String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
            connection.setRequestProperty("Authorization", authHeader);
            
            // Set request body
            String requestBody = "grant_type=client_credentials";
            byte[] postData = requestBody.getBytes(StandardCharsets.UTF_8);
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
            }
            
            // Get response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                // Extract access token from response
                String jsonResponse = response.toString();
                return jsonResponse.split("\"access_token\":\"")[1].split("\"")[0];
            } else {
                System.out.println("Failed to fetch access token. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to make GET request to Spotify API
    private String getData(String apiUrl, String accessToken) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method
            connection.setRequestMethod("GET");
            
            // Set authorization header
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            
            // Get response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode); // Print response code for debugging
            
            if (responseCode == HttpURLConnection.HTTP_OK) { // Success
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
            
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
                // Print error message if available
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorInputLine;
                StringBuffer errorResponse = new StringBuffer();
                while ((errorInputLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorInputLine);
                }
                errorReader.close();
                System.out.println("Error Response: " + errorResponse.toString()); // Print error response for debugging
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    private String getAudioData(String apiUrl, String accessToken) {

        try {
            // Create URL object
            URL url = new URL(apiUrl);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            // Set Spotify API token in Authorization header if needed
            // connection.setRequestProperty("Authorization", "Bearer YOUR_ACCESS_TOKEN");

            // Get response code
            int responseCode = connection.getResponseCode();

            // Read response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Process the Spotify API response as needed
                System.out.println("Response from Spotify API: " + response.toString());
                return response.toString();
            } else {
                System.out.println("Failed to retrieve audiobook from Spotify. Response code: " + responseCode);
            }

            // Close connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }


}
