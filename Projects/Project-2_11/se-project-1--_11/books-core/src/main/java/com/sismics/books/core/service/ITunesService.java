package com.sismics.books.core.service;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ITunesService implements AudioServiceProvider {
    private static final String ITUNES_API_URL = "https://itunes.apple.com/search";

    @Override
    public String playAudiobook(String audiobook) {
        try {
            // Create URL object
            String apiUrl = ITUNES_API_URL + "?term=" + audiobook + "&media=audiobook";

            URL url = new URL(apiUrl);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

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

                // Process the iTunes API response as needed
                System.out.println("Response from iTunes API: " + response.toString());

                return response.toString() ;

            } else {
                System.out.println("Failed to retrieve podcasts from iTunes. Response code: " + responseCode);
            }
            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    public String playPodcast(String podcast) {
        try {
            // Create URL object
            String apiUrl = ITUNES_API_URL + "?term=" + podcast + "&media=podcast";
            URL url = new URL(apiUrl);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

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

                // Process the iTunes API response as needed
                System.out.println("Response from iTunes API: " + response.toString());

                return response.toString() ;

            } else {
                System.out.println("Failed to retrieve podcasts from iTunes. Response code: " + responseCode);
            }
            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }
}
