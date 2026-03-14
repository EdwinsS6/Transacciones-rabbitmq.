package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Transaccion;

public class Post {

    private static final String URL =
        "https://7e0d9ogwzd.execute-api.us-east-1.amazonaws.com/default/guardarTransacciones";

    public boolean enviarTransaccion(Transaccion transaccion) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(transaccion);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Código respuesta API: " + response.statusCode());
            System.out.println("Respuesta API: " + response.body());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("Transacción guardada correctamente");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}