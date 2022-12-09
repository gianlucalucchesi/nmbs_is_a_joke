package com.nmbs_is_a_joke.api.helper;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmbs_is_a_joke.api.model.Liveboard;
import com.nmbs_is_a_joke.api.model.Stations;
import com.nmbs_is_a_joke.api.model.VehicleRetrieval;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Objects.*;

@Component
public class IRailApiHelper {
    final static String host = "api.irail.be";
    final static String scheme = "https";
    ch.qos.logback.classic.Logger log =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.nmbs_is_a_joke");

    public IRailApiHelper() {
        log.setLevel(Level.INFO);
    }

    public Liveboard retrieveLiveboard(String stationId, Calendar calendar, String time) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = "/liveboard";

        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String strDate = dateFormat.format(getDateFromCalendar(calendar));

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(endpoint)
                .addParameter("id", stationId)
                .addParameter("date", strDate)
                .addParameter("time", time) // time is formatted as hhmm.
                .addParameter("arrdep", "departure") // arrivals or departures
                .addParameter("format", "json")
                .addParameter("lang", "en");

        String jsonString = getRequest(uriBuilder);
        return nonNull(jsonString) ? mapper.readValue(jsonString, Liveboard.class) : null;
    }

    public Stations retrieveAllStations() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = "/stations";

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(endpoint)
                .addParameter("format", "json")
                .addParameter("lang", "en");

        String jsonString = getRequest(uriBuilder);
        return nonNull(jsonString) ? mapper.readValue(jsonString, Stations.class) : null;
    }

    public VehicleRetrieval retrieveVehicle(String vehicleId, Calendar calendar) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = "/vehicle";

        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String strDate = dateFormat.format(getDateFromCalendar(calendar));

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(endpoint)
                .addParameter("id", vehicleId)
                .addParameter("date", strDate)
                .addParameter("format", "json")
                .addParameter("lang", "en");

        String jsonString = getRequest(uriBuilder);
        return nonNull(jsonString) ? mapper.readValue(jsonString, VehicleRetrieval.class) : null;
    }

    private String getRequest(URIBuilder uriBuilder) throws IOException {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        URL url = null;

        try {
            url = new URL(uriBuilder.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000); // https://stackoverflow.com/a/2799955/10470183
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while (nonNull((inputLine = reader.readLine()))) {
                    content.append(inputLine);
                }
                return content.toString();
            } else {
                log.info("########## HTTP ERROR {}: {}", httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
            }
        } catch (SocketTimeoutException e) {
            log.info("########## Connection timeout: {}", url);
        } catch (RuntimeException e) {
            log.info("########## RuntimeException: {}", e.toString());
        } catch (IOException e) {
            log.info("########## IOException: {}", e.toString());
        } finally {
            if (nonNull(reader)) {
                reader.close();
            }
            if (nonNull(httpURLConnection)) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    private Date getDateFromCalendar(Calendar calendar) {
        return calendar.getTime(); // Called getTime but returns date
    }

}
