package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQ {

    private static final String HOST = "localhost";

    public void enviarMensaje(String cola, String mensaje) {

        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // crear cola si no existe
            channel.queueDeclare(cola, true, false, false, null);

            // enviar mensaje
            channel.basicPublish("", cola, null, mensaje.getBytes());

            System.out.println("Mensaje enviado a cola: " + cola);

            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}