package com.example.equipmentmanagement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class GeocodingService {

    public static Optional<double[]> getLatLonFromAddress(String address) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?q=" +
                    address.replace(" ", "+") + "&format=json&limit=1";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "JavaApp")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            if (root.isArray() && root.size() > 0) {
                JsonNode location = root.get(0);
                double lat = location.get("lat").asDouble();
                double lon = location.get("lon").asDouble();
                return Optional.of(new double[]{lat, lon});
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Aquí podrías hacer logging o re-lanzar excepción personalizada si quieres
        }

        return Optional.empty();
    }
}
