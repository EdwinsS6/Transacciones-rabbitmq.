package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Lote;

public class Get {

    private static final String URL = "https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones";

    public Lote obtenerLote() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            Lote lote = mapper.readValue(response.body(), Lote.class);

            return lote;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}