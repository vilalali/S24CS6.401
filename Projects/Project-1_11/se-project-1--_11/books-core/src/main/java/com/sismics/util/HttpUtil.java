package com.sismics.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * HTTP request utilities.
 * 
 * @author jtremeaux
 */
public class HttpUtil {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Loads the content of an URL into a string.
     * 
     * @param url URL to load
     * @return Contents of the resource
     */
    public static String readUrlIntoString(URL url) {
        StringBuilder sb = new StringBuilder();
        // Automatically closes the BufferedReader (auto-closeable resource)
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Error reading URL", e);
            }
        return null;
        }
        return sb.toString();
    }
    
    public static String postUrl(URL url, String data) throws IOException {
        StringBuilder sb = new StringBuilder();
        // Automatically closes the OutputStreamWriter and BufferedReader
        try (OutputStreamWriter wr = new OutputStreamWriter(url.openConnection().getOutputStream());
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            wr.write(data);
            wr.flush();

            // Get the response
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } // Automatic resource management (closing) applies here
        return sb.toString();
    }

}
