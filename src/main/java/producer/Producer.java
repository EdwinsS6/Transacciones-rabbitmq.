package producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Lote;
import model.Transaccion;
import service.Get;
import service.RabbitMQ;

public class Producer {

    public static void main(String[] args) {

        Get servicioGet = new Get();
        RabbitMQ rabbit = new RabbitMQ();

        try {

            Lote lote = servicioGet.obtenerLote();

            if (lote == null) {
                System.out.println("No se pudo obtener el lote");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();

            for (Transaccion t : lote.getTransacciones()) {

                String json = mapper.writeValueAsString(t);

                String banco = t.getBancoDestino();

                rabbit.enviarMensaje(banco, json);

                System.out.println("Transacción enviada a cola: " + banco);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}