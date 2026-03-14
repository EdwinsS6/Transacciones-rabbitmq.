# Procesamiento de Transacciones Bancarias con RabbitMQ y Java

## Descripción
En este proyecto desarrollo un sistema para procesar transacciones bancarias utilizando Java, Maven y RabbitMQ. El sistema sigue el modelo Producer y Consumer.

Primero obtengo un lote de transacciones desde una API externa. Luego cada transacción se envía a RabbitMQ según el banco destino. Finalmente un Consumer procesa las transacciones y las envía a un endpoint POST donde se almacenan.

## Arquitectura
El sistema funciona con los siguientes componentes:

API de transacciones  
Producer  
RabbitMQ  
Consumer  
API de almacenamiento

El Producer obtiene las transacciones desde la API y las envía a RabbitMQ.  
El Consumer lee las colas de RabbitMQ y envía cada transacción al endpoint POST.

## Estructura del proyecto
El proyecto está organizado en paquetes donde cada clase tiene una responsabilidad específica.

src/main/java

model  
service  
producer  
consumer  

## model
Contiene las clases que representan la estructura del JSON recibido desde la API.

Lote  
Transaccion  
Detalle  
Referencias  

## service
Contiene las clases que interactúan con servicios externos.

Get.java obtiene las transacciones desde la API.  
Post.java envía las transacciones al endpoint POST.  
RabbitMQ.java gestiona la conexión con RabbitMQ.

## producer
El Producer obtiene las transacciones desde la API y las envía a RabbitMQ según el banco destino.

Las colas que se crean corresponden a cada banco, por ejemplo:

BAC  
BANRURAL  
BI  
GYT  

## consumer
El Consumer escucha las colas de RabbitMQ y procesa los mensajes.

Cada transacción se convierte a objeto Java, se agregan los datos del estudiante y se envía al endpoint POST para almacenarla.

## Uso de RabbitMQ
RabbitMQ se utiliza para manejar las colas de mensajes y separar el envío de transacciones del procesamiento.

Cada banco tiene su propia cola y el Consumer procesa los mensajes que llegan a esas colas.

## Confirmación de mensajes
El sistema utiliza confirmación manual de mensajes.  
El Consumer confirma el mensaje solo cuando el POST responde correctamente.

## Ejecución del proyecto

Iniciar RabbitMQ

http://localhost:15672

Usuario  
guest

Contraseña  
guest

Ejecutar Producer.java para enviar las transacciones a RabbitMQ.

Ejecutar Consumer.java para procesar los mensajes y enviarlos al endpoint POST.

## Video demostración
En el siguiente enlace se encuentra el video donde muestro el funcionamiento del sistema.

Link del video  
[PEGAR_AQUI_EL_LINK_DE_DRIVE](https://drive.google.com/file/d/1_OZ0qhWTrXRpTOqBNsjigOhFgynkjtPC/view?usp=sharing)
