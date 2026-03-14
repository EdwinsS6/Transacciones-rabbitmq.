package consumer;

import com.rabbitmq.client.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Transaccion;
import service.Post;

public class Consumer {

    private static final String HOST = "localhost";

    public static void main(String[] args) throws Exception {

        String[] colas = {"BAC", "BANRURAL", "BI", "GYT"};

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Post postService = new Post();
        ObjectMapper mapper = new ObjectMapper();

     // Crear cola de rechazados
        channel.queueDeclare("cola_rechazados", true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String mensaje = new String(delivery.getBody());

            try {

                Transaccion transaccion =
                        mapper.readValue(mensaje, Transaccion.class);

                transaccion.setNombre("Edwins Josué Argueta Duarte");
                transaccion.setCarnet("0905-24-6913");

                transaccion.setIdTransaccion(
                        transaccion.getIdTransaccion() + "-Edwins2004"
                );

                // VALIDAR MONTO
                if (transaccion.getMonto() > 4000) {

                    String json = mapper.writeValueAsString(transaccion);

                    // enviar a cola_rechazados
                    channel.basicPublish(
                            "",
                            "cola_rechazados",
                            null,
                            json.getBytes()
                    );

                    System.out.println(
                            "ID: " + transaccion.getIdTransaccion()
                            + " | Monto: " + transaccion.getMonto()
                            + " | Estado: RECHAZADA"
                    );

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                } else {

                    boolean guardado = postService.enviarTransaccion(transaccion);

                    if (guardado) {

                        System.out.println(
                                "ID: " + transaccion.getIdTransaccion()
                                + " | Monto: " + transaccion.getMonto()
                                + " | Estado: ACEPTADA"
                        );

                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                        System.out.println("ACK enviado");
                    }
                }

            } catch (Exception e) {

                System.out.println("Error procesando transacción");
                e.printStackTrace();
            }

        };

        for (String cola : colas) {

            channel.queueDeclare(cola, true, false, false, null);

            channel.basicConsume(
                    cola,
                    false,
                    deliverCallback,
                    consumerTag -> {}
            );

            System.out.println("Escuchando cola: " + cola);
        }
    }
}