import pika
import json
from datetime import datetime, timezone

# Configuración de RabbitMQ
credentials = pika.PlainCredentials('admin', 'password')
connection = pika.BlockingConnection(
    pika.ConnectionParameters('localhost', 5672, '/', credentials)
)
channel = connection.channel()

# Mensaje de log
log_message = {
    "timestamp": datetime.now(timezone.utc).isoformat(),
    "service": "servicio-ejemplo",
    "level": "INFO",
    "message": "Este es un mensaje de prueba 3",
    "details": {
        "usuario": "admin",
        "accion": "login"
    }
}

# Enviar mensaje a la cola
channel.basic_publish(
    exchange='',
    routing_key='log-queue',
    body=json.dumps(log_message)
)

print("Mensaje enviado")
connection.close()