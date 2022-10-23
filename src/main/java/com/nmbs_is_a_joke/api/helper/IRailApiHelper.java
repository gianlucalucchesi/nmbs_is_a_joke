package com.nmbs_is_a_joke.api.helper;

import org.apache.http.client.utils.URIBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class IRailApiHelper {
    final static String host = "api.irail.be";
    final static String scheme = "https";

    public static void retrieveLiveboardForGivenStation(String station, Date date) throws IOException {
        final String endpoint = "/liveboard";

        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String strDate = dateFormat.format(date);

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(endpoint)
                .addParameter("station", station)
                .addParameter("date", strDate);

        String stations = retrieveAllStations();
        String liveboard = getRequest(uriBuilder);
    }

    public static String retrieveAllStations() throws IOException {
        final String endpoint = "/stations";

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(endpoint);

        return getRequest(uriBuilder);
    }

    private static String getRequest(URIBuilder uriBuilder) throws IOException {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(uriBuilder.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }
                return content.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (Objects.nonNull(reader)) {
                reader.close();
            }
            if (Objects.nonNull(httpURLConnection)) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
}
